package doggytalents.client.backward_imitate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.render.DogRenderer;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class BaseDogModel_21_3 extends BaseEntityModel_21_3<Dog> implements IBaseDogModel_21_3 {
    
    public boolean young = false;

    @Override
    public void setDogYoung(boolean val) {
        this.young = val;
    }
    @Override
    public boolean getDogYoung() {
        return this.young;
    }

    public static void renderColoredCutoutModel(
        BaseModel_21_3 model,
        ResourceLocation texture,
        PoseStack stack,
        MultiBufferSource buffer,
        int light,
        Dog dog,
        int color_overlay
    ) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        model.renderToBuffer(stack, vertexconsumer, light, DogRenderer.getOverlayCoords(dog, 0.0F), color_overlay);
    }

    public static void renderColoredCutoutModel(
        DogModel model,
        ResourceLocation texture,
        PoseStack stack,
        MultiBufferSource buffer,
        int light,
        Dog dog,
        int color_overlay
    ) {
        renderColoredCutoutModel(BaseModel_21_3.wrap(model), texture, stack, buffer, light, dog, color_overlay);
    }
}
