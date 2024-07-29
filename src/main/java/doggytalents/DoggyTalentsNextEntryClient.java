package doggytalents;

import doggytalents.forge_imitate.client.ForgeGuiOverlayManager;
import doggytalents.forge_imitate.event.client.ClientEventHandlerRegisterer;
import doggytalents.forge_imitate.event.client.FabricEventCallbackHandlerClient;
import doggytalents.forge_imitate.event.client.ForgeClientSetup;
import doggytalents.common.network.DTNNetworkHandler
;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class DoggyTalentsNextEntryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricEventCallbackHandlerClient.init();
        registerBlockRenderTypes();

        //Last
        ForgeClientSetup.init();
    }

    private void registerBlockRenderTypes() {
        BlockRenderLayerMap.INSTANCE.putBlock(DoggyBlocks.RICE_CROP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DoggyBlocks.SOY_CROP.get(), RenderType.cutout());
    }
    
}
