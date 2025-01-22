package doggytalents.client.entity.model;

import java.util.Optional;

import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AnimatedSyncedAccessoryModel extends SyncedAccessoryModel {

    public AnimatedSyncedAccessoryModel(ModelPart root) {
        super(root);
    }

    public Optional<ModelPart> searchForPartWithName(String name) {
        return DogKeyframeAnimations.searchForPartWithName(root, name);
    }

    public void resetPart(ModelPart part, Dog dog) {
        part.resetPose();
    }

    @Override
    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        resetAllPose();
    }

    public abstract void resetAllPose();

}
