package doggytalents.client.screen.widget.DoggySpin;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import doggytalents.DogVariants;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.animation.DogAnimationSequences;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations;
import doggytalents.client.entity.model.animation.DogKeyframeAnimations.AnimationContext;
import doggytalents.client.entity.model.dog.AmaterasuModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.oina.HopeModel;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.lib.Resources;
import doggytalents.common.variant.DogVariant;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DoggySpinModel {

    private static DoggySpinModel INSTANCE = null;
    public static DoggySpinModel get() {
        if (INSTANCE == null)
            throw new IllegalStateException("DoggySpinModel is not initilized. If this exception get thrown again, please disable DTN world load icon and report to the DTN Server.");
        return INSTANCE;
    }

    public static void init() {
        INSTANCE = new DoggySpinModel();
    }


    public static enum Style { CHOPIN, BACKFLIP, SIT, AMMY, HOPE }
    private Style style = Style.CHOPIN;
    private DogVariant variant = DogVariant.PALE;
    private int collarColor = 0xffB02e26;
    private static final List<Supplier<DogVariant>> possibleVariants = List.of(
        DogVariants.PALE,
        DogVariants.CHESTNUT,
        DogVariants.RUSTY,
        DogVariants.WOOD,
        DogVariants.STRIPED,
        DogVariants.SPOTTED,
        DogVariants.ASHEN,
        DogVariants.SNOWY,
        DogVariants.BLACK,
        DogVariants.MOLTEN,
        DogVariants.HIMALAYAN_SALT,
        DogVariants.CHERRY
    );
    private static final int[] possibleColors = new int[] {
        0xffB02e26, 0xff80c71f, 0xff3c44aa, 0xffc74ebd, 0xff169c9c, 
        0xff3ab3da, 0xfff38baa, 0xff8932b8, 0xfff9801d, 0xfffed83d,
        0xff835432, 0xff1d1d21, 0xff5e7c16, 
    };
    private static final Random random = new Random();
    private ModelPart root;
    private ModelPart tail;

    private ModelPart rootAmmy;
    private ModelPart tailAmmy;

    private ModelPart rootHope;
    private ModelPart tailHope;
    
    private DoggySpinModel() {
        this.root = DogModel.createBodyLayer().bakeRoot();
        this.rootAmmy = AmaterasuModel.createBodyLayer().bakeRoot();
        this.rootHope = HopeModel.createBodyLayer().bakeRoot();
        this.tail = root.getChild("tail");
        this.tailAmmy = rootAmmy.getChild("tail");
        this.tailHope = rootHope.getChild("tail");
    }

    private ModelPart getRootForStyle() {
        if (this.style == Style.AMMY)
            return rootAmmy;
        if (this.style == Style.HOPE)
            return rootHope;
        return root;
    }

    private ModelPart getTailForStyle() {
        if (this.style == Style.AMMY)
            return tailAmmy;
        if (this.style == Style.HOPE)
            return tailHope;
        return tail;
    }

    private void pickDogVariant() {
        if (possibleVariants.isEmpty())
            return;
        int random_indx = random.nextInt(possibleVariants.size());
        this.variant = possibleVariants.get(random_indx).get();
    }    

    private void pickCollarColor() {
        if (possibleColors.length <= 0)
            return;
        int random_indx = random.nextInt(possibleColors.length);
        this.collarColor = possibleColors[random_indx];
    }
    
    public void configureRandomStyle() {
        float r = random.nextFloat();
        var selected_style = Style.CHOPIN;
        if (r >= 0.5f) {
            selected_style = Style.CHOPIN;
        } else if (r >= 0.27f) {
            selected_style = Style.BACKFLIP;
        } else if (r >= 0.04f) {
            selected_style = Style.SIT;
        } else {
            selected_style = ConfigHandler.CLIENT.AMMY_SPINNA.get() ? 
                (r >= 0.01f ? Style.AMMY : Style.HOPE)
            : Style.BACKFLIP;
        }
        this.style = selected_style;
        if (this.style != Style.AMMY && this.style != Style.HOPE) {
            pickCollarColor();
            pickDogVariant();
        }
    }

    private Vector3f buf = new Vector3f();

    private void resetPart(ModelPart part) {
        if (part == getTailForStyle()) {
            part.resetPose();
            getTailForStyle().xRot = 1.73f;
            return;
        }
        part.resetPose();
    }

    public void resetAllPose() {
        if (this.style == Style.AMMY) {
            this.rootAmmy.getAllParts().forEach(x -> x.resetPose());
            return;
        }
        if (this.style == Style.HOPE) {
            this.rootHope.getAllParts().forEach(x -> x.resetPose());
            return;
        }
        this.root.getAllParts().forEach(x -> x.resetPose());
    }

    public void prepareRender(long elapsed_millis) {
        resetAllPose();
        
        if (style == Style.CHOPIN || this.style == Style.AMMY || this.style == Style.HOPE) {
            long len_millis = Mth.ceil(TAIL_CHASE_LOOP.lengthInSeconds() * 1000);
            long passed_millis = elapsed_millis % len_millis;
            var seq = TAIL_CHASE_LOOP;
            DogKeyframeAnimations.keyframeAnimate(AnimationContext.of(
                name -> DogKeyframeAnimations.searchForPartWithName(getRootForStyle(), name),
                this::resetPart
                ), seq, passed_millis, 1, buf);
        } else if (style == Style.SIT) {
            long len_millis = Mth.ceil(DogAnimationSequences.SIT_LOOK_AROUND.lengthInSeconds() * 1000);
            long passed_millis = Mth.floor(elapsed_millis * 1.7f) % len_millis;
            var seq = DogAnimationSequences.SIT_LOOK_AROUND;
            DogKeyframeAnimations.keyframeAnimate(AnimationContext.of(
                name -> DogKeyframeAnimations.searchForPartWithName(getRootForStyle(), name),
                this::resetPart
                ), seq, passed_millis, 1, buf);
        } else if (style == Style.BACKFLIP) {
            long len_millis = Mth.ceil(DogAnimationSequences.BACKFLIP.lengthInSeconds() * 1000);
            long passed_millis = elapsed_millis % len_millis;
            var seq = DogAnimationSequences.BACKFLIP;
            DogKeyframeAnimations.keyframeAnimate(AnimationContext.of(
                name -> DogKeyframeAnimations.searchForPartWithName(getRootForStyle(), name),
                this::resetPart
                ), seq, passed_millis, 1, buf);
        }
        
    }

    public void renderGui(GuiGraphics graphics, float mid_x, float mid_y) {
        int scale = 70;
        var offset = new Vector3f(0, 0.5f - 0.0625F, 0);
        Quaternionf rotation;
        if (this.style == Style.CHOPIN || this.style == Style.AMMY || this.style == Style.HOPE) {
            rotation = Axis.XP.rotationDegrees(15);
        } else if (this.style == Style.BACKFLIP) {
            rotation = Axis.XP.rotationDegrees(4).mul(Axis.YP.rotationDegrees(20));
            offset.sub(0, -0.15f, 0);
        } else {
            rotation = Axis.XP.rotationDegrees(10);
            offset.sub(0, -0.08f, 0);
        }
        renderGui(graphics, mid_x, mid_y, scale, offset, rotation);
    }

    public void renderGui(
        GuiGraphics graphics,
        float mid_x,
        float mid_y,
        float scale,
        Vector3f offset,
        Quaternionf rot
    ) {
        
        graphics.pose().pushPose();
        graphics.pose().translate((double)mid_x, (double)mid_y, 50.0);
        graphics.pose().scale(scale, scale, -scale);
        graphics.pose().translate(offset.x, offset.y, offset.z);
        graphics.pose().mulPose(rot);
        graphics.pose().translate(0.0F, -1.501F, 0.0F);
        Lighting.setupForEntityInInventory();
        RenderSystem.runAsFancy(() -> this.doRenderModel(graphics.pose(), graphics.bufferSource()));
        graphics.flush();
        graphics.pose().popPose();
        Lighting.setupFor3DItems();
    }
    
    private void doRenderModel(PoseStack stack, MultiBufferSource source) {
        if (this.style == Style.AMMY) {
            doRenderModelWithTexture(stack, source, true, Resources.OKAMI_AMATERASU, 0xffffffff);
            return;
        }
        if (this.style == Style.HOPE) {
            doRenderModelWithTexture(stack, source, true, Resources.SOL_HOPE, 0xffffffff);
            return;
        }
        doRenderModelWithTexture(stack, source, false, this.variant.texture(), 0xffffffff);
        doRenderModelWithTexture(stack, source, false, Resources.COLLAR_THICC, this.collarColor);
    }

    private void doRenderModelWithTexture(PoseStack stack, MultiBufferSource source, boolean translucent, ResourceLocation loc, int color) {
        var renderType = translucent ? 
            RenderType.entityTranslucent(loc) 
            : RenderType.entityCutoutNoCull(loc);
        var buffer = source.getBuffer(renderType);
        this.renderToBuffer(stack, buffer, 15728880, OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(false)), color);
    }

    public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, 
        int overlay_coord, int color_overlay) {
        
        var root = getRootForStyle();
        var pivot = DogModel.DEFAULT_ROOT_PIVOT;
        stack.pushPose();
        stack.translate((double)(root.x / 16.0F), (double)(root.y / 16.0F), (double)(root.z / 16.0F));
        stack.translate((double)(pivot.x / 16.0F), (double)(pivot.y / 16.0F), (double)(pivot.z / 16.0F));
        if (root.zRot != 0.0F) {
            stack.mulPose(Axis.ZP.rotation(root.zRot));
        }

        if (root.yRot != 0.0F) {
            stack.mulPose(Axis.YP.rotation(root.yRot));
        }

        if (root.xRot != 0.0F) {
            stack.mulPose(Axis.XP.rotation(root.xRot));
        }
        float xRot0 = root.xRot, yRot0 = root.yRot, zRot0 = root.zRot;
        float x0 = root.x, y0 = root.y, z0 = root.z;
        root.xRot = 0; root.yRot = 0; root.zRot = 0;
        root.x = 0; root.y = 0; root.z = 0;
        stack.pushPose();
        stack.translate((double)(-pivot.x / 16.0F), (double)(-pivot.y / 16.0F), (double)(-pivot.z / 16.0F));
        root.render(stack, consumer, light, overlay_coord, color_overlay);
        stack.popPose();
        stack.popPose();
        root.xRot = xRot0; root.yRot = yRot0; root.zRot = zRot0;
        root.x = x0; root.y = y0; root.z = z0;
    }

    
    
    public static final AnimationDefinition TAIL_CHASE_LOOP = AnimationDefinition.Builder.withLength(1.75f).looping()
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(2.25f, -1f, 1.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(2.25f, -1f, 1.75f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("head",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -90f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(-1.17f, -2.22f, -1.55f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(-1.17f, -2.22f, -1.55f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-0.76f, 16.57f, 8.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-0.76f, 16.57f, 8.5f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(1.4f, -0.14f, -0.79f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.25f, KeyframeAnimations.posVec(0.25f, -0.14f, -1.27f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(1.4f, -0.14f, -0.79f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(16.52f, -3.33f, -9.87f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(16.52f, -3.33f, -9.87f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9583434f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(16.52f, -3.33f, -9.87f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(16.52f, -3.33f, -9.87f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 0f, 32.5f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, -2f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(0f, -1f, -2f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(16.37f, 5.96f, -12.31f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.6766666f, KeyframeAnimations.degreeVec(16.37f, 5.96f, -12.31f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9583434f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(16.37f, 5.96f, -12.31f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(16.37f, 5.96f, -12.31f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0.67f, -1.28f, -0.65f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(0.67f, -1.28f, -0.65f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-36.72f, -1.14f, 8.05f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-30.04f, -1.13f, -8.27f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-36.72f, -1.14f, 8.05f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(-30.04f, -1.13f, -8.27f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-36.72f, -1.14f, 8.05f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -1.25f, 2.75f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(0f, -1.25f, 2.75f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(9.01f, -20.7f, -24.15f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(9.01f, -20.7f, -24.15f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(1.7f, -2.68f, -1.66f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(1.7f, -2.68f, -1.66f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.7083434f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.9583434f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.1676667f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, -40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, 40f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.8343333f, KeyframeAnimations.degreeVec(120f, 13.33f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(-0.06f, -2.33f, -0.32f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(-0.06f, -2.33f, -0.32f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("upper_body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-4.75f, -36.46f, -8.92f),
                    AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(-4.75f, -36.46f, -8.92f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(0.9583434f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM), 
                new Keyframe(1.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                    AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-30f, -360f, 0f),
                    AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.degreeVec(15f, -720f, 25f),
                    AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.4167667f, KeyframeAnimations.degreeVec(0f, -1080f, 0f),
                    AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(0f, -1440f, 0f),
                    AnimationChannel.Interpolations.LINEAR)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-45.19f, -0.15f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.POSITION, 
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -0.25f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM)))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-32.5f, 0f, 0f),
                    AnimationChannel.Interpolations.CATMULLROM))).build();

}
