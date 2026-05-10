package doggytalents.client;

import net.neoforged.neoforge.client.IArmPoseTransformer;

public class PettingArmPoseParams {

    public static Object declareParameter(int param_index, Class<?> param_type) {
        switch (param_index) {
        case 0: return Boolean.FALSE; // twoHanded
        case 1: return Boolean.FALSE; // affectsOffhandPose
        case 2:
            IArmPoseTransformer transformer = (model, renderState, arm) -> {
                PettingArmPose.applyTransform(model, renderState, arm);
            };
            return transformer;
        default: throw new IllegalStateException("Undefined param_index: " + param_index);
        }
    }

}
