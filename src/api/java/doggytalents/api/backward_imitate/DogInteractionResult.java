package doggytalents.api.backward_imitate;

import net.minecraft.world.InteractionResult;

public enum DogInteractionResult {
    SUCCESS, PASS, FAIL, CONSUME;

    public boolean shouldSwing() {
        return this == SUCCESS;
    }

    public InteractionResult toVanilla() {
        switch (this) {
        case SUCCESS:
            return InteractionResult.SUCCESS;
        case FAIL:
            return InteractionResult.FAIL;
        case CONSUME:
            return InteractionResult.CONSUME;
        default:
            return InteractionResult.PASS;
        }
    }

    public static DogInteractionResult fromVanilla(InteractionResult val) {
        if (val == InteractionResult.SUCCESS)
            return SUCCESS;
        if (val == InteractionResult.FAIL)
            return FAIL;
        if (val == InteractionResult.CONSUME)
            return CONSUME;
        return PASS;
    }
}
