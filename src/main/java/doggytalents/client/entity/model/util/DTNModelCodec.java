package doggytalents.client.entity.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.ibm.icu.impl.number.DecimalFormatProperties.ParseMode;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.util.ModelAccessUtil.PartAccess;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;

public class DTNModelCodec {
    
    /*
    // All rotations involved in this format are in Degrees.
    {
        "dtn_format_version" : 1.0,
        "texture_size": [0, 0],
        //Optional. The props fields bellow are for Dog Model.
        //Accessories model will have their own props schema later.
        //All Fields in this objects are Optional. The default values are shown below. 
        "props": {
            "root_pivot": [0, 9, 0], 
            "scale": 1.0, 

            "scale_baby": true, 
            "wet_shade": true, 
            "glowing_eyes_legacy": false, 
            
            "accessory_props": {
                "compatibility_state": "have_not_tested", // not_compatible, some_will_fit, have_not_tested, recommended, model_only
                "use_default_model": false
            }
        },
        "parts": [
            {
                "id": "head",
                "pivot": [0, 0, 0], //Optional. Default == Position
                "rotation": [0, 0, 0], //Optional. Default == [0, 0, 0]
                "cubes": [ // Optional. Default == []
                    {
                        "uv": [0, 0],                   
                        "from": [0, 0, 0],
                        "to": [0, 0, 0],
                        "mirror": true, //Optional. Default == false
                        "inflate": 0 //Optional. Default == 0
                    }
                    //...
                ],
                "children": [ //Optional. Default == []
                    {
                        "id": "ear"
                        //...    
                    }
                ],

                
                //Optional. Default == false. Reserved to mark "synthetic parts"
                //generated from Blockbench. Has no effect when parsing in-game. 
                "bb_inline": true
            }
            //...
        ]
    }
    */
    public static final Codec<ParsedModelResult> CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            LocalUtil.VECTOR2I.fieldOf("texture_size")
                .forGetter(ParsedModelResult::texSize),
            parsedPartCodec().listOf().fieldOf("parts")
                .forGetter(ParsedModelResult::parts)
        )
        .apply(builder, ParsedModelResult::of)
    );

    private static Codec<ParsedPart> parsedPartCodec() {
        return Codec.recursive("DTNParsedPart", self -> {
            return RecordCodecBuilder.create(
                builder -> builder.group(
                    Codec.STRING.fieldOf("id")
                        .forGetter(ParsedPart::id),
                    LocalUtil.VECTOR3F.optionalFieldOf("pivot", new Vector3f())
                        .forGetter(ParsedPart::pivot),
                    LocalUtil.VECTOR3F.optionalFieldOf("rotation", new Vector3f())
                        .forGetter(ParsedPart::rotation),
                    parsedCubeCodec().listOf().optionalFieldOf("cubes")
                        .forGetter(wrapOptional(ParsedPart::cubeList)),
                    self.listOf().optionalFieldOf("children")
                        .forGetter(wrapOptional(ParsedPart::children))
                )
                .apply(builder, ParsedPart::of)
            );
        });
    }

    private static Codec<ParsedCube> parsedCubeCodec() {
        return RecordCodecBuilder.create(
            builder -> builder.group(
                LocalUtil.VECTOR2I.fieldOf("uv")
                    .forGetter(ParsedCube::uv),
                LocalUtil.VECTOR3F.fieldOf("from")
                    .forGetter(ParsedCube::from),
                LocalUtil.VECTOR3F.fieldOf("to")
                    .forGetter(ParsedCube::to),
                Codec.BOOL.optionalFieldOf("mirror", false)
                    .forGetter(ParsedCube::mirror),
                Codec.FLOAT.optionalFieldOf("inflate")
                    .forGetter(ParsedCube::inflate)
            )
            .apply(builder, ParsedCube::of)
        );
    }

    public static ParsedModelResult parsedFromLayerDefintion(LayerDefinition layer) {
        final var model = ModelAccessUtil.createAccess(layer);
        
        final var tex_size = model.texSize();
        final var root = model.root();

        final var parts = new ArrayList<ParsedPart>();
        for (var part : root) {
            parts.add(encodePart(part, new Vector3f()));
        }

        return new ParsedModelResult(tex_size.x(), tex_size.y(), parts);
    }

    private static ParsedPart encodePart(PartAccess part, 
        Vector3fc global_offset) {

        final var id = part.id();
        
        final var part_pose = part.partPose();
        final var rotation = (Vector3fc) new Vector3f(
            part_pose.xRot, part_pose.yRot, part_pose.zRot
        ); 
        final var global_pos = new Vector3f(
            part_pose.x, part_pose.y, part_pose.z
        ).add(global_offset);
        
        final var encoded_rotation = vec(rotation);
        COORDINATE_CODEC.encodeRotation(encoded_rotation);
        zeroSanitizeMut(encoded_rotation);
        encoded_rotation.mul(Mth.RAD_TO_DEG);

        final var encoded_pivot = vec(global_pos);
        COORDINATE_CODEC.encodePosition(encoded_pivot, true);
        zeroSanitizeMut(encoded_pivot);

        final var cubes = new ArrayList<ParsedCube>();
        for (var cube : part.cubes()) {
            final var uv = cube.uv();
            final boolean mirror = cube.mirror();
            var cube_args = COORDINATE_CODEC.encodeCubeArgs(
                cube.origin(), cube.dimension(), global_pos);
            final var from = cube_args.getLeft();
            final var to = cube_args.getRight();
            final var inflate = Optional.of(cube.inflate())
                .filter(val -> !Mth.equal(val, 0));
            var encoded_cube = new ParsedCube(uv.x(), uv.y(), 
                from, to, mirror, inflate);
            cubes.add(encoded_cube);
        }

        final var children = new ArrayList<ParsedPart>();
        for (var child : part.children()) {
            var encoded_child = encodePart(child, global_pos);
            children.add(encoded_child);
        }
        return new ParsedPart(id, encoded_pivot, 
            encoded_rotation, cubes, children);
    }

    public static LayerDefinition layerDefinitionFromParsed(ParsedModelResult result) {
        final int tex_x = result.textureX();
        final int tex_y = result.textureY();
        
        var mesh = new MeshDefinition();
        var root = mesh.getRoot();
        
        for (var part : result.parts()) {
            addParsedPartToDefinition(root, part, null);
        }
        return LayerDefinition.create(mesh, tex_x, tex_y);
    }

    private static void addParsedPartToDefinition(PartDefinition targetParent, 
        ParsedPart part, @Nullable ParsedPart parent) {
    
        final var id = part.id();

        final var part_pose = parsePartPose(
            Optional.ofNullable(parent).map(x -> x.pivot()), 
            part.pivot(), part.rotation());

        var cube_list_builder = CubeListBuilder.create();
        for (var parsed_cube: part.cubeList()) {
            parseCubeDefintionAndAddTo(cube_list_builder, parsed_cube, part.pivot());
        }

        final var added_part_def = targetParent.addOrReplaceChild(id, cube_list_builder, part_pose);
        
        for (var child : part.children) {
            addParsedPartToDefinition(added_part_def, child, part);
        }
    }

    private static PartPose parsePartPose(Optional<Vector3fc> parentPivot, 
        Vector3fc pivot, Vector3fc rawRotation) {
        
        final var offset = vec(pivot);
        parentPivot.ifPresent(offset::sub);
        final var rotation = vec(rawRotation)
            .mul(Mth.DEG_TO_RAD);
        
        COORDINATE_CODEC.decodePosition(offset, !parentPivot.isPresent());
        COORDINATE_CODEC.decodeRotation(rotation);
        
        zeroSanitizeMut(offset);
        zeroSanitizeMut(rotation);

        boolean zero_pose = offset.equals(LocalUtil.ZERO_3) && rotation.equals(LocalUtil.ZERO_3);
        return zero_pose ? PartPose.ZERO 
            : PartPose.offsetAndRotation(
                offset.x(), offset.y(), offset.z(), 
                rotation.x(), rotation.y(), rotation.z()
            );
    }

    private static void parseCubeDefintionAndAddTo(CubeListBuilder builder, 
        ParsedCube parsedCube, Vector3fc parentPivot) {
        final boolean mirror = parsedCube.mirror();
        var cube_args = COORDINATE_CODEC.decodeCubeArgs(
            parsedCube.from(), parsedCube.to(), parentPivot);
        final var from = cube_args.getLeft();
        final var dimension = cube_args.getRight();
        
        final var inflate = parsedCube.inflate
            .filter(x -> !Mth.equal(x, 0))
            .map(x -> new CubeDeformation(x))
            .orElse(CubeDeformation.NONE);
        builder
            .mirror(mirror)
            .texOffs(parsedCube.u(), parsedCube.v())
            .addBox(
                from.x(), from.y(), from.z(), 
                dimension.x(), dimension.y(), dimension.z(), 
                inflate
            );
    }

    private static final CoordinateCodec COORDINATE_CODEC = new CoordinateCodec();

    public static class CoordinateCodec {

        public void encodePosition(Vector3f pos, boolean absolute) {
            pos.set(
                -pos.x(), -pos.y() + (absolute ? 24 : 0), pos.z()
            );
        }

        public void decodePosition(Vector3f pos, boolean absolute) {
            pos.set(
                -pos.x(), -pos.y() + (absolute ? 24 : 0), pos.z()
            );
        }

        public void encodeRotation(Vector3f rotation) {
            rotation.set(
                -rotation.x(), -rotation.y(), rotation.z()
            );
        }

        public void decodeRotation(Vector3f rotation) {
            rotation.set(
                -rotation.x(), -rotation.y(), rotation.z()
            );
        }

        public Pair<Vector3f, Vector3f> decodeCubeArgs(Vector3fc from, Vector3fc to, @Nullable Vector3fc parentPivot) {
            var from_mut = vec(from); 
            var to_mut = vec(to);
            var parent_pivot_mut = parentPivot == null ?
                new Vector3f() : vec(parentPivot);

            decodePosition(from_mut, true);
            decodePosition(to_mut, true);
            decodePosition(parent_pivot_mut, true);

            from = from_mut; 
            to = to_mut; 
            parentPivot = parent_pivot_mut;
            
            var min_corner = new Vector3f(
                Math.min(from.x(), to.x()),
                Math.min(from.y(), to.y()),
                Math.min(from.z(), to.z())
            );
            var dimension = new Vector3f(
                Math.abs(from.x() - to.x()),
                Math.abs(from.y() - to.y()),
                Math.abs(from.z() - to.z())
            );
            min_corner.sub(parentPivot);
            return Pair.of(min_corner, dimension);
        }

        public Pair<Vector3f, Vector3f> encodeCubeArgs(Vector3fc minCornerOffset, 
            Vector3fc dimension, @Nullable Vector3fc parentAbsPos) {

            if (parentAbsPos == null)
                parentAbsPos = new Vector3f();

            var from = vec(minCornerOffset).add(parentAbsPos);
            var to = vec(minCornerOffset).add(dimension).add(parentAbsPos);
            this.encodePosition(from, true);
            this.encodePosition(to, true);
            
            return Pair.of(
                new Vector3f(
                    Math.min(from.x(), to.x()),
                    Math.min(from.y(), to.y()),
                    Math.min(from.z(), to.z())
                ),
                new Vector3f(
                    Math.max(from.x(), to.x()),
                    Math.max(from.y(), to.y()),
                    Math.max(from.z(), to.z())
                )
            );
        }
    }

    public static record ParsedModelResult(int textureX, int textureY,
        List<ParsedPart> parts) {
        public static ParsedModelResult of(Vector2i textureSize, List<ParsedPart> parts) {
            return new ParsedModelResult(textureSize.x(), textureSize.y(), 
                parts == null ? List.of() : parts);
        }
        public Vector2i texSize() {
            return new Vector2i(this.textureX(), this.textureY());
        }
    }

    public static record DogModelProps(
        Optional<Vector3f> rootPivot,
        float scale, 
        
        boolean scaleBabyDog,
        boolean wetShade,
        boolean glowingEyes
    ) {

    }

    public static record DogModelAccessoryProps(
        boolean forceDefaultModel,
        DogModel.AccessoryState compatabilityState
    ) {

    }

    public static record ParsedPart(String id, 
        Vector3f pivot, Vector3f rotation,
        List<ParsedCube> cubeList, List<ParsedPart> children
    ) {

        public static ParsedPart of(String id, Vector3f pivot, Vector3f rotation, 
            Optional<List<ParsedCube>> cubeList, 
            Optional<List<ParsedPart>> children
        ) {

            return new ParsedPart(id, pivot, 
                rotation,
                cubeList.orElse(List.of()), children.orElse(List.of()));
        }
    }

    public static record ParsedCube(
        int u, int v, Vector3f from, Vector3f to, 
        boolean mirror, Optional<Float> inflate
    ) {

        public static ParsedCube of(Vector2i uv, Vector3f from, Vector3f to, boolean mirror, Optional<Float> inflate) {
            return new ParsedCube(uv.x(), uv.y(), from, to, mirror, inflate);
        }

        public Vector2i uv() {
            return new Vector2i(u(), v());
        }

    }

    private static class LocalUtil {
        private static final Vector3fc ZERO_3 = new Vector3f();
        private static final Codec<Vector2i> VECTOR2I = Codec.INT
            .listOf()
            .comapFlatMap(
                to_decode -> 
                    net.minecraft.Util.fixedSize(to_decode, 2)
                        .map(val -> new Vector2i(val.get(0), val.get(1))),
                to_encode -> List.of(to_encode.x(), to_encode.y())
            );
        private static final Codec<Vector3f> VECTOR3F = ExtraCodecs.VECTOR3F;
    }

    private static Vector3f vec(Vector3fc vec) {
        return new Vector3f(vec);
    }
    private static void zeroSanitizeMut(Vector3f vec) {
        vec.set(
            Mth.equal(vec.x(), 0) ? 0 : vec.x(),
            Mth.equal(vec.y(), 0) ? 0 : vec.y(),
            Mth.equal(vec.z(), 0) ? 0 : vec.z()
        );
    }
    private static <A, T> Function<A, Optional<T>> wrapOptional(Function<A, T> wrapped) {
        return val -> Optional.of(wrapped.apply(val));
    }
}
