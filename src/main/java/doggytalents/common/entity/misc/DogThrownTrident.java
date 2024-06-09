package doggytalents.common.entity.misc;

import java.util.Optional;

import javax.annotation.Nullable;

import doggytalents.DoggyEntityTypes;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.event.EventHandler;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class DogThrownTrident extends AbstractArrow {
    
    private static final int loyalty_amount = 3;

    private static final EntityDataAccessor<Boolean> FOIL = SynchedEntityData.defineId(DogThrownTrident.class, EntityDataSerializers.BOOLEAN);
    private boolean isReturning;
    private boolean playedClientReturnSound;
    private int timeOutTick = 0;

    //1.20.2 under
    private ItemStack tridentStack;

    public DogThrownTrident(EntityType<DogThrownTrident> p_37561_, Level p_37562_) {
        super(p_37561_, p_37562_);
    }

    public DogThrownTrident(Dog dog, ItemStack trident_stack) {
        super(DoggyEntityTypes.DOG_TRIDENT_PROJ.get(), dog, dog.level());
        this.entityData.set(FOIL, trident_stack.hasFoil());
        this.tridentStack = trident_stack;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FOIL, false);
    }

    @Override
    public void tick() {

        var return_owner_optional = this.getDogOwnerForReturn();

        if (!this.level().isClientSide) {
            if (this.doDogTridentInvalidateSelf(return_owner_optional))
                return;
        }
        
        if (return_owner_optional.isPresent()) {
            var return_owner = return_owner_optional.get();
            updateReturning(return_owner);
            if (this.isReturning)
                returnToDog(return_owner);
        }

        super.tick();
    }

    private void updateReturning(Dog return_dog) {
        if (this.isReturning)
            return;
        if (this.inGroundTime > 1 || this.isNoPhysics()) {
            switchToReturnToDog();
            return;
        }
    }

    private void returnToDog(Dog return_dog) {
        this.setNoPhysics(true);
        var pos = this.position();
        var dog_pos = return_dog.getEyePosition();
        var v_self_dog = dog_pos.subtract(pos);

        var current_move_vec = this.getDeltaMovement();
        double l_current_move_vec = current_move_vec.length();
        final float max_speed = 1.2f;
        final double acceleration = 0.05 * loyalty_amount;
        double return_speed = Mth.clamp(l_current_move_vec + acceleration, 0, max_speed);
        var return_vec = v_self_dog.normalize().scale(return_speed);
        this.setDeltaMovement(return_vec);
        if (!this.playedClientReturnSound) {
            this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
            this.playedClientReturnSound = true;
        }
    }

    private void switchToReturnToDog() {
        this.isReturning = true;
    }
 
    private boolean doDogTridentInvalidateSelf(Optional<Dog> return_dog_optional) {
        ++this.timeOutTick;
        if (this.timeOutTick > getDogTridentTimeout()) {
            this.discard();
            return true;
        }
        if (!return_dog_optional.isPresent()) {
            this.discard();
            return true;
        }
        var return_dog = return_dog_optional.get();
        if (this.isReturning && return_dog.distanceToSqr(this) <= 2 * 2) {
            this.discard();
            return true;
        }
        return false;
    }

    public Optional<Dog> getDogOwnerForReturn() {
        var owner = this.getOwner();
        if (!(owner instanceof Dog dog)) {
            return Optional.empty();
        }
        if (!dog.isDoingFine())
            return Optional.empty();
        if (dog.distanceToSqr(this) > 30 * 30)
            return Optional.empty();
        if (dog.isInSittingPose())
            return Optional.empty();

        return Optional.of(dog);
    }

    public boolean isFoil() {
        return this.entityData.get(FOIL);
    }

    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 p_37575_, Vec3 p_37576_) {
        return this.isReturning ? null : super.findHitEntity(p_37575_, p_37576_);
    }
    

    @Override
    protected void onHitEntity(EntityHitResult hit_result) {
        var target = hit_result.getEntity();
        
        var hurt_success = this.hurtDogTridentTarget(target);
        var did_lightning = this.maySummonLightningBolt(target);
        this.switchToReturnToDog();
        this.setDeltaMovement(Vec3.ZERO);
        
        SoundEvent play_sound = null;
        if (did_lightning) {
            play_sound = SoundEvents.TRIDENT_THUNDER;
        } else if (hurt_success) {
            play_sound = SoundEvents.TRIDENT_HIT;
        } else {
            play_sound = null;
        }
        float sound_volume = did_lightning ? 5 : 1;
        if (play_sound != null)
            this.playSound(play_sound, sound_volume, 1);
    }

    private boolean hurtDogTridentTarget(Entity target) {
        float damage = 8;
        if (target instanceof LivingEntity living) {
            damage += EnchantmentHelper.getDamageBonus(tridentStack, living.getMobType());
        }
        var owner = this.getOwner();
        var indirect_entity_source = owner == null ? this : owner;
        var trident_source = this.damageSources().arrow(this, indirect_entity_source);
        if (this.isOnFire()) {
            target.setSecondsOnFire(5);
        }
        var result = target.hurt(trident_source, damage);
        if (!result)
            return false;

        doDogTridentEnchantDamageEffects(owner, target);
        return true;
    }

    private void doDogTridentEnchantDamageEffects(Entity owner, Entity target) {
        if (target instanceof LivingEntity target_living) {
            if (owner instanceof LivingEntity owner_living) {
                EnchantmentHelper.doPostHurtEffects(target_living, owner_living);
                EnchantmentHelper.doPostDamageEffects(owner_living, target_living);
            }

            this.doPostHurtEffects(target_living);
        }
    }

    public boolean isChanneling() {
        return EnchantmentHelper.hasChanneling(tridentStack);
    }

    private boolean maySummonLightningBolt(Entity target) {
        if (!(this.level() instanceof ServerLevel))
            return false;
        if (!this.level().isThundering())
            return false;
        if (!this.isChanneling())
            return false;

        var target_b0 = target.blockPosition();
        if (!this.level().canSeeSky(target_b0))
            return false;
        
        var lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level());
        if (lightningbolt == null)
            return false;

        lightningbolt.moveTo(Vec3.atBottomCenterOf(target_b0));
        lightningbolt.setCause(null);
        this.level().addFreshEntity(lightningbolt);

        return true;
    }

    public static int getDogTridentTimeout() {
        return 50;
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.TRIDENT);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double p_37588_, double p_37589_, double p_37590_) {
        return true;
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        if (ConfigHandler.SERVER.DOGGY_TOOLS_PROJECTILE_PASS_ALLIES.get()
            && target instanceof LivingEntity living) {
            if (checkAlliesToDog(living))
                return false;
        }
        return super.canHitEntity(target);
    }

    private boolean checkAlliesToDog(LivingEntity target) {
        var owner = this.getOwner();
        if (!(owner instanceof Dog dog)) {
            return false;
        }
        var dog_owner = dog.getOwner();
        if (dog_owner == null)
            return false;
        return EventHandler.isAlliedToDog(target, dog_owner);
    }

}
