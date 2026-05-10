package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.common.entity.misc.DogThrownTrident;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class DogThrownTridentRenderer extends EntityRenderer<DogThrownTrident, DogThrownTridentRenderer.DogThrownTridentRenderState> {

    public static class DogThrownTridentRenderState extends EntityRenderState {
        public float xRot;
        public float yRot;
        public boolean foil;
    }

    private final TridentModel model;

    public DogThrownTridentRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new TridentModel(ctx.bakeLayer(ModelLayers.TRIDENT));
    }

    @Override
    public DogThrownTridentRenderState createRenderState() {
        return new DogThrownTridentRenderState();
    }

    @Override
    public void extractRenderState(DogThrownTrident entity, DogThrownTridentRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.xRot = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        state.yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
        state.foil = entity.isFoil();
    }

    public Identifier getTextureLocation(DogThrownTridentRenderState state) {
        return ThrownTridentRenderer.TRIDENT_LOCATION;
    }

    @Override
    public void submit(DogThrownTridentRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(state.xRot + 90.0F));
        collector.submitModel(model, net.minecraft.util.Unit.INSTANCE, stack,
            model.renderType(ThrownTridentRenderer.TRIDENT_LOCATION),
            state.lightCoords, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
        if (state.foil) {
            collector.submitModel(model, net.minecraft.util.Unit.INSTANCE, stack,
                net.minecraft.client.renderer.rendertype.RenderTypes.entityGlint(),
                state.lightCoords, OverlayTexture.NO_OVERLAY, 0xffffffff, null);
        }
        stack.popPose();
    }
}
