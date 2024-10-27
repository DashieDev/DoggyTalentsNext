package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.AccessoryItem;
import net.minecraft.world.level.ItemLike;

public class FireFighterHat extends Accessory implements IAccessoryHasModel {
    
    public FireFighterHat(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public byte getRenderLayer() {
        return AccessoryInstance.RENDER_TOP;
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.FIREFIGHTER_HAT;
    }

    public static class FireFighterHatItem extends AccessoryItem {

        public FireFighterHatItem(Supplier<? extends Accessory> accessoryIn, Properties properties) {
            super(accessoryIn, properties);
        }

    }

}
