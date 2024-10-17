package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;

public class DyeableLocatorOrb extends DoubleDyeableAccessory implements IAccessoryHasModel {
    
    public DyeableLocatorOrb(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.SCARF, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.DYEABLE_LOCATOR_ORB;
    }

}
