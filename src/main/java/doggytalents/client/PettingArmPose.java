package doggytalents.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;

public class PettingArmPose {

    public static final ArmPose VALUE = ArmPose.valueOf("doggytalents_petting");

    public static void applyTransform(HumanoidModel<?> model, HumanoidRenderState renderState, HumanoidArm arm) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            DTNClientPettingManager.get().applyTransform(model, player, arm);
        }
    }

}
