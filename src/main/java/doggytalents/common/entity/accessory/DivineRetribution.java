package doggytalents.common.entity.accessory;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.registry.Accessory;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import net.minecraft.world.level.ItemLike;
import java.util.function.Supplier;

public class DivineRetribution extends Accessory implements IAccessoryHasModel {
    
    public DivineRetribution(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.WINGS, itemIn);
        this.setAccessoryRenderType(AccessoryRenderType.MODEL);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.DIVINE_RETRIB;
    }

}
