package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.api.inferface.IAccessoryHasModel;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import net.minecraft.world.level.ItemLike;

public class SmartyGlasses extends Glasses implements IAccessoryHasModel {

    public SmartyGlasses(Supplier<? extends ItemLike> itemIn) {
        super(itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.SMARTY_GLASSES;
    }
    
}
