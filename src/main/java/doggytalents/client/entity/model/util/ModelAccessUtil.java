package doggytalents.client.entity.model.util;

import java.util.List;

import org.joml.Vector2i;
import org.joml.Vector3fc;

import doggytalents.mixin.CubeDefinitionMixinAccessor;
import doggytalents.mixin.CubeDeformationMixinAccessor;
import doggytalents.mixin.LayerDefinitionMixinAccessor;
import doggytalents.mixin.MaterialDefinitionMixinAccessor;
import doggytalents.mixin.PartDefinitionMixinAccessor;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelAccessUtil {
    
    public static interface LayerDefinitionAccess {
        Vector2i texSize();
        List<PartAccess> root();
    }

    public static interface PartAccess {
        String id();
        PartPose partPose();
        List<CubeAccess> cubes();
        List<PartAccess> children();
    }

    public static interface CubeAccess {
        Vector3fc origin();
        Vector3fc dimension();
        float inflate();
        boolean mirror();
        Vector2i uv();
    }

    public static LayerDefinitionAccess createAccess(LayerDefinition layer) {
        return new LayerDefinitionAccessImpl(layer);
    }

    private static record LayerDefinitionAccessImpl(LayerDefinition layer) implements LayerDefinitionAccess {
    
        @Override
        public Vector2i texSize() {
            var material = wrapMaterial(wrapLayer(layer()).dtn__material());
            return new Vector2i(
                material.dtn__xTexSize(),
                material.dtn__yTexSize()
            );
        }

        @Override
        public List<PartAccess> root() {
            var root = wrapLayer(layer()).dtn__mesh().getRoot();
            return new PartAccessImpl(root, "").children();
        }
    }

    private static record PartAccessImpl(PartDefinition part, String id) implements PartAccess {

        @Override
        public PartPose partPose() {
            return wrapPart(part).dtn__partPose();
        }

        @Override
        public List<CubeAccess> cubes() {
            return wrapPart(part).dtn__cubes().stream()
                .map(ModelAccessUtil::getCubeAccess)
                .toList();
        }

        @Override
        public List<PartAccess> children() {
            return wrapPart(part).dtn__children()
                .entrySet().stream()
                .map(entry -> (PartAccess) new PartAccessImpl(entry.getValue(), entry.getKey()))
                .toList();
        }
    }

    private static CubeAccess getCubeAccess(CubeDefinition cube) {
        var grow = wrapCube(cube).dtn__grow();
        if (grow == CubeDeformation.NONE)
            return new CubeAccessImpl(cube, 0);
        float inflate = wrapGrow(grow).dtn__growX();
        boolean is_uniform = 
            wrapGrow(grow).dtn__growY() == inflate
            && wrapGrow(grow).dtn__growZ() == inflate;
        if (!is_uniform)
            throw new IllegalArgumentException("Multi-dimensional grow is not supported yet.");
        return new CubeAccessImpl(cube, inflate);
    }

    private static record CubeAccessImpl(CubeDefinition cube, float inflate) 
        implements CubeAccess {

        @Override
        public Vector3fc origin() {
            return wrapCube(cube()).dtn__origin();
        }

        @Override
        public Vector3fc dimension() {
            return wrapCube(cube()).dtn__dimensions();
        }

        @Override
        public boolean mirror() {
            return wrapCube(cube()).dtn__mirror();
        }

        @Override
        public Vector2i uv() {
            var uv_pair = wrapCube(cube()).dtn__texCoord();
            return new Vector2i((int) uv_pair.u(), (int) uv_pair.v());
        }
    }

    private static LayerDefinitionMixinAccessor wrapLayer(LayerDefinition layer) {
        return (LayerDefinitionMixinAccessor)(Object) layer;
    }

    private static MaterialDefinitionMixinAccessor wrapMaterial(MaterialDefinition material) {
        return (MaterialDefinitionMixinAccessor)(Object) material;
    }

    private static PartDefinitionMixinAccessor wrapPart(PartDefinition part) {
        return (PartDefinitionMixinAccessor)(Object) part;
    }

    private static CubeDefinitionMixinAccessor wrapCube(CubeDefinition cube) {
        return (CubeDefinitionMixinAccessor)(Object) cube;
    }

    private static CubeDeformationMixinAccessor wrapGrow(CubeDeformation grow) {
        return (CubeDeformationMixinAccessor)(Object) grow;
    }
}
