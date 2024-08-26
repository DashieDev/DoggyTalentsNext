package doggytalents.client.entity.model.dog;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.api.anim.DogAnimation;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.common.entity.Dog;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ColorableAgeableListModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DogModel extends EntityModel<Dog> {

    public static final float[] MANE_LYING_OFF = {0f, 6f, 1f};
    public static final float[] MANE_SITTING_OFF = {0f, 2f, 0f};
    public static final float[] TAIL_LYING_OFF = {0, 6f, 0};
    public static final float[] TAIL_SITTING_OFF = {0, 9f, -2f};

    public static final Vector3f DEFAULT_ROOT_PIVOT = new Vector3f(0, 15, 0);

    public ModelPart head;
    public ModelPart realHead;
    public ModelPart body;
    public ModelPart mane;
    public ModelPart legBackRight;
    public ModelPart legBackLeft;
    public ModelPart legFrontRight;
    public ModelPart legFrontLeft;
    public ModelPart tail;
    public ModelPart realTail;
    
    public ModelPart root;

    //Optional parts
    public Optional<ModelPart> earLeft;
    public Optional<ModelPart> earRight;

    public DogModel(ModelPart box) {
        this.root = box;
        this.head = box.getChild("head");
        this.realHead = this.head.getChild("real_head");
        this.body = box.getChild("body");
        this.mane = box.getChild("upper_body");
        this.legBackRight = box.getChild("right_hind_leg");
        this.legBackLeft = box.getChild("left_hind_leg");
        this.legFrontRight = box.getChild("right_front_leg");
        this.legFrontLeft = box.getChild("left_front_leg");
        this.tail = box.getChild("tail");
        this.realTail = this.tail.getChild("real_tail");

        this.addOptionalParts(box);
        this.correctInitalPose();
    }

    public DogModel(ModelPart box, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.root = box;
        this.head = box.getChild("head");
        this.realHead = this.head.getChild("real_head");
        this.body = box.getChild("body");
        this.mane = box.getChild("upper_body");
        this.legBackRight = box.getChild("right_hind_leg");
        this.legBackLeft = box.getChild("left_hind_leg");
        this.legFrontRight = box.getChild("right_front_leg");
        this.legFrontLeft = box.getChild("left_front_leg");
        this.tail = box.getChild("tail");
        this.realTail = this.tail.getChild("real_tail");
        
        this.addOptionalParts(box);
        this.correctInitalPose();
    }

    protected void addOptionalParts(ModelPart box) {
        this.earLeft = getChildIfPresent(this.realHead, "left_ear");
        this.earRight = getChildIfPresent(this.realHead, "right_ear");
    }

    protected Optional<ModelPart> getChildIfPresent(ModelPart box, String name) {
        if (!box.hasChild(name))
            return Optional.empty();
        return Optional.of(box.getChild(name));
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayerInternal(CubeDeformation.NONE);
    }

    public boolean useDefaultModelForAccessories() {
        return false;
    }

    private static LayerDefinition createBodyLayerInternal(CubeDeformation scale) {
        MeshDefinition var0 = new MeshDefinition();
        PartDefinition var1 = var0.getRoot();
        float var2 = 13.5F;
        PartDefinition var3 = var1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0F, 13.5F, -7.0F));
        var real_head = var3.addOrReplaceChild("real_head", CubeListBuilder.create()
                // Head
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, scale)
                // Nose
                .texOffs(0, 10).addBox(-1.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F, scale)
                , PartPose.ZERO);
        real_head.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), 
                PartPose.offset(-2.0F, -3.0F, 0.5F));
        real_head.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), 
                PartPose.offset(2.0F, -3.0F, 0.5F));
    
        var1.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, scale)
        , PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        var1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, scale), PartPose.offsetAndRotation(0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(-1F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale);
        var1.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-1.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(1.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-1.5F, 16.0F, -4.0F));
        var1.addOrReplaceChild("left_front_leg", var4, PartPose.offset(1.5F, 16.0F, -4.0F));
        PartDefinition var5 = var1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        var5.addOrReplaceChild("real_tail", CubeListBuilder.create()
                .texOffs(9, 18).addBox(-1F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale)
        , PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 32);
    }
    @Override
    public void prepareMobModel(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {

        this.resetAllPose();

        var pose = dog.getDogPose();

        var anim = dog.getAnim();
        if (anim != DogAnimation.NONE) {
            if (anim.freeHead() && pose.canBeg)
                this.translateBeggingDog(dog, limbSwing, limbSwingAmount, partialTickTime);
            return;
        };
        
        switch (pose) {
            case FAINTED:
                this.setupFaintPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case FAINTED_2:
                this.setupFaintPose2(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case SIT:
                this.setUpSitPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case REST:
                this.setupRestPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case LYING_2:
                this.setupLyingPose2(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case DROWN:
                this.setupDrownPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case FLYING:
                this.setupFlyPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case REST_BELLY:
                this.setupRestBellyPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            default:
                this.setUpStandPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
        }
        if (pose.canShake)
        this.translateShakingDog(dog, limbSwing, limbSwingAmount, partialTickTime);

        if (pose.canBeg)
        this.translateBeggingDog(dog, limbSwing, limbSwingAmount, partialTickTime);

    }

    public void setUpStandPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        animateStandWalking(dog, limbSwing, limbSwingAmount, partialTickTime);
    }

    public void animateStandWalking(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        float w = Mth.cos(limbSwing * 0.6662F);
        float w1 = Mth.cos(limbSwing * 0.6662F + (float) Math.PI);
        float swing = Mth.clamp(limbSwingAmount, 0, 1);
        float modifier = 2.5f;
        this.body.xRot += getAnimateWalkingValue(w, swing, modifier * -5f*Mth.DEG_TO_RAD);
        this.body.y += getAnimateWalkingValue(w, swing, -0.25f*modifier);
        this.body.z +=  getAnimateWalkingValue(w, swing, -0.25f*modifier);

        this.mane.xRot += getAnimateWalkingValue(w, swing, modifier * 2.5f*Mth.DEG_TO_RAD );
        this.mane.y += getAnimateWalkingValue(w, swing, -0.25f*modifier);

        this.head.y += getAnimateWalkingValue(w, swing, -0.25f*modifier);

        this.tail.y += getAnimateWalkingValue(w, swing, 0.5f*modifier);
        this.tail.z += getAnimateWalkingValue(w, swing, -0.5f*modifier);

        if (this.earRight.isPresent()) {
            this.earRight.get().xRot += getAnimateWalkingValue(w, swing, -40f*Mth.DEG_TO_RAD );
            this.earRight.get().zRot += getAnimateWalkingValue(w, swing, -27.5f*Mth.DEG_TO_RAD );
            this.earRight.get().y += getAnimateWalkingValue(w, swing, 0.5f );
        }
        if (this.earLeft.isPresent()) {
            this.earLeft.get().xRot += getAnimateWalkingValue(w, swing, -40f*Mth.DEG_TO_RAD );
            this.earLeft.get().zRot += getAnimateWalkingValue(w, swing, 27.5f*Mth.DEG_TO_RAD );
            this.earLeft.get().y += getAnimateWalkingValue(w, swing, 0.5f );
        }

        this.legBackRight.xRot += w * 1.4F * limbSwingAmount;
        this.legBackLeft.xRot += w1 * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot += w1 * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot += w * 1.4F * limbSwingAmount;
    }

    private float getAnimateWalkingValue(float w, float swingAmount, float amplitude) {
        int sign = Mth.sign(amplitude);
        amplitude = Math.abs(amplitude);
        return sign*Math.abs(amplitude * swingAmount * w);
    }

    public void setUpSitPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.tail.offsetPos(KeyframeAnimations.posVec(0f, -9f, -2f));
        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-27f, 0f, 0f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0.01f, -1f, 0f));
        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-27f, 0f, 0f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(0.01f, -1f, 0f));
        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-90f, 0f, 0f));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0f, -6f, -5f));
        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-90f, 0f, 0f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(0f, -6f, -5f));
        this.mane.offsetRotation(KeyframeAnimations.degreeVec(-18f, 0f, 0f));
        this.mane.offsetPos(KeyframeAnimations.posVec(0f, -2f, 0f));
        this.body.offsetRotation(KeyframeAnimations.degreeVec(-45f, 0f, 0f));
        this.body.offsetPos(KeyframeAnimations.posVec(0f, -4f, -2f));
        this.head.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 0f));
        this.head.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
    }

    public void setupFaintPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.head.offsetRotation(KeyframeAnimations.degreeVec(6.2f, 4.97f, -2.04f));
        this.head.offsetPos(KeyframeAnimations.posVec(0f, -1f, -0.5f));
        this.body.offsetRotation(KeyframeAnimations.degreeVec(2.5f, 0f, 0f));
        this.body.offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -22.5f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -15f));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -30f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(-1f, -1f, 0f));
        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, -7.5f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(1f, -0.25f, 0f));
        this.tail.offsetRotation(KeyframeAnimations.degreeVec(162.35f, 11.59f, 38.36f));
        this.tail.offsetPos(KeyframeAnimations.posVec(0f, -0.29f, 0.15f));
        this.mane.offsetRotation(KeyframeAnimations.degreeVec(7.5f, 0f, 0f));
        this.mane.offsetPos(KeyframeAnimations.posVec(0f, -1f, 0.75f));
        this.root.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 90f));
        this.root.offsetPos(KeyframeAnimations.posVec(0f, -5f, 0f));
        if (this.earRight.isPresent()) {
            this.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(40.11192f, -29.43433f, -3.24563f));
            this.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.25f, 0f));
        }
        if (this.earLeft.isPresent()) {
            this.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(38.7522f, -4.33973f, 12.33768f));
            this.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.25f, 0f));
        }
    }

    public void setupFaintPose2(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.head.offsetRotation(KeyframeAnimations.degreeVec(4.34f, -4.46f, 16.94f));
        this.head.offsetPos(KeyframeAnimations.posVec(0, -7f, 0.25f));

        this.body.offsetRotation(KeyframeAnimations.degreeVec(-7.5f, 0, 0));
        this.body.offsetPos(KeyframeAnimations.posVec(0, -6.5f, -1.5f));

        this.mane.offsetRotation(KeyframeAnimations.degreeVec(-5, 0, 0));
        this.mane.offsetPos(KeyframeAnimations.posVec(0, -6.75f, 0));

        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(87.38f, -17.48f, 0.79f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(0, -7.5f, -1));

        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(90, 22.5f, 0));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0, -7.5f, -1.25f));

        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-88.01f, 24.9f, -2.33f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(0, -6.75f, 0));

        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-91.14f, -29.72f, 4.31f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0, -7, 0));

        this.tail.xRot = ((Dog) dog).getTailRotation();
        this.tail.offsetRotation(KeyframeAnimations.degreeVec(62.36f, -4.65f, 2.29f));
        this.tail.offsetPos(KeyframeAnimations.posVec(0.17f, -7.48f, -1.35f));

        
        
        if (this.earRight.isPresent()) {
            this.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(51.29f, 23.61f, -14.74f));
            this.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
        if (this.earLeft.isPresent()) {
            this.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(53.71f, -14.26f, 10.25f));
            this.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
    }

    public void setupRestPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.head.offsetRotation(KeyframeAnimations.degreeVec(-7.54f, 0.76f, 2.5f));
        this.head.offsetPos(KeyframeAnimations.posVec(0f, -5f, 2f));
        this.body.offsetRotation(KeyframeAnimations.degreeVec(0.5f, 0f, 0f));
        this.body.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 0f));
        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-90f, 22.5f, 0f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(-0.5f, -7f, 1f));
        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-90f, -22.5f, 0f));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0.5f, -7f, 1f));
        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, 14.98539f, 0.66963f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, -14.98539f, -0.66963f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        this.tail.xRot = ((Dog) dog).getTailRotation();
        this.tail.offsetRotation(KeyframeAnimations.degreeVec(0f, -40f, 0f));
        this.tail.offsetPos(KeyframeAnimations.posVec(0f, -7f, -0.25f));
        this.mane.offsetRotation(KeyframeAnimations.degreeVec(-2.5f, 0f, 0f));
        this.mane.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 2f));
        if (this.earRight.isPresent()) {
            this.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, 14.48f, -26.57f));
            this.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
        if (this.earLeft.isPresent()) {
            this.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, -14.48f, 26.57f));
            this.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
    }

    public void setupLyingPose2(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        
        this.head.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 27.5f));
        this.head.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        this.body.offsetRotation(KeyframeAnimations.degreeVec(0.5f, 0f, 0f));
        this.body.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 0f));
        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-90f, 22.5f, 0f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(-0.5f, -7f, 1f));
        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-90f, -22.5f, 0f));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0.5f, -7f, 1f));
        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, 14.98539f, 0.66963f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-87.41193f, -14.98539f, -0.66963f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -6.75f, 2f));
        this.tail.xRot = ((Dog) dog).getTailRotation();
        this.tail.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 0f));
        this.tail.offsetPos(KeyframeAnimations.posVec(0f, -7f, -0.25f));
        this.mane.offsetRotation(KeyframeAnimations.degreeVec(-2.5f, 0f, 0f));
        this.mane.offsetPos(KeyframeAnimations.posVec(0f, -6.5f, 2f));
        if (this.earRight.isPresent()) {
            this.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, 14.48f, -26.57f));
            this.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
        if (this.earLeft.isPresent()) {
            this.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(26.57f, -14.48f, 26.57f));
            this.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.5f, 0f));
        }
    }

    public void setupFlyPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.tail.offsetRotation(KeyframeAnimations.degreeVec(84.84f, -5.13f, -5.65f));
        this.tail.offsetPos(KeyframeAnimations.posVec(0f, -1.18f, -1.63f));
        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-84.33f, 0f, 0f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -1.21f, 0f));
        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-100.98f, 0f, 0f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(0f, -1.21f, 0f));
        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(78.49f, 0f, 0f));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0f, 0f, -1.85f));
        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(59.61f, 0f, 0f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(0f, 0f, -1.85f));
        this.mane.offsetRotation(KeyframeAnimations.degreeVec(-9.67f, 0f, 0f));
        this.mane.offsetPos(KeyframeAnimations.posVec(0f, -1.21f, 0.46f));
        this.head.offsetRotation(KeyframeAnimations.degreeVec(-13.12f, 0f, 0f));
        this.head.offsetPos(KeyframeAnimations.posVec(0f, 0f, 0f));
        this.root.offsetRotation(KeyframeAnimations.degreeVec(0f, 0f, 0f));
        this.root.offsetPos(KeyframeAnimations.posVec(0f, 2.03f, 0f));
        this.body.offsetRotation(KeyframeAnimations.degreeVec(1.02f, 0f, 0f));
        this.body.offsetPos(KeyframeAnimations.posVec(0f, -1.03f, -1.32f));

        if (this.earRight.isPresent()) {
            this.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(-55.69f, 2.98f, -3.43f));
            this.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.35f, 0f));
        }
        if (this.earLeft.isPresent()) {
            this.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(-55.69f, -2.98f, 3.43f));
            this.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.35f, 0f));
        }
        
    }

    public void setupDrownPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.head.offsetRotation(KeyframeAnimations.degreeVec(35, 0, 0));
        this.head.offsetPos(KeyframeAnimations.posVec(0, -0.5f, 0.25f));

        this.body.offsetRotation(KeyframeAnimations.degreeVec(-22.5f, 0, 0));
        this.body.offsetPos(KeyframeAnimations.posVec(0, 1.5f, -2.5f));

        this.mane.offsetRotation(KeyframeAnimations.degreeVec(10, 0, 0));
        this.mane.offsetPos(KeyframeAnimations.posVec(0, 1, 0));

        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(0, 0, -3.5f));

        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0, 0, -3.5f));

        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(0, 2, 0));

        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(0, 0, 0));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0, 1, 0));

        this.tail.xRot = ((Dog) dog).getTailRotation();
        this.tail.offsetRotation(KeyframeAnimations.degreeVec(10, 0, 0));
        this.tail.offsetPos(KeyframeAnimations.posVec(0, -0.5f, -2f));
    }

    public void setupRestBellyPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.head.offsetRotation(KeyframeAnimations.degreeVec(-38.87f, -25.09f, -23.41f));
        this.head.offsetPos(KeyframeAnimations.posVec(-0.01f, -1.9f, 0.36f));
        this.body.offsetRotation(KeyframeAnimations.degreeVec(-18.36f, 14.74f, 22.42f));
        this.body.offsetPos(KeyframeAnimations.posVec(0.21f, -3.81f, -0.83f));
        this.legBackRight.offsetRotation(KeyframeAnimations.degreeVec(-26.7f, 14.23f, 47.53f));
        this.legBackRight.offsetPos(KeyframeAnimations.posVec(-0.09f, -3.49f, -0.65f));
        this.legBackLeft.offsetRotation(KeyframeAnimations.degreeVec(-38.05f, -3.87f, 0f));
        this.legBackLeft.offsetPos(KeyframeAnimations.posVec(0.09f, -6.17f, -2.3f));
        this.legFrontRight.offsetRotation(KeyframeAnimations.degreeVec(-42.32f, 19.74f, 41.58f));
        this.legFrontRight.offsetPos(KeyframeAnimations.posVec(-0.5f, -2f, 1.18f));
        this.legFrontLeft.offsetRotation(KeyframeAnimations.degreeVec(-29.77f, 2.06f, -2.6f));
        this.legFrontLeft.offsetPos(KeyframeAnimations.posVec(0f, -2f, 0.35f));
        this.tail.offsetRotation(KeyframeAnimations.degreeVec(0f, 29.9f, 0f));
        this.tail.offsetPos(KeyframeAnimations.posVec(1.24f, -6.7f, -1.49f));
        this.mane.offsetRotation(KeyframeAnimations.degreeVec(-12.78f, 7.97f, 26.29f));
        this.mane.offsetPos(KeyframeAnimations.posVec(0f, -2.79f, 0.35f));
        this.root.offsetRotation(KeyframeAnimations.degreeVec(22.48072f, -0.95645f, 132.30991f));
        this.root.offsetPos(KeyframeAnimations.posVec(2f, -8f, 0f));

        if (this.earRight.isPresent()) {
            this.earRight.get().offsetRotation(KeyframeAnimations.degreeVec(37.09314f, 13.50103f, -46.63682f));
            this.earRight.get().offsetPos(KeyframeAnimations.posVec(0f, -0.12f, 0f));
        }
        if (this.earLeft.isPresent()) {
            this.earLeft.get().offsetRotation(KeyframeAnimations.degreeVec(32.49638f, 13.81807f, -32.61916f));
            this.earLeft.get().offsetPos(KeyframeAnimations.posVec(0f, -0.51f, 0f));
        }
    }

    public void translateShakingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.mane.zRot = dog.getShakeAngle(partialTickTime, -0.08F);
        this.body.zRot = dog.getShakeAngle(partialTickTime, -0.16F);
        this.realTail.zRot = dog.getShakeAngle(partialTickTime, -0.2F);
    }

    public void translateBeggingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.realHead.zRot = dog.getInterestedAngle(partialTickTime) + dog.getShakeAngle(partialTickTime, 0.0F);
    }

    Vector3f vecObj = new Vector3f();
    private float headXRot0 = 0, headYRot0 = 0, realHeadZRot0 = 0;

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        var pose = dog.getDogPose();
        var animationManager = dog.animationManager;

        if (pose.freeHead) {
            this.head.xRot += headPitch * ((float)Math.PI / 180F); 
            this.head.yRot += netHeadYaw * (dog.isInSittingPose() && dog.isLying() ? 0.005F : (float)Math.PI / 180F);
        }
        if (pose.freeTail) {
            this.tail.xRot = dog.getTailRotation();
            this.tail.yRot = dog.getWagAngle(limbSwing, limbSwingAmount, ageInTicks);
        }

        var animState = animationManager.animationState;
        var anim = dog.getAnim();
        if (anim == DogAnimation.NONE) return;
        var sequence = this.getAnimationSequence(anim);
        if (sequence == null) return;
        if (pose.freeHead && anim.freeHead()) {
            headXRot0 = this.head.xRot;
            headYRot0 = this.head.yRot;
            realHeadZRot0 = this.realHead.zRot;
        } else if (pose.freeHead && anim.freeHeadXRotOnly()) {
            headXRot0 = this.head.xRot;
        }

        anim.rootRotation().ifPresent(x -> {
            this.root.yRot += x * Mth.DEG_TO_RAD;
        });
        
        if (animState.isStarted()) {
            animState.updateTime(ageInTicks, anim.getSpeedModifier());
            DogKeyframeAnimations.animate(this, dog, sequence, animState.getAccumulatedTimeMillis(), 1.0F, vecObj);
        }
    }

    protected AnimationDefinition getAnimationSequence(DogAnimation anim) {
        return DogAnimationRegistry.getSequence(anim);
    }

    public void resetAllPose() {
        this.root.getAllParts().forEach(x -> x.resetPose());
        this.realHead.resetPose();
        this.realTail.resetPose();
        this.earLeft.ifPresent(ear -> ear.resetPose());
        this.earRight.ifPresent(ear -> ear.resetPose());
    }

    public void copyFrom(DogModel dogModel) {
        this.root.copyFrom(dogModel.root);
        this.head.copyFrom(dogModel.head);
        this.realHead.copyFrom(dogModel.realHead);
        this.body.copyFrom(dogModel.body);
        this.mane.copyFrom(dogModel.mane);
        this.legBackRight.copyFrom(dogModel.legBackRight);
        this.legBackLeft.copyFrom(dogModel.legBackLeft);
        this.legFrontRight.copyFrom(dogModel.legFrontRight);
        this.legFrontLeft.copyFrom(dogModel.legFrontLeft);
        this.tail.copyFrom(dogModel.tail);
        this.realTail.copyFrom(dogModel.realTail);
    }

    public void resetPart(ModelPart part, Dog dog) {
        if (part == this.tail && dog.getAnim().freeTail()) {
            this.tail.resetPose();
            this.tail.xRot = dog.getTailRotation();
            return;
        }
        if (part == this.head && dog.getAnim().freeHead() && dog.getDogPose().freeHead) {
            this.head.resetPose();
            this.head.xRot = headXRot0;
            this.head.yRot = headYRot0;
            this.realHead.resetPose();
            this.realHead.zRot = realHeadZRot0;
            return;
        }
        if (part == this.head && dog.getAnim().convertHeadZRot()) {
            this.head.resetPose();
            this.head.xRot = headXRot0;
            return;
        }
        part.resetPose();
    }

    public void adjustAnimatedPart(ModelPart part, Dog dog) {
        if (part == this.tail && dog.getAnim().freeTail()) {
            if (part.xRot > 3f) {
                part.xRot = 3f;
            }
        }
        if (part == this.head && part.zRot != 0 && dog.getAnim().convertHeadZRot()) {
            this.realHead.zRot = part.zRot;
            part.zRot = 0;
        }
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

    protected void correctInitalPose() {
        var tailPose = this.tail.getInitialPose();
        float tailX = tailPose.x, tailY = tailPose.y, tailZ = tailPose.z;
        this.tail.setInitialPose(PartPose.offset(tailX, tailY, tailZ));
    }

    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        return true;
    }

    public boolean armorShouldRender(Dog dog) {
        return true;
    }

    public boolean incapShouldRender(Dog dog) {
        return true;
    }

    public boolean scaleBabyDog() {
        return true;
    }

    public boolean warnAccessory(Dog dog, Accessory inst)  {
        return false;
    }

    /**
     * Custom pivot point <b>in Minecraft format</b>
     * to convert from Blockbench, simply negate x, y
     * then add 24 to y.
     * @return
     */
    public @Nullable Vector3f getCustomRootPivotPoint() {
        return null;
    }

    public boolean hasDefaultScale() {
        return false;
    }

    public float getDefaultScale() {
        return 1f;
    }

    public static enum AccessoryState {
        NON_COMPATIBLE,
        SOME_WILL_FIT,
        HAVE_NOT_TESTED,
        RECOMMENDED
    }

    private AccessoryState accessoryState = AccessoryState.HAVE_NOT_TESTED;
    public AccessoryState getAccessoryState() {
        return accessoryState;
    }

    public DogModel setAccessoryState(AccessoryState state) {
        if (state == null) state = AccessoryState.HAVE_NOT_TESTED;
        this.accessoryState = state;
        return this;
    }

    public void setVisible(boolean visible) {
        this.head.visible = visible;
        this.body.visible = visible;
        this.legBackRight.visible = visible;
        this.legBackLeft.visible = visible;
        this.legFrontRight.visible = visible;
        this.legFrontLeft.visible = visible;
        this.tail.visible = visible;
        this.mane.visible = visible;
    }

    @Override
    public void copyPropertiesTo(EntityModel<Dog> model) {
        super.copyPropertiesTo(model);
        if (!this.scaleBabyDog())
            model.young = false;
    }

    protected float wetShade = 1f;

    public void setWetShade(float shade) {
        this.wetShade = shade;
    }
    
    public void resetWetShade() {
        wetShade = 1f;
    }

    @Override
    public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
        p_102038_ *= this.wetShade;
        p_102039_ *= this.wetShade;
        p_102040_ *= this.wetShade;
        
        var pivot = DEFAULT_ROOT_PIVOT;
        var custom_pivot = getCustomRootPivotPoint();
        if (custom_pivot != null) {
            pivot = custom_pivot;
        }
        p_102034_.pushPose();
        p_102034_.translate((double)(root.x / 16.0F), (double)(root.y / 16.0F), (double)(root.z / 16.0F));
        p_102034_.translate((double)(pivot.x / 16.0F), (double)(pivot.y / 16.0F), (double)(pivot.z / 16.0F));
        if (root.zRot != 0.0F) {
            p_102034_.mulPose(Axis.ZP.rotation(root.zRot));
        }

        if (root.yRot != 0.0F) {
            p_102034_.mulPose(Axis.YP.rotation(root.yRot));
        }

        if (root.xRot != 0.0F) {
            p_102034_.mulPose(Axis.XP.rotation(root.xRot));
        }
        float xRot0 = root.xRot, yRot0 = root.yRot, zRot0 = root.zRot;
        float x0 = root.x, y0 = root.y, z0 = root.z;
        root.xRot = 0; root.yRot = 0; root.zRot = 0;
        root.x = 0; root.y = 0; root.z = 0;
        p_102034_.pushPose();
        p_102034_.translate((double)(-pivot.x / 16.0F), (double)(-pivot.y / 16.0F), (double)(-pivot.z / 16.0F));
        
        if (this.young && this.scaleBabyDog()) {

            boolean headVisible0 = this.head.visible;
            
            this.head.visible = false;
            p_102034_.pushPose();
            //float f1 = 1.0F / 2f;
            //p_102034_.scale(f1, f1, f1);
            //p_102034_.translate(0.0D, (double)(24 / 16.0F), 0.0D);
            this.root.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
            p_102034_.popPose();
            
            this.head.visible = headVisible0;
            p_102034_.pushPose();
            //p_102034_.translate(0.0D, (double)(5f / 16.0F), (double)(2f / 16.0F));
            p_102034_.scale(2, 2, 2);
            p_102034_.translate(0, -0.5, 0.15);
            this.head.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
            p_102034_.popPose();            
        } else {
            this.root.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
        }

        p_102034_.popPose();
        p_102034_.popPose();
        root.xRot = xRot0; root.yRot = yRot0; root.zRot = zRot0;
        root.x = x0; root.y = y0; root.z = z0;
    }
}
