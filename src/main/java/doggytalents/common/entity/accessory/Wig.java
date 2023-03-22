package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.item.DyeableAccessoryItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class Wig extends DyeableAccessory {

    public Wig(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    public static class WigItem extends DyeableAccessoryItem {

        public WigItem(Supplier<? extends DyeableAccessory> accessoryIn, Properties properties) {
            super(accessoryIn, properties);
        }

        @Override
        public int getDefaultColor(ItemStack stack) {
            //return 0xffffffff;
            return 0xff24211d;
        }

    }
}