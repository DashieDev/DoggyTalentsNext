package doggytalents.client.entity.model.util;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

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
    {
        "dtn_format_version" : 1.0,
        "texture_size": [0, 0],
        "parts": [
            {
                "id": "head",
                "position": [0, 0, 0], //Optional. Default == [0, 0, 0]
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

    private static Codec<ParsedCube> parsedCubeCodec() {
        return RecordCodecBuilder.create(
            builder -> builder.group(
                LocalUtil.VECTOR2I.fieldOf("uv")
                    .forGetter(ParsedCube::uv),
                ExtraCodecs.VECTOR3F.fieldOf("from")
                    .forGetter(ParsedCube::from),
                ExtraCodecs.VECTOR3F.fieldOf("to")
                    .forGetter(ParsedCube::to),
                Codec.BOOL.optionalFieldOf("mirror", false)
                    .forGetter(ParsedCube::mirror),
                Codec.FLOAT.optionalFieldOf("inflate")
                    .forGetter(ParsedCube::inflate)
            )
            .apply(builder, ParsedCube::of)
        );
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
        public ParsedModelResult of(Vector2i textureSize, List<ParsedPart> parts) {
            return new ParsedModelResult(textureSize.x(), textureSize.y(), 
                parts == null ? List.of() : parts);
        }
    }

    public static record ParsedPart(String id, 
        Vector3f position, Vector3f rotation, Vector3f pivot, 
        List<ParsedCube> cubeList, List<ParsedPart> children
    ) {
        public static ParsedPart of(String id, Optional<Vector3f> positionOptional, Optional<Vector3f> rotation,
            Optional<Vector3f> pivotOptional, Optional<List<ParsedCube>> cubeList, 
            Optional<List<ParsedPart>> children
        ) {
            var position = positionOptional.orElse(new Vector3f());
            var pivot = pivotOptional.orElse(position);

            return new ParsedPart(id, position, 
                rotation.orElse(new Vector3f()), pivot, 
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
}
