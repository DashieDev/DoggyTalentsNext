package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import doggytalents.common.fabric_helper.entity.FabricMobKillDropCapture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("TAIL"))
    public void dtn__spawnAtLocation(ItemStack stack, float yOffset, CallbackInfoReturnable<ItemEntity> info) {
        var self = (Entity)(Object)this;
        if (self.level().isClientSide)
            return;
        if (!(self instanceof LivingEntity living))
            return;
        FabricMobKillDropCapture.onServerMobSpawnItemDrop(living);
    }
    
}
