package doggytalents.forge_imitate.event;

import doggytalents.DoggyTalentsNextEntry;
import doggytalents.common.network.DTNNetworkHandler;
import doggytalents.forge_imitate.atrrib.ForgeMod;

public class ForgeCommonSetup {
    
    public static void init() {
        DTNNetworkHandler.init();
        EventHandlerRegisterer.init();
        DoggyTalentsNextEntry.MOD_BUS.finishRegister();
        FabricTempEventFinishRegisterFix.onFinish();
        ForgeMod.init();
        fireAttributeEvent();
    }

    public static void fireAttributeEvent() {
        DoggyTalentsNextEntry.MOD_BUS.post(new EntityAttributeCreationEvent());
    }

}
