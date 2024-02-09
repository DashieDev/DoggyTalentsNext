package doggytalents.client.entity.model;

import java.util.Optional;

import doggytalents.api.enu.forward_imitate.anim.DogModelPart;
import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AnimatedSyncedAccessoryModel extends SyncedAccessoryModel {

    public AnimatedSyncedAccessoryModel(ModelPart root) {
        super(root);
    }

    public Optional<DogModelPart> searchForPartWithName(String name) {
        if (((DogModelPart)this.root).hasChild(name)) 
            return Optional.of((DogModelPart)this.root.getChild(name));
        if (name.equals("root"))
            return Optional.of((DogModelPart)this.root);
        var partOptional = this.root.getAllParts()
            .filter(part -> ((DogModelPart)part).hasChild(name))
            .findFirst();
        return partOptional.map(part -> (DogModelPart)part.getChild(name));
    }

    public void resetPart(DogModelPart part, Dog dog) {
        part.resetPose();
    }

    @Override
    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        resetAllPose();
    }

    public abstract void resetAllPose();

}
