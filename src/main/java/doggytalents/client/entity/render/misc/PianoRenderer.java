package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.misc.GrandPianoModel;
import doggytalents.client.entity.model.misc.UprightPianoModel;
import doggytalents.common.entity.misc.Piano;
import doggytalents.common.entity.misc.Piano.PianoType;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class PianoRenderer extends EntityRenderer<Piano, PianoRenderer.PianoRenderState> {

    private GrandPianoModel model;
    private UprightPianoModel modelUpright;

    public static class PianoRenderState extends EntityRenderState {
        public float yRot;
        public PianoType pianoType;
        public Identifier texture;
        public boolean fallboardOpen;
    }

    public PianoRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        model = new GrandPianoModel(ctx.bakeLayer(ClientSetup.PIANO));
        modelUpright = new UprightPianoModel(ctx.bakeLayer(ClientSetup.PIANO_UPRIGHT));
    }

    @Override
    public PianoRenderState createRenderState() {
        return new PianoRenderState();
    }

    @Override
    public void extractRenderState(Piano entity, PianoRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yRot = Mth.wrapDegrees(entity.getYRot());
        state.pianoType = entity.getPianoType();
        state.texture = getTextureForPiano(entity);
        state.fallboardOpen = !entity.isFallboardClosed();
    }

    private Identifier getTextureForPiano(Piano piano) {
        var type = piano.getPianoType();
        var color = piano.getPianoColor();
        if (type == PianoType.GRAND) {
            switch (color) {
            default:
                return Resources.PIANO_GRAND_BLACK;
            case WHITE:
                return Resources.PIANO_GRAND_WHITE;
            }
        } else if (type == PianoType.UPRIGHT) {
            switch (color) {
            default:
                return Resources.PIANO_UPRIGHT_BLACK;
            case BROWN:
                return Resources.PIANO_UPRIGHT_BROWN;
            case WHITE:
                return Resources.PIANO_UPRIGHT_WHITE;
            }
        }
        return Resources.PIANO_GRAND_BLACK;
    }

    public Identifier getTextureLocation(PianoRenderState state) {
        return state.texture != null ? state.texture : Resources.PIANO_GRAND_BLACK;
    }

    @Override
    public void submit(PianoRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        stack.pushPose();
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.translate(0.0F, -1.501F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(state.yRot));
        if (state.pianoType == PianoType.UPRIGHT) {
            collector.submitModel(modelUpright, state, stack,
                RenderTypes.entityCutout(getTextureLocation(state)),
                state.lightCoords, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
        } else {
            model.setFallboard(state.fallboardOpen);
            collector.submitModel(model, state, stack,
                RenderTypes.entityCutout(getTextureLocation(state)),
                state.lightCoords, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
        }
        stack.popPose();
    }
}
