package doggytalents.forge_imitate.atrrib;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;;

public class ForgeMod {
    
    public static DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(() -> BuiltInRegistries.ATTRIBUTE, Constants.MOD_ID);
    public static RegistryObject<Attribute> SWIM_SPEED = ATTRIBUTES.register("forge_swim_speed", () -> new RangedAttribute("forge_imitate.forge_swim_speed", 1, 0, 1024).setSyncable(true));

    public static void init() {
        ATTRIBUTES.initAll();
    }

}
