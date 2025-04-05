package doggytalents.client;

import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class PettingArmPose {

    public static final ArmPose VALUE = ArmPose.valueOf("doggytalents_petting");

    public static void applyTransform(HumanoidModel<?> model, HumanoidRenderState player, HumanoidArm arm) {
        //1.21.3+
        if (PlayerRenderPrep_21_3.player == null)
            return;
        
        DTNClientPettingManager.get().applyTransform(model, PlayerRenderPrep_21_3.player, arm);
    }


    //1.21.5+
    public static boolean activeRight_1_21_5 = false;
    public static boolean activeLeft_1_21_5 = false;

}
