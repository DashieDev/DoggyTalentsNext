package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.Camera;

@Mixin(Camera.class)
public interface CameraMixinAccessor {
    
    @Invoker("setRotation(FF)V")
    void dtn__setRotation(float yRot, float xRot);

    @Invoker("setPosition(DDD)V")
    void dtn__setPosition(double x, double y, double z);

    @Invoker("move(FFF)V")
    void dtn__move(float x, float y, float z);

    @Invoker("getMaxZoom(F)F")
    float dtn__getMaxZoom(float distance);

}
