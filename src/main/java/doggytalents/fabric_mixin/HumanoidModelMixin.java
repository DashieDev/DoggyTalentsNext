package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.PettingArmPose;
import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import doggytalents.client.backward_imitate.fabric_util.FabricClientAttachmentHolder_1_21_10;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "poseRightArm(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;Lnet/minecraft/client/model/HumanoidModel$ArmPose;)V")
    public void dtn__poseRightArm(HumanoidRenderState state, ArmPose pose, CallbackInfo info) {
        if (!FabricClientAttachmentHolder_1_21_10.hasAttachment(state, PettingArmPose.ACTIVE_RIGHT_1_21_10))
            return;
        var self = (HumanoidModel<?>)(Object)this;
        PettingArmPose.applyTransform(self, state, HumanoidArm.RIGHT);
        info.cancel();
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "poseLeftArm(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;Lnet/minecraft/client/model/HumanoidModel$ArmPose;)V")
    public void dtn__poseLeftArm(HumanoidRenderState state, ArmPose pose, CallbackInfo info) {
        if (!FabricClientAttachmentHolder_1_21_10.hasAttachment(state, PettingArmPose.ACTIVE_LEFT_1_21_10))
            return;
        var self = (HumanoidModel<?>)(Object)this;
        PettingArmPose.applyTransform(self, state, HumanoidArm.LEFT);
        info.cancel();
    }

}
