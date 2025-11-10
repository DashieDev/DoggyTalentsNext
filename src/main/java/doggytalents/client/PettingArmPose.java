package doggytalents.client;

import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import doggytalents.client.backward_imitate.PlayerRenderUtil_1_21_9;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class PettingArmPose {

    public static final ArmPose VALUE = ArmPose.valueOf("doggytalents_petting");

    public static void applyTransform(HumanoidModel<?> model, HumanoidRenderState player, HumanoidArm arm) {
        //1.21.9+
        var player_1_21_9 = PlayerRenderUtil_1_21_9.getPlayerFromState(player, false);
        if (!player_1_21_9.isPresent())
            return;
        
        DTNClientPettingManager.get().applyTransform(model, player_1_21_9.get(), arm);
    }


    //1.21.5+
    public static boolean activeRight_1_21_5 = false;
    public static boolean activeLeft_1_21_5 = false;

}
