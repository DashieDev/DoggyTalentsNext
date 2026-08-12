package doggytalents.client.entity.model.dog;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.joml.Quaternionf;
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
import doggytalents.client.entity.model.animation.DTNAnimationLoader;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.client.entity.model.animation.DTNAnimationLoader.DogAnimationHolder;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations.AnimationContext;
import doggytalents.client.entity.model.util.DogModelRenderType;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogClassicalAnimationState;
import doggytalents.common.entity.anim.DogPose;
import doggytalents.common.entity.anim.DogAnimationManager.BlendState;
import doggytalents.common.entity.anim.DogAnimationManager.DogCapturedProceduralState;
import doggytalents.common.util.Util;
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
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;

public class DogModel extends EntityModel<Dog> {

    public final DogModelRenderType dogModelRendserType;

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

    private final AnimSnapshot animSnapshot1 = new AnimSnapshot();
    private final AnimSnapshot animSnapshot2 = new AnimSnapshot();

    private final DogAnimationHolder WALK_SLOW_TROT = DTNAnimationLoader.INSTANCE.getAnim("slow_trot");
    private final DogAnimationHolder WALK_GALLOP = DTNAnimationLoader.INSTANCE.getAnim("gallop");

    public DogModel(ModelPart box) {
        this(box, DogModelRenderType.CUTOUT);
    }

    public DogModel(ModelPart box, DogModelRenderType renderType) {
        super(renderType.renderType());
        this.dogModelRendserType = renderType;

        initDogModel(box);
    }

    protected void initDogModel(ModelPart box) {
        populateMandatoryParts(box);
        this.addOptionalParts(box);
        this.correctInitalPose();
    }

    private final void populateMandatoryParts(ModelPart box) {
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

    protected void correctInitalPose() {
        var tailPose = this.tail.getInitialPose();
        float tailX = tailPose.x, tailY = tailPose.y, tailZ = tailPose.z;
        this.tail.setInitialPose(PartPose.offset(tailX, tailY, tailZ));
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayerInternal(CubeDeformation.NONE);
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

        //Do nothing

    }

    private void setupProceduralPose(
        Dog dog, 
        float limbSwing, float limbSwingAmount, 
        float relativeHeadYRot, float headPitch,
        float pticks, float ageInTicks
    ) {
        
        
        final var anim = dog.getAnim();
        final boolean playing_full_anim = this.playingFullAnim(dog, pticks);
        final var anim_manager = dog.animationManager;

        final var captured_procedural = 
            anim_manager.getBlendState(pticks).hasProceduralCapture() ? 
                dog.animationManager.capturedProcedural
                : DogCapturedProceduralState.NONE;
        
        var pose = dog.getDogPose();
        if (!captured_procedural.isNone()) {
            pose = captured_procedural.pose();
        }

        final boolean should_beg =
            pose.canBeg
            && (!playing_full_anim || anim.freeHead());

        final float shake_value = !captured_procedural.isNone() ? 
            captured_procedural.shakeAnim() : dog.getDogClassicalShakeAnim(pticks);
        final float beg_value = !captured_procedural.isNone() ? 
            captured_procedural.begAnim() : dog.getDogClassicalBegAnim(pticks);

        if (!playing_full_anim) {
            boolean stand_pose = !DogPoseSetups.setupPose(pose, this, dog, limbSwing, limbSwingAmount, pticks);
            if (stand_pose)
                this.setUpStandPose(dog, limbSwing, limbSwingAmount, pticks);

            if (pose.canShake)
                this.translateShakingDog(dog, shake_value, limbSwing, limbSwingAmount, pticks);
        }

        if (should_beg)
            this.translateBeggingDog(dog, shake_value, beg_value, limbSwing, limbSwingAmount, pticks);

        if (pose.freeHead) {
            this.head.xRot += headPitch * ((float)Math.PI / 180F); 
            this.head.yRot += relativeHeadYRot *  Mth.DEG_TO_RAD;
        }
        if (pose.freeTail) {
            this.tail.xRot = dog.getTailRotation();
            this.tail.yRot = DogClassicalAnimationState.wagAngle(limbSwing, limbSwingAmount, ageInTicks);
        }
    }

    private boolean playingFullAnim(Dog dog, float pticks) {
        return dog.animationManager.playingFullAnim(pticks);
    }

    public void setUpStandPose(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {
        animateWalkAndRun(dog, limbSwing, limbSwingAmount, partialTickTime);
    }

    public void animateWalkAndRun(Dog dog, float limbSwing, float limbSwingAmount, float partialTickTime) {        
        final var pose_1 = this.animSnapshot1;
        final var pose_2 = this.animSnapshot2;

        var walk_pos = dog.dogWalkAnimation.position(partialTickTime);
        var walk_speed = dog.dogWalkAnimation.speed(partialTickTime);
        var anim_context = AnimationContext.of(
            this::searchForPartWithName, 
            x -> x.resetPose());
        
        final long walk_animation_start = 830;
        long time = walk_animation_start + Util.tickMayWithPartialToMillis(walk_pos * 2.5);
        float anim_swing = walk_speed <= 0.2f ? walk_speed/0.2f : 1;
        anim_swing = Mth.clamp(anim_swing, 0, 1);
        
        this.resetAllPose();
        if (anim_swing > Mth.EPSILON) {
            DogKeyframeAnimations.keyframeAnimate(anim_context, WALK_SLOW_TROT.get(), time, anim_swing, vecObj);
        }
        
        var anim_blend = dog.dogWalkAnimation.runningBlend(partialTickTime);
        if (anim_blend > Mth.EPSILON) {
            pose_1.store(this);
            this.resetAllPose();
            DogKeyframeAnimations.keyframeAnimate(anim_context, WALK_GALLOP.get(), time / 2, 1, vecObj);
            pose_2.store(this);
            AnimSnapshot.blendAndApply(anim_blend, pose_1, pose_2, this);
        }
    }

    public void translateShakingDog(Dog dog, float shakeValue, float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.mane.zRot = DogClassicalAnimationState.shakeAngle(shakeValue, -0.08F);
        this.body.zRot = DogClassicalAnimationState.shakeAngle(shakeValue, -0.16F);
        this.realTail.zRot = DogClassicalAnimationState.shakeAngle(shakeValue, -0.2F);
    }

    public void translateBeggingDog(Dog dog, float shakeValue, float begValue, 
        float limbSwing, float limbSwingAmount, float partialTickTime) {
        this.realHead.zRot = DogClassicalAnimationState.begAngle(begValue)
            + DogClassicalAnimationState.shakeAngle(shakeValue, 0);
    }

    Vector3f vecObj = new Vector3f();

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float relativeHeadYRot, float headPitch) {

        this.resetAllPose();

        if (dog.isDogInAnimDebug() && dog.getAnim().isNone()) {
            setDogUpDebugAnim(dog);
            return;
        }

        final float pticks = ageInTicks - dog.tickCount;
        
        this.setupProceduralPose(
            dog, 
            limbSwing, limbSwingAmount, 
            relativeHeadYRot, headPitch, 
            pticks, ageInTicks
        );

        final var anim_manager = dog.animationManager;
        final var anim = dog.getAnim();

        final boolean is_procedural_only = 
            anim.isNone() && anim_manager.getBlendState(pticks).isNone();

        if (is_procedural_only)
            return;

        final var cached_procedural_val =
            new CachedProceduralValues(this.head.xRot, this.head.yRot, this.realHead.zRot);

        final long anim_time_millis = dog.animationManager.animationState
            .updateTimeAndGet(ageInTicks, anim.getSpeedModifier());

        if (this.playingFullAnim(dog, pticks)) {
            setupKeyframeAnimationPose(dog, dog.getAnim(), anim_time_millis, cached_procedural_val);
            return;
        }
        
        final var blend_state = anim_manager.getBlendState(pticks);

        final var pose_A = this.animSnapshot1;
        final var pose_B = this.animSnapshot2;
        
        //TODO Move this into setupProceduralPose.
        if (blend_state.hasProceduralCapture() && !anim.freeHeadXRot()) {
            this.head.xRot = dog.animationManager.capturedProcedural.headXRot() * Mth.DEG_TO_RAD; 
        }

        if (blend_state == BlendState.ANIM_TO_ANIM) {
            final var captured_keyframe = dog.animationManager.capturedKeyframeAnim;

            if (!captured_keyframe.isNone()) {
                setupKeyframeAnimationPose(dog, captured_keyframe.anim(), 
                    captured_keyframe.timestampMillis(), cached_procedural_val);
            }
        }

        pose_A.store(this);

        if (blend_state == BlendState.BLEND_OUT) {
            final var captured_keyframe = dog.animationManager.capturedKeyframeAnim;

            if (!captured_keyframe.isNone()) {
                setupKeyframeAnimationPose(dog, captured_keyframe.anim(), 
                    captured_keyframe.timestampMillis(), cached_procedural_val);
            }
        } else {
            setupKeyframeAnimationPose(dog, dog.getAnim(), anim_time_millis, cached_procedural_val);
        }
        
        pose_B.store(this);

        final float blend_progress = blend_state == BlendState.BLEND_OUT ?
            anim_manager.getBlendOutProgress(pticks)
            : anim_manager.getBlendInProgress(pticks);

        if (anim.blendIn().blendHeadRotAndChildrenOnly()) {
            AnimSnapshot.blendAndApplyHeadRotAndChildrenOnly(blend_progress, pose_A, pose_B, this);   
        } else {
            AnimSnapshot.blendAndApply(blend_progress, pose_A, pose_B, this);
        }
    }

    private boolean setupKeyframeAnimationPose(Dog dog, 
        DogAnimation anim, long animTimeMillis, 
        CachedProceduralValues proceduralValues) {

        if (anim.isNone()) 
            return false;

        var sequence = this.getAnimationSequence(anim);
        if (sequence == null) 
            return false;

        resetAllPoseForAnim(dog, anim, proceduralValues);
            
        DogKeyframeAnimations.animate(this, dog, sequence, animTimeMillis, 1.0F, vecObj);

        return true;
    }

    private static record CachedProceduralValues(
        float headXRot, float headYRot, float realHeadZRot 
    ) {}

    private void resetAllPoseForAnim(Dog dog, DogAnimation anim, CachedProceduralValues proceduralValues) {
        this.resetAllPose();
        
        if (anim.freeTail()) {
            this.tail.xRot = dog.getTailRotation();
        }

        if (anim.freeHead() && dog.getDogPose().freeHead) {
            this.head.xRot = proceduralValues.headXRot;
            this.head.yRot = proceduralValues.headYRot;
            this.realHead.zRot = proceduralValues.realHeadZRot;
        }

        if (anim.freeHeadXRotOnly()) {
            this.head.xRot = proceduralValues.headXRot;
        }

        anim.rootRotation().ifPresent(x -> {
            this.root.yRot = x * Mth.DEG_TO_RAD;
        });
    }

    private void setDogUpDebugAnim(Dog dog) {
        this.resetAllPose();
        var debug_state = dog.getDogAnimDebugState();
        var dog_anim = debug_state.anim();
        var sequence = DogAnimationRegistry.getSequence(dog_anim);
        if (sequence == null)
            return;
        var timestamp_millis = Util.tickMayWithPartialToMillis(debug_state.timestamp());
        DogKeyframeAnimations.animate(this, dog, sequence, timestamp_millis, 1.0F, vecObj);
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
        //Do nothing
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
        return DogKeyframeAnimations.searchForPartWithName(this.root, name);
    }

    public boolean useDefaultModelForAccessories() {
        return false;
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

    public boolean renderDogWetShade() {
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

    public static enum AccessoryState implements StringRepresentable {
        NON_COMPATIBLE,
        SOME_WILL_FIT,
        HAVE_NOT_TESTED,
        RECOMMENDED,
        MODEL_ONLY;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
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
    public void renderToBuffer(PoseStack stack, VertexConsumer vertex_consumer, int light, int overlay, int color_overlay) {
        if (renderDogWetShade()) {
            int wet_color = FastColor.ARGB32.colorFromFloat(1, this.wetShade, this.wetShade, this.wetShade);
            color_overlay = FastColor.ARGB32.multiply(color_overlay, wet_color);
        }
        
        var ctx = DogModelRenderContext.forDogModelRendering(this, vertex_consumer, light, overlay, color_overlay, this.getDogModelAdditionalHeadRenderer());
        renderDogModelFromRootWithPivot(stack, ctx);
    }

    protected Optional<AddtionalHeadRenderer> getDogModelAdditionalHeadRenderer() {
        return Optional.empty();
    }
    
    private Optional<ModelPart> getDogModelBabyHead() {
        return this.doScaleBabyHead() ? Optional.of(this.head) : Optional.empty();
    }

    protected boolean doScaleBabyHead() {
        return this.young && this.scaleBabyDog();
    }
    
    public static void renderDogModelFromRootWithPivot(PoseStack stack, DogModelRenderContext ctx) {
        
        var root = ctx.root();
        var pivot = ctx.pivot();
        var head_baby = ctx.headBaby();
        var part_ctx = ctx.renderPartContext().orElse(null);
        var addtional_head = ctx.additionalHeadRenderer().orElse(null);
        
        stack.pushPose();
        applyRootTransformWithPivotedRotation(root, stack, pivot);

        var stashed_root = RootRotationTranslationStash.stash(root);
        
        if (head_baby.isPresent()) {
            var head = head_baby.get();

            boolean headVisible0 = head.visible;
            head.visible = false;
            stack.pushPose();
            if (part_ctx != null)
                part_ctx.renderPart(stack, root);
            stack.popPose();
            head.visible = headVisible0;

            stack.pushPose();
            stack.scale(2, 2, 2);
            stack.translate(0, -0.5, 0.15);
            if (part_ctx != null)
                part_ctx.renderPart(stack, head);
            if (addtional_head != null)
                addtional_head.render(stack, ctx.renderPartContext());
            stack.popPose();             
        } else {
            if (part_ctx != null)
                part_ctx.renderPart(stack, root);
            if (addtional_head != null)
                addtional_head.render(stack, ctx.renderPartContext());
        }

        stack.popPose();
        stashed_root.restore(root);
    }

    public static record DogModelRenderContext(
        ModelPart root, Vector3f pivot, Optional<ModelPart> headBaby, 
        Optional<DogRenderPartContext> renderPartContext,
        Optional<AddtionalHeadRenderer> additionalHeadRenderer
    ) {
        public static DogModelRenderContext forDogModelRendering(DogModel model, VertexConsumer vertex_consumer, int light, int overlay, int color_overlay, 
            Optional<AddtionalHeadRenderer> addtionalHeadRenderer) {
            var pivot = DEFAULT_ROOT_PIVOT;
            var custom_pivot = model.getCustomRootPivotPoint();
            if (custom_pivot != null) {
                pivot = custom_pivot;
            }
            var render_part_ctx = new DogRenderPartContext(vertex_consumer, light, overlay, color_overlay);
            return new DogModelRenderContext(model.root, 
                pivot, model.getDogModelBabyHead(), Optional.of(render_part_ctx), addtionalHeadRenderer);
        }
    }

    public static record DogRenderPartContext(VertexConsumer vertex_consumer, int light, int overlay, int color_overlay) {
        
        public void renderPart(PoseStack stack, ModelPart part) {
            part.render(stack, vertex_consumer(), light(), overlay(), color_overlay());
        }

        public void renderGlowingPart(PoseStack stack, ModelPart part) {
            part.render(stack, vertex_consumer(), 15728880, overlay(), color_overlay());
        }

    }
    
    @FunctionalInterface
    public static interface AddtionalHeadRenderer {
        void render(PoseStack stack, Optional<DogRenderPartContext> part_ctx);
    }

    private static void applyRootTransformWithPivotedRotation(ModelPart root, PoseStack stack, Vector3f pivot) {
        //Translation
        stack.translate(root.x / 16f, root.y / 16f, root.z / 16f);
        
        //Rotation with pivot
        stack.translate(pivot.x / 16f, pivot.y / 16f, pivot.z / 16f);
        if (root.xRot != 0.0F || root.yRot != 0.0F || root.zRot != 0.0F) {
            stack.mulPose(new Quaternionf().rotationZYX(root.zRot, root.yRot, root.xRot));
        }
        stack.translate(-pivot.x / 16f, -pivot.y / 16f, -pivot.z / 16f);
    }

    private static record RootRotationTranslationStash(
        float x, float y, float z, 
        float xRot, float yRot, float zRot) {

        public static RootRotationTranslationStash stash(ModelPart root) {
            var ret = new RootRotationTranslationStash(
                root.x, root.y, root.z,
                root.xRot, root.yRot, root.zRot);
            root.x = 0; root.y = 0; root.z = 0;
            root.xRot = 0; root.yRot = 0; root.zRot = 0;
            return ret;
        }

        public void restore(ModelPart root) {
            root.x = this.x; root.y = this.y; root.z = this.z;
            root.xRot = this.xRot; root.yRot = this.yRot; root.zRot = this.zRot;
        }
    }
}
