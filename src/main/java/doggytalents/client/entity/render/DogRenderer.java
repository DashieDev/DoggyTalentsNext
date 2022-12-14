package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.ClientSetup;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.model.DogModel;
import doggytalents.client.entity.render.layer.BoneLayer;
import doggytalents.client.entity.render.layer.LayerFactory;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
                        I18n.get(tip),
                        hunger,
                        I18n.get(dog.getGender().getUnlocalisedTip())
                );

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

    @Override
    protected void renderNameTag(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, int packedLight) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(dog);

        final int TXTCLR_HEALTH_70_100 = 0x0aff43;
        final int TXTCLR_HEALTH_30_70 = 0xeffa55;
        final int TXTCLR_HEALTH_0_30 = 0xff3636;
        final int TXTCLR_BKG = 0x4a4a4a;

        if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(dog, d0)) {
           boolean flag = !dog.isDiscrete();
           float f = dog.getBbHeight() + 0.5F;
           int i = 0;
           stack.pushPose();
           stack.translate(0.0D, (double)f, 0.0D);
           stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
           stack.scale(-0.025F, -0.025F, 0.025F);
           var matrix4f = stack.last().pose();
           float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
           int j = (int)(f1 * 255.0F) << 24;
           var font = this.getFont();
           float f2 = (float)(-font.width(text) / 2);
           boolean flag1 = 
                this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()
                && ConfigHandler.CLIENT.getConfig(ConfigHandler.CLIENT.RENDER_HEALTH_IN_NAME);
           if (flag1) {
                int noCharsInName = text.getString().length();
                float healthPercentage = dog.getHealth()/dog.getMaxHealth();
                int noCharHighlighted = Mth.ceil( noCharsInName * healthPercentage );
                var hlPart = text.getString().substring(0, noCharHighlighted);
                String nonHlPart = "";
                if (noCharHighlighted <= noCharsInName) {
                    nonHlPart = text.getString().substring(noCharHighlighted, noCharsInName);
                }
                int color = TXTCLR_HEALTH_0_30;
                if (healthPercentage >= 0.7) {
                    color = TXTCLR_HEALTH_70_100;
                } else if (healthPercentage >= 0.3) {
                    color = TXTCLR_HEALTH_30_70;
                }
                var newTxt = Component.literal(hlPart).withStyle(
                    Style.EMPTY
                    .withColor(color)
                );
                var restTxt = Component.literal(nonHlPart).withStyle(
                    Style.EMPTY
                    .withColor(TXTCLR_BKG)
                );
                newTxt.append(restTxt);
                text = newTxt;
           }
           font.drawInBatch(text, f2, (float)i, 553648127, false, matrix4f, buffer, flag, j, packedLight);
           if (flag) {
              font.drawInBatch(text, f2, (float)i, -1, false, matrix4f, buffer, false, 0, packedLight);
           }
  
           stack.popPose();
        }
     }
}
