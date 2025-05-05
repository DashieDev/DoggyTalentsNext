package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import doggytalents.client.backward_imitate.fabric_util.RenderPlayerEvent_21_3;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.RenderPlayerEvent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin_21_3 {
    
    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void dtn__render(LivingEntityRenderState state, PoseStack stack, MultiBufferSource buffer, int light, CallbackInfo info) {
        var self = (LivingEntityRenderer<?,?,?>)(Object) this;
        if (self instanceof PlayerRenderer) {
            EventCallbacksRegistry.postEvent(new RenderPlayerEvent.Pre(PlayerRenderPrep_21_3.player));
        }
    }

    @Inject(at = @At("RETURN"), method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void dtn__render_post(LivingEntityRenderState state, PoseStack stack, MultiBufferSource buffer, int light, CallbackInfo info) {
        var self = (LivingEntityRenderer<?,?,?>)(Object) this;
        if (self instanceof PlayerRenderer) {
            EventCallbacksRegistry.postEvent(new RenderPlayerEvent_21_3.Post(PlayerRenderPrep_21_3.player));
        }
    }

}
