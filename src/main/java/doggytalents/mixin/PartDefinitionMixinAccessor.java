package doggytalents.mixin;

import java.util.List;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Mixin(PartDefinition.class)
public interface PartDefinitionMixinAccessor {
    
    @Accessor("cubes")
    List<CubeDefinition> dtn__cubes();
    
    @Accessor("partPose")
    PartPose dtn__partPose();

    @Accessor("children")
    Map<String, PartDefinition> dtn__children();

}
