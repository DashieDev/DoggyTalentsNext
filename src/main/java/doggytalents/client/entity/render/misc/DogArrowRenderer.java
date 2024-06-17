package doggytalents.client.entity.render.misc;

import doggytalents.common.entity.misc.DogArrow;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DogArrowRenderer extends ArrowRenderer<DogArrow> {
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
}

