package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.misc.GrandPianoModel;
import doggytalents.client.entity.model.misc.UprightPianoModel;
import doggytalents.common.entity.misc.Piano;
import doggytalents.common.entity.misc.Piano.PianoType;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PianoRenderer extends EntityRenderer<Piano> {

    private GrandPianoModel model;
    private UprightPianoModel modelUpright;

    public PianoRenderer(EntityRendererProvider.Context  ctx) {
        super(ctx);
        model = new GrandPianoModel(ctx.bakeLayer(ClientSetup.PIANO));
        modelUpright = new UprightPianoModel(ctx.bakeLayer(ClientSetup.PIANO_UPRIGHT));
    }

    @Override
    public ResourceLocation getTextureLocation(Piano piano) {
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
            }
        }
        return Resources.PIANO_GRAND_BLACK;
    }
    
    private RenderType getRenderType(Piano piano) {
        return RenderType.entityTranslucent(getTextureLocation(piano));
    }

    @Override
    public void render(Piano piano, float p_114486_, float p_114487_, PoseStack stack,
            MultiBufferSource bufferSource, int light) {
        stack.pushPose();
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.translate(0.0F, -1.501F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(Mth.wrapDegrees(piano.getYRot())));
        if (piano.getPianoType() == PianoType.UPRIGHT) {
            var consumer = bufferSource.getBuffer(getRenderType(piano));
            this.modelUpright.renderToBuffer(stack, consumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
        } else {
            model.preparePianoModel(piano);
            var consumer = bufferSource.getBuffer(getRenderType(piano));
            this.model.renderToBuffer(stack, consumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
        }
        
        stack.popPose();
    }

}
