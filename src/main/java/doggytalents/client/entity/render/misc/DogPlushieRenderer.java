package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.misc.DogPlushieModel;
import doggytalents.common.entity.misc.DogPlushie;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

public class DogPlushieRenderer extends EntityRenderer<DogPlushie, DogPlushieRenderer.DogPlushieRenderState> {

    private DogPlushieModel model;

    public static class DogPlushieRenderState extends EntityRenderState {
        public float yRot;
        public Identifier texture;
        public int collarColor;
        public boolean collarThicc;
    }

    public DogPlushieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        model = new DogPlushieModel(ctx.bakeLayer(ClientSetup.DOG_PLUSHIE));
    }

    @Override
    public DogPlushieRenderState createRenderState() {
        return new DogPlushieRenderState();
    }

    @Override
    public void extractRenderState(DogPlushie entity, DogPlushieRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yRot = Mth.wrapDegrees(entity.getYRot());
        state.texture = entity.getDogVariant().texture();
        state.collarColor = entity.getCollarColor();
        state.collarThicc = entity.getCollarThicc();
    }

    public Identifier getTextureLocation(DogPlushieRenderState state) {
        return state.texture != null ? state.texture : Resources.COLLAR_DEFAULT;
    }

    @Override
    public void submit(DogPlushieRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        stack.pushPose();
        stack.scale(-0.6F, -0.6F, 0.6F);
        stack.translate(0.0F, -1.05F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        // Body
        collector.submitModel(model, state, stack,
            RenderTypes.entityTranslucent(getTextureLocation(state)),
            state.lightCoords, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
        // Collar overlay tinted with collar color
        var collarTex = state.collarThicc ? Resources.COLLAR_THICC : Resources.COLLAR_DEFAULT;
        int collarArgb = ARGB.color(255, ARGB.red(state.collarColor), ARGB.green(state.collarColor), ARGB.blue(state.collarColor));
        collector.submitModel(model, state, stack,
            RenderTypes.entityTranslucent(collarTex),
            state.lightCoords, OverlayTexture.NO_OVERLAY, collarArgb, null);
        stack.popPose();
    }
}
