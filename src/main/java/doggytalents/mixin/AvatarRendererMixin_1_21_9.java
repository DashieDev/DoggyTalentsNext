package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.DTNClientDogSleepOnManager;
import doggytalents.client.PettingArmPose;
import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin_1_21_9 {
    
    @Inject(at = @At("TAIL"),  method = "extractRenderState")
    public void dtn__extractRenderState(Avatar avatar, AvatarRenderState state, float pticks, CallbackInfo info) {
        PlayerRenderPrep_21_3.player = null;
        if (avatar.getType() != EntityType.PLAYER)
            return;
        if (!(avatar instanceof AbstractClientPlayer player))
            return;

        boolean result = DTNClientDogSleepOnManager.get().markPlayerRenderStateIfSleepingOnDog_1_21_9(player, state);
        
        if (result)
            PlayerRenderPrep_21_3.player = player;
    }

    @Inject(at = @At("HEAD"),  method = "setupRotations", cancellable = true)
    protected void dtn__setupRotation(AvatarRenderState living, PoseStack p_115318_, float p_115319_, float p_115320_, CallbackInfo info) {
        
        if (PlayerRenderPrep_21_3.player == null) return;
        
        boolean result = DTNClientDogSleepOnManager.get()
            .onLivingModelSetupRotation(PlayerRenderPrep_21_3.player, p_115318_, p_115319_, p_115320_);
        if (result) 
            info.cancel();
    }

    @Inject(at = @At("HEAD"),  method = "getArmPose", cancellable = true)
    private static void dtn__getArmPose(Avatar player, HumanoidArm arm, CallbackInfoReturnable<HumanoidModel.ArmPose> info) {
        if (arm == HumanoidArm.RIGHT && PettingArmPose.activeRight_1_21_5) {
            PettingArmPose.activeRight_1_21_5 = false;
            info.setReturnValue(PettingArmPose.VALUE);
            return;
        }
        if (arm == HumanoidArm.LEFT && PettingArmPose.activeLeft_1_21_5) {
            PettingArmPose.activeLeft_1_21_5 = false;
            info.setReturnValue(PettingArmPose.VALUE);
            return;
        }
    }

}
