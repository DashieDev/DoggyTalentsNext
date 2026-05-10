package doggytalents.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.common.entity.DogAllyCheck;
import net.minecraft.world.entity.Entity;

@Mixin(Entity.class)
public class EntityMixin {
    
    @Inject(at = @At("HEAD"),  method = "isAlliedTo", cancellable = true)
    protected void dtn__isAlliedTo(Entity entity, CallbackInfoReturnable<Boolean> info) {
        var self = (Entity)(Object)this;
        if (self.level().isClientSide())
            return;
        boolean result = DogAllyCheck.onEntityIsAlliedToServer(self, entity);
        if (result)
            info.setReturnValue(true);
    }
    
}
