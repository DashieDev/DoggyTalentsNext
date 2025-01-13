package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.client.DTNClientDogSleepOnManager;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(at = @At("HEAD"),  method = "setupRotations", cancellable = true)
    protected void dtn__setupRotation(LivingEntity living, PoseStack p_115318_, float p_115319_, float p_115320_,
        float p_115321_, float x, CallbackInfo info) {
        boolean result = DTNClientDogSleepOnManager.get()
            .onLivingModelSetupRotation(living, p_115318_, p_115319_, p_115320_, p_115321_, x);
        if (result) 
            info.cancel();
    }

}
