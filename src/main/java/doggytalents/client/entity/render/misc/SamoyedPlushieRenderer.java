package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.misc.SamoyedPlushieModel;
import doggytalents.common.entity.misc.SamoyedPlushie;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class SamoyedPlushieRenderer extends EntityRenderer<SamoyedPlushie, SamoyedPlushieRenderer.SamoyedPlushieRenderState> {

    private SamoyedPlushieModel model;

    public static class SamoyedPlushieRenderState extends EntityRenderState {
        public float yRot;
    }

    public SamoyedPlushieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        model = new SamoyedPlushieModel(ctx.bakeLayer(ClientSetup.SAMOYED_PLUSHIE));
    }

    @Override
    public SamoyedPlushieRenderState createRenderState() {
        return new SamoyedPlushieRenderState();
    }

    @Override
    public void extractRenderState(SamoyedPlushie entity, SamoyedPlushieRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yRot = Mth.wrapDegrees(entity.getYRot());
    }

    public Identifier getTextureLocation(SamoyedPlushieRenderState state) {
        return Resources.SAMOYED_PLUSHIE_TOY;
    }

    @Override
    public void submit(SamoyedPlushieRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        stack.pushPose();
        stack.scale(-0.6F, -0.6F, 0.6F);
        stack.translate(0.0F, -1.5F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        collector.submitModel(model, state, stack,
            RenderTypes.entityTranslucent(Resources.SAMOYED_PLUSHIE_TOY),
            state.lightCoords, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
        stack.popPose();
    }
}
