package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.RenderArmEvent;
import doggytalents.forge_imitate.event.client.RenderPlayerEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    
    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    public void dtn__render(AbstractClientPlayer player, float p_117789_, float pTicks, PoseStack stack, MultiBufferSource buffer, int light, CallbackInfo info) {
        EventCallbacksRegistry.postEvent(new RenderPlayerEvent.Pre(player));
    }

    @Inject(at = @At("HEAD"), method = "renderRightHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;)V")
    public void dtn__renderRightHand(PoseStack stack, MultiBufferSource buffer, int light, AbstractClientPlayer player, CallbackInfo info) {
        EventCallbacksRegistry.postEvent(new RenderArmEvent(stack, player, HumanoidArm.RIGHT));
    }

}
