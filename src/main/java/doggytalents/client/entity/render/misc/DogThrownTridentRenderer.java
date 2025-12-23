package doggytalents.client.entity.render.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import doggytalents.client.backward_imitate.EntityRenderer_1_21_9;
import doggytalents.client.backward_imitate.EntityRenderer_21_3;
import doggytalents.common.entity.misc.DogThrownTrident;
import net.minecraft.client.model.object.projectile.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;

public class DogThrownTridentRenderer extends EntityRenderer_1_21_9<DogThrownTrident> {
    private final TridentModel model;

    public DogThrownTridentRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new TridentModel(ctx.bakeLayer(ModelLayers.TRIDENT));
    }

    @Override
    public void submit(DogThrownTrident p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, RenderContext_1_21_9<DogThrownTrident> context_1_21_9, int p_116116_) {
        p_116114_.pushPose();
        p_116114_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.yRotO, p_116111_.getYRot()) - 90.0F));
        p_116114_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.xRotO, p_116111_.getXRot()) + 90.0F));
        
        // var vertexconsumer = ItemRenderer.getFoilBuffer(
        //     p_116115_, this.model.renderType(this.getTextureLocation(p_116111_)), false, p_116111_.isFoil()
        // );
        // this.model.renderToBuffer(p_116114_, vertexconsumer, p_116116_, OverlayTexture.NO_OVERLAY, 0xffffffff);
        submitDogThrownTrident_1_21_9(p_116114_, context_1_21_9, p_116111_);

        p_116114_.popPose();
        //super.render(p_116111_, p_116112_, p_116113_, p_116114_, context_1_21_9, p_116116_);
    }

    public Identifier getTextureLocation(DogThrownTrident p_116109_) {
        return ThrownTridentRenderer.TRIDENT_LOCATION;
    }



    //1.21.9+
    private void submitDogThrownTrident_1_21_9(PoseStack stack, RenderContext_1_21_9<DogThrownTrident> ctx, DogThrownTrident trident) {
        var render_state = ctx.renderState();
        var list = ItemRenderer.getFoilRenderTypes(
            this.model.renderType(getTextureLocation(trident)), false, trident.isFoil());

        for (int i = 0; i < list.size(); i++) {
            ctx.collector().order(i)
                .submitModel(
                    this.model, Unit.INSTANCE, stack, list.get(i), render_state.lightCoords, OverlayTexture.NO_OVERLAY, -1, null, render_state.outlineColor, null
                );
        }
    }
}