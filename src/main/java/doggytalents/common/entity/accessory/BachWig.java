package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.AccessoryItem;
import doggytalents.common.item.DyeableAccessoryItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class BachWig extends Accessory implements IAccessoryHasModel {

    public BachWig(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    public static class BachWigItem extends AccessoryItem {

        public BachWigItem(Supplier<? extends DyeableAccessory> accessoryIn, Properties properties) {
            super(accessoryIn, properties);
        }

    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.BACH_WIG;
    }
}
