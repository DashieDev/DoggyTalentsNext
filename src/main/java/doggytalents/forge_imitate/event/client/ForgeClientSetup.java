package doggytalents.forge_imitate.event.client;

import doggytalents.DoggyTalentsNextEntryClient;
import doggytalents.common.network.DTNNetworkHandlerClient;
import doggytalents.forge_imitate.client.ForgeGuiOverlayManager;
import doggytalents.forge_imitate.event.util.EventBus;
import doggytalents.forge_imitate.event.FabricTempEventFinishRegisterFix;
import doggytalents.forge_imitate.event.RegisterColorHandlersEvent;

public class ForgeClientSetup {
    
    public static void init() {
        DTNNetworkHandlerClient.initClient();
        ClientEventHandlerRegisterer.init();
        DoggyTalentsNextEntryClient.MOD_BUS.finishRegister();
        FabricTempEventFinishRegisterFix.onFinish();
        ForgeGuiOverlayManager.init();
        fireModelLayersRegistration();
        RegisterAndModifyBakingManager.init();
        fireResManRegistration();
        fireKeybindingRegistration();
        fireColorRegisterEvent();

        fireClientSetupEvent();
    }

    private static void fireColorRegisterEvent() {
        DoggyTalentsNextEntryClient.MOD_BUS.post(new RegisterColorHandlersEvent.Block());
        DoggyTalentsNextEntryClient.MOD_BUS.post(new RegisterColorHandlersEvent.Item());
    }

    private static void fireModelLayersRegistration() {
        DoggyTalentsNextEntryClient.MOD_BUS.post(new EntityRenderersEvent.RegisterLayerDefinitions());
        DoggyTalentsNextEntryClient.MOD_BUS.post(new EntityRenderersEvent.RegisterRenderers());
    }

    private static void fireResManRegistration() {
        DoggyTalentsNextEntryClient.MOD_BUS.post(new RegisterClientReloadListenersEvent());
    }

    private static void fireClientSetupEvent() {
        DoggyTalentsNextEntryClient.MOD_BUS.post(new FMLClientSetupEvent());
    }

    private static void fireKeybindingRegistration() {
        DoggyTalentsNextEntryClient.MOD_BUS.post(new RegisterKeyMappingsEvent());
    }

}
