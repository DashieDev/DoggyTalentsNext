package doggytalents.api.backward_imitate;

import doggytalents.DoggyBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class DogBedItemProps_1_21_5 {
    
    public static ResourceLocation dogBedModelLocation() {
        return BuiltInRegistries.BLOCK.getKey(DoggyBlocks.DOG_BED.get());
    }

    public static Item.Properties props(Item.Properties props) {
        return props.component(DataComponents.ITEM_MODEL, dogBedModelLocation());
    }

}
