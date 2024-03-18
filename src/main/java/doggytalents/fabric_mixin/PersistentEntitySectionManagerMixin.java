package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.common.entity.Dog;
import doggytalents.forge_imitate.event.EntityJoinLevelEvent;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin {
    
    @Inject(at = @At("RETURN"),  method = "addEntity(Lnet/minecraft/world/level/entity/EntityAccess;Z)Z")
    public void dtn__addEntity(EntityAccess entity, boolean x, CallbackInfoReturnable<Boolean> info) {
        var retVal = info.getReturnValue();
        if (!retVal)
            return;
        if (entity instanceof Dog dog) {
            dog.setAddedToWorld(true);
            dog.onAddedToWorld();
        }
        if (entity instanceof LivingEntity living) {
            EventCallbacksRegistry.postEvent(new EntityJoinLevelEvent(living));
        }
    }

}
