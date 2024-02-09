package doggytalents.client.entity.model;

import java.util.Optional;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;

public abstract class AnimatedSyncedAccessoryModel extends SyncedAccessoryModel {

    public AnimatedSyncedAccessoryModel(ModelPart root) {
        super(root);
    }

    public Optional<ModelPart> searchForPartWithName(String name) {
        if (this.root.hasChild(name)) 
            return Optional.of(this.root.getChild(name));
        if (name.equals("root"))
            return Optional.of(this.root);
        var partOptional = this.root.getAllParts()
            .filter(part -> part.hasChild(name))
            .findFirst();
        return partOptional.map(part -> part.getChild(name));
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
