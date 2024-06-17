package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.DyeableAccessoryItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class AngelWings extends DyeableAccessory implements IAccessoryHasModel{

    public AngelWings(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.WINGS, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.ANGEL_WINGS;
    }
    
    
    public static class Item extends DyeableAccessoryItem {

        public Item(Supplier<? extends DyeableAccessory> accessoryIn, Properties properties) {
            super(accessoryIn, properties);
        }
        @Override
        public int getDefaultColor(ItemStack stack) {
            return 0xffffffff;
        }

    }
    
}
