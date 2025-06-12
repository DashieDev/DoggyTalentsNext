package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.Camera;

@Mixin(Camera.class)
public interface CameraMixinAccessor {
    
    @Invoker("setRotation")
    void dtn__setRotation(float yRot, float xRot);

    @Invoker("setPosition")
    void dtn__setPosition(double x, double y, double z);

    @Invoker("move")
    void dtn__move(double x, double y, double z);

    @Invoker("getMaxZoom")
    double dtn__getMaxZoom(double distance);

}
