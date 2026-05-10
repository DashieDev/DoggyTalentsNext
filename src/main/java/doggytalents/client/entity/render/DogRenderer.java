package doggytalents.client.entity.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import doggytalents.api.anim.DogAnimation;
import doggytalents.client.ClientSetup;
import doggytalents.client.DogTextureManager;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.NullDogModel;
import doggytalents.client.entity.render.layer.LayerFactory;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.client.screen.widget.DoggySpin.DoggySpinModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.WhistleItem;
import net.minecraft.ChatFormatting;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public class DogRenderer extends MobRenderer<Dog, DogRenderState, DogModel> {

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
        for (LayerFactory<DogRenderState, DogModel> layer : CollarRenderManager.getLayers()) {
            this.addLayer(layer.createLayer(this, ctx));
        }

        this.nullDogModel = new NullDogModel(ctx.bakeLayer(ClientSetup.DOG_NULL));
        this.model = this.nullDogModel;
    }

    @Override
    public DogRenderState createRenderState() {
        return new DogRenderState();
    }

    @Override
    public void extractRenderState(Dog dog, DogRenderState state, float partialTick) {
        super.extractRenderState(dog, state, partialTick);
        state.dog = dog;
        state.walkAnimSpeed = dog.walkAnimation.speed(partialTick);
        state.walkAnimPos = dog.walkAnimation.position(partialTick);
        state.ageInTicksForAnim = dog.tickCount + partialTick;
        state.headYawForAnim = Mth.wrapDegrees(
            Mth.rotLerp(partialTick, dog.yHeadRotO, dog.yHeadRot)
            - state.bodyRot
        );
        state.headPitchForAnim = Mth.lerp(partialTick, dog.xRotO, dog.getXRot());

        // Capture skin at extraction time — dog skin may be temporarily changed for
        // skin preview rendering and restored before submit() is called.
        var skin = dog.getClientSkin();
        state.activeSkin = skin;
        state.skinTexture = DogTextureManager.INSTANCE.getTexture(dog);
        if (skin.useCustomModel()) {
            this.model = skin.getCustomModel().getValue();
        } else {
            this.model = this.defaultModel;
        }

        if (this.model != null) {
            this.model.resetWetShade();
            if (dog.isDogSoaked() && !dog.dogVariant().preventWetShade()) {
                float f = dog.getShadingWhileWet(partialTick);
                this.model.setWetShade(f);
            }
        }
    }

    @Override
    public void submit(DogRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        // Restore model based on skin captured at extraction time
        var skin = state.activeSkin;
        if (skin != null) {
            if (skin.useCustomModel()) {
                this.model = skin.getCustomModel().getValue();
            } else {
                this.model = this.defaultModel;
            }
        }
        if (this.model == null) {
            this.model = this.nullDogModel;
        }
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public Identifier getTextureLocation(DogRenderState state) {
        if (state.skinTexture != null) {
            return state.skinTexture;
        }
        return doggytalents.common.lib.Resources.ENTITY_WOLF;
    }

    @Override
    protected void scale(DogRenderState state, PoseStack matrixStackIn) {
        if (state.dog == null) return;
        Dog dogIn = state.dog;
        float size = dogIn.isBaby() ? 0.5f
            : dogIn.getDogSize().getScale();
        this.shadowRadius = size * 0.5F;
        var skin = state.activeSkin;
        if (skin != null && skin.useCustomModel()) {
            var model = skin.getCustomModel().getValue();
            if (model.hasDefaultScale()) {
                var default_scale = model.getDefaultScale();
                matrixStackIn.scale(default_scale, default_scale, default_scale);
                this.shadowRadius *= default_scale;
            }
        }
    }

    @Override
    protected boolean shouldShowName(Dog dog, double distanceToCameraSq) {
        if (ConfigHandler.CLIENT.ALWAYS_RENDER_DOG_NAME.get()
            && !dog.isVehicle() && dog.hasCustomName())
            return true;
        return super.shouldShowName(dog, distanceToCameraSq);
    }

    @Override
    protected void setupRotations(DogRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
        Dog dog = state.dog;
        if (dog != null && dog.deathTime <= 0 && dog.dogWalkAnimation.isBanking()) {
            // Bank logic applied separately below
        }

        super.setupRotations(state, poseStack, bodyRot, entityScale);

        if (dog != null && dog.deathTime <= 0 && dog.dogWalkAnimation.isBanking()) {
            float partialTick = state.partialTick;
            var bank_value = -dog.dogWalkAnimation.bankValue(partialTick);
            var max_bank = dog.dogWalkAnimation.maxBankZRot();
            poseStack.mulPose(Axis.ZP.rotationDegrees(bank_value * max_bank));
        }
    }

    private Component getNameUnknown(Dog dogIn) {
        return Component.translatable(dogIn.getOwnerUUID() != null ? "entity.doggytalents.dog.unknown_owner" : "entity.doggytalents.dog.untamed");
    }

    private int getBkgTextColorWithOpacity(boolean diffOwnerRender) {
        final int color = 0x0;
        float bkg_opacity = 0;
        if (!diffOwnerRender)
            bkg_opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);

        int alpha = (int)(bkg_opacity * 255.0F) << 24;
        return alpha | color;
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

    private Component modifyMainText(Dog dog, Component text, boolean diffOwnerRender, boolean renderDogOnDuty) {
        if (diffOwnerRender) {
            text = createC1WithColor(text, TXTCLR_DIFFOWNER);
            return text;
        }

        if (renderDogOnDuty) {
            if (dog.dogOnDuty())
                text = createC1WithColor(text, 0xFFFF10F9);
            return text;
        }

        if (ClientEventHandler.shouldRenderAnimDebugNametag(dog)) {
            text = createC1WithColor(text, 0xffcda700);
            return text;
        }

        boolean renderHealthInNameActive =
                this.entityRenderDispatcher.camera.entity().isShiftKeyDown()
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

}
