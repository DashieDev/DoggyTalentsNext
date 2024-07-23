package doggytalents.forge_imitate.event.client;

import doggytalents.client.ClientSetup;
import doggytalents.client.DTNClientPettingManager;
import doggytalents.client.DoggyKeybinds;
import doggytalents.client.entity.render.world.BedFinderRenderer;
import doggytalents.client.entity.render.world.CanineTrackerLocateRenderer;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.forge_imitate.client.ForgeGuiOverlayManager.RegisterGuiOverlaysEvent;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.InstanceEventCallBack;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;

public class ClientEventHandlerRegisterer {

    private static ClientEventHandler INST = new ClientEventHandler();
    
    public static void init() {
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<EntityRenderersEvent.RegisterLayerDefinitions>
                (EntityRenderersEvent.RegisterLayerDefinitions.class,
                    ClientSetup::setupEntityRenderers
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<EntityRenderersEvent.RegisterRenderers>
                (EntityRenderersEvent.RegisterRenderers.class,
                    ClientSetup::setupTileEntityRenderers
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<FMLClientSetupEvent>
                (FMLClientSetupEvent.class,
                    ClientSetup::setupCollarRenderers
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<FMLClientSetupEvent>
                (FMLClientSetupEvent.class,
                    ClientSetup::setupScreenManagers
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<RegisterGuiOverlaysEvent>
                (RegisterGuiOverlaysEvent.class,
                    ClientSetup::registerOverlay
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<RegisterClientReloadListenersEvent>
                (RegisterClientReloadListenersEvent.class,
                    ClientSetup::addClientReloadListeners
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<RenderLevelStageEvent>
                (RenderLevelStageEvent.class,
                    BedFinderRenderer::onWorldRenderLast
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<RenderLevelStageEvent>
                (RenderLevelStageEvent.class,
                    CanineTrackerLocateRenderer::onWorldRenderLast
                )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<ClientTickEvent>
                (ClientTickEvent.class,
                    CanineTrackerLocateRenderer::tickUpdate
                )
        );
        // EventCallbacksRegistry.registerCallback(
        //     new SingleEventCallBack<RegisterKeyMappingsEvent>
        //         (RegisterKeyMappingsEvent.class,
        //             DoggyKeybinds::registerDTKeyMapping
        //         )
        // );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<ClientEventHandler, ScreenEvent.Init.Post>
                (INST, ScreenEvent.Init.Post.class,
                    ClientEventHandler::onScreenInit
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<ClientEventHandler, ScreenEvent.Render.Post>
                (INST, ScreenEvent.Render.Post.class,
                    ClientEventHandler::onScreenDrawForeground
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<ClientEventHandler, MovementInputUpdateEvent>
                (INST, MovementInputUpdateEvent.class,
                    ClientEventHandler::onInputEvent
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<ClientEventHandler, InputEvent.Key>
                (INST, InputEvent.Key.class,
                    ClientEventHandler::onKeyboardInput
                )
        );
        
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<DTNClientPettingManager, ClientTickEvent>
                (DTNClientPettingManager.get(), ClientTickEvent.class,
                    DTNClientPettingManager::tickClient
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<DTNClientPettingManager, RenderArmEvent>
                (DTNClientPettingManager.get(), RenderArmEvent.class,
                    DTNClientPettingManager::onRenderHand
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<DTNClientPettingManager, InputEvent.MouseButton.Pre>
                (DTNClientPettingManager.get(), InputEvent.MouseButton.Pre.class,
                    DTNClientPettingManager::onMouseInput
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<DTNClientPettingManager, RenderPlayerEvent.Pre>
                (DTNClientPettingManager.get(), RenderPlayerEvent.Pre.class,
                    DTNClientPettingManager::onPlayerRender
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<DTNClientPettingManager, ComputeCameraAngles>
                (DTNClientPettingManager.get(), ComputeCameraAngles.class,
                    DTNClientPettingManager::modifyCameraAngle
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<DTNClientPettingManager, MovementInputUpdateEvent>
                (DTNClientPettingManager.get(), MovementInputUpdateEvent.class,
                    DTNClientPettingManager::onMovementInput
                )
        );
    }

}
