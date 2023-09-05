package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;

public class LocatorOrbAccessory extends Accessory implements IAccessoryHasModel {

    private int color = 0;

    public LocatorOrbAccessory(Supplier<? extends ItemLike> itemIn, int color) {
        super(DoggyAccessoryTypes.BAND, itemIn);
        this.color = color;
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.LOCATOR_ORB;
    }

    public int getOrbColor() {
        return this.color;
    }
    
}
