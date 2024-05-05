package doggytalents.forge_imitate.event;

import doggytalents.common.network.DTNNetworkHandler;
import doggytalents.forge_imitate.atrrib.ForgeMod;

public class ForgeCommonSetup {
    
    public static void init() {
        DTNNetworkHandler.init();
        EventHandlerRegisterer.init();
        ForgeMod.init();
        fireAttributeEvent();
    }

    public static void fireAttributeEvent() {
        EventCallbacksRegistry.postEvent(new EntityAttributeCreationEvent());
    }

}
