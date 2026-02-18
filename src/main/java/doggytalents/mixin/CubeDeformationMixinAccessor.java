package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.geom.builders.CubeDeformation;

@Mixin(CubeDeformation.class)
public interface CubeDeformationMixinAccessor {
    
    @Accessor("growX")
    float dtn__growX();

    @Accessor("growY")
    float dtn__growY();

    @Accessor("growZ")
    float dtn__growZ();

}
