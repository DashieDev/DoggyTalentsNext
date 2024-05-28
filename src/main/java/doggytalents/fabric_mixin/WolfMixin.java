package doggytalents.fabric_mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.common.variants.DTNWolfVariantsSpawnPlacements;
import doggytalents.forge_imitate.event.EntityTravelToDimensionEvent;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.MobSpawnEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.entity.animal.Wolf.WolfPackData;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

@Mixin(Wolf.class)
public class WolfMixin {
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "checkWolfSpawnRules(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)Z")
    private static void dtn__checkWolfSpawnRule(EntityType<Wolf> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean> info) {
        var ret = DTNWolfVariantsSpawnPlacements.DTNWolfVariantsSpawnableOn(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
        if (ret) {
            info.setReturnValue(true);
        }
    }

    @Inject(at = @At("TAIL"), method = "finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;")
    public void dtn__finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> info) {
        var self = (Wolf)(Object)this;
        //Check only for spanwed Rusty for now but may check
        //for every spawning wolf in the future.
        var variant = self.getVariant();
        if (!variant.is(WolfVariants.RUSTY))
            return;
        var event = new MobSpawnEvent.FinalizeSpawn(self, mobSpawnType, null);
        EventCallbacksRegistry.postEvent(event);
        var ret_data = event.getSpawnData();
        if (ret_data instanceof WolfPackData wolf_data) {
            self.setVariant(wolf_data.type);
        }
    }


}
