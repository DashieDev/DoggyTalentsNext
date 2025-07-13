package doggytalents.common.backward_imitate.fabric_util;

import doggytalents.client.backward_imitate.GuiDoggySpinRenderer_1_21_7;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;

public class FabricEventRegisterer_1_21_7 {
    
    public static void init() {
        // Fabric 1.21.7+: We directly invoke this callback in the mixin for now.
        // EventCallbacksRegistry.registerCallback(
        //     new SingleEventCallBack<RegisterPictureInPictureRenderersEvent>(
        //         RegisterPictureInPictureRenderersEvent.class,
        //         GuiDoggySpinRenderer_1_21_7::onRegisterPIPRenderers
        //     )
        // );
    }

}
