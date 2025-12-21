package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.common.event.PackHandler;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.repository.Pack;

@Mixin(PackSelectionScreen.class)
public class PackSelectionScreenMixin {
    
    @Inject(method = "loadPackIcon", at = @At(value = "HEAD"), cancellable = true)
    public void dtn__loadPackIcon(TextureManager texture_manager, Pack pack, 
        CallbackInfoReturnable<Identifier> info) {
        
        var result = PackHandler.onPackLoadIcon(pack);
        if (!result.isPresent())
            return;
        info.setReturnValue(result.get());
    }

}
