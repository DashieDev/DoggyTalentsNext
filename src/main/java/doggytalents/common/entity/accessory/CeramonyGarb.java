package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.AccessoryType;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.DyeableAccessoryItem;
import net.minecraft.world.level.ItemLike;

public class CeramonyGarb extends DyeableAccessory implements IAccessoryHasModel{

    public CeramonyGarb(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.CLOTHING, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.CERA_GARB;
    }
    public static class CeramonyGarbItem extends DyeableAccessoryItem {

        public CeramonyGarbItem(Supplier<? extends DyeableAccessory> accessoryIn, Properties properties) {
            super(accessoryIn, properties);
        }
    }
    
}
