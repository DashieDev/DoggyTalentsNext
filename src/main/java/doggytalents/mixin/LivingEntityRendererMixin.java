package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import doggytalents.client.DTNClientDogSleepOnManager;
import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(at = @At("HEAD"),  method = "setupRotations", cancellable = true)
    protected void dtn__setupRotation(LivingEntityRenderState living, PoseStack p_115318_, float p_115319_, float p_115320_,
         CallbackInfo info) {
        //1_21_3+ check
        if (PlayerRenderPrep_21_3.player == null) return;
        
        boolean result = DTNClientDogSleepOnManager.get()
            .onLivingModelSetupRotation(PlayerRenderPrep_21_3.player, p_115318_, p_115319_, p_115320_);
        if (result) 
            info.cancel();
    }

}
