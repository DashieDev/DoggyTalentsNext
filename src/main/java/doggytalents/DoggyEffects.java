package doggytalents;

import doggytalents.common.effects.NattoBiteEffect;
import doggytalents.common.lib.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class DoggyEffects {
    
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);

    public static DeferredHolder<MobEffect, NattoBiteEffect> NATTO_BITE = register("natto_bite", () -> new NattoBiteEffect());

    public static <T extends MobEffect> DeferredHolder<MobEffect ,T> register(String id, Supplier<T> sup) {
        return EFFECTS.register(id, sup);
    }

}
