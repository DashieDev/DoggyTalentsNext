package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin_1_21_3 {
    
    @Inject(at = @At("TAIL"),  method = "extractRenderState")
    public void dtn__extractRenderState(AbstractClientPlayer player, PlayerRenderState state, float pticks, CallbackInfo info) {
        PlayerRenderPrep_21_3.player = player;
    }

}
