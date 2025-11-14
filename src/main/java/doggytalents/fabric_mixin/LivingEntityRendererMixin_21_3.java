package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import doggytalents.client.backward_imitate.PlayerRenderUtil_1_21_9;
import doggytalents.client.backward_imitate.fabric_util.RenderPlayerEvent_21_3;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.RenderPlayerEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin_21_3 {
    
    @Inject(at = @At("HEAD"), method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V")
    public void dtn__submit(LivingEntityRenderState state, PoseStack stack, SubmitNodeCollector collector_1_21_10, CameraRenderState cameraState_1_21_10, CallbackInfo info) {
        var self = (LivingEntityRenderer<?,?,?>)(Object) this;
        if (self instanceof AvatarRenderer) {
            //1.21.10+ check
            var player_1_21_10 = PlayerRenderUtil_1_21_9.getPlayerFromState(state, false);
            if (!player_1_21_10.isPresent())
                return;
            EventCallbacksRegistry.postEvent(new RenderPlayerEvent.Pre(player_1_21_10.get(), state));
        }
    }

    @Inject(at = @At("RETURN"), method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V")
    public void dtn__submit_post(LivingEntityRenderState state, PoseStack stack, SubmitNodeCollector collector_1_21_10, CameraRenderState cameraState_1_21_10, CallbackInfo info) {
        var self = (LivingEntityRenderer<?,?,?>)(Object) this;
        if (self instanceof AvatarRenderer) {
            //1.21.10+ check
            var player_1_21_10 = PlayerRenderUtil_1_21_9.getPlayerFromState(state, true);
            if (!player_1_21_10.isPresent())
                return;
            EventCallbacksRegistry.postEvent(new RenderPlayerEvent_21_3.Post(player_1_21_10.get()));
        }
    }

}
