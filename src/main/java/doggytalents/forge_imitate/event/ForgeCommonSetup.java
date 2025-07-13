package doggytalents.forge_imitate.event;

import doggytalents.common.backward_imitate.TicketTypeUtil_1_21_5;
import doggytalents.common.backward_imitate.fabric_util.FabricEventRegisterer_1_21_3;
import doggytalents.common.backward_imitate.fabric_util.FabricEventRegisterer_1_21_5;
import doggytalents.common.backward_imitate.fabric_util.FabricEventRegisterer_1_21_7;
import doggytalents.common.network.DTNNetworkHandler;
import doggytalents.forge_imitate.atrrib.ForgeMod;

public class ForgeCommonSetup {
    
    public static void init() {
        DTNNetworkHandler.init();
        EventHandlerRegisterer.init();
        ForgeMod.init();
        fireAttributeEvent();

        //1_21_3+
        FabricEventRegisterer_1_21_3.init();

        //1_21_5+
        FabricEventRegisterer_1_21_5.init();
        TicketTypeUtil_1_21_5.init();

        //1_21_7+
        FabricEventRegisterer_1_21_7.init();
    }

    public static void fireAttributeEvent() {
        EventCallbacksRegistry.postEvent(new EntityAttributeCreationEvent());
    }

}
