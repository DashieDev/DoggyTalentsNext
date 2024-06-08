package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.MobSpawnEvent;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

@Mixin(PathfinderMob.class)
public class PathFinderMobMixin {
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z")
    public void dtn__checkSpawnRule(LevelAccessor levelAccessor, MobSpawnType mobSpawnType, CallbackInfoReturnable<Boolean> info) {
        if (!(levelAccessor instanceof ServerLevelAccessor sLevelAccessor))
            return;

        var self = (PathfinderMob)(Object)this;
        var event = new MobSpawnEvent.PositionCheck(self, sLevelAccessor, mobSpawnType);
        EventCallbacksRegistry.postEvent(event);
        if (event.getResult() == MobSpawnEvent.PositionCheck.Result.ALLOW) {
            info.setReturnValue(true);
        }
    }

}
