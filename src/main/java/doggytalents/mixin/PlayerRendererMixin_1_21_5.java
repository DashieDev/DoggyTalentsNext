package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.client.PettingArmPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin_1_21_5 {

    @Inject(at = @At("HEAD"),  method = "getArmPose", cancellable = true)
    private static void dtn__getArmPose(AbstractClientPlayer player, HumanoidArm arm, CallbackInfoReturnable<HumanoidModel.ArmPose> info) {
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
