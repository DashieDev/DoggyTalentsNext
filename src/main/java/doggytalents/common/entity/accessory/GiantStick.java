package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryType;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;

public class GiantStick extends Clothing implements IAccessoryHasModel{

    public GiantStick(Supplier<? extends ItemLike> itemIn) {
        super(itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.GIANT_STICK;
    }
    
}
