package doggytalents;

import java.util.function.Supplier;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.*;
import doggytalents.common.util.Util;
import doggytalents.common.variant.DogVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

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

    public static Supplier<IForgeRegistry<DogVariant>> DOG_VARIANT;

    public static void newRegistry(NewRegistryEvent event) {
        DoggyTalentsAPI.TALENTS = event.create(makeRegistry(Keys.TALENTS_REGISTRY, Talent.class));
        DoggyTalentsAPI.ACCESSORIES = event.create(makeRegistry(Keys.ACCESSORIES_REGISTRY, Accessory.class));
        DoggyTalentsAPI.ACCESSORY_TYPE = event.create(makeRegistry(Keys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class).disableSync());
        DoggyTalentsAPI.TALENT_OPTIONS = makeDogSyncRegistry(event);
        DOG_VARIANT = makeRegistry(event, Keys.DOG_VARIANT, DogVariant.class, Util.getVanillaResource("pale"));
    }

    private static <T> RegistryBuilder<T> makeRegistry(final ResourceLocation rl, Class<T> type) {
        return new RegistryBuilder<T>().setName(rl);
    }

    private static <T> Supplier<IForgeRegistry<T>> makeRegistry(NewRegistryEvent event, 
        final ResourceLocation key, Class<T> type, ResourceLocation defaultKey) {
        var builder = new RegistryBuilder<T>().setName(key);
        //builder.sync(true);
        builder.setDefaultKey(defaultKey);
        var ret = event.create(builder);
        return ret;
    }

    private static Supplier<IForgeRegistry<TalentOption<?>>> makeDogSyncRegistry(NewRegistryEvent event) {
        var builder = new RegistryBuilder<TalentOption<?>>().setName(Keys.TALENT_OPTION);
        //builder.sync(true);
        var ret = event.create(builder);
        return ret;
    }
}
