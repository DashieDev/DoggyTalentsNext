package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.misc.SamoyedPlushieModel;
import doggytalents.common.entity.misc.SamoyedPlushie;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SamoyedPlushieRenderer extends EntityRenderer<SamoyedPlushie> {

    private SamoyedPlushieModel model;

    public SamoyedPlushieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        model = new SamoyedPlushieModel(ctx.bakeLayer(ClientSetup.SAMOYED_PLUSHIE));
    }

    @Override
    public ResourceLocation getTextureLocation(SamoyedPlushie plushie) {
        return Resources.SAMOYED_PLUSHIE_TOY;
    }

    private RenderType getRenderType(SamoyedPlushie plushie) {
        return RenderType.entityTranslucent(getTextureLocation(plushie));
    }

    @Override
    public void render(SamoyedPlushie piano, float p_114486_, float p_114487_, PoseStack stack,
            MultiBufferSource bufferSource, int light) {
        stack.pushPose();
        stack.scale(-0.6F, -0.6F, 0.6F);
        stack.translate(0.0F, -1.5F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(Mth.wrapDegrees(piano.getYRot())));
        var consumer = bufferSource.getBuffer(getRenderType(piano));
        this.model.renderToBuffer(stack, consumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);

        stack.popPose();
    }

    
}
