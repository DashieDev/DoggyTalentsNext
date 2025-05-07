package doggytalents.common.backward_imitate.fabric_util;

import doggytalents.client.backward_imitate.DoubleDyableTint_1_21_5;
import doggytalents.client.backward_imitate.fabric_util.RegisterColorHandlersEvent_1_21_5;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;

public class FabricEventRegisterer_1_21_5 {
    
    public static void init() {
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<RegisterColorHandlersEvent_1_21_5.ItemTintSources>(
                RegisterColorHandlersEvent_1_21_5.ItemTintSources.class,
                DoubleDyableTint_1_21_5::registerTints
            )
        );
    }
    
}
