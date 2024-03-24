package doggytalents.client.entity.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import doggytalents.api.anim.DogAnimation;
import doggytalents.client.ClientSetup;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.IwankoModel;
import doggytalents.client.entity.model.dog.NullDogModel;
import doggytalents.client.entity.render.layer.LayerFactory;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public class DogRenderer extends MobRenderer<Dog, DogModel> {

    private static final int TXTCLR_DIFFOWNER = 0x574a4a4a;
    
    private static final int TXTCLR_HEALTH_70_100 = 0x0aff43;
    private static final int TXTCLR_HEALTH_30_70 = 0xeffa55;
    private static final int TXTCLR_HEALTH_0_30 = 0xff3636;
    private static final int TXTCLR_HEALTH_BKG = 0x4a4a4a;
    
    private static final int TXCLR_SEPERATOR = 0xffa1a1a1;

    private DogModel defaultModel;
    private NullDogModel nullDogModel;

    public DogRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, null, 0.5F);
//        this.addLayer(new DogTalentLayer(this, ctx));
//        this.addLayer(new DogAccessoryLayer(this, ctx));
        DogModelRegistry.resolve(ctx);
        this.defaultModel = DogModelRegistry.getDogModelHolder("default").getValue();
        for (LayerFactory<Dog, DogModel> layer : CollarRenderManager.getLayers()) {
            this.addLayer(layer.createLayer(this, ctx));
        }
        this.originalDogLayers = new ArrayList<>(this.layers);
        
        this.nullDogModel = new NullDogModel(ctx.bakeLayer(ClientSetup.DOG_NULL));
        this.model = this.nullDogModel;
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
        if (dog.isDogSoaked()) {
            float f = dog.getShadingWhileWet(partialTicks);
            this.model.setWetShade(f);
        }

        if (ConfigHandler.CLIENT.BLOCK_THIRD_PARTY_NAMETAG.get()) {
            MobRenderer_render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        } else
            super.render(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (this.model.hasAdditonalRendering())
            this.model.doAdditonalRendering(dog, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    
        this.model = this.nullDogModel;
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
            renderMainName(dog, text, stack, buffer, packedLight, renderDiffOwnerName && isDiffOwner, isDiffOwner);
        if (d0 <= 64 * 64)
            renderExtraInfo(dog, text, stack, buffer, packedLight, d0, renderDiffOwnerName && isDiffOwner, isDiffOwner);
        
    }

    private void renderMainName(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, 
        int light, boolean diffOwnerRender, boolean isDiffOwner) {

        text = modifyMainText(dog, text, diffOwnerRender);

        renderDogText(dog, text, 0, 0.025f, stack, buffer, light, diffOwnerRender, isDiffOwner);
    }

    private int getBkgTextColorWithOpacity(boolean diffOwnerRender) {
        final int color = 0x0;
        float bkg_opacity = 0;
        if (!diffOwnerRender) 
            bkg_opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        
        int alpha = (int)(bkg_opacity * 255.0F) << 24;
        return alpha | color;
    }

    private void renderDogText(Dog dog, Component text, double y_offset_from_default, float scale,
        PoseStack stack, MultiBufferSource buffer, int light,
        boolean render_diffowner, boolean is_diffowner) {

        boolean dog_not_sneaking = !dog.isDiscrete();
        double render_y_offset = dog.getBbHeight() + 0.5F + y_offset_from_default;

        stack.pushPose();

        stack.translate(0.0D, (double)render_y_offset, 0.0D);
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.scale(-scale, -scale, scale);
        
        var pose = stack.last().pose();
        var font = this.getFont();
        float tX = (float)(-font.width(text) / 2);
        float tY = 0;
        
        boolean bkg_see_through = dog_not_sneaking && !is_diffowner;
        var bkg_display_mode = bkg_see_through ? true : false;
        int bkg_color = getBkgTextColorWithOpacity(render_diffowner);
        int bkg_txtcolor = 0x20FFFFFF;
        font.drawInBatch(text, tX, tY, bkg_txtcolor, false, pose, buffer, bkg_display_mode, bkg_color, light);
        
        boolean draw_fg_text = dog_not_sneaking;
        if (draw_fg_text) {
            var fg_display_mode = false;
            int fg_color = 0x0;
            int fg_txtcolor = 0xFFFFFFFF;
            font.drawInBatch(text, tX, tY, fg_txtcolor, false, pose, buffer, fg_display_mode, fg_color, light);
        }

        stack.popPose();
    }

    private void renderExtraInfo(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, 
        int packedLight, double d0, boolean diffOwnerRender, boolean isDiffOwner) {
        
        renderInfoDogText(dog, text, stack, buffer, packedLight, d0, diffOwnerRender, isDiffOwner);
        renderSecondaryInfoDogText(dog, text, stack, buffer, packedLight, d0, diffOwnerRender, isDiffOwner);
    }

    private void renderInfoDogText(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, 
        int packedLight, double d0, boolean diffOwnerRender, boolean isDiffOwner) {
        
        boolean renderHealthInNameActivated = 
            this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()
            && ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_HEALTH_IN_NAME);
        final String seperator = ConfigHandler.CLIENT.DOG_INFO_SEPERATOR.get();
        final Component seperator_c1 = createC1WithColor(seperator, TXCLR_SEPERATOR);

        var extra_info_c1 = Component.translatable(dog.getMode().getTip());
        
        var hunger_c1_optional = getHungerC1(dog, renderHealthInNameActivated);
        var gender_c1_optional = getGenderC1(dog);

        if (hunger_c1_optional.isPresent()) {
            extra_info_c1.append(seperator_c1);
            var hunger_c1 = hunger_c1_optional.get();
            extra_info_c1.append(hunger_c1);
        }

        if (gender_c1_optional.isPresent()) {
            extra_info_c1.append(seperator_c1);
            var gender_c1 = gender_c1_optional.get();
            extra_info_c1.append(gender_c1);
        }

        if (diffOwnerRender) {
            extra_info_c1 = createC1WithColor(extra_info_c1, TXTCLR_DIFFOWNER);
        }

        renderDogText(dog, extra_info_c1, 0.12f, 0.01f, stack, buffer, packedLight, diffOwnerRender, isDiffOwner);

    }

    private void renderSecondaryInfoDogText(Dog dog, Component text, PoseStack stack, MultiBufferSource buffer, 
        int packedLight, double d0, boolean diffOwnerRender, boolean isDiffOwner) {

        if (d0 > 5 * 5)
            return;
        if (!this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown())
            return;

        var ownerC0 = dog.getOwnersName().orElseGet(() -> this.getNameUnknown(dog));
        if (diffOwnerRender) {
            ownerC0 = createC1WithColor(ownerC0, TXTCLR_DIFFOWNER);
        }

        renderDogText(dog, ownerC0, -0.25f, 0.01f, stack, buffer, packedLight, diffOwnerRender, isDiffOwner);
    }

    private Optional<Component> getHungerC1(Dog dog, boolean renderHealthInNameActivated) {
        if (ConfigHandler.SERVER.DISABLE_HUNGER.get() && !dog.isDefeated())
            return Optional.empty();

        final String hunger_format = ConfigHandler.CLIENT.DOG_INFO_HUNGER_FORMAT.get();
        
        int hunger = 0;
        if (dog.isDefeated()) {
            hunger = -dog.getDogIncapValue();
        } else {
            hunger = Mth.ceil(dog.getDogHunger());
        }
        var hunger_c1 = Component.literal(String.format(Locale.ROOT, hunger_format, hunger));
        boolean hightlight_red = 
            (dog.getDogHunger() <= 10 && renderHealthInNameActivated)
            || dog.isDefeated();
        if (hightlight_red) {
            hunger_c1.withStyle(Style.EMPTY.withColor(0xff3636));
        }
        return Optional.of(hunger_c1);
    }

    private Optional<Component> getGenderC1(Dog dog) {
        if (ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DISABLE_GENDER))
            return Optional.empty();

        var ret = Component.translatable(dog.getGender().getUnlocalisedTip());
        return Optional.of(ret);
    }

    private Component modifyMainText(Dog dog, Component text, boolean diffOwnerRender) {
        if (diffOwnerRender) {
            text = createC1WithColor(text, TXTCLR_DIFFOWNER);
            return text;
        }

        boolean renderHealthInNameActive = 
                this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()
                && ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_HEALTH_IN_NAME);
        if (renderHealthInNameActive) {
            text = colorTextWithHealth(dog, text);
        }
        return text;
    }

    private Component colorTextWithHealth(Dog dog, Component text) {
        int noCharsInName = text.getString().length();
        float healthPercentage = dog.getHealth()/dog.getMaxHealth();
        healthPercentage = Mth.clamp(healthPercentage, 0, 1);
        int noCharHighlighted = Mth.ceil( noCharsInName * healthPercentage );
        noCharHighlighted = Mth.clamp(noCharHighlighted, 0, noCharsInName);
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
        var newTxt = createC1WithColor(hlPart, color);
        var restTxt = createC1WithColor(nonHlPart, TXTCLR_HEALTH_BKG);
        newTxt.append(restTxt);
        text = newTxt;
        return text;
    }

    private MutableComponent createC1WithColor(String str, int color) {
        return Component.literal(str).withStyle(
            Style.EMPTY
            .withColor(color)
        );
    }

    private MutableComponent createC1WithColor(Component c1, int color) {
        return createC1WithColor(c1.getString(), color);
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
       
                p_115318_.mulPose(Vector3f.ZP.rotationDegrees(f * this.getFlipDegrees(p_115317_)));
            } else
            p_115318_.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_115320_));
            return;
        }
        super.setupRotations(p_115317_, p_115318_, p_115319_, p_115320_, p_115321_);
    }


}
