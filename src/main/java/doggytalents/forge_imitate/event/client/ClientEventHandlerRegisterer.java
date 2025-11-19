package doggytalents.forge_imitate.event.client;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.DoggyTalentsNextEntryClient;
import doggytalents.client.ClientSetup;
import doggytalents.client.DTNClientDogSleepOnManager;
import doggytalents.client.DTNClientPettingManager;
import doggytalents.client.DoggyKeybinds;
import doggytalents.client.entity.render.world.BedFinderRenderer;
import doggytalents.client.entity.render.world.CanineTrackerLocateRenderer;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.forge_imitate.client.ForgeGuiOverlayManager.RegisterGuiOverlaysEvent;
import doggytalents.forge_imitate.event.RegisterColorHandlersEvent;
import doggytalents.forge_imitate.event.util.EventBus;

public class ClientEventHandlerRegisterer {
    
    public static void init() {
        EventBus.COMMON_BUS.register(new ClientEventHandler());
        EventBus.COMMON_BUS.register(DTNClientPettingManager.get());
        EventBus.COMMON_BUS.register(DTNClientDogSleepOnManager.get());
        DoggyTalentsNextEntryClient.MOD_BUS.addListener(RegisterColorHandlersEvent.Block.class, 
            DoggyBlocks::registerBlockColours);
        DoggyTalentsNextEntryClient.MOD_BUS.addListener(RegisterColorHandlersEvent.Item.class, 
            DoggyItems::registerItemColours);        
        DoggyTalentsNextEntryClient.MOD_BUS.addListener(EntityRenderersEvent.RegisterLayerDefinitions.class, 
            ClientSetup::setupEntityRenderers);
        DoggyTalentsNextEntryClient.MOD_BUS.addListener(EntityRenderersEvent.RegisterRenderers.class, 
            ClientSetup::setupTileEntityRenderers);
        DoggyTalentsNextEntryClient.MOD_BUS.addListener(RegisterClientReloadListenersEvent.class,
            ClientSetup::addClientReloadListeners);
        DoggyTalentsNextEntryClient.MOD_BUS.addListener(FMLClientSetupEvent.class, 
            ClientSetup::onClientSetup);
        DoggyTalentsNextEntryClient.MOD_BUS.addListener(FMLClientSetupEvent.class, 
            ClientSetup::setupScreenManagers);
        EventBus.COMMON_BUS.addListener(RegisterGuiOverlaysEvent.class, 
            ClientSetup::registerOverlay);
        EventBus.COMMON_BUS.addListener(RenderLevelStageEvent.class, 
            BedFinderRenderer::onWorldRenderLast);
        EventBus.COMMON_BUS.addListener(RenderLevelStageEvent.class, 
            CanineTrackerLocateRenderer::onWorldRenderLast);
        EventBus.COMMON_BUS.addListener(ClientTickEvent.class, 
            CanineTrackerLocateRenderer::tickUpdate);
        // DoggyTalentsNextEntry.MOD_BUS.addListener(RegisterKeyMappingsEvent.class, 
        //     DoggyKeybinds::registerDTKeyMapping);
    }

}
