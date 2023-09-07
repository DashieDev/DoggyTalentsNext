package doggytalents.client.entity.render;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

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
import net.minecraft.client.gui.Font;
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
        this.addLayer(new BoneLayer(this, ctx.getItemInHandRenderer()));
        for (LayerFactory<Dog, DogModel> layer : CollarRenderManager.getLayers()) {
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

        if (dog.isDogWet()) {
            float f = dog.getShadingWhileWet(partialTicks);
            this.model.setColor(f, f, f);
        }

        if (this.model.modelNeedRefreshBeforeCurrentRender(dog)) {
            this.model.resetAllPose();
        }

        if (ConfigHandler.CLIENT.BLOCK_THIRD_PARTY_NAMETAG.get()) {
            MobRenderer_render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else
            super.render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (dog.isDogWet()) {
            this.model.setColor(1.0F, 1.0F, 1.0F);
        }

        if (this.model.hasAdditonalRendering())
            this.model.doAdditonalRendering(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (this.model.modelNeedRefreshBeforeNextRender(dog)) {
            this.model.resetAllPose();
        }

        if (model != defaultModel
            && model.useDefaultModelForAccessories() 
            && this.defaultModel.modelNeedRefreshBeforeNextRender(dog)) {
            this.defaultModel.resetAllPose();
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
        
        font.drawInBatch(text, f2, (float)i, 553648127, false, matrix4f, buffer, flag ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, j, packedLight);
           if (flag) {
              font.drawInBatch(text, f2, (float)i, -1, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLight);
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

     public void MobRenderer_render(Dog p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        LivingEntityRenderer_render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
        Entity entity = p_115455_.getLeashHolder();
        if (entity != null) {
            MobRenderer_renderLeash(p_115455_, p_115457_, p_115458_, p_115459_, entity);
        }
     }

    private void MobRenderer_renderLeash(Dog p_115462_, float p_115463_, PoseStack p_115464_, MultiBufferSource p_115465_, Entity p_115466_) {
        p_115464_.pushPose();
        Vec3 vec3 = p_115466_.getRopeHoldPosition(p_115463_);
        double d0 = (double)(Mth.lerp(p_115463_, p_115462_.yBodyRotO, p_115462_.yBodyRot) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 = p_115462_.getLeashOffset(p_115463_);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp((double)p_115463_, p_115462_.xo, p_115462_.getX()) + d1;
        double d4 = Mth.lerp((double)p_115463_, p_115462_.yo, p_115462_.getY()) + vec31.y;
        double d5 = Mth.lerp((double)p_115463_, p_115462_.zo, p_115462_.getZ()) + d2;
        p_115464_.translate(d1, vec31.y, d2);
        float f = (float)(vec3.x - d3);
        float f1 = (float)(vec3.y - d4);
        float f2 = (float)(vec3.z - d5);
        float f3 = 0.025F;
        VertexConsumer vertexconsumer = p_115465_.getBuffer(RenderType.leash());
        Matrix4f matrix4f = p_115464_.last().pose();
        float f4 = Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = BlockPos.containing(p_115462_.getEyePosition(p_115463_));
        BlockPos blockpos1 = BlockPos.containing(p_115466_.getEyePosition(p_115463_));
        int i = this.getBlockLightLevel(p_115462_, blockpos);
        int j = EntityRenderer_getBlockLightLevel(p_115466_, blockpos1);
        int k = p_115462_.level().getBrightness(LightLayer.SKY, blockpos);
        int l = p_115462_.level().getBrightness(LightLayer.SKY, blockpos1);
  
        for(int i1 = 0; i1 <= 24; ++i1) {
           MobRenderer_addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
        }
  
        for(int j1 = 24; j1 >= 0; --j1) {
           MobRenderer_addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
        }
  
        p_115464_.popPose();
    }

    private static void MobRenderer_addVertexPair(VertexConsumer p_174308_, Matrix4f p_254405_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
        float f = (float)p_174321_ / 24.0F;
        int i = (int)Mth.lerp(f, (float)p_174313_, (float)p_174314_);
        int j = (int)Mth.lerp(f, (float)p_174315_, (float)p_174316_);
        int k = LightTexture.pack(i, j);
        float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        p_174308_.vertex(p_254405_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
        p_174308_.vertex(p_254405_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
     }

    private static int EntityRenderer_getBlockLightLevel(Entity p_114496_, BlockPos p_114497_) {
        return p_114496_.isOnFire() ? 15 : p_114496_.level().getBrightness(LightLayer.BLOCK, p_114497_);
    }

    public void LivingEntityRenderer_render(Dog p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        //if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<T, M>(p_115308_, this, p_115310_, p_115311_, p_115312_, p_115313_))) return;
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
        //    f6 *= -1.0F;
        //    f2 *= -1.0F;
        // }
  
        // if (p_115308_.hasPose(Pose.SLEEPING)) {
        //    Direction direction = p_115308_.getBedOrientation();
        //    if (direction != null) {
        //       float f4 = p_115308_.getEyeHeight(Pose.STANDING) - 0.1F;
        //       p_115311_.translate((float)(-direction.getStepX()) * f4, 0.0F, (float)(-direction.getStepZ()) * f4);
        //    }
        // }
  
        float f7 = this.getBob(p_115308_, p_115310_);
        this.setupRotations(p_115308_, p_115311_, f7, f, p_115310_);
        p_115311_.scale(-1.0F, -1.0F, 1.0F);
        this.scale(p_115308_, p_115311_, p_115310_);
        p_115311_.translate(0.0F, -1.501F, 0.0F);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && p_115308_.isAlive()) {
           f8 = p_115308_.walkAnimation.speed(p_115310_);
           f5 = p_115308_.walkAnimation.position(p_115310_);
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
           for(var renderlayer : this.layers) {
              renderlayer.render(p_115311_, p_115312_, p_115313_, p_115308_, f5, f8, p_115310_, f7, f2, f6);
           }
        }
  
        p_115311_.popPose();
        if (this.shouldShowName(p_115308_)) {
            this.renderNameTag(p_115308_, p_115308_.getDisplayName(), p_115311_, p_115312_, p_115313_);
         }
        //net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<T, M>(p_115308_, this, p_115310_, p_115311_, p_115312_, p_115313_));
    }


}
