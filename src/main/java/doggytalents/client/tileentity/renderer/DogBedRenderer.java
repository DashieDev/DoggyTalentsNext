package doggytalents.client.tileentity.renderer;

import com.google.common.base.Objects;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.block.tileentity.DogBedTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class DogBedRenderer implements BlockEntityRenderer<DogBedTileEntity, DogBedRenderer.DogBedRenderState> {

    public static class DogBedRenderState extends BlockEntityRenderState {
        public Component ownerName;
        public boolean isLookedAt;
    }

    public DogBedRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public DogBedRenderState createRenderState() {
        return new DogBedRenderState();
    }

    @Override
    public void extractRenderState(DogBedTileEntity bed, DogBedRenderState state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderState.extractBase(bed, state, crumblingOverlay);
        state.ownerName = bed.getOwnerName();
        state.isLookedAt = isLookingAtBed(bed);
    }

    @Override
    public void submit(DogBedRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        if (!state.isLookedAt || state.ownerName == null) return;
        var font = Minecraft.getInstance().font;
        FormattedCharSequence text = state.ownerName.getVisualOrderText();
        int textWidth = font.width(text);
        poseStack.pushPose();
        poseStack.translate(0.5, 1.55, 0.5);
        poseStack.scale(0.025f, -0.025f, 0.025f);
        collector.submitText(poseStack, -textWidth / 2f, 0f, text,
            false, Font.DisplayMode.NORMAL,
            state.lightCoords, 0xffffffff, 0x40000000, 0x20ffffff);
        poseStack.popPose();
    }

    private boolean isLookingAtBed(DogBedTileEntity bed) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult) mc.hitResult).getBlockPos();
            return Objects.equal(blockpos, bed.getBlockPos());
        }
        return false;
    }
}
