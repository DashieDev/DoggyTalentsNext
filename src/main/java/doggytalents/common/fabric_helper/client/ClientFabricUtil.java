package doggytalents.common.fabric_helper.client;

import doggytalents.common.entity.Dog;

public class ClientFabricUtil {
    
    public static boolean isNameplateInRenderDistance(Dog dog, double d0) {
        return d0 < 64 * 64;
    }

}
