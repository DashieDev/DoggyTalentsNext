package doggytalents;

import doggytalents.forge_imitate.client.ForgeGuiOverlayManager;
import doggytalents.forge_imitate.event.client.ClientEventHandlerRegisterer;
import doggytalents.forge_imitate.event.client.FabricEventCallbackHandlerClient;
import doggytalents.forge_imitate.event.client.ForgeClientSetup;
import doggytalents.client.screen.widget.DoggySpin.DoggySpinModel;
import doggytalents.common.network.DTNNetworkHandler
;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class DoggyTalentsNextEntryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricEventCallbackHandlerClient.init();
        registerBlockRenderTypes();

        //Last
        ForgeClientSetup.init();
    }

    private void registerBlockRenderTypes() {
        BlockRenderLayerMap.putBlock(DoggyBlocks.RICE_CROP.get(), ChunkSectionLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(DoggyBlocks.SOY_CROP.get(), ChunkSectionLayer.CUTOUT);
    }
    
}
