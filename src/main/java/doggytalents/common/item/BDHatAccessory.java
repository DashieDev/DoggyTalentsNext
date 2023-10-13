package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryType;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;

public class BDHatAccessory extends Accessory implements IAccessoryHasModel {
    private int color = 0;

    public BDHatAccessory(Supplier<? extends ItemLike> itemIn, int color) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
        this.color = color;
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.BD_HAT;
    }
    public int getBDHatColor() {
        return this.color;
    }
}
