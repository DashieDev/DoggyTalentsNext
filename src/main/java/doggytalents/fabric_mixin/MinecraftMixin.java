package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.common.entity.Dog;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    
    //Patch dismount dog, for some reason Fabric ProjectileUtil doesn't pick Dog on head.
    @Inject(at = @At("HEAD"),  method = "startUseItem()V")
    public void dtn_startUseItem(CallbackInfo info) {
        var self = (Minecraft)(Object) this;
        var hitResult = self.hitResult;
        var player = self.player;

        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS)
            return;
        
        if (player == null)
            return;
        if (!player.isVehicle())
            return;
        
        var passengers = player.getPassengers();
        if (passengers == null)
            return;
        Dog toDismount = null;
        for (var p : passengers) {
            if (p instanceof Dog dog) {
                toDismount = dog;
            }
        }
        if (toDismount == null)
            return;

        var camera_entity = self.getCameraEntity();
        double pick_range = player.entityInteractionRange();
        var view_vec = camera_entity.getViewVector(1);
        var eye_pos = camera_entity.getEyePosition(0);
        var from_vec = eye_pos;
        var to_vec = eye_pos.add(view_vec.scale(pick_range));
        var dog_bb = toDismount.getBoundingBox();
        boolean hit_dog = dog_bb.clip(from_vec, to_vec).isPresent();

        if (hit_dog)
            self.hitResult = new EntityHitResult(toDismount);
    }

}
