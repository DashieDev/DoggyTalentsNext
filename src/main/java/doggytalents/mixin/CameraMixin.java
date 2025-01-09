package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.DTNClientDogSleepOnManager;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(at = @At("TAIL"),  method = "setup", cancellable = false)
    protected void dtn__setup(BlockGetter level, Entity entity, boolean p_90578_, boolean p_90579_, float p_90580_, CallbackInfo info) {
        var self = (Camera)(Object)this;
        DTNClientDogSleepOnManager.get().afterCameraSetup(self, entity);   
    }

}
