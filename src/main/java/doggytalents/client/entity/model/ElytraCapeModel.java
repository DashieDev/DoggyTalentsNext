package doggytalents.client.entity.model;

import java.util.Optional;

import org.joml.Vector3f;

import doggytalents.api.anim.DogAnimation;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ElytraCapeModel extends AnimatedSyncedAccessoryModel {

    public ModelPart flyingParts;
    public ModelPart rWing;
    public ModelPart lWing;
    public ModelPart elytra;

    public ElytraCapeModel(ModelPart root) {
        super(root);
    }

    @Override
    protected void populatePart(ModelPart box) {
        this.mane = Optional.of(box.getChild("upper_body"));
        flyingParts = this.mane.get().getChild("elytra_rot");
        elytra = flyingParts.getChild("elytra");
        lWing = elytra.getChild("left_wing");
        rWing = elytra.getChild("right_wing");
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
    public static LayerDefinition angel() {
        MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upper_body = partdefinition.addOrReplaceChild("upper_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition elytra_rot = upper_body.addOrReplaceChild("elytra_rot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.75F, -0.25F, -1.5708F, 0.0F, 0.0F));

		PartDefinition elytra = elytra_rot.addOrReplaceChild("elytra", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_wing = elytra.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(2.0F, 1.0F, 2.0F));

		PartDefinition left_r1 = left_wing.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.8914F, 3.8079F, 2.3224F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.599F)).mirror(false), PartPose.offsetAndRotation(0.25F, -1.5F, -6.0F, 1.6144F, 0.0873F, 0.0F));

		PartDefinition left_r2 = left_wing.addOrReplaceChild("left_r2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.8914F, 3.8079F, 2.3224F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.199F)).mirror(false), PartPose.offsetAndRotation(1.25F, -1.5F, -4.5F, 1.6144F, 0.0873F, 0.0F));

		PartDefinition left_r3 = left_wing.addOrReplaceChild("left_r3", CubeListBuilder.create().texOffs(3, 1).mirror().addBox(-0.8914F, 3.8079F, 3.3224F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.25F, -1.0F, -1.0F, 1.6144F, 0.0873F, 0.0F));

		PartDefinition left_r4 = left_wing.addOrReplaceChild("left_r4", CubeListBuilder.create().texOffs(2, 1).mirror().addBox(-6.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.7F, -1.0F, 3.25F, 1.8161F, 1.3909F, 0.2453F));

		PartDefinition left_r5 = left_wing.addOrReplaceChild("left_r5", CubeListBuilder.create().texOffs(2, 1).mirror().addBox(-6.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.2F, -1.0F, 3.25F, 1.9648F, 1.4574F, 0.3956F));

		PartDefinition left_r6 = left_wing.addOrReplaceChild("left_r6", CubeListBuilder.create().texOffs(2, 1).mirror().addBox(-6.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.101F)).mirror(false), PartPose.offsetAndRotation(2.0F, -1.0F, 4.0F, 1.9965F, 1.4654F, 0.4274F));

		PartDefinition left_r7 = left_wing.addOrReplaceChild("left_r7", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-7.8724F, 2.3725F, 3.3414F, 5.0F, 2.0F, 1.0F, new CubeDeformation(-0.399F)).mirror(false), PartPose.offsetAndRotation(0.25F, -1.0F, -3.25F, 1.3802F, 1.3494F, -0.169F));

		PartDefinition left_r8 = left_wing.addOrReplaceChild("left_r8", CubeListBuilder.create().texOffs(0, 5).mirror().addBox(-7.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.399F)).mirror(false), PartPose.offsetAndRotation(1.0F, -1.0F, -0.75F, 1.3802F, 1.3494F, -0.169F));

		PartDefinition left_r9 = left_wing.addOrReplaceChild("left_r9", CubeListBuilder.create().texOffs(0, 5).mirror().addBox(-7.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)).mirror(false), PartPose.offsetAndRotation(0.75F, -1.0F, 3.5F, 1.3802F, 1.3494F, -0.169F));

		PartDefinition left_r10 = left_wing.addOrReplaceChild("left_r10", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)).mirror(false), PartPose.offsetAndRotation(1.5F, -1.0F, 5.5F, 1.3802F, 1.3494F, -0.169F));

		PartDefinition left_r11 = left_wing.addOrReplaceChild("left_r11", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)).mirror(false), PartPose.offsetAndRotation(2.5F, -1.0F, 6.25F, 1.1222F, 1.4748F, -0.4298F));

		PartDefinition left_r12 = left_wing.addOrReplaceChild("left_r12", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.099F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.0F, 8.0F, -0.0164F, 1.5292F, -1.5702F));

		PartDefinition left_r13 = left_wing.addOrReplaceChild("left_r13", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.099F)).mirror(false), PartPose.offsetAndRotation(2.75F, -1.0F, 8.0F, -1.4117F, 1.3051F, -2.971F));

		PartDefinition left_r14 = left_wing.addOrReplaceChild("left_r14", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.199F)).mirror(false), PartPose.offsetAndRotation(2.75F, -1.0F, 8.0F, -1.481F, 1.0885F, -3.045F));

		PartDefinition left_r15 = left_wing.addOrReplaceChild("left_r15", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.199F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.0F, 8.0F, -1.5026F, 0.9145F, -3.0705F));

		PartDefinition left_r16 = left_wing.addOrReplaceChild("left_r16", CubeListBuilder.create().texOffs(1, 1).mirror().addBox(-7.8724F, 2.3725F, 3.3414F, 5.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)).mirror(false), PartPose.offsetAndRotation(0.25F, -1.0F, -2.5F, 1.6973F, 1.2191F, 0.1226F));

		PartDefinition left_r17 = left_wing.addOrReplaceChild("left_r17", CubeListBuilder.create().texOffs(1, 1).mirror().addBox(-7.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)).mirror(false), PartPose.offsetAndRotation(1.0F, -1.0F, 0.0F, 1.6973F, 1.2191F, 0.1226F));

		PartDefinition left_r18 = left_wing.addOrReplaceChild("left_r18", CubeListBuilder.create().texOffs(1, 1).mirror().addBox(-7.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.25F, -1.0F, 4.0F, 1.6973F, 1.2191F, 0.1226F));

		PartDefinition left_r19 = left_wing.addOrReplaceChild("left_r19", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(2.25F, -1.0F, 4.75F, 1.6973F, 1.2191F, 0.1226F));

		PartDefinition left_r20 = left_wing.addOrReplaceChild("left_r20", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.201F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.0F, 7.25F, 1.769F, 1.3483F, 0.1973F));

		PartDefinition left_r21 = left_wing.addOrReplaceChild("left_r21", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.101F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.0F, 8.75F, -1.6576F, 1.0456F, 3.0703F));

		PartDefinition left_r22 = left_wing.addOrReplaceChild("left_r22", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.201F)).mirror(false), PartPose.offsetAndRotation(2.75F, -1.0F, 8.75F, -1.8926F, 1.433F, 2.8264F));

		PartDefinition left_r23 = left_wing.addOrReplaceChild("left_r23", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.101F)).mirror(false), PartPose.offsetAndRotation(2.75F, -1.0F, 8.75F, -1.6973F, 1.2192F, 3.0265F));

		PartDefinition left_r24 = left_wing.addOrReplaceChild("left_r24", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.201F)).mirror(false), PartPose.offsetAndRotation(3.0F, -1.0F, 8.75F, 1.8923F, 1.4328F, 0.3224F));

		PartDefinition left_r25 = left_wing.addOrReplaceChild("left_r25", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 2.3725F, 3.3414F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.201F)).mirror(false), PartPose.offsetAndRotation(-0.5F, -1.0F, 3.5F, -1.6421F, 0.9152F, 3.0888F));

		PartDefinition left_r26 = left_wing.addOrReplaceChild("left_r26", CubeListBuilder.create().texOffs(4, 1).mirror().addBox(-4.8724F, 3.3725F, 3.3414F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.299F)).mirror(false), PartPose.offsetAndRotation(-1.3F, -1.0F, 3.0F, -1.6647F, 1.0891F, 3.0621F));

		PartDefinition left_r27 = left_wing.addOrReplaceChild("left_r27", CubeListBuilder.create().texOffs(2, 1).mirror().addBox(-6.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-1.8F, -1.0F, 3.25F, -1.6647F, 1.0891F, 3.0621F));

		PartDefinition left_r28 = left_wing.addOrReplaceChild("left_r28", CubeListBuilder.create().texOffs(4, 1).mirror().addBox(-4.8724F, 3.3725F, 3.3414F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.201F)).mirror(false), PartPose.offsetAndRotation(-0.55F, -1.0F, 3.25F, -1.6734F, 1.1325F, 3.0525F));

		PartDefinition left_r29 = left_wing.addOrReplaceChild("left_r29", CubeListBuilder.create().texOffs(4, 1).mirror().addBox(-4.8724F, 3.3725F, 3.3414F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.301F)).mirror(false), PartPose.offsetAndRotation(1.75F, -1.0F, 1.0F, -2.3211F, 1.4501F, 2.3785F));

		PartDefinition left_r30 = left_wing.addOrReplaceChild("left_r30", CubeListBuilder.create().texOffs(0, 1).mirror().addBox(-8.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -1.0F, 3.0F, -1.6647F, 1.0891F, 3.0621F));

		PartDefinition left_r31 = left_wing.addOrReplaceChild("left_r31", CubeListBuilder.create().texOffs(4, 1).mirror().addBox(-4.8724F, 3.3725F, 3.3414F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.401F)).mirror(false), PartPose.offsetAndRotation(0.75F, -1.0F, 3.0F, -2.3079F, 1.3905F, 2.3759F));

		PartDefinition left_r32 = left_wing.addOrReplaceChild("left_r32", CubeListBuilder.create().texOffs(1, 1).mirror().addBox(-2.8914F, 3.8079F, 3.3224F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.75F, -1.0F, -2.0F, 1.6144F, 0.0873F, 0.0F));

		PartDefinition left_r33 = left_wing.addOrReplaceChild("left_r33", CubeListBuilder.create().texOffs(2, 0).mirror().addBox(-0.8914F, 3.8079F, 2.3224F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.5F, -3.0F, 1.6158F, 0.2616F, 0.0078F));

		PartDefinition right_wing = elytra.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 2.0F));

		PartDefinition right_r1 = right_wing.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 1).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.101F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 8.75F, -1.6576F, -1.0456F, -3.0703F));

		PartDefinition right_r2 = right_wing.addOrReplaceChild("right_r2", CubeListBuilder.create().texOffs(0, 1).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.101F)), PartPose.offsetAndRotation(-2.75F, -1.0F, 8.75F, -1.6973F, -1.2192F, -3.0265F));

		PartDefinition right_r3 = right_wing.addOrReplaceChild("right_r3", CubeListBuilder.create().texOffs(0, 1).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.201F)), PartPose.offsetAndRotation(-2.75F, -1.0F, 8.75F, -1.8926F, -1.433F, -2.8264F));

		PartDefinition right_r4 = right_wing.addOrReplaceChild("right_r4", CubeListBuilder.create().texOffs(0, 1).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.201F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 8.75F, 1.8923F, -1.4328F, -0.3224F));

		PartDefinition right_r5 = right_wing.addOrReplaceChild("right_r5", CubeListBuilder.create().texOffs(0, 1).addBox(0.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.201F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 7.25F, 1.769F, -1.3483F, -0.1973F));

		PartDefinition right_r6 = right_wing.addOrReplaceChild("right_r6", CubeListBuilder.create().texOffs(0, 1).addBox(0.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.25F, -1.0F, 4.75F, 1.6973F, -1.2191F, -0.1226F));

		PartDefinition right_r7 = right_wing.addOrReplaceChild("right_r7", CubeListBuilder.create().texOffs(1, 1).addBox(0.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.25F, -1.0F, 4.0F, 1.6973F, -1.2191F, -0.1226F));

		PartDefinition right_r8 = right_wing.addOrReplaceChild("right_r8", CubeListBuilder.create().texOffs(1, 1).addBox(0.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(-1.0F, -1.0F, 0.0F, 1.6973F, -1.2191F, -0.1226F));

		PartDefinition right_r9 = right_wing.addOrReplaceChild("right_r9", CubeListBuilder.create().texOffs(1, 1).addBox(2.8724F, 2.3725F, 3.3414F, 5.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(-0.25F, -1.0F, -2.5F, 1.6973F, -1.2191F, -0.1226F));

		PartDefinition right_r10 = right_wing.addOrReplaceChild("right_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-0.1086F, 3.8079F, 2.3224F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.599F)), PartPose.offsetAndRotation(-0.25F, -1.5F, -6.0F, 1.6144F, -0.0873F, 0.0F));

		PartDefinition right_r11 = right_wing.addOrReplaceChild("right_r11", CubeListBuilder.create().texOffs(0, 0).addBox(-0.1086F, 3.8079F, 2.3224F, 4.0F, 3.0F, 2.0F, new CubeDeformation(-0.199F)), PartPose.offsetAndRotation(-1.25F, -1.5F, -4.5F, 1.6144F, -0.0873F, 0.0F));

		PartDefinition right_r12 = right_wing.addOrReplaceChild("right_r12", CubeListBuilder.create().texOffs(3, 1).addBox(-4.1086F, 3.8079F, 3.3224F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.25F, -1.0F, -1.0F, 1.6144F, -0.0873F, 0.0F));

		PartDefinition right_r13 = right_wing.addOrReplaceChild("right_r13", CubeListBuilder.create().texOffs(2, 1).addBox(0.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.7F, -1.0F, 3.25F, 1.8161F, -1.3909F, -0.2453F));

		PartDefinition right_r14 = right_wing.addOrReplaceChild("right_r14", CubeListBuilder.create().texOffs(2, 1).addBox(0.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.2F, -1.0F, 3.25F, 1.9648F, -1.4574F, -0.3956F));

		PartDefinition right_r15 = right_wing.addOrReplaceChild("right_r15", CubeListBuilder.create().texOffs(2, 1).addBox(0.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.101F)), PartPose.offsetAndRotation(-2.0F, -1.0F, 4.0F, 1.9965F, -1.4654F, -0.4274F));

		PartDefinition right_r16 = right_wing.addOrReplaceChild("right_r16", CubeListBuilder.create().texOffs(20, 0).addBox(2.8724F, 2.3725F, 3.3414F, 5.0F, 2.0F, 1.0F, new CubeDeformation(-0.399F)), PartPose.offsetAndRotation(-0.25F, -1.0F, -3.25F, 1.3802F, -1.3494F, 0.169F));

		PartDefinition right_r17 = right_wing.addOrReplaceChild("right_r17", CubeListBuilder.create().texOffs(0, 5).addBox(0.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.399F)), PartPose.offsetAndRotation(-1.0F, -1.0F, -0.75F, 1.3802F, -1.3494F, 0.169F));

		PartDefinition right_r18 = right_wing.addOrReplaceChild("right_r18", CubeListBuilder.create().texOffs(0, 5).addBox(0.8724F, 2.3725F, 3.3414F, 7.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(-0.75F, -1.0F, 3.5F, 1.3802F, -1.3494F, 0.169F));

		PartDefinition right_r19 = right_wing.addOrReplaceChild("right_r19", CubeListBuilder.create().texOffs(0, 0).addBox(0.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(-1.5F, -1.0F, 5.5F, 1.3802F, -1.3494F, 0.169F));

		PartDefinition right_r20 = right_wing.addOrReplaceChild("right_r20", CubeListBuilder.create().texOffs(0, 0).addBox(0.8724F, 2.3725F, 3.3414F, 8.0F, 2.0F, 1.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(-2.5F, -1.0F, 6.25F, 1.1222F, -1.4748F, 0.4298F));

		PartDefinition right_r21 = right_wing.addOrReplaceChild("right_r21", CubeListBuilder.create().texOffs(0, 3).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.199F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 8.0F, -1.5026F, -0.9145F, 3.0705F));

		PartDefinition right_r22 = right_wing.addOrReplaceChild("right_r22", CubeListBuilder.create().texOffs(0, 3).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(-2.75F, -1.0F, 8.0F, -1.4117F, -1.3051F, 2.971F));

		PartDefinition right_r23 = right_wing.addOrReplaceChild("right_r23", CubeListBuilder.create().texOffs(0, 3).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.199F)), PartPose.offsetAndRotation(-2.75F, -1.0F, 8.0F, -1.481F, -1.0885F, 3.045F));

		PartDefinition right_r24 = right_wing.addOrReplaceChild("right_r24", CubeListBuilder.create().texOffs(0, 3).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.099F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 8.0F, -0.0164F, -1.5292F, 1.5702F));

		PartDefinition right_r25 = right_wing.addOrReplaceChild("right_r25", CubeListBuilder.create().texOffs(0, 1).addBox(3.8724F, 2.3725F, 3.3414F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.201F)), PartPose.offsetAndRotation(0.5F, -1.0F, 3.5F, -1.6421F, -0.9152F, -3.0888F));

		PartDefinition right_r26 = right_wing.addOrReplaceChild("right_r26", CubeListBuilder.create().texOffs(4, 1).addBox(0.8724F, 3.3725F, 3.3414F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.299F)), PartPose.offsetAndRotation(1.3F, -1.0F, 3.0F, -1.6647F, -1.0891F, -3.0621F));

		PartDefinition right_r27 = right_wing.addOrReplaceChild("right_r27", CubeListBuilder.create().texOffs(2, 1).addBox(0.8724F, 3.3725F, 3.3414F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.8F, -1.0F, 3.25F, -1.6647F, -1.0891F, -3.0621F));

		PartDefinition right_r28 = right_wing.addOrReplaceChild("right_r28", CubeListBuilder.create().texOffs(4, 1).addBox(0.8724F, 3.3725F, 3.3414F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.201F)), PartPose.offsetAndRotation(0.55F, -1.0F, 3.25F, -1.6734F, -1.1325F, -3.0525F));

		PartDefinition right_r29 = right_wing.addOrReplaceChild("right_r29", CubeListBuilder.create().texOffs(4, 1).addBox(0.8724F, 3.3725F, 3.3414F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.301F)), PartPose.offsetAndRotation(-1.75F, -1.0F, 1.0F, -2.3211F, -1.4501F, -2.3785F));

		PartDefinition right_r30 = right_wing.addOrReplaceChild("right_r30", CubeListBuilder.create().texOffs(0, 1).addBox(0.8724F, 3.3725F, 3.3414F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(3.0F, -1.0F, 3.0F, -1.6647F, -1.0891F, -3.0621F));

		PartDefinition right_r31 = right_wing.addOrReplaceChild("right_r31", CubeListBuilder.create().texOffs(4, 1).addBox(-0.1276F, 3.3725F, 3.3414F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.401F)), PartPose.offsetAndRotation(-0.75F, -1.0F, 3.0F, -2.3079F, -1.3905F, -2.3759F));

		PartDefinition right_r32 = right_wing.addOrReplaceChild("right_r32", CubeListBuilder.create().texOffs(1, 1).addBox(-3.1086F, 3.8079F, 3.3224F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.75F, -1.0F, -2.0F, 1.6144F, -0.0873F, 0.0F));

		PartDefinition right_r33 = right_wing.addOrReplaceChild("right_r33", CubeListBuilder.create().texOffs(2, 0).addBox(-5.1086F, 3.8079F, 2.3224F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -1.5F, -3.0F, 1.6158F, -0.2616F, -0.0078F));

		return LayerDefinition.create(meshdefinition, 32, 16);
	}

}
