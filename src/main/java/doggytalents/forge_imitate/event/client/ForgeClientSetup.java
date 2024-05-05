package doggytalents.forge_imitate.event.client;

import doggytalents.common.network.DTNNetworkHandlerClient;
import doggytalents.forge_imitate.client.ForgeGuiOverlayManager;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.RegisterColorHandlersEvent;

public class ForgeClientSetup {
    
    public static void init() {
        DTNNetworkHandlerClient.initClient();
        ClientEventHandlerRegisterer.init();
        ForgeGuiOverlayManager.init();
        fireModelLayersRegistration();
        RegisterAndModifyBakingManager.init();
        fireResManRegistration();
        fireKeybindingRegistration();
        fireColorRegisterEvent();

        fireClientSetupEvent();
    }

    private static void fireColorRegisterEvent() {
        EventCallbacksRegistry.postEvent(new RegisterColorHandlersEvent.Block());
        EventCallbacksRegistry.postEvent(new RegisterColorHandlersEvent.Item());
    }

    private static void fireModelLayersRegistration() {
        EventCallbacksRegistry.postEvent(new EntityRenderersEvent.RegisterLayerDefinitions());
        EventCallbacksRegistry.postEvent(new EntityRenderersEvent.RegisterRenderers());
    }

    private static void fireResManRegistration() {
        EventCallbacksRegistry.postEvent(new RegisterClientReloadListenersEvent());
    }

    private static void fireClientSetupEvent() {
        EventCallbacksRegistry.postEvent(new FMLClientSetupEvent());
    }

    private static void fireKeybindingRegistration() {
        EventCallbacksRegistry.postEvent(new RegisterKeyMappingsEvent());
    }

}
