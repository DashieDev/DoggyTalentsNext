package doggytalents.client.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.ClientSetup;
import doggytalents.client.block.model.RiceMillModel;
import doggytalents.common.block.RiceMillBlock;
import doggytalents.common.block.tileentity.RiceMillBlockEntity;
import doggytalents.common.lib.Resources;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Unit;

public class RiceMillRenderer implements BlockEntityRenderer<RiceMillBlockEntity, RiceMillRenderer.RiceMillRenderState> {

    private RiceMillModel model;

    public RiceMillRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new RiceMillModel(ctx.bakeLayer(ClientSetup.RICE_MILL));
    }

    public static class RiceMillRenderState extends BlockEntityRenderState {
        public long animTimeMillis;
        public net.minecraft.core.Direction facing;
    }

    @Override
    public RiceMillRenderState createRenderState() {
        return new RiceMillRenderState();
    }

    @Override
    public void extractRenderState(RiceMillBlockEntity mill, RiceMillRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(mill, state, crumblingOverlay);
        state.facing = RiceMillBlock.getFacing(mill.getBlockState());
        double timeLine = mill.isSpinning()
            ? (mill.getAnimationTick() + partialTick) % (double) RiceMillBlockEntity.GRIND_ANIM_TICK_LEN
            : 0.0;
        state.animTimeMillis = doggytalents.common.util.Util.tickMayWithPartialToMillis(timeLine);
    }

    @Override
    public void submit(RiceMillRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        stack.pushPose();
        stack.scale(1f, -1f, -1f);
        stack.translate(0.5f, 0f, -0.5f);
        stack.mulPose(Axis.YP.rotationDegrees(state.facing.getOpposite().toYRot()));
        stack.scale(2f, 2f, 2f);
        stack.translate(-0.25f, -1.501f, -0.25f);
        model.resetAllPose();
        model.setupAnimFromTime(state.animTimeMillis);
        collector.submitModel(model, net.minecraft.util.Unit.INSTANCE, stack,
            RenderTypes.entityCutout(Resources.RICE_MILL_MODEL),
            state.lightCoords, OverlayTexture.NO_OVERLAY, 0xffffffff, state.breakProgress);
        stack.popPose();
    }

    // Neoforge IBlockEntityRendererExtension
    public net.minecraft.world.phys.AABB getRenderBoundingBox(RiceMillBlockEntity mill) {
        return mill.getRenderBoundingBox();
    }

}
