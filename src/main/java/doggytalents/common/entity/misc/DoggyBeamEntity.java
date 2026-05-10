package doggytalents.common.entity.misc;

import com.google.common.base.Predicates;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.api.feature.DogMode;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.EntityUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Collections;
import java.util.UUID;

public class DoggyBeamEntity extends ThrowableProjectile {

    public DoggyBeamEntity(EntityType<? extends ThrowableProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public DoggyBeamEntity(Level worldIn, LivingEntity livingEntityIn) {
        super(DoggyEntityTypes.DOG_BEAM.get(), livingEntityIn.getX(), livingEntityIn.getEyeY() - 0.1, livingEntityIn.getZ(), worldIn);
        this.setOwner(livingEntityIn);
    }

    // public DoggyBeamEntity(PlayMessages.SpawnEntity packet, Level worldIn) {
    //     super(DoggyEntityTypes.DOG_BEAM.get(), worldIn);
    // }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            if (!this.level().isClientSide()) {
                mayTriggerNearbyDogs((EntityHitResult)result);
            } else {
                for (int j = 0; j < 8; ++j) {
                    this.level().addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
                }
            }
        }

        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    private void mayTriggerNearbyDogs(EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity hit))
            return;
        if (!(this.getOwner() instanceof Player thrower))
            return;

        boolean do_cooldown = false;

        var trigger_bb = thrower.getBoundingBox().inflate(16, 8, 16);
        var trigger_list = 
            this.level().getEntitiesOfClass(Dog.class, trigger_bb,
                filter_dog -> isEligibleDog(filter_dog, hit, thrower));
        do_cooldown = !trigger_list.isEmpty();

        int trigger_limit = ConfigHandler.SERVER.TACTICAL_LIMIT.get();
        if (trigger_limit > 0 && trigger_list.size() > trigger_limit) {
            Collections.shuffle(trigger_list);
            trigger_list = trigger_list.subList(0, trigger_limit);
        }
        for (var dog : trigger_list) {
            var attack_manager = dog.dogAttackManager;
            attack_manager.setDogTaticalTarget(hit);
        }

        if (do_cooldown)
            thrower.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(DoggyItems.WHISTLE.get()), 40);
    }

    private boolean isEligibleDog(Dog dog, LivingEntity target, LivingEntity thrower) {
        if (dog == target)
            return false;
        if (dog.isOrderedToSit())
            return false;
        var dog_owner_id = dog.getOwnerUUID();
        var thrower_id = thrower.getUUID();
        if (dog_owner_id == null)
            return false;
        if (!dog_owner_id.equals(thrower_id))
            return false;
        if (!dog.wantsToAttack(target, thrower))
            return false;

        double target_dist_sqr = dog.distanceToSqr(target);
        var attack_manager = dog.dogAttackManager;
        final double range_far = attack_manager.getFarFollowRange();
        boolean tactical_condition =
            dog.getMode() == DogMode.TACTICAL
            && target_dist_sqr < range_far * range_far;
        return tactical_condition;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder b) {

    }
}
