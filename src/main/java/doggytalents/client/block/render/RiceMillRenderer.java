package doggytalents.client.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.block.model.RiceMillModel;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class RiceMillRenderer implements BlockEntityRenderer<RiceMillBlockEntity> {

    private RiceMillModel model;

    public RiceMillRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new RiceMillModel(ctx.bakeLayer(ClientSetup.RICE_MILL));
    }

    @Override
    public void render(RiceMillBlockEntity mill, float pTicks, PoseStack stack, 
            MultiBufferSource buffer,
            int light, int overlay) {
        stack.pushPose();
        // float scaleFactor = 1f;
        // stack.scale(scaleFactor, -scaleFactor, -scaleFactor);
        // stack.translate(0.47F, -1.501F, -0f);
        stack.scale(1, -1, -1);
        stack.translate(0.5F, 0F, -0.5F);
        var state = mill.getBlockState();
        var facing = RiceMillBlock.getFacing(state);
        stack.mulPose(Axis.YP.rotationDegrees(facing.getOpposite().toYRot()));
        //stack.mulPose(Axis.YP.rotationDegrees(degTimeLine));
        stack.scale(2f, 2f, 2f);
        stack.translate(-0.25f, -1.501F, -0.25f);
        //stack.translate(0.47F, 0, -0f);
        this.model.setUpMillAnim(mill, pTicks);
        var consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(Resources.RICE_MILL_MODEL));
        this.model.renderToBuffer(stack, consumer, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
        stack.popPose();
    }
        
}
