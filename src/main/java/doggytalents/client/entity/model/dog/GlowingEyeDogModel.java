package doggytalents.client.entity.model.dog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.common.entity.Dog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.function.Function;

public class GlowingEyeDogModel extends DogModel {

    private ModelPart glowingEyes;
    private ModelPart realGlowingEyes;

    public GlowingEyeDogModel(ModelPart box) {
        super(box);
        setupGlowingEyes();
    }

    public GlowingEyeDogModel(ModelPart box, Function<ResourceLocation, RenderType> renderType) {
        super(box, renderType);
        setupGlowingEyes();
    }

    private void setupGlowingEyes() {
        this.glowingEyes = this.root.getChild("glowing_eyes");
        this.realGlowingEyes = glowingEyes.getChild("real_glowing_eyes");
    }

    @Override
    public void setupAnim(Dog dog, float limbSwing, float limbSwingAmount, float ageInTicks, float relativeHeadYRot,
            float headPitch) {
        super.setupAnim(dog, limbSwing, limbSwingAmount, ageInTicks, relativeHeadYRot, headPitch);
        this.glowingEyes.copyFrom(this.head);
        this.realGlowingEyes.copyFrom(this.realHead);
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer vertex_consumer, int light, int overlay,
            int color_overlay) {
        this.glowingEyes.visible = false;
        this.realGlowingEyes.visible = false;
        super.renderToBuffer(stack, vertex_consumer, light, overlay, color_overlay);
    }

    @Override
    protected Optional<AddtionalHeadRenderer> getDogModelAdditionalHeadRenderer() {
        return Optional.of(this::renderGlowingEyes);
    }

    private void renderGlowingEyes(PoseStack stack, Optional<DogModel.DogRenderPartContext> part_ctx_optional) {
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
