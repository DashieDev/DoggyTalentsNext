package doggytalents.api.inferface;

import net.minecraft.world.InteractionResult;

/**
 * Replacement for the removed vanilla InteractionResultHolder, used in IDogAlteration callbacks
 * to return both an interaction result and a value.
 */
public final class DTNInteractionResultHolder<T> {
    private final InteractionResult result;
    private final T object;

    public DTNInteractionResultHolder(InteractionResult result, T object) {
        this.result = result;
        this.object = object;
    }

    public static <T> DTNInteractionResultHolder<T> pass(T object) {
        return new DTNInteractionResultHolder<>(InteractionResult.PASS, object);
    }

    public static <T> DTNInteractionResultHolder<T> success(T object) {
        return new DTNInteractionResultHolder<>(InteractionResult.SUCCESS, object);
    }

    public static <T> DTNInteractionResultHolder<T> fail(T object) {
        return new DTNInteractionResultHolder<>(InteractionResult.FAIL, object);
    }

    public T getObject() {
        return this.object;
    }

    public InteractionResult getResult() {
        return this.result;
    }

    public boolean isPass() {
        return this.result == InteractionResult.PASS;
    }

    public boolean isSuccess() {
        return this.result instanceof InteractionResult.Success;
    }

    public boolean isFail() {
        return this.result instanceof InteractionResult.Fail;
    }
}
