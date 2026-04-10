package doggytalents.client.backward_imitate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;

public class AtlasUtil_1_21_9 {
    
    public static TextureAtlasSprite getSpriteFromMaterial(Material material) {
        var mc = Minecraft.getInstance();
        var atlas_manager = mc.getAtlasManager();
        return atlas_manager.get(material);
    }

    public static TextureAtlasSprite getSpriteFromMaterial(Material material, Identifier atlasOverride) {
        return getSpriteFromMaterial(new Material(atlasOverride, material.texture()));
    }

}
