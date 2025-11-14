package doggytalents.common.backward_imitate.fabric_util;

import java.util.function.Consumer;

import doggytalents.client.backward_imitate.PlayerRenderUtil_1_21_9;
import doggytalents.client.backward_imitate.fabric_util.RenderPlayerEvent_21_3;
import doggytalents.forge_imitate.event.Event;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;

public class FabricEventRegisterer_1_21_9 {
    
    public static void init() {
        PlayerRenderUtil_1_21_9.registerEvents();
    }

    public static <T extends Event> void registerSingleEvent(Class<T> eventClass, Consumer<T> event) {
        EventCallbacksRegistry.registerCallback(new SingleEventCallBack<T>(eventClass, event));
    }

}
