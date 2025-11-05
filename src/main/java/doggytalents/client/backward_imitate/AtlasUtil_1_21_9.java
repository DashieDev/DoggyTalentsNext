package doggytalents.client.backward_imitate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;

public class AtlasUtil_1_21_9 {
    
    public static TextureAtlasSprite getSpriteFromMaterial(Material material) {
        var mc = Minecraft.getInstance();
        var atlas_manager = mc.getAtlasManager();
        return atlas_manager.get(material);
    }

}
