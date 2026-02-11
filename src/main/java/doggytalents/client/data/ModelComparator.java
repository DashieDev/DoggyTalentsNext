package doggytalents.client.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joml.Vector3fc;

import doggytalents.client.entity.model.util.ModelAccessUtil;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ModelComparator {

    private static final float EPSILON = 0.0001f;

    public static void verifyModelsMatch(LayerDefinition expected, LayerDefinition actual) {
        var expected_access = ModelAccessUtil.createAccess(expected);
        var actual_access = ModelAccessUtil.createAccess(actual);

        compareInts("Texture Width", expected_access.texSize().x, actual_access.texSize().x);
        compareInts("Texture Height", expected_access.texSize().y, actual_access.texSize().y);

        // 2. Compare Root Parts
        // Note: Blockbench export usually puts everything under "root" or similar.
        // We compare the lists of top-level parts.
        comparePartLists("Root", expected_access.root(), actual_access.root());
    }

    private static void comparePartLists(String path, List<ModelAccessUtil.PartAccess> expParts, List<ModelAccessUtil.PartAccess> actParts) {
        if (expParts.size() != actParts.size()) {
            throw new AssertionError(String.format("Path [%s]: Part count mismatch. Expected %d, got %d.", 
                path, expParts.size(), actParts.size()));
        }

        // Map for easy lookup by ID (Handling order differences)
        // Do orderless compare here.
        var expected_map = expParts.stream()
            .collect(Collectors.toMap(ModelAccessUtil.PartAccess::id, p -> p));
        var actual_map = actParts.stream()
            .collect(Collectors.toMap(ModelAccessUtil.PartAccess::id, p -> p));

        for (var id : expected_map.keySet()) {
            if (!actual_map.containsKey(id)) {
                throw new AssertionError(String.format("Path [%s]: Missing part '%s' in Actual model.", path, id));
            }
            compareParts(path + " -> " + id, expected_map.get(id), actual_map.get(id));
        }
    }

    private static void compareParts(String path, ModelAccessUtil.PartAccess exp, ModelAccessUtil.PartAccess act) {
        var expected_pose = exp.partPose();
        var actual_pose = act.partPose();

        compareFloats(path + ".x", expected_pose.x, actual_pose.x);
        compareFloats(path + ".y", expected_pose.y, actual_pose.y);
        compareFloats(path + ".z", expected_pose.z, actual_pose.z);
        compareFloats(path + ".xRot", expected_pose.xRot, actual_pose.xRot);
        compareFloats(path + ".yRot", expected_pose.yRot, actual_pose.yRot);
        compareFloats(path + ".zRot", expected_pose.zRot, actual_pose.zRot);

        // 2. Compare Cubes
        // Do orderless compare here.
        var expected_cubes = exp.cubes();
        var actual_cubes = act.cubes();
        
        if (expected_cubes.size() != actual_cubes.size()) {
            throw new AssertionError(String.format("Path [%s]: Cube count mismatch. Expected %d, got %d.", 
                path, expected_cubes.size(), actual_cubes.size()));
        }

        for (int i = 0; i < expected_cubes.size(); i++) {
            compareCubes(path + ".cube[" + i + "]", expected_cubes.get(i), actual_cubes.get(i));
        }

        // 3. Recursion
        comparePartLists(path, exp.children(), act.children());
    }

    private static void compareCubes(String path, ModelAccessUtil.CubeAccess exp, ModelAccessUtil.CubeAccess act) {
        // Origin
        compareVectors(path + ".origin", exp.origin(), act.origin());
        
        // Dimension
        compareVectors(path + ".dimension", exp.dimension(), act.dimension());
        
        // UVs
        if (exp.uv().x != act.uv().x || exp.uv().y != act.uv().y) {
            throw new AssertionError(String.format("Path [%s]: UV mismatch. Expected [%d, %d], got [%d, %d]",
                path, exp.uv().x, exp.uv().y, act.uv().x, act.uv().y));
        }

        // Properties
        if (exp.mirror() != act.mirror()) {
             throw new AssertionError(path + ": Mirror mismatch.");
        }
        compareFloats(path + ".inflate", exp.inflate(), act.inflate());
    }

    // --- Helpers ---

    private static void compareVectors(String context, Vector3fc v1, Vector3fc v2) {
        compareFloats(context + ".x", v1.x(), v2.x());
        compareFloats(context + ".y", v1.y(), v2.y());
        compareFloats(context + ".z", v1.z(), v2.z());
    }

    private static void compareFloats(String context, float f1, float f2) {
        if (Math.abs(f1 - f2) > EPSILON) {
            throw new AssertionError(String.format("%s mismatch. Expected %f, got %f (Diff: %f)", 
                context, f1, f2, Math.abs(f1 - f2)));
        }
    }

    private static void compareInts(String context, int i1, int i2) {
        if (i1 != i2) {
            throw new AssertionError(String.format("%s mismatch. Expected %d, got %d", context, i1, i2));
        }
    }
}
