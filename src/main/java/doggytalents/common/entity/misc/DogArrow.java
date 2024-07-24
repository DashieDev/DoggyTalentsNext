package doggytalents.common.entity.misc;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.event.EventHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

public class DogArrow extends AbstractArrow {

    private static final int NULL_COLOR = -1;
    
    private static final EntityDataAccessor<Integer> EFFECT_COLOR = SynchedEntityData.defineId(DogArrow.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_SPECTRAL = SynchedEntityData.defineId(DogArrow.class, EntityDataSerializers.BOOLEAN);

    public DogArrow(EntityType<DogArrow> p_36858_, Level p_36859_) {
        super(p_36858_, p_36859_);
    }

    public DogArrow(Level p_36866_, double p_308912_, double p_308958_, double p_309185_, ItemStack stack, ItemStack proj_stack) {
        super(DoggyEntityTypes.DOG_ARROW_PROJ.get(), p_308912_, p_308958_, p_309185_, p_36866_, stack, proj_stack);
        this.updateColor();
        if (stack.is(Items.SPECTRAL_ARROW)) {
            this.entityData.set(IS_SPECTRAL, true);
        }
    }

    public DogArrow(Level p_36861_, LivingEntity p_308924_, ItemStack stack, ItemStack proj_stack) {
        super(DoggyEntityTypes.DOG_ARROW_PROJ.get(), p_308924_, p_36861_, stack, proj_stack);
        this.updateColor();
        if (stack.is(Items.SPECTRAL_ARROW)) {
            this.entityData.set(IS_SPECTRAL, true);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326324_) {
        super.defineSynchedData(p_326324_);
        p_326324_.define(EFFECT_COLOR, NULL_COLOR);
        p_326324_.define(IS_SPECTRAL, false);
    }

    private PotionContents getPotionContents() {
        return this.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }

    private boolean hasPotionContents() {
        var data = getPotionContents();
        if (data == PotionContents.EMPTY)
            return false;
        return true;
    }

    private void clearPotionContents() {
        this.setPickupItemStack(new ItemStack(Items.ARROW));
    }

    @Override
    protected void setPickupItemStack(ItemStack p_331667_) {
        super.setPickupItemStack(p_331667_);
        this.updateColor();
    }

    private void updateColor() {
        int update_color = NULL_COLOR;
        if (this.hasPotionContents()) {
            update_color = this.getPotionContents().getColor();
        }
        this.entityData.set(EFFECT_COLOR, update_color);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isDogSpectralArrow()) {
            updateSpectralArrow();
        } else {
            if (this.level().isClientSide) {
                makeClientArrowParticle();
            } else {
                updateEffectTimeout();
            }
        }
    }

    private void makeClientArrowParticle() {
        int color = this.getColor();
        if (this.inGround) {
            if (this.inGroundTime % 5 == 0) {
                this.makeParticle(1, color);
            }
        } else {
            this.makeParticle(2, color);
        }
    }

    private void updateEffectTimeout() {
        boolean effect_expired = 
            this.inGround && this.inGroundTime >= 600
            && this.hasPotionContents();
        if (effect_expired) {
            this.clearPotionContents();
        }
    }

    private void updateSpectralArrow() {
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    private void makeParticle(int amount, int color) {
        if (color == NULL_COLOR || amount <= 0)
            return;
        for (int i = 0; i < amount; ++i) {
            this.level().addParticle(
                ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color),
                this.getRandomX(0.5),
                this.getRandomY(),
                this.getRandomZ(0.5),
                0.0,
                0.0,
                0.0
            );
        }
    }

    public int getColor() {
        return this.entityData.get(EFFECT_COLOR);
    }

    public boolean isDogSpectralArrow() {
        return this.entityData.get(IS_SPECTRAL);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        if (this.isDogSpectralArrow()) {
            doDogSpectralArrowEffectHurt(target);
        } else {
            doNormalDogArrowEffectHurt(target);
        }
        killCreeperIfCreeperSweeper(target);
    }

    private void doNormalDogArrowEffectHurt(LivingEntity target) {
        var effect_source = this.getEffectSource();
        var potion_contents = this.getPotionContents();
        if (potion_contents.potion().isPresent()) {
            for (var effect_inst : potion_contents.potion().get().value().getEffects()) {
                target.addEffect(
                    new MobEffectInstance(
                        effect_inst.getEffect(),
                        Math.max(effect_inst.mapDuration(x -> x / 8), 1),
                        effect_inst.getAmplifier(),
                        effect_inst.isAmbient(),
                        effect_inst.isVisible()
                    ),
                    effect_source
                );
            }
        }

        for (var custom_effect_inst : potion_contents.customEffects()) {
            target.addEffect(custom_effect_inst, effect_source);
        }
    }

    private void doDogSpectralArrowEffectHurt(LivingEntity target) {
        var glow_inst = new MobEffectInstance(MobEffects.GLOWING, 200, 0);
        target.addEffect(glow_inst, this.getEffectSource());
    }

    private void killCreeperIfCreeperSweeper(LivingEntity target) {
        var owner = this.getOwner();
        if (!(owner instanceof Dog dog))
            return;
        if (dog.getDogLevel(DoggyTalents.CREEPER_SWEEPER) < 5)
            return;
        if (!(target instanceof Creeper creeper))
            return;
        creeper.setHealth(0);
        creeper.die(dog.damageSources().mobAttack(dog));
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        if (target instanceof LivingEntity living) {
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
