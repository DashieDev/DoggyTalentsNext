package doggytalents.common.entity.accessory;

import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.AccessoryItem;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;

public class AngelHalo extends Accessory implements IAccessoryHasModel {

    public AngelHalo(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.ANGEL_HALO;
    }

    public static class AngelHaloItem extends AccessoryItem {

        public AngelHaloItem(Supplier<? extends Accessory> type, Properties properties) {
            super(type, properties);
        }

    }

}
