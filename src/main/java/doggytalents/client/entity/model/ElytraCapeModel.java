package doggytalents.client.entity.model;

import java.util.Optional;

import org.joml.Vector3f;

import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.animation.KeyframeAnimationsDelegate;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ElytraCapeModel extends AnimatedSyncedAccessoryModel {

    private ModelPart flyingParts;

    public ElytraCapeModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.mane = Optional.of(box.getChild("upper_body"));
        flyingParts = this.mane.get().getChild("elytra_rot");
    }

    @Override
    public Optional<ModelPart> searchForPartWithName(String name) {
        if (this.flyingParts.hasChild(name)) 
            return Optional.of(this.flyingParts.getChild(name));
        var partOptional = this.flyingParts.getAllParts()
            .filter(part -> part.hasChild(name))
            .findFirst();
        return partOptional.map(part -> part.getChild(name));
    }

    private Vector3f vecObj = new Vector3f(0, 0, 0);

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
            float headPitch) {
        var animationManager = dog.animationManager;
        var animState = animationManager.animationState;
        var anim = dog.getAnim();
        if (anim == DogAnimation.NONE) return;
        var sequence = DogAnimationRegistry.getSequence(anim);
        if (sequence == null) return;
        
        if (animState.isStarted()) {
            KeyframeAnimationsDelegate.animate(this, dog, sequence, animState.getAccumulatedTime(), 1.0F, vecObj);
        }
    }
    
    public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition elytra_rot = upper_body.addOrReplaceChild("elytra_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.75F, -0.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition elytra = elytra_rot.addOrReplaceChild("elytra", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cape_r1 = elytra.addOrReplaceChild("cape_r1", CubeListBuilder.create().texOffs(40, 42).mirror().addBox(-2.8914F, 3.8079F, 2.3224F, 10.0F, 20.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, -1.0F, 1.6144F, 0.0F, 0.0F));

		PartDefinition right_wing = elytra.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 2.0F));

		PartDefinition right_r1 = right_wing.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-6.6851F, 3.7889F, 2.3232F, 10.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.6144F, -0.1309F, 0.0F));

		PartDefinition left_wing = elytra.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(2.0F, 1.0F, 2.0F));

		PartDefinition left_r1 = left_wing.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-2.8914F, 3.8079F, 2.3224F, 10.0F, 20.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.6144F, 0.0873F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

}
