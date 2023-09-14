package doggytalents;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.*;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class DoggyRegistries {

    protected class Keys {
        public static final ResourceLocation TALENTS_REGISTRY = Util.getResource("talents");
        public static final ResourceLocation ACCESSORIES_REGISTRY = Util.getResource("accessories");
        public static final ResourceLocation ACCESSORY_TYPE_REGISTRY = Util.getResource("accessory_type");
    }

    public static void newRegistry(NewRegistryEvent event) {
        DoggyTalentsAPI.TALENTS = event.create(makeRegistry(Keys.TALENTS_REGISTRY, Talent.class));
        DoggyTalentsAPI.ACCESSORIES = event.create(makeRegistry(Keys.ACCESSORIES_REGISTRY, Accessory.class));
        DoggyTalentsAPI.ACCESSORY_TYPE = event.create(makeRegistry(Keys.ACCESSORY_TYPE_REGISTRY, AccessoryType.class).disableSync());
    }

    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(final ResourceLocation rl, Class<T> type) {
        return new RegistryBuilder<T>().setName(rl).setType(type);
    }
}
