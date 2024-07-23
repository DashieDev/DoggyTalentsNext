package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.client.ComputeCameraAngles;
import doggytalents.forge_imitate.event.client.RenderPlayerEvent;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;

@Mixin(Camera.class)
public class CameraMixin {
    
    @Inject(at = @At("TAIL"), method = "setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V")
    public void dtn__setup(BlockGetter p_90576_, Entity p_90577_, boolean p_90578_, boolean p_90579_, float p_90580_,CallbackInfo info) {
        var self = (Camera)(Object)this;
        EventCallbacksRegistry.postEvent(new ComputeCameraAngles(
            self, self.getYRot(), self.getXRot()
        ));
    }

}
