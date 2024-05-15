package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;

public class DyableLocatorOrb extends DoubleDyableAccessory implements IAccessoryHasModel {
    
    public DyableLocatorOrb(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.BAND, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.DYABLE_LOCATOR_ORB;
    }

}
