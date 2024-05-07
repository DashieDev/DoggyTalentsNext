package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator.Context.Local;

import doggytalents.DoggyAttributes;
import doggytalents.common.entity.Dog;
import doggytalents.forge_imitate.atrrib.ForgeMod;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.LootingLevelEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    
    @ModifyArgs(
        method = "dropAllDeathLoot(Lnet/minecraft/world/damagesource/DamageSource;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;dropCustomDeathLoot(Lnet/minecraft/world/damagesource/DamageSource;IZ)V"
        )
    )
    private void dtn__lootingLevelEvent(Args args) {
        final int SOURCE_INDX = 0, LOOTING_LEVEL_INDX = 1;
        var source = (DamageSource) args.get(SOURCE_INDX);
        int oldLevel = (Integer) args.get(LOOTING_LEVEL_INDX);
        if (source == null)
            return;
        var ret = EventCallbacksRegistry.postEvent(new LootingLevelEvent(source, oldLevel));
        int newLevel = ret.getLootingLevel();
        if (newLevel != oldLevel) {
            args.set(LOOTING_LEVEL_INDX, newLevel);
        }
    }

    // @ModifyVariable(
    //     method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
    //     at = @At(value = "STORE"),
    //     ordinal = 0
    // )
    // public double dtn__travel_gravityModify(double _gravity) {
    //     var self = (LivingEntity)(Object)this;
    //     if (self instanceof Dog dog) {
    //         return dog.getAttributeValue(ForgeMod.ENTITY_GRAVITY.get());
    //     }
    //     return _gravity;
    // }

    @ModifyArgs(
        method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"
        )
    )
    private void dtn__travel_modifySwimSpeed(Args args) {
        var self = (LivingEntity)(Object)this;
        if (!(self instanceof Dog dog))
            return;
        if (!dog.isInWater() || dog.isInLava())
            return;
        final int FLOAT_INDX = 0, VEC3_INDEX = 1;
        float current = (Float) args.get(FLOAT_INDX);
        current *= dog.getAttributeValue(ForgeMod.SWIM_SPEED.holder());
        args.set(FLOAT_INDX, current);
    }

}
