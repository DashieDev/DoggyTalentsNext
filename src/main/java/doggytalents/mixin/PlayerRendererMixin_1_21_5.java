package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.client.PettingArmPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin_1_21_5 {

    @Inject(at = @At("HEAD"),  method = "getArmPose", cancellable = true)
    public void dtn__getArmPose(Player player, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> info) {
        if (hand == InteractionHand.MAIN_HAND && PettingArmPose.activeRight_1_21_5) {
            PettingArmPose.activeRight_1_21_5 = false;
            info.setReturnValue(PettingArmPose.VALUE);
            return;
        }
        if (hand == InteractionHand.OFF_HAND && PettingArmPose.activeLeft_1_21_5) {
            PettingArmPose.activeLeft_1_21_5 = false;
            info.setReturnValue(PettingArmPose.VALUE);
            return;
        }
    }

}
