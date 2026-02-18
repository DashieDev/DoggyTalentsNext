package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MaterialDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

@Mixin(LayerDefinition.class)
public interface LayerDefinitionMixinAccessor {
    
    @Accessor("mesh")
    MeshDefinition dtn__mesh();

    @Accessor("material")
    MaterialDefinition dtn__material();

}
