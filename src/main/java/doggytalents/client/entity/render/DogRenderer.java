package doggytalents.client.entity.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import doggytalents.client.ClientSetup;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.IwankoModel;
import doggytalents.client.entity.render.layer.LayerFactory;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.ChatFormatting;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public class DogRenderer extends MobRenderer<Dog, DogModel> {

    private DogModel defaultModel;

    public DogRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, null, 0.5F);
//        this.addLayer(new DogTalentLayer(this, ctx));
//        this.addLayer(new DogAccessoryLayer(this, ctx));
        DogModelRegistry.resolve(ctx);
        this.model = DogModelRegistry.getDogModelHolder("default").getValue();
        this.defaultModel = this.model;
        for (LayerFactory<Dog, DogModel> layer : CollarRenderManager.getLayers()) {
            this.addLayer(layer.createLayer(this, ctx));
        }
        this.originalDogLayers = new ArrayList<>(this.layers);
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

        this.model.resetWetShade();
        if (dog.isDogWet()) {
            float f = dog.getShadingWhileWet(partialTicks);
            this.model.setWetShade(f);
        }

        if (ConfigHandler.CLIENT.BLOCK_THIRD_PARTY_NAMETAG.get()) {
            MobRenderer_render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else
            super.render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (this.model.hasAdditonalRendering())
            this.model.doAdditonalRendering(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
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
        float size = dogIn.isBaby() ? 0.5f 
            : dogIn.getDogSize().getScale();
        var skin = dogIn.getClientSkin();
        if (skin.useCustomModel()) {
            var model = skin.getCustomModel().getValue();
            if (model.hasDefaultScale())
                size *= model.getDefaultScale();
        }
        matrixStackIn.scale(size, size, size);
        this.shadowRadius = size * 0.5F;
    }

    @Override
    protected boolean shouldShowName(Dog p_115506_) {
        return ConfigHandler.CLIENT.ALWAYS_RENDER_DOG_NAME.get()
            || super.shouldShowName(p_115506_);
    }

    @Override
    protected void renderNameTag(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, int packedLight) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(dog);

        var player = Minecraft.getInstance().player;
        boolean renderDiffOwnerName = 
            ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_DIFFOWNER_NAME_DIFFERENT)
            && dog != this.entityRenderDispatcher.crosshairPickEntity;
        boolean isDiffOwner = 
            (player == null || !Objects.equals(player.getUUID(), dog.getOwnerUUID()));

        if (isDiffOwner && ConfigHandler.CLIENT.DONT_RENDER_DIFFOWNER_NAME.get())
            return;

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

        var label = Component.translatable(tip);
        var hunger_c1 = Component.literal("(" + hunger + ")");
        if (dog.getDogHunger() <= 10 && flag1) {
            hunger_c1.withStyle(Style.EMPTY.withColor(0xff3636));
        }
        if (ConfigHandler.SERVER.DISABLE_HUNGER.get() && !dog.isDefeated())
            hunger_c1 = Component.empty();
        label.append(hunger_c1);
        if (ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GENDER)) {
            label.append(Component.translatable(dog.getGender().getUnlocalisedTip()));
        }

        final int TXTCLR_BKG = 0x574a4a4a;

        if (diffOwnerRender) {
            label = Component.literal(label.getString())
            .withStyle(
                Style.EMPTY
                .withColor(TXTCLR_BKG)
            );
        }

        RenderUtil.renderLabelWithScale(dog, this, this.entityRenderDispatcher, label, stack, buffer, packedLight, 0.01F, 0.12F, !diffOwnerRender);

        if (d0 <= 5 * 5 && this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
            var ownerC0 = dog.getOwnersName().orElseGet(() -> this.getNameUnknown(dog));
            if (diffOwnerRender) {
               ownerC0 = Component.literal(ownerC0.getString())
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
            text = Component.literal(text.getString())
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
        return text;
     }

    //Super call Inlined without broastcasting any render event as an attempt to resolve render conflict, 
    //if users opt for it.

    private List<RenderLayer<Dog, DogModel>> originalDogLayers = List.of();

     public void MobRenderer_render(Dog p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        LivingEntityRenderer_render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
     }
     
    public void LivingEntityRenderer_render(Dog p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
       // if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<Dog, DogModel<Dog>>(p_115308_, this, p_115310_, p_115311_, p_115312_, p_115313_))) return;
        p_115311_.pushPose();
        this.model.attackTime = this.getAttackAnim(p_115308_, p_115310_);

        boolean shouldSit = p_115308_.isPassenger() && (p_115308_.getVehicle() != null && p_115308_.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        this.model.young = p_115308_.isBaby();
        float f = Mth.rotLerp(p_115310_, p_115308_.yBodyRotO, p_115308_.yBodyRot);
        float f1 = Mth.rotLerp(p_115310_, p_115308_.yHeadRotO, p_115308_.yHeadRot);
        float f2 = f1 - f;
        if (shouldSit && p_115308_.getVehicle() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)p_115308_.getVehicle();
            f = Mth.rotLerp(p_115310_, livingentity.yBodyRotO, livingentity.yBodyRot);
            f2 = f1 - f;
            float f3 = Mth.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = Mth.lerp(p_115310_, p_115308_.xRotO, p_115308_.getXRot());
        // if (isEntityUpsideDown(p_115308_)) {
        //     f6 *= -1.0F;
        //     f2 *= -1.0F;
        // }

        // if (p_115308_.hasPose(Pose.SLEEPING)) {
        //     Direction direction = p_115308_.getBedOrientation();
        //     if (direction != null) {
        //         float f4 = p_115308_.getEyeHeight(Pose.STANDING) - 0.1F;
        //         p_115311_.translate((double)((float)(-direction.getStepX()) * f4), 0.0D, (double)((float)(-direction.getStepZ()) * f4));
        //     }
        // }

        float f7 = this.getBob(p_115308_, p_115310_);
        this.setupRotations(p_115308_, p_115311_, f7, f, p_115310_);
        p_115311_.scale(-1.0F, -1.0F, 1.0F);
        this.scale(p_115308_, p_115311_, p_115310_);
        p_115311_.translate(0.0D, (double)-1.501F, 0.0D);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && p_115308_.isAlive()) {
            f8 = Mth.lerp(p_115310_, p_115308_.animationSpeedOld, p_115308_.animationSpeed);
            f5 = p_115308_.animationPosition - p_115308_.animationSpeed * (1.0F - p_115310_);
            if (p_115308_.isBaby()) {
                f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.model.prepareMobModel(p_115308_, f5, f8, p_115310_);
        this.model.setupAnim(p_115308_, f5, f8, f7, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isBodyVisible(p_115308_);
        boolean flag1 = !flag && !p_115308_.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(p_115308_);
        RenderType rendertype = this.getRenderType(p_115308_, flag, flag1, flag2);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = p_115312_.getBuffer(rendertype);
            int i = getOverlayCoords(p_115308_, this.getWhiteOverlayProgress(p_115308_, p_115310_));
            this.model.renderToBuffer(p_115311_, vertexconsumer, p_115313_, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }

        if (!p_115308_.isSpectator()) {
            for(var renderlayer : this.originalDogLayers) {
                renderlayer.render(p_115311_, p_115312_, p_115313_, p_115308_, f5, f8, p_115310_, f7, f2, f6);
            }
        }

        p_115311_.popPose();

        if (this.shouldShowName(p_115308_)) {
            this.renderNameTag(p_115308_, p_115308_.getDisplayName(), p_115311_, p_115312_, p_115313_);
         }

        //net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<Dog, DogModel<Dog>>(p_115308_, this, p_115310_, p_115311_, p_115312_, p_115313_));
    }

    @Override
    protected void setupRotations(Dog p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_,
            float p_115321_) {
        if (ConfigHandler.CLIENT.BLOCK_THIRD_PARTY_NAMETAG.get()) {
            if (p_115317_.deathTime > 0) {
                float f = ((float)p_115317_.deathTime + p_115321_ - 1.0F) / 20.0F * 1.6F;
                f = Mth.sqrt(f);
                if (f > 1.0F) {
                   f = 1.0F;
                }
       
                p_115318_.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(p_115317_)));
            } else
            p_115318_.mulPose(Axis.YP.rotationDegrees(180.0F - p_115320_));
            return;
        }
        super.setupRotations(p_115317_, p_115318_, p_115319_, p_115320_, p_115321_);
    }


}
