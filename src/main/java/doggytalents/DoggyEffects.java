package doggytalents;

import doggytalents.common.effects.NattoBiteEffect;
import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import java.util.function.Supplier;

public class DoggyEffects {
    
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(() -> BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);

    public static RegistryObject<MobEffect> NATTO_BITE = register("natto_bite", () -> new NattoBiteEffect());

    public static <T extends MobEffect> RegistryObject<T> register(String id, Supplier<T> sup) {
        return EFFECTS.register(id, sup);
    }

}
