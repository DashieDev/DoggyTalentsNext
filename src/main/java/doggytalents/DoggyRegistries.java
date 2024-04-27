package doggytalents;

import java.util.function.Supplier;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.*;
import doggytalents.common.util.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class DoggyRegistries {

    public class Keys {
        public static final ResourceKey<Registry<Talent>> TALENTS_REGISTRY = regKey("talents");
        public static final ResourceKey<Registry<Accessory>> ACCESSORIES_REGISTRY = regKey("accessories");
        public static final ResourceKey<Registry<AccessoryType>> ACCESSORY_TYPE_REGISTRY = regKey("accessory_type");
        // public static final ResourceKey<Registry<Bed>> BEDDING_REGISTRY = regKey("bedding");
        // public static final ResourceLocation CASING_REGISTRY = regKey("casing");
    }

    public static void newRegistry(NewRegistryEvent event) {
        DoggyTalentsAPI.TALENTS = makeRegistry(event, Keys.TALENTS_REGISTRY, Talent.class);
        DoggyTalentsAPI.ACCESSORIES = makeRegistry(event, Keys.ACCESSORIES_REGISTRY, Accessory.class);
        DoggyTalentsAPI.ACCESSORY_TYPE = makeRegistry(event, Keys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class, true);
    }

    private static <T> Supplier<Registry<T>> makeRegistry(NewRegistryEvent event, 
        final ResourceKey<Registry<T>> key, Class<T> type, boolean disableSync) {
        var builder = new RegistryBuilder<T>(key);
        builder.sync(!disableSync);
        var ret = event.create(builder);
        return () -> ret;
    }

    private static <T> Supplier<Registry<T>> makeRegistry(NewRegistryEvent event,
        final ResourceKey<Registry<T>> key, Class<T> type) {
        return makeRegistry(event, key, type, false);
    }

    private static <T> ResourceKey<Registry<T>> regKey(String key) {
        var rl = Util.getResource(key);
        return ResourceKey.createRegistryKey(rl);
    }
}
