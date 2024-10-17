package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;

public class StripedScarf extends DoubleDyeableAccessory implements IAccessoryHasModel {

    public StripedScarf(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.SCARF, itemIn);
    }
 
    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.STRIPED_SCARF;
    }

}
