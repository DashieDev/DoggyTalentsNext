package doggytalents;

import doggytalents.common.effects.NattoBiteEffect;
import doggytalents.common.lib.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DoggyEffects {
    
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);

    public static RegistryObject<MobEffect> NATTO_BITE = register("natto_bite", () -> new NattoBiteEffect());

    public static <T extends MobEffect> RegistryObject<MobEffect> register(String id, Supplier<T> sup) {
        return EFFECTS.register(id, sup);
    }

}
