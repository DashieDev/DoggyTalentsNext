package doggytalents.client.entity.render.misc;

import doggytalents.common.entity.misc.DogArrow;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class DogArrowRenderer extends ArrowRenderer<DogArrow, DogArrowRenderer.DogArrowRenderState> {
    public static final Identifier NORMAL_ARROW_LOCATION = Util.getVanillaResource("textures/entity/projectiles/arrow.png");
    public static final Identifier TIPPED_ARROW_LOCATION = Util.getVanillaResource("textures/entity/projectiles/tipped_arrow.png");
    public static final Identifier SPECTRAL_ARROW_LOCATION = Util.getVanillaResource("textures/entity/projectiles/spectral_arrow.png");

    public static class DogArrowRenderState extends ArrowRenderState {
        public boolean spectral;
        public int color;
    }

    public DogArrowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public DogArrowRenderState createRenderState() {
        return new DogArrowRenderState();
    }

    @Override
    public void extractRenderState(DogArrow entity, DogArrowRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.spectral = entity.isDogSpectralArrow();
        state.color = entity.getColor();
    }

    @Override
    public Identifier getTextureLocation(DogArrowRenderState state) {
        if (state.spectral)
            return SPECTRAL_ARROW_LOCATION;
        return state.color > 0 ? TIPPED_ARROW_LOCATION : NORMAL_ARROW_LOCATION;
    }
}
