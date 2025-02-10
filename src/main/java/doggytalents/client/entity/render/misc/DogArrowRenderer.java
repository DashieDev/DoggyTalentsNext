package doggytalents.client.entity.render.misc;

import doggytalents.client.backward_imitate.DogArrowRenderState_21_3;
import doggytalents.common.entity.misc.DogArrow;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DogArrowRenderer extends ArrowRenderer<DogArrow, DogArrowRenderState_21_3> {
    public static final ResourceLocation NORMAL_ARROW_LOCATION = Util.getVanillaResource("textures/entity/projectiles/arrow.png");
    public static final ResourceLocation TIPPED_ARROW_LOCATION = Util.getVanillaResource("textures/entity/projectiles/tipped_arrow.png");
    public static final ResourceLocation SPECTRAL_ARROW_LOCATION = Util.getVanillaResource("textures/entity/projectiles/spectral_arrow.png");

    public DogArrowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    public ResourceLocation getTextureLocation(DogArrow arrow) {
        if (arrow.isDogSpectralArrow())
            return SPECTRAL_ARROW_LOCATION;
        return arrow.getColor() > 0 ? TIPPED_ARROW_LOCATION : NORMAL_ARROW_LOCATION;
    }



    //1.21.3+
    @Override
    protected ResourceLocation getTextureLocation(DogArrowRenderState_21_3 p_368566_) {
        return getTextureLocation(p_368566_.dogArrow);
    }
    @Override
    public DogArrowRenderState_21_3 createRenderState() {
        return new DogArrowRenderState_21_3();
    }
    public void extractRenderState(DogArrow dog_arrow, DogArrowRenderState_21_3 render_state, float pticks) {
        super.extractRenderState(dog_arrow, render_state, pticks);
        render_state.dogArrow = dog_arrow;
    };
}

