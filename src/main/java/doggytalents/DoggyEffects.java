package doggytalents;

import doggytalents.common.effects.NattoBiteEffect;
import doggytalents.common.lib.Constants;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;

public class DoggyEffects {
    
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.Keys.MOB_EFFECTS, Constants.MOD_ID);

    public static RegistryObject<NattoBiteEffect> NATTO_BITE = register("natto_bite", () -> new NattoBiteEffect());

    public static <T extends MobEffect> RegistryObject<T> register(String id, Supplier<T> sup) {
        return EFFECTS.register(id, sup);
    }

}
