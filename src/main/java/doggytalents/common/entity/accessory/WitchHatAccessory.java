package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;

public class WitchHatAccessory extends Accessory implements IAccessoryHasModel{
    public WitchHatAccessory(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.WITCH_HAT;
    }
}
