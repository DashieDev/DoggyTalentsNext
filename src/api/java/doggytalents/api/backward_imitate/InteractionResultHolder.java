package doggytalents.api.backward_imitate;

import net.minecraft.world.InteractionResult;

public class InteractionResultHolder<T> {
    
    private final DogInteractionResult result;
    private final T val;

    private InteractionResultHolder(DogInteractionResult result, T val) {
        this.val = val;
        this.result = result;
    }

    public InteractionResultHolder(InteractionResult result, T val) {
        this.result = DogInteractionResult.fromVanilla(result);
        this.val = val;
    }

    public DogInteractionResult getResult() {
        return this.result;
    }

    public InteractionResult getResultVanilla() {
        return this.result.toVanilla();
    }

    public T getObject() {
        return this.val;
    }

    public static <T> InteractionResultHolder<T> success(T val) {
        return new InteractionResultHolder<T>(DogInteractionResult.SUCCESS, val);
    }
    
    public static <T> InteractionResultHolder<T> pass(T val) {
        return new InteractionResultHolder<T>(DogInteractionResult.PASS, val);
    }

    public static <T> InteractionResultHolder<T> fail(T val) {
        return new InteractionResultHolder<T>(DogInteractionResult.FAIL, val);
    }

}
