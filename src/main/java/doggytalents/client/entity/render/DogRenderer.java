package doggytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.client.ClientSetup;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.IwankoModel;
import doggytalents.client.entity.render.layer.BoneLayer;
import doggytalents.client.entity.render.layer.LayerFactory;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.ChatFormatting;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DogRenderer extends MobRenderer<Dog, DogModel<Dog>> {

    private DogModel defaultModel;

    public DogRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, null, 0.5F);
//        this.addLayer(new DogTalentLayer(this, ctx));
//        this.addLayer(new DogAccessoryLayer(this, ctx));
        DogModelRegistry.resolve(ctx);
        this.model = DogModelRegistry.getDogModelHolder("default").getValue();
        this.defaultModel = this.model;
        this.addLayer(new BoneLayer(this, Minecraft.getInstance().getItemInHandRenderer()));
        for (LayerFactory<Dog, DogModel<Dog>> layer : CollarRenderManager.getLayers()) {
            this.addLayer(layer.createLayer(this, ctx));
        }
    }

    @Override
    protected float getBob(Dog livingBase, float partialTicks) {
        return super.getBob(livingBase, partialTicks);
    }

    @Override
    public void render(Dog dog, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        
        var skin = dog.getClientSkin();

        if (skin.useCustomModel()) {
            this.model = dog.getClientSkin().getCustomModel().getValue();
        } else {
            this.model = this.defaultModel;
        }

        this.model.realTail.visible = true;
        this.model.realTail2.visible = false;
        this.model.realTail3.visible = false;
        byte tailIndx = skin.getTail();
        if (tailIndx != 0) {
            switch (tailIndx) {
                case 1 :
                    this.model.realTail.visible = false;
                    this.model.realTail2.visible = true;
                    this.model.realTail3.visible = false;
                    break;
                case 2 :
                    this.model.realTail.visible = false;
                    this.model.realTail2.visible = false;
                    this.model.realTail3.visible = true;
                    break;
            }
        }

        this.model.earNormal.visible = true;
        this.model.earBoni.visible = false;
        this.model.earSmall.visible = false;
        byte earIndx = skin.getEar();
        if (earIndx != 0) {
            switch (earIndx) {
                case 1 :
                    this.model.earNormal.visible = false;
                    this.model.earBoni.visible = true;
                    this.model.earSmall.visible = false;
                    break; 
                case 2 :
                    this.model.earNormal.visible = false;
                    this.model.earBoni.visible = false;
                    this.model.earSmall.visible = true;
                    break;
            }
        }
        
        if (dog.isDogWet()) {
            float f = dog.getShadingWhileWet(partialTicks);
            this.model.setColor(f, f, f);
        }

        if (this.model.modelNeedRefreshBeforeCurrentRender(dog)) {
            this.model.resetAllPose();
        }

        super.render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (dog.isDogWet()) {
            this.model.setColor(1.0F, 1.0F, 1.0F);
        }

        if (this.model.hasAdditonalRendering())
            this.model.doAdditonalRendering(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (this.model.modelNeedRefreshBeforeNextRender(dog)) {
            this.model.resetAllPose();
        }
    }

    private Component getNameUnknown(Dog dogIn) {
        return ComponentUtil.translatable(dogIn.getOwnerUUID() != null ? "entity.doggytalents.dog.unknown_owner" : "entity.doggytalents.dog.untamed");
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

        var player = Minecraft.getInstance().player;
        boolean renderDiffOwnerName = 
            ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_DIFFOWNER_NAME_DIFFERENT)
            && dog != this.entityRenderDispatcher.crosshairPickEntity;
        boolean isDiffOwner = 
            (player == null || !player.getUUID().equals(dog.getOwnerUUID()));

        if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(dog, d0))
            renderMainName(dog, text, stack, buffer, packedLight, d0, renderDiffOwnerName && isDiffOwner);
        if (d0 <= 64 * 64)
            renderExtraInfo(dog, text, stack, buffer, packedLight, d0, renderDiffOwnerName && isDiffOwner);
        
    }

    private void renderMainName(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, 
        int packedLight, double d0, boolean diffOwnerRender) {
        boolean flag = !dog.isDiscrete();
        float f = dog.getBbHeight() + 0.5F;
        int i = 0;
        stack.pushPose();
        stack.translate(0.0D, (double)f, 0.0D);
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.scale(-0.025F, -0.025F, 0.025F);
        var matrix4f = stack.last().pose();
        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        if (diffOwnerRender) f1 = 0;
        int j = (int)(f1 * 255.0F) << 24;
        var font = this.getFont();
        float f2 = (float)(-font.width(text) / 2);
        
        text = modifyText(dog, text, diffOwnerRender);
        
        font.drawInBatch(text, f2, (float)i, 553648127, false, matrix4f, buffer, flag, j, packedLight);
        if (flag) {
            font.drawInBatch(text, f2, (float)i, -1, false, matrix4f, buffer, false, 0, packedLight);
        }

        stack.popPose();
    }

    private void renderExtraInfo(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, 
        int packedLight, double d0, boolean diffOwnerRender) {
        String tip = dog.getMode().getTip();
        var hunger = Mth.ceil(
            (dog.isDefeated()?
            (dog.getDogHunger() - dog.getMaxIncapacitatedHunger()) :
            dog.getDogHunger())
        );

        boolean flag1 = 
            this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()
            && ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_HEALTH_IN_NAME);

        MutableComponent label = ComponentUtil.translatable(tip);
        var hunger_c1 = ComponentUtil.literal("(" + hunger + ")");
        if (dog.getDogHunger() <= 10 && flag1) {
            hunger_c1.withStyle(Style.EMPTY.withColor(0xff3636));
        }
        label.append(hunger_c1);
        if (ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GENDER)) {
            label.append(ComponentUtil.translatable(dog.getGender().getUnlocalisedTip()));
        }

        final int TXTCLR_BKG = 0x574a4a4a;

        if (diffOwnerRender) {
            label = ComponentUtil.literal(label.getString())
            .withStyle(
                Style.EMPTY
                .withColor(TXTCLR_BKG)
            );
        }

        RenderUtil.renderLabelWithScale(dog, this, this.entityRenderDispatcher, label, stack, buffer, packedLight, 0.01F, 0.12F, !diffOwnerRender);

        if (d0 <= 5 * 5 && this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
            var ownerC0 = dog.getOwnersName().orElseGet(() -> this.getNameUnknown(dog));
            if (diffOwnerRender) {
               ownerC0 = ComponentUtil.literal(ownerC0.getString())
                .withStyle(
                    Style.EMPTY
                    .withColor(TXTCLR_BKG)
                );
            }

            RenderUtil.renderLabelWithScale(dog, this, this.entityRenderDispatcher, ownerC0, stack, buffer, packedLight, 0.01F, -0.25F, !diffOwnerRender);
        }
    }

     private Component modifyText(Dog dog, Component text, boolean diffOwnerRender) {
        final int TXTCLR_HEALTH_70_100 = 0x0aff43;
        final int TXTCLR_HEALTH_30_70 = 0xeffa55;
        final int TXTCLR_HEALTH_0_30 = 0xff3636;
        final int TXTCLR_BKG = 0x4a4a4a;
        
        if (diffOwnerRender) {
            text = ComponentUtil.literal(text.getString())
                .withStyle(
                    Style.EMPTY
                    .withColor(TXTCLR_BKG)
                );
            return text;
        }

        boolean flag1 = 
                this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()
                && ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_HEALTH_IN_NAME);
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
            var newTxt = ComponentUtil.literal(hlPart).withStyle(
                Style.EMPTY
                .withColor(color)
            );
            var restTxt = ComponentUtil.literal(nonHlPart).withStyle(
                Style.EMPTY
                .withColor(TXTCLR_BKG)
            );
            newTxt.append(restTxt);
            text = newTxt;
        }
        return text;
     }
}
