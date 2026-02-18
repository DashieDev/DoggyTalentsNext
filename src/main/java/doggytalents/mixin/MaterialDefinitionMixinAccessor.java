package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.geom.builders.MaterialDefinition;

@Mixin(MaterialDefinition.class)
public interface MaterialDefinitionMixinAccessor {
    
    @Accessor("xTexSize")
    int dtn__xTexSize();

    @Accessor("yTexSize")
    int dtn__yTexSize();

}
