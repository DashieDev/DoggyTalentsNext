package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.ClientSetup;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.model.DogModel;
import doggytalents.client.entity.render.layer.BoneLayer;
import doggytalents.client.entity.render.layer.LayerFactory;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DogRenderer extends MobRenderer<Dog, DogModel<Dog>> {

    public DogRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DogModel(ctx.bakeLayer(ClientSetup.DOG)), 0.5F);
//        this.addLayer(new DogTalentLayer(this, ctx));
//        this.addLayer(new DogAccessoryLayer(this, ctx));
        this.addLayer(new BoneLayer(this, ctx.getItemInHandRenderer()));
        for (LayerFactory<Dog, DogModel<Dog>> layer : CollarRenderManager.getLayers()) {
            this.addLayer(layer.createLayer(this, ctx));
        }
    }

    @Override
    protected float getBob(Dog livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    @Override
    public void render(Dog dog, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (dog.isDogWet()) {
            float f = dog.getShadingWhileWet(partialTicks);
            this.model.setColor(f, f, f);
        }

        super.render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (this.shouldShowName(dog)) {

            double d0 = this.entityRenderDispatcher.distanceToSqr(dog);
            if (d0 <= 64 * 64) {
                String tip = dog.getMode().getTip();
                var hunger = Mth.ceil(
                    (dog.isDefeated()?
                    -(dog.getMaxIncapacitatedHunger() - dog.getDogHunger()) :
                    dog.getDogHunger())
                );
                String label = String.format(ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GENDER) ? "%s(%d)%s" : "%s(%d)",
                        Component.translatable(tip).getString(),
                        hunger,
                        Component.translatable(dog.getGender().getUnlocalisedTip()).getString());

                RenderUtil.renderLabelWithScale(dog, this, this.entityRenderDispatcher, label, matrixStackIn, bufferIn, packedLightIn, 0.01F, 0.12F);

                if (d0 <= 5 * 5 && this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
                    RenderUtil.renderLabelWithScale(dog, this, this.entityRenderDispatcher, dog.getOwnersName().orElseGet(() -> this.getNameUnknown(dog)), matrixStackIn, bufferIn, packedLightIn, 0.01F, -0.25F);
                }
            }
        }


        if (dog.isDogWet()) {
            this.model.setColor(1.0F, 1.0F, 1.0F);
        }
    }


    private Component getNameUnknown(Dog dogIn) {
        return Component.translatable(dogIn.getOwnerUUID() != null ? "entity.doggytalents.dog.unknown_owner" : "entity.doggytalents.dog.untamed");
    }

    @Override
    public ResourceLocation getTextureLocation(Dog dogIn) {
        return DogTextureManager.INSTANCE.getTexture(dogIn);
    }

    @Override
    protected void scale(Dog dogIn, PoseStack matrixStackIn, float partialTickTime) {
        float size = dogIn.getDogSize() * 0.3F + 0.1F;
        matrixStackIn.scale(size, size, size);
        this.shadowRadius = size * 0.5F;
    }
}
