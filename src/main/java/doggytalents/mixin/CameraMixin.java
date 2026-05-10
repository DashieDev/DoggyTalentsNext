package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.client.DTNClientDogSleepOnManager;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(at = @At("TAIL"), method = "update", cancellable = false)
    protected void dtn__setup(DeltaTracker deltaTracker, CallbackInfo info) {
        var self = (Camera)(Object)this;
        DTNClientDogSleepOnManager.get().afterCameraSetup(self, self.entity());
    }

}
