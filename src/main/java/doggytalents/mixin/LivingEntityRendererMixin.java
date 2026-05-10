package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.client.DTNClientDogSleepOnManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(at = @At("HEAD"), method = "setupRotations", cancellable = true)
    protected void dtn__setupRotation(LivingEntityRenderState state, PoseStack poseStack,
        float yRot, float partialTick, CallbackInfo info) {
        if (!(state instanceof AvatarRenderState avatar)) return;
        var mc = Minecraft.getInstance();
        if (mc.level == null) return;
        var entity = mc.level.getEntity(avatar.id);
        if (!(entity instanceof LivingEntity living)) return;
        boolean result = DTNClientDogSleepOnManager.get().onLivingModelSetupRotation(living, poseStack);
        if (result)
            info.cancel();
    }

}
