package doggytalents;

import java.util.function.Supplier;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.*;
import doggytalents.common.util.Util;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DoggyRegistries {

    public class Keys {
        public static final ResourceKey<Registry<Talent>> TALENTS_REGISTRY = regKey("talents");
        public static final ResourceKey<Registry<Accessory>> ACCESSORIES_REGISTRY = regKey("accessories");
        public static final ResourceKey<Registry<AccessoryType>> ACCESSORY_TYPE_REGISTRY = regKey("accessory_type");
        // public static final ResourceKey<Registry<Bed>> BEDDING_REGISTRY = regKey("bedding");
        // public static final ResourceLocation CASING_REGISTRY = regKey("casing");
    }

    public static void newRegistry() {
        DoggyTalentsAPI.TALENTS = (makeRegistry(Keys.TALENTS_REGISTRY, Talent.class));
        DoggyTalentsAPI.ACCESSORIES = (makeRegistry(Keys.ACCESSORIES_REGISTRY, Accessory.class));
        DoggyTalentsAPI.ACCESSORY_TYPE = (makeRegistry(Keys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class));//.disableSync());
    }

    private static <T> Supplier<Registry<T>> makeRegistry(final ResourceKey<Registry<T>> key, Class<T> type) {
        var ret =  FabricRegistryBuilder.createSimple(key)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
        return () -> ret;
    }

    private static <T> ResourceKey<Registry<T>> regKey(String key) {
        var rl = Util.getResource(key);
        return ResourceKey.createRegistryKey(rl);
    }
}
