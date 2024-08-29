package doggytalents.legacy_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.PettingArmPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin_1_18_2 {
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "poseRightArm(Lnet/minecraft/world/entity/LivingEntity;)V")
    public void dtn__poseRightArm(LivingEntity player, CallbackInfo info) {
        if (!PettingArmPose.activateRight)
            return;
        PettingArmPose.activateRight = false;
        var self = (HumanoidModel<?>)(Object)this;
        PettingArmPose.applyTransform(self, player, HumanoidArm.RIGHT);
        info.cancel();
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "poseLeftArm(Lnet/minecraft/world/entity/LivingEntity;)V")
    public void dtn__poseLeftArm(LivingEntity player, CallbackInfo info) {
        if (!PettingArmPose.activateLeft)
            return;
        PettingArmPose.activateLeft = false;
        var self = (HumanoidModel<?>)(Object)this;
        PettingArmPose.applyTransform(self, player, HumanoidArm.LEFT);
        info.cancel();
    }

}
