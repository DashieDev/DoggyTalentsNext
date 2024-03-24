package doggytalents.client.entity.model;

import java.util.Optional;

import com.mojang.math.Vector3f;

import doggytalents.api.anim.DogAnimation;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogPose;
import doggytalents.api.enu.forward_imitate.anim.DogModelPart;
import doggytalents.api.enu.forward_imitate.anim.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ElytraCapeModel extends AnimatedSyncedAccessoryModel {

    public DogModelPart flyingParts;
    public DogModelPart rWing;
    public DogModelPart lWing;
    public DogModelPart elytra;

    public ElytraCapeModel(ModelPart root) {
        super(DogModelPart.recreateFromModelPart(root));
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.mane = Optional.of(box.getChild("upper_body"));
        flyingParts = (DogModelPart)(this.mane.get().getChild("elytra_rot"));
        elytra = (DogModelPart)(flyingParts.getChild("elytra"));
        lWing = (DogModelPart)(elytra.getChild("left_wing"));
        rWing = (DogModelPart)(elytra.getChild("right_wing"));
    }

    @Override
    public Optional<DogModelPart> searchForPartWithName(String name) {
        if (this.flyingParts.hasChild(name)) 
            return Optional.of((DogModelPart)this.flyingParts.getChild(name));
        var partOptional = this.flyingParts.getAllParts()
            .filter(part -> ((DogModelPart)part).hasChild(name))
            .findFirst();
        return partOptional.map(part -> (DogModelPart)part.getChild(name));
    }

    @Override
    public void prepareMobModel(Dog dogIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.prepareMobModel(dogIn, limbSwing, limbSwingAmount, partialTickTime);
        var pose = dogIn.getDogPose();
        if (pose == DogPose.FLYING) {
            this.elytra.offsetRotation(KeyframeAnimations.degreeVec(24.59f, 0f, 0f));
            this.elytra.offsetPos(KeyframeAnimations.posVec(0f, -0.24f, 1.45f));
            this.rWing.offsetRotation(KeyframeAnimations.degreeVec(-15.18f, -46.68f, 11.36f));
            this.rWing.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
            this.lWing.offsetRotation(KeyframeAnimations.degreeVec(-15.18f, 46.68f, -11.36f));
            this.lWing.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
        }
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
            DogKeyframeAnimations.animate(this, dog, sequence, animState.getAccumulatedTimeMillis(), 1.0F, vecObj);
        }
    }

    @Override
    public void resetAllPose() {
        elytra.resetPose();
        lWing.resetPose();
        rWing.resetPose();
    }
    
    public static LayerDefinition cape() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition elytra_rot = upper_body.addOrReplaceChild("elytra_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, -3.75F, -0.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition elytra = elytra_rot.addOrReplaceChild("elytra", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cape_r1 = elytra.addOrReplaceChild("cape_r1", CubeListBuilder.create().texOffs(40, 42).mirror().addBox(-2.8914F, 3.8079F, 2.3224F, 10.0F, 20.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, -1.0F, 1.6144F, 0.0F, 0.0F));

		PartDefinition right_wing = elytra.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 2.0F));

		PartDefinition right_r1 = right_wing.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-6.6851F, 3.7889F, 2.3232F, 10.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.6144F, -0.1309F, 0.0F));

		PartDefinition left_wing = elytra.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(2.0F, 1.0F, 2.0F));

		PartDefinition left_r1 = left_wing.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-2.8914F, 3.8079F, 2.3224F, 10.0F, 20.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, -3.0F, 1.6144F, 0.0873F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

    public static LayerDefinition bat() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition elytra_rot = upper_body.addOrReplaceChild("elytra_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, -3.75F, -0.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition elytra = elytra_rot.addOrReplaceChild("elytra", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cape_r1 = elytra.addOrReplaceChild("cape_r1", CubeListBuilder.create().texOffs(40, 42).mirror().addBox(-2.8914F, 3.8079F, 2.3224F, 10.0F, 20.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 0.0F, -1.0F, 1.6144F, 0.0F, 0.0F));

		PartDefinition left_wing = elytra.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(2.0F, 1.0F, 2.0F));

		PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(20, 47).mirror().addBox(0.6329F, -8.4717F, 18.5F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false)
		.texOffs(0, 51).mirror().addBox(10.6329F, -7.4717F, 18.5F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 12.75F, 1.25F, 1.5708F, -1.1345F, 0.0F));

		PartDefinition right_wing = elytra.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 2.0F));

		PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(20, 47).addBox(-10.6329F, -8.4717F, 18.5F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 51).addBox(-18.6329F, -7.4717F, 18.5F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 12.75F, 1.25F, 1.5708F, 1.1345F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

}
