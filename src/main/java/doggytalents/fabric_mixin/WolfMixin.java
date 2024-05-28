package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.common.variants.DTNWolfVariantsSpawnPlacements;
import doggytalents.forge_imitate.event.EntityTravelToDimensionEvent;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.LevelAccessor;

@Mixin(Wolf.class)
public class WolfMixin {
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "checkWolfSpawnRules(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)Z")
    private static void dtn__checkWolfSpawnRule(EntityType<Wolf> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean> info) {
        var ret = DTNWolfVariantsSpawnPlacements.DTNWolfVariantsSpawnableOn(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
        if (ret) {
            info.setReturnValue(true);
        }
    }


}
