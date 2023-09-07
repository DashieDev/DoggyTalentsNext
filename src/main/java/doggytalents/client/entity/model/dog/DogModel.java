package doggytalents.client.entity.model.dog;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import doggytalents.api.enu.forward_imitate.anim.DogModelPart;
import doggytalents.api.enu.forward_imitate.anim.KeyframeAnimations;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.animation.KeyframeAnimationsDelegate;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
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

    public DogModelPart head;
    public DogModelPart realHead;
    public DogModelPart body;
    public DogModelPart mane;
    public DogModelPart legBackRight;
    public DogModelPart legBackLeft;
    public DogModelPart legFrontRight;
    public DogModelPart legFrontLeft;
    public DogModelPart tail;
    public DogModelPart realTail;
    
    public DogModelPart root;

    public DogModel(ModelPart box) {
        this.root = DogModelPart.recreateFromModelPart(box);
        box = this.root;
        this.head = (DogModelPart)box.getChild("head");
        this.realHead = (DogModelPart)this.head.getChild("real_head");
        this.body = (DogModelPart)box.getChild("body");
        this.mane = (DogModelPart)box.getChild("upper_body");
        this.legBackRight = (DogModelPart)box.getChild("right_hind_leg");
        this.legBackLeft = (DogModelPart)box.getChild("left_hind_leg");
        this.legFrontRight = (DogModelPart)box.getChild("right_front_leg");
        this.legFrontLeft = (DogModelPart)box.getChild("left_front_leg");
        this.tail = (DogModelPart)box.getChild("tail");
        this.realTail = (DogModelPart)this.tail.getChild("real_tail");
        this.correctInitalPose();
    }

    public DogModel(ModelPart box, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.root = DogModelPart.recreateFromModelPart(box);
        box = this.root;
        this.head = (DogModelPart)box.getChild("head");
        this.realHead = (DogModelPart)this.head.getChild("real_head");
        this.body = (DogModelPart)box.getChild("body");
        this.mane = (DogModelPart)box.getChild("upper_body");
        this.legBackRight = (DogModelPart)box.getChild("right_hind_leg");
        this.legBackLeft = (DogModelPart)box.getChild("left_hind_leg");
        this.legFrontRight = (DogModelPart)box.getChild("right_front_leg");
        this.legFrontLeft = (DogModelPart)box.getChild("left_front_leg");
        this.tail = (DogModelPart)box.getChild("tail");
        this.realTail = (DogModelPart)this.tail.getChild("real_tail");
        this.correctInitalPose();
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
        PartDefinition var3 = var1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, 13.5F, -7.0F));
        var real_head = var3.addOrReplaceChild("real_head", CubeListBuilder.create()
                // Head
                .texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, scale)
                // Nose
                .texOffs(0, 10).addBox(-0.5F, -0.001F, -5.0F, 3.0F, 3.0F, 4.0F, scale)
                , PartPose.ZERO);
        var ear_normal = real_head.addOrReplaceChild("ear_normal", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale)
            .texOffs(16, 14).addBox(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, scale)
        ,PartPose.ZERO);
        var1.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(18, 14).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, scale)
        , PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        var1.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(21, 0).addBox(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, scale), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        CubeListBuilder var4 = CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale);
        var1.addOrReplaceChild("right_hind_leg", var4, PartPose.offset(-2.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("left_hind_leg", var4, PartPose.offset(0.5F, 16.0F, 7.0F));
        var1.addOrReplaceChild("right_front_leg", var4, PartPose.offset(-2.5F, 16.0F, -4.0F));
        var1.addOrReplaceChild("left_front_leg", var4, PartPose.offset(0.5F, 16.0F, -4.0F));
        PartDefinition var5 = var1.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        var5.addOrReplaceChild("real_tail", CubeListBuilder.create()
                .texOffs(9, 18).addBox(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, scale)
        , PartPose.ZERO);
        return LayerDefinition.create(var0, 64, 32);
    }
    @Override
    public void prepareMobModel(Dog d, float limbSwing, float limbSwingAmount, float partialTickTime) {
        
        if (!(d instanceof Dog dog))
            return;

        var pose = dog.getDogPose();

        var anim = dog.getAnim();
        if (anim != DogAnimation.NONE) return;
    
        if (!pose.canShake)
        this.resetShakingDog(dog, limbSwing, limbSwingAmount, partialTickTime);

        if (!pose.canBeg)
        this.resetBeggingDog(dog, limbSwing, limbSwingAmount, partialTickTime);

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
            case LYING:
                this.setupLyingPose(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case LYING_2:
                this.setupLyingPose2(dog, limbSwing, limbSwingAmount, partialTickTime);
                break;
            case DROWN:
                this.setupDrownPose(dog, limbSwing, limbSwingAmount, partialTickTime);
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

        this.body.setPos(0.0F, 14.0F, 2.0F);
        this.body.xRot = ((float) Math.PI / 2F);
        this.mane.setPos(-1.0F, 14.0F, -3.0F);
        this.mane.xRot = this.body.xRot;
        this.tail.setPos(-1.0F, 12.0F, 8.0F);
        this.legBackRight.setPos(-2.5F, 16.0F, 7.0F);
        this.legBackLeft.setPos(0.5F, 16.0F, 7.0F);
        this.legFrontRight.setPos(-2.5F, 16.0F, -4.0F);
        this.legFrontLeft.setPos(0.5F, 16.0F, -4.0F);
        this.legBackRight.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackLeft.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.head.setPos(-1.0F, 13.5F, -7.0F);
        this.legFrontRight.yRot = 0.0F;
        this.legFrontLeft.yRot = 0.0F;
    }

    public void setUpSitPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.head.setPos(-1.0F, 13.5F, -7.0F);
        this.mane.setPos(
                -1f + MANE_SITTING_OFF[0],
                14f + MANE_SITTING_OFF[1],
                -3f + MANE_SITTING_OFF[2]);
        this.mane.xRot = ((float) Math.PI * 2F / 5F);
        this.mane.yRot = 0.0F;
        this.body.setPos(0.0F, 18.0F, 0.0F);
        this.body.xRot = ((float) Math.PI / 4F);
        this.tail.setPos(-1F, 21.0F, 6.0F);
        this.legBackRight.setPos(-2.5F, 22.0F, 2.0F);
        this.legBackRight.xRot = ((float) Math.PI * 3F / 2F);
        this.legBackLeft.setPos(0.5F, 22.0F, 2.0F);
        this.legBackLeft.xRot = ((float) Math.PI * 3F / 2F);
        this.legFrontRight.xRot = 5.811947F;
        this.legFrontRight.setPos(-2.49F, 17.0F, -4.0F);
        this.legFrontLeft.xRot = 5.811947F;
        this.legFrontLeft.setPos(0.51F, 17.0F, -4.0F);

        this.head.setPos(-1.0F, 13.5F, -7.0F);
        this.legFrontRight.yRot = 0;
        this.legFrontLeft.yRot = 0;
    }

    public void setupFaintPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.head.zRot += 90 * Mth.DEG_TO_RAD;
        this.head.x += 2;
        this.head.y += 7;

        this.body.zRot += 90 * Mth.DEG_TO_RAD;
        this.body.x += 2;
        this.body.y += 6.75;

        this.legBackRight.zRot += 80 * Mth.DEG_TO_RAD;
        this.legBackRight.x += 2;
        this.legBackRight.y += 2.25;

        this.legBackLeft.zRot += 90 * Mth.DEG_TO_RAD;
        this.legBackLeft.x += -1;
        this.legBackLeft.y += 5.75;

        this.legFrontRight.zRot += 75 * Mth.DEG_TO_RAD;
        this.legFrontRight.x += 2;
        this.legFrontRight.y += 2;

        this.legFrontLeft.zRot += 90 * Mth.DEG_TO_RAD;
        this.legFrontLeft.x += -1;
        this.legFrontLeft.y += 6;

        this.tail.xRot = ((Dog) dog).getTailRotation() + 62.5f * Mth.DEG_TO_RAD;
        this.tail.x += 3.75;
        this.tail.y += 8.25;
        this.tail.z += -0.5;
        
        this.mane.zRot += 90 * Mth.DEG_TO_RAD;
        this.mane.x += 2.5;
        this.mane.y += 5.5;
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

    public void setupLyingPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
         this.head.setPos(-1, 19.5F, -7);
        this.body.setPos(0, 20, 2);
        this.body.xRot = (float) Math.PI / 2F;
        this.mane.setPos(
                -1f + MANE_LYING_OFF[0],
                14f + MANE_LYING_OFF[1],
                -3f + MANE_LYING_OFF[2]);
        this.mane.xRot = this.body.xRot;
        this.tail.setPos(-1, 18, 8);
        this.legBackRight.setPos(-4.5F, 23, 7);
        this.legBackRight.xRot = -(float) Math.PI / 2F;
        this.legBackLeft.setPos(2.5F, 23, 7);
        this.legBackLeft.xRot = -(float) Math.PI / 2F;
        this.legFrontRight.setPos(-4.5F, 23, -4);
        this.legFrontRight.xRot = -(float) Math.PI / 2F;
        this.legFrontLeft.setPos(2.5F, 23, -4);
        this.legFrontLeft.xRot = -(float) Math.PI / 2F;
    }

    public void translateShakingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.mane.zRot = dog.getShakeAngle(partialTickTime, -0.08F);
        this.body.zRot = dog.getShakeAngle(partialTickTime, -0.16F);
        this.realTail.zRot = dog.getShakeAngle(partialTickTime, -0.2F);
    }

    public void resetShakingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.mane.zRot = 0;
        this.body.zRot = 0;
        this.realTail.zRot = 0;
    }

    public void translateBeggingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.realHead.zRot = dog.getInterestedAngle(partialTickTime) + dog.getShakeAngle(partialTickTime, 0.0F);
    }

    public void resetBeggingDog(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.realHead.zRot = 0;
    }

    Vector3f vecObj = new Vector3f();

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        var pose = dog.getDogPose();
        var animationManager = dog.animationManager;

        if (pose.freeHead) {
            this.head.xRot = headPitch * ((float)Math.PI / 180F); 
            this.head.yRot = netHeadYaw * (dog.isInSittingPose() && dog.isLying() ? 0.005F : (float)Math.PI / 180F);
        }
        if (pose.freeTail) {
            this.tail.xRot = dog.getTailRotation();
            this.tail.yRot = dog.getWagAngle(limbSwing, limbSwingAmount, ageInTicks);
        }

        var animState = animationManager.animationState;
        var anim = dog.getAnim();
        if (anim == DogAnimation.NONE) return;
        var sequence = DogAnimationRegistry.getSequence(anim);
        if (sequence == null) return;
        
        if (animState.isStarted()) {
            animState.updateTime(ageInTicks, anim.getSpeedModifier());
            KeyframeAnimationsDelegate.animate(this, dog, sequence, animState.getAccumulatedTime(), 1.0F, vecObj);
        }
    }

    public void resetAllPose() {
        this.root.resetPose();
        this.root.getAllParts().forEach(x -> ((DogModelPart)x).resetPose());
        this.realHead.resetPose();
        this.realTail.resetPose();
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

    public void resetPart(DogModelPart part, Dog dog) {
        if (part == this.tail && dog.getAnim().freeTail()) {
            this.tail.resetPose();
            this.tail.xRot = dog.getTailRotation();
            return;
        }
        part.resetPose();
    }

    public void adjustAnimatedPart(DogModelPart part, Dog dog) {
        if (part == this.tail && dog.getAnim().freeTail()) {
            if (part.xRot > 3f) {
                part.xRot = 3f;
            }
        }
    }

    public Optional<DogModelPart> searchForPartWithName(String name) {
        if (this.root.hasChild(name)) 
            return Optional.of((DogModelPart)this.root.getChild(name));
        if (name.equals("root"))
            return Optional.of((DogModelPart)this.root);
        var partOptional = this.root.getAllParts()
            .filter(part -> ((DogModelPart)part).hasChild(name))
            .findFirst();
        return partOptional.map(part -> (DogModelPart)part.getChild(name));
    }

    protected void correctInitalPose() {
        var tailPose = this.tail.getInitialPose();
        float tailX = tailPose.x, tailY = tailPose.y, tailZ = tailPose.z;
        this.tail.setInitialPose(PartPose.offset(tailX, tailY, tailZ));
    }

    public boolean modelNeedRefreshBeforeNextRender(Dog dog) {
        if (dog.getAnim() != DogAnimation.NONE)
            return true;
        if (dog.getDogPose().needRenderRefresh)
            return true;
        return false;
    }

    public boolean modelNeedRefreshBeforeCurrentRender(Dog dog) {
        if (dog.getAnim() != DogAnimation.NONE)
            return true;
        if (dog.getDogPose().needRenderRefresh)
            return true;
        return false;
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

    public boolean tickClient() { return false; }
    public void doTickClient(Dog dog) {}
    public boolean hasAdditonalRendering() { return false; }
    public void doAdditonalRendering(Dog dog, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {}

    //Decoupled from ColorableAgeableListModel
    private float r = 1.0F;
    private float g = 1.0F;
    private float b = 1.0F;

    public void setColor(float p_102420_, float p_102421_, float p_102422_) {
        this.r = p_102420_;
        this.g = p_102421_;
        this.b = p_102422_;
    }
    //END

    @Override
    public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
        p_102034_.pushPose();
        p_102034_.translate((double)(root.x / 16.0F), (double)(root.y / 16.0F), (double)(root.z / 16.0F));
        p_102034_.translate((double)(0 / 16.0F), (double)(15 / 16.0F), (double)(0 / 16.0F));
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
        p_102034_.translate((double)(0 / 16.0F), (double)(-15 / 16.0F), (double)(0 / 16.0F));
        
        if (this.young) {

            boolean headVisible0 = this.head.visible;
            
            this.head.visible = false;
            p_102034_.pushPose();
            float f1 = 1.0F / 2f;
            p_102034_.scale(f1, f1, f1);
            p_102034_.translate(0.0D, (double)(24 / 16.0F), 0.0D);
            this.root.render(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
            p_102034_.popPose();
            
            this.head.visible = headVisible0;
            p_102034_.pushPose();
            p_102034_.translate(0.0D, (double)(5f / 16.0F), (double)(2f / 16.0F));
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
