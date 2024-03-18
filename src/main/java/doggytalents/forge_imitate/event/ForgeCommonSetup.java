package doggytalents.forge_imitate.event;

import doggytalents.forge_imitate.atrrib.ForgeMod;
import doggytalents.forge_imitate.network.ForgeNetworkHandler;

public class ForgeCommonSetup {
    
    public static void init() {
        ForgeNetworkHandler.init();
        EventHandlerRegisterer.init();
        ForgeMod.init();
        fireAttributeEvent();
    }

    public static void fireAttributeEvent() {
        EventCallbacksRegistry.postEvent(new EntityAttributeCreationEvent());
    }

}
