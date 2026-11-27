package doggytalents.common.fabric_helper.client;

import com.mojang.blaze3d.platform.InputConstants;

import doggytalents.common.entity.Dog;
import doggytalents.fabric_mixin.KeyMappingMixinAccessor;
import net.minecraft.client.KeyMapping;

public class ClientFabricUtil {
    
    public static boolean isNameplateInRenderDistance(Dog dog, double d0) {
        return d0 < 64 * 64;
    }

    public static InputConstants.Key getKey(KeyMapping mapping) {
        return ((KeyMappingMixinAccessor)(Object) mapping).dtn__getKey();
    }

}
