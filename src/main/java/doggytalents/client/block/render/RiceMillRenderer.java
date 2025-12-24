package doggytalents.client.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.backward_imitate.LegacyBlockEntityRendererUtil_1_21_9.BlockEntityRenderer_1_21_9;
import doggytalents.client.backward_imitate.LegacyBlockEntityRendererUtil_1_21_9.RenderContext_1_21_9;
import doggytalents.client.block.model.RiceMillModel;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;

public class RiceMillRenderer extends BlockEntityRenderer_1_21_9<RiceMillBlockEntity> {

    private RiceMillModel model;

    public RiceMillRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new RiceMillModel(ctx.bakeLayer(ClientSetup.RICE_MILL));
    }

    @Override
    public void submit(RiceMillBlockEntity mill, float pTicks, PoseStack stack, 
            RenderContext_1_21_9<RiceMillBlockEntity> context_1_21_9,
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
        context_1_21_9.renderState().defferedSetup_1_21_9 = () -> { //1.21.9+
        this.model.setUpMillAnim(mill, pTicks);
        }; //1.21.9+
        var renderType_1_21_9 = (RenderTypes.entityCutoutNoCull(Resources.RICE_MILL_MODEL));
        this.submitModel(context_1_21_9, this.model, renderType_1_21_9, stack, light, OverlayTexture.NO_OVERLAY, 0xffffffff);
        stack.popPose();
    }

    // Neoforge
    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(RiceMillBlockEntity mill) {
        return mill.getRenderBoundingBox();
    }
        
}
