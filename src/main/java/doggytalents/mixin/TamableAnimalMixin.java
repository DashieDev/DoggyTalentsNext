package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.common.event.EventHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;

@Mixin(TamableAnimal.class)
public class TamableAnimalMixin {
    
    @Inject(at = @At("TAIL"),  method = "tame(Lnet/minecraft/world/entity/player/Player;)V", cancellable = false)
    protected void dtn__tame(Player player, CallbackInfo info) {
        var self = (TamableAnimal)(Object)this;
        if (self.getType() != EntityType.WOLF)
            return;
        if (!(self instanceof Wolf wolf))
            return;
        EventHandler.onWolfTame(wolf);
    }

}
