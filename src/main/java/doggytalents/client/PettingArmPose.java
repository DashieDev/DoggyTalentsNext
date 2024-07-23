package doggytalents.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

public class PettingArmPose {

    // public static final ArmPose VALUE = ArmPose.create("doggytalents_petting", true, (x, y, z) -> {
    //     PettingArmPose.applyTransform(x, y, z);
    // });

    public static void applyTransform(HumanoidModel<?> model, LivingEntity player, HumanoidArm arm) {
        DTNClientPettingManager.get().applyTransform(model, player, arm);
    }

    public static void init() {}

    //Fabric
    public static boolean activate = false;
    

}
