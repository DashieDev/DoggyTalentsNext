package doggytalents.client;

import doggytalents.DoggyBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class DogBlockRenderTypeSetup {
    
    public static void init() {
        var cutout = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(DoggyBlocks.RICE_CROP.get(), cutout);
        ItemBlockRenderTypes.setRenderLayer(DoggyBlocks.SOY_CROP.get(), cutout);
    }

}
