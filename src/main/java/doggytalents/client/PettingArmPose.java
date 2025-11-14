package doggytalents.client;

import doggytalents.client.backward_imitate.PlayerRenderPrep_21_3;
import doggytalents.client.backward_imitate.PlayerRenderUtil_1_21_9;
import doggytalents.common.util.Util;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Unit;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class PettingArmPose {

    // public static final ArmPose VALUE = ArmPose.create("doggytalents_petting", true, (x, y, z) -> {
    //     PettingArmPose.applyTransform(x, y, z);
    // });

    public static void applyTransform(HumanoidModel<?> model, HumanoidRenderState player, HumanoidArm arm) {
        //1.21.9+
        var player_1_21_9 = PlayerRenderUtil_1_21_9.getPlayerFromState(player, false);
        if (!player_1_21_9.isPresent())
            return;
        
        DTNClientPettingManager.get().applyTransform(model, player_1_21_9.get(), arm);
    }

    public static void init() {}

    //Fabric
    public static ContextKey<Unit> ACTIVE_RIGHT_1_21_10 = new ContextKey<>(Util.getResource("fabric_petting_r"));
    public static ContextKey<Unit> ACTIVE_LEFT_1_21_10 = new ContextKey<>(Util.getResource("fabric_petting_l"));
    

}
