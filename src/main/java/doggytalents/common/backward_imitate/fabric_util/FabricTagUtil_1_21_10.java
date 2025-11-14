package doggytalents.common.backward_imitate.fabric_util;

import doggytalents.fabric_mixin.CustomDataAccessorMixin_1_21_10;
import net.minecraft.world.item.component.CustomData;

public class FabricTagUtil_1_21_10 {
    
    public static boolean containsEntry(CustomData data, String entry) {
        return ((CustomDataAccessorMixin_1_21_10)(Object) data).dtn__getInnerTag().contains(entry);
    }

}
