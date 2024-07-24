package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.misc.DogPlushieModel;
import doggytalents.common.entity.misc.DogPlushie;
import doggytalents.common.lib.Resources;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class DogPlushieRenderer extends EntityRenderer<DogPlushie> {

    private DogPlushieModel model;

    public DogPlushieRenderer(EntityRendererProvider.Context  ctx) {
        super(ctx);
        model = new DogPlushieModel(ctx.bakeLayer(ClientSetup.DOG_PLUSHIE));
    }

    @Override
    public ResourceLocation getTextureLocation(DogPlushie plushie) {
        return Resources.DOG_PLUSHIE_TOY;
    }
    
    private RenderType getRenderType(DogPlushie plushie, boolean overlay) {
        if (overlay) {
            return RenderType.entityTranslucent(Resources.DOG_PLUSHIE_TOY_OVERLAY);
        }
        return RenderType.entityTranslucent(getTextureLocation(plushie));
    }

    @Override
    public void render(DogPlushie piano, float p_114486_, float p_114487_, PoseStack stack,
            MultiBufferSource bufferSource, int light) {
        stack.pushPose();
        stack.scale(-0.6F, -0.6F, 0.6F);
        stack.translate(0.0F, -1.05F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(Mth.wrapDegrees(piano.getYRot())));
        var consumer = bufferSource.getBuffer(getRenderType(piano, false));
        this.model.renderToBuffer(stack, consumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
        int color = piano.getCollarColor();
        var arr = Util.rgbIntToFloatArray(color);
        consumer = bufferSource.getBuffer(getRenderType(piano, true));
        this.model.renderToBuffer(stack, consumer, light, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1, arr[0], arr[1], arr[2]));

        stack.popPose();
    }

}