package doggytalents.mixin;

import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;

@Mixin(CubeDefinition.class)
public interface CubeDefinitionMixinAccessor {
    
    @Accessor("origin")
    Vector3f dtn__origin();

    @Accessor("dimensions")
    Vector3f dtn__dimensions();

    @Accessor("grow")
    CubeDeformation dtn__grow();

    @Accessor("mirror")
    boolean dtn__mirror();

    @Accessor("texCoord")
    UVPair dtn__texCoord();

}
