package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;F)V")
    public void dtn__fallOn(Level level, BlockState state, 
        BlockPos pos, Entity entity, float fallDist, CallbackInfo info) {
        if (entity instanceof Dog dog && !dog.canTrample(state, pos, fallDist))
            info.cancel();
    }

}
