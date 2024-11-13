package doggytalents.common.entity.misc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import doggytalents.common.event.EventHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class DogArrow extends AbstractArrow {

    private static final int NULL_COLOR = -1;

    //1.20.1 and under
    private PotionContents potionContents = PotionContents.EMPTY;
    
    private static final EntityDataAccessor<Integer> EFFECT_COLOR = SynchedEntityData.defineId(DogArrow.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_SPECTRAL = SynchedEntityData.defineId(DogArrow.class, EntityDataSerializers.BOOLEAN);

    private Optional<UUID> dogOwnerUUID = Optional.empty();

    public DogArrow(EntityType<DogArrow> p_36858_, Level p_36859_) {
        super(p_36858_, p_36859_);
    }

    public DogArrow(Level p_36861_, AbstractDog dog, ItemStack stack) {
        super(DoggyEntityTypes.DOG_ARROW_PROJ.get(), dog, p_36861_);
        this.setEffectsFromItem_1_20_1_and_under(stack);
        this.updateColor();
        if (stack.is(Items.SPECTRAL_ARROW)) {
            this.entityData.set(IS_SPECTRAL, true);
        }
        var owner = dog.getOwner();
        if (owner != null)
            this.dogOwnerUUID = Optional.ofNullable(owner.getUUID());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EFFECT_COLOR, NULL_COLOR);
        this.entityData.define(IS_SPECTRAL, false);
    }

    private PotionContents getPotionContents() {
        return potionContents;
    }

    private boolean hasPotionContents() {
        var data = getPotionContents();
        if (data == PotionContents.EMPTY)
            return false;
        return true;
    }

    private void clearPotionContents() {
        this.potionContents = PotionContents.EMPTY;
    }

    // @Override
    // protected void setPickupItemStack(ItemStack p_331667_) {
    //     super.setPickupItemStack(p_331667_);
    //     this.updateColor();
    // }

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
        // for (int i = 0; i < amount; ++i) {
            // this.level().addParticle(
            //     ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color),
            //     this.getRandomX(0.5),
            //     this.getRandomY(),
            //     this.getRandomZ(0.5),
            //     0.0,
            //     0.0,
            //     0.0
            // );
        // }
        double r = (double)(color >> 16 & 255) / 255.0D;
        double g = (double)(color >> 8 & 255) / 255.0D;
        double b = (double)(color >> 0 & 255) / 255.0D;
        for (int i = 0; i < amount; ++i) {
            // this.level().addParticle(
            //     ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, color),
            //     this.getRandomX(0.5),
            //     this.getRandomY(),
            //     this.getRandomZ(0.5),
            //     0.0,
            //     0.0,
            //     0.0
            // );
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), r, g, b);
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
        if (potion_contents.potion.isPresent()) {
            for (var effect_inst : potion_contents.potion.get().getEffects()) {
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
    protected ItemStack getPickupItem() {
        return getPickupItem_1_20_1_under();
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

    @Override
    protected boolean tryPickup(Player picker) {
        if (shouldDogArrowBlockPickup(picker))
            return false;
        return super.tryPickup(picker);
    }

    private boolean shouldDogArrowBlockPickup(Player picker) {
        if (picker.isCreative())
            return false;
        if (!this.dogOwnerUUID.isPresent())
            return false;
        if (this.dogOwnerUUID.get().equals(picker.getUUID()))
            return false;
        return true;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.dogOwnerUUID.ifPresent(x -> {
            tag.putUUID("dtn_dog_owner_id", x);
        });

        //1.20 under
        this.add_1_20_under_PotionData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("dtn_dog_owner_id")) {
            var uuid = tag.getUUID("dtn_dog_owner_id");
            this.dogOwnerUUID = Optional.ofNullable(uuid);
        }

        //1.20 under
        this.read1_20_under_PotionData(tag);
    }

    public void setEffectsFromItem_1_20_1_and_under(ItemStack stack) {
        if (stack.is(Items.TIPPED_ARROW)) {
            var potion = PotionUtils.getPotion(stack);
            var customEffectsList = PotionUtils.getCustomEffects(stack);
            Set<MobEffectInstance> customEffectSet = null; 
            if (!customEffectsList.isEmpty()) {
                customEffectSet = new HashSet<>(customEffectsList.size());
                for(var effectInst : customEffectsList) {
                    customEffectSet.add(new MobEffectInstance(effectInst));
                }
            }
            this.potionContents = PotionContents.from(potion, customEffectSet);
        } else {
            this.potionContents = PotionContents.EMPTY;
        }

        updateColor();
  
     }

    //Forward imitate
    private static class PotionContents {
    
        public final Optional<Potion> potion;
        public final Optional<Set<MobEffectInstance>> customEffects;
        public final int color;
        
        private PotionContents(Optional<Potion> potion, Optional<Set<MobEffectInstance>> customEffects) {
            this.potion = potion;
            this.customEffects = customEffects;
            this.color = initColor();
        }

        public static PotionContents EMPTY = new PotionContents(Optional.empty(), Optional.empty());

        public static PotionContents from(Potion potion, Set<MobEffectInstance> custom) {
            boolean empty_condition = 
                (potion == null || potion == Potions.EMPTY)
                && (custom == null || custom.isEmpty());
            if (empty_condition)
                return EMPTY;
            if (potion == null) potion = Potions.EMPTY;
            if (custom == null) custom = Set.of();
            return new PotionContents(Optional.of(potion), Optional.of(custom));
        }
        
        public int initColor() {
            if (!this.potion.isPresent() && !this.customEffects.isPresent()) {
                return -1;
            } else {
                return PotionUtils.getColor(PotionUtils.getAllEffects(this.potion.get(), this.customEffects.get()));
            }
        }

        public int getColor() {
            return this.color;
        }

        public Set<MobEffectInstance> customEffects() {
            return this.customEffects.orElse(Set.of());
        }
    
    }
    private void read1_20_under_PotionData(CompoundTag compound) {
        Potion potion = null;
        if (compound.contains("Potion", 8)) {
            potion = PotionUtils.getPotion(compound);
        }

        var custom_set = new HashSet<MobEffectInstance>();
        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(compound)) {
            custom_set.add(mobeffectinstance);
        }

        this.potionContents = PotionContents.from(potion, custom_set);
        this.updateColor();
    }
    public void add_1_20_under_PotionData(CompoundTag compound) {
        if (this.potionContents.potion.isPresent()) {
            var potion = this.potionContents.potion.get();
            compound.putString("Potion", BuiltInRegistries.POTION.getKey(potion).toString());
        }
        if (this.potionContents.customEffects.isPresent()) {
            var customEffects = this.potionContents.customEffects.get();
            var listtag = new ListTag();

            for(MobEffectInstance mobeffectinstance : customEffects) {
                listtag.add(mobeffectinstance.save(new CompoundTag()));
            }

            compound.put("CustomPotionEffects", listtag);
        }
    }
    private ItemStack getPickupItem_1_20_1_under() {
        if (this.potionContents == PotionContents.EMPTY) {
            return new ItemStack(Items.ARROW);
        } else {
            var stack = new ItemStack(Items.TIPPED_ARROW);
            this.potionContents.potion.ifPresent(x -> {
                PotionUtils.setPotion(stack, x);
            });
            this.potionContents.customEffects.ifPresent(x -> {
                PotionUtils.setCustomEffects(stack, x);
            });

            return stack;
        }
    }

}
