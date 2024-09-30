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
        public static final ResourceLocation TALENTS_REGISTRY = Util.getResource("talents");
        public static final ResourceLocation ACCESSORIES_REGISTRY = Util.getResource("accessories");
        public static final ResourceLocation ACCESSORY_TYPE_REGISTRY = Util.getResource("accessory_type");
        public static final ResourceLocation DOG_VARIANT = Util.getResource("dog_variant");
        public static final ResourceLocation TALENT_OPTION = Util.getResource("talent_options");
        // public static final ResourceLocation BEDDING_REGISTRY = Util.getResource("bedding");
        // public static final ResourceLocation CASING_REGISTRY = Util.getResource("casing");
    }

    public static Supplier<Registry<DogVariant>> DOG_VARIANT;

    public static void newRegistry() {
        DoggyTalentsAPI.TALENTS = makeRegistry(Keys.TALENTS_REGISTRY, Talent.class);
        DoggyTalentsAPI.ACCESSORIES = makeRegistry(Keys.ACCESSORIES_REGISTRY, Accessory.class);
        DoggyTalentsAPI.ACCESSORY_TYPE = makeRegistry(Keys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class);
        DoggyTalentsAPI.TALENT_OPTIONS = makeDogSyncRegistry();
        DOG_VARIANT = makeRegistry(Keys.DOG_VARIANT, DogVariant.class, Util.getVanillaResource("pale"));
    }

    private static <T> Supplier<Registry<T>> makeRegistry(final ResourceLocation rl, Class<T> type) {
        var ret =  FabricRegistryBuilder.createSimple(ResourceKey.<T>createRegistryKey(rl))
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
        return () -> ret;
    }

    private static Supplier<Registry<TalentOption<?>>> makeDogSyncRegistry() {
        var ret = FabricRegistryBuilder.createSimple(Keys.TALENT_OPTION)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
        return () -> ret;
    }

    private static <T> ResourceKey<Registry<T>> regKey(String key) {
        var rl = Util.getResource(key);
        return ResourceKey.createRegistryKey(rl);
    }

    private static <T> Supplier<Registry<T>> makeRegistry(
        final ResourceLocation key, Class<T> type, ResourceLocation defaultKey) {
        var ret =  FabricRegistryBuilder.createDefaulted(ResourceKey.<T>createRegistryKey(key), defaultKey)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
        return () -> ret;
    }

    private static Supplier<IForgeRegistry<TalentOption<?>>> makeDogSyncRegistry(NewRegistryEvent event) {
        var builder = new RegistryBuilder<TalentOption<?>>().setName(Keys.TALENT_OPTION);
        //builder.sync(true);
        var ret = event.create(builder);
        return ret;
    }
}
