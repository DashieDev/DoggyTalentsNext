package doggytalents.client.entity.model.dog;

import java.util.Optional;

import javax.annotation.Nullable;

import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import doggytalents.api.anim.DogAnimation;
import doggytalents.api.events.RegisterCustomDogModelsEvent.DogModelProps;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.Accessory.AccessoryRenderType;
import doggytalents.common.entity.Dog;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;

public class CustomDogModel extends DogModel {

    public final DogModelProps props;
    private ModelPart glowingEyes;
    private ModelPart realGlowingEyes;


    public CustomDogModel(ModelPart box, DogModelProps props) {
        super(box);
        this.props = props;
        if (this.props.glowingEyes)
            setupGlowingEyes();
    }

    private void setupGlowingEyes() {
        this.glowingEyes = this.root.getChild("glowing_eyes");
        this.realGlowingEyes = glowingEyes.getChild("real_glowing_eyes");
    }

    @Override
    public boolean acessoryShouldRender(Dog dog, AccessoryInstance inst) {
        if (props.renderModelAccessoriesOnly) {
            return inst.getAccessory().getAccessoryRenderType() == AccessoryRenderType.MODEL;
        }
        return props.shouldRenderAccessories;
    }

    @Override
    public boolean incapShouldRender(Dog dog) {
        return props.shouldRenderIncapacitated;
    }

    @Override
    public boolean scaleBabyDog() {
        return false;
    }

    @Override
    public @Nullable Vector3f getCustomRootPivotPoint() {
        return this.props.customRootPivot;
    }

    @Override
    public boolean hasDefaultScale() {
        return this.props.hasDefaultScale;
    }

    @Override
    public float getDefaultScale() {
        return this.props.defaultScale;
    }

    @Override
    public boolean armorShouldRender(Dog dog) {
        return false;
    }

    @Override
    public AccessoryState getAccessoryState() {
        if (this.props.shouldRenderAccessories)
            return AccessoryState.HAVE_NOT_TESTED;
        if (this.props.renderModelAccessoriesOnly)
            return AccessoryState.MODEL_ONLY;
        return AccessoryState.NON_COMPATIBLE;
    }

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float relativeHeadYRot,
            float headPitch) {
        super.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, relativeHeadYRot, headPitch);
        if (this.props.glowingEyes) {
            this.glowingEyes.copyFrom(this.head);
            this.realGlowingEyes.copyFrom(this.realHead);
        }
    }

    @Override
    protected AnimationDefinition getAnimationSequence(DogAnimation anim) {
        var animOverride = this.props.getAnimOverride();
        if (animOverride.isEmpty())
            return super.getAnimationSequence(anim);
        var animDef = animOverride.get(anim);
        if (animDef == null)
            return super.getAnimationSequence(anim);
        return animDef;
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer vertex_consumer, int light, int overlay, int color_overlay) {    
        if (props.glowingEyes) {
            this.glowingEyes.visible = false;
            this.realGlowingEyes.visible = false;
        }
        super.renderToBuffer(stack, vertex_consumer, light, overlay, color_overlay);
    }

    @Override
    protected Optional<AddtionalHeadRenderer> getDogModelAdditionalHeadRenderer() {
        return Optional.of(this::renderGlowingEyes);
    }

    private void renderGlowingEyes(PoseStack stack, Optional<DogModel.DogRenderPartContext> part_ctx_optional) {
        if (!props.glowingEyes)
            return;
        if (!part_ctx_optional.isPresent())
            return;
        var part_ctx = part_ctx_optional.get();
        if (this.head.visible) {
            this.glowingEyes.visible = true;
            this.realGlowingEyes.visible = true;
            part_ctx.renderGlowingPart(stack, this.glowingEyes);
            this.glowingEyes.visible = false;
            this.realGlowingEyes.visible = false;
        }
    }
    
}
