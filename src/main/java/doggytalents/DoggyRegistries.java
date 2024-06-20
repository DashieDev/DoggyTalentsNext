package doggytalents;

import java.util.function.Supplier;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.*;
import doggytalents.common.util.Util;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import doggytalents.common.variant.DogVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DoggyRegistries {

    public class Keys {
        public static final ResourceKey<Registry<Talent>> TALENTS_REGISTRY = regKey("talents");
        public static final ResourceKey<Registry<Accessory>> ACCESSORIES_REGISTRY = regKey("accessories");
        public static final ResourceKey<Registry<AccessoryType>> ACCESSORY_TYPE_REGISTRY = regKey("accessory_type");
        public static final ResourceKey<Registry<DogVariant>> DOG_VARIANT = regKey("dog_variant");
        // public static final ResourceKey<Registry<Bed>> BEDDING_REGISTRY = regKey("bedding");
        // public static final ResourceLocation CASING_REGISTRY = regKey("casing");
    }

    public static Supplier<Registry<DogVariant>> DOG_VARIANT;

    public static void newRegistry(NewRegistryEvent event) {
        DoggyTalentsAPI.TALENTS = makeRegistry(event, Keys.TALENTS_REGISTRY, Talent.class);
        DoggyTalentsAPI.ACCESSORIES = makeRegistry(event, Keys.ACCESSORIES_REGISTRY, Accessory.class);
        DoggyTalentsAPI.ACCESSORY_TYPE = makeRegistry(event, Keys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class, true);
        DOG_VARIANT = makeRegistry(event, Keys.DOG_VARIANT, DogVariant.class, Util.getVanillaResource("pale"));
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

    private static <T> Supplier<Registry<T>> makeRegistry(NewRegistryEvent event, 
        final ResourceKey<Registry<T>> key, Class<T> type, ResourceLocation defaultKey) {
        var builder = new RegistryBuilder<T>(key);
        builder.sync(true);
        builder.defaultKey(defaultKey);
        var ret = event.create(builder);
        return () -> ret;
    }
}
