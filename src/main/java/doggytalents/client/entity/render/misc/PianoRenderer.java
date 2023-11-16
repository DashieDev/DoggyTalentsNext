package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.misc.PianoModel;
import doggytalents.common.entity.misc.Piano;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PianoRenderer extends EntityRenderer<Piano> {

    private PianoModel model;

    public PianoRenderer(EntityRendererProvider.Context  ctx) {
        super(ctx);
        model = new PianoModel(ctx.bakeLayer(ClientSetup.PIANO));
    }

    @Override
    public ResourceLocation getTextureLocation(Piano piano) {
        return Resources.PIANO;
    }
    
    private RenderType getRenderType(Piano piano) {
        return RenderType.entityTranslucent(getTextureLocation(piano));
    }

    @Override
    public void render(Piano piano, float p_114486_, float p_114487_, PoseStack stack,
            MultiBufferSource bufferSource, int light) {
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.translate(0.0F, -1.501F, 0.0F);
        var consumer = bufferSource.getBuffer(getRenderType(piano));
        this.model.renderToBuffer(stack, consumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}
