package doggytalents;

import java.util.function.Supplier;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.TalentOption;
import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.registry.DeferredRegister;

public class TalentsOptions {
    
    public static final DeferredRegister<TalentOption<?>> TALENT_OPTIONS = DeferredRegister.create(() -> DoggyTalentsAPI.TALENT_OPTIONS.get(), Constants.MOD_ID);
    
    public static final Supplier<TalentOption<Boolean>> DOGGY_TORCH_ENABLE = registerBool("doggy_torch_0");
    public static final Supplier<TalentOption<Boolean>> GATE_PASSER_ENABLE = registerBool("gate_passer_0");
    public static final Supplier<TalentOption<Boolean>> DOGGY_TORCH_RENDER = registerBool("doggy_torch_1");
    public static final Supplier<TalentOption<Boolean>> DOGGY_TOOLS_EXC = registerBool("doggy_tools_0");
    public static final Supplier<TalentOption<Boolean>> CREEPER_SWEEPER_EXC = registerBool("creeper_sweeper_0");
    public static final Supplier<TalentOption<Boolean>> RESCUE_DOG_RENDER = registerBool("rescue_dog_0");
    public static final Supplier<TalentOption<Boolean>> FISHER_DOG_RENDER = registerBool("fisher_dog_0");
    public static final Supplier<TalentOption<Boolean>> PACK_PUPPY_RENDER = registerBool("pack_puppy_0");
    public static final Supplier<TalentOption<Boolean>> PACK_PUPPY_PICKUP = registerBool("pack_puppy_1");
    public static final Supplier<TalentOption<Boolean>> PACK_PUPPY_FOOD = registerBool("pack_puppy_2");
    public static final Supplier<TalentOption<Boolean>> PACK_PUPPY_LOOT = registerBool("pack_puppy_3");

    private static Supplier<TalentOption<Boolean>> registerBool(String name) {
        return register(name, () -> new TalentOption.BooleanOption());
    }

    private static <T> Supplier<TalentOption<T>> register(final String name, final Supplier<TalentOption<T>> sup) {
        final var captured_val = sup.get();
        return TALENT_OPTIONS.register(name, () -> captured_val);
    }

}
