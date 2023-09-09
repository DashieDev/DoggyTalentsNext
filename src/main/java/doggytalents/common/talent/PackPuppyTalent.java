package doggytalents.common.talent;

import doggytalents.DoggyItems;
import doggytalents.DoggyTags;
import doggytalents.DoggyTalents;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.MeatFoodHandler;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.checkerframework.checker.nullness.qual.NonNull;

public class PackPuppyTalent extends TalentInstance {

    public static Capability<PackPuppyItemHandler> PACK_PUPPY_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});;

    private PackPuppyItemHandler packPuppyHandler;
    private LazyOptional<?> lazyPackPuppyHandler;

    public static Predicate<ItemEntity> SHOULD_PICKUP_ENTITY_ITEM = (entity) -> {
        return entity.isAlive() && !entity.hasPickUpDelay() && !entity.getItem().is(DoggyTags.PACK_PUPPY_BLACKLIST);// && !EntityAIFetch.BONE_PREDICATE.test(entity.getItem());
    };
    public static ChestDogFoodHandler FOOD_HANDLER = new ChestDogFoodHandler();

    public PackPuppyTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
        PackPuppyItemHandler handler = new PackPuppyItemHandler();
        this.packPuppyHandler = handler;
        this.lazyPackPuppyHandler = LazyOptional.of(() -> handler);
    }

    public PackPuppyItemHandler inventory() {
        return this.packPuppyHandler;
    }

    @Override
    public void tick(AbstractDog dogIn) {
        if (dogIn.isDoingFine() && !dogIn.level.isClientSide && this.level() >= 5) {
            List<ItemEntity> list = dogIn.level.getEntitiesOfClass(ItemEntity.class, dogIn.getBoundingBox().inflate(2.5D, 1D, 2.5D), SHOULD_PICKUP_ENTITY_ITEM);

            if (!list.isEmpty()) {
                for (ItemEntity entityItem : list) {
                    ItemStack remaining = InventoryUtil.addItem(this.packPuppyHandler, entityItem.getItem());

                    if (!remaining.isEmpty()) {
                        entityItem.setItem(remaining);
                    } else {
                        entityItem.discard();
                        dogIn.playSound(SoundEvents.ITEM_PICKUP, 0.25F, ((dogIn.level.random.nextFloat() - dogIn.level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    }
                }
            }
        }
        tickOfferFoodToTeammate(dogIn);
    }


    private final int TRIGGER_RADIUS = 12;
    private int tickTillUpdateFood = 30;
    private void tickOfferFoodToTeammate(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;

        if (--this.tickTillUpdateFood > 0)
            return;
        this.tickTillUpdateFood = 30;
        
        if (!(dogIn instanceof Dog dog))
            return;
        
        var food = findFood(dog, dog);
        if (food == null) 
            return;

        var hungry_dogs = getNearbyHungryDogs(dog);
        if (hungry_dogs.isEmpty()) return;
        for (var hungry_dog : hungry_dogs) {
            checkAndFeedDog(dog, hungry_dog, food);
        }

    }

    private void checkAndFeedDog(Dog dog, Dog target, @NonNull ItemStack food) {
        if (target == dog) {
            FOOD_HANDLER.consume(dog, food, target);
            return;
        }
        if (target.isBusy())
            return;
        if (target.isInSittingPose())
            return;
        target.triggerAction(
            new DogEatFromChestDogAction(target, dog)
        );
    }

    private List<Dog> getNearbyHungryDogs(Dog dog) {
        var dogs = dog.level().getEntitiesOfClass(
            Dog.class, 
            dog.getBoundingBox().inflate(TRIGGER_RADIUS, 4, TRIGGER_RADIUS), 
            this::isHungryDog);
        return dogs;
    }

    private boolean isHungryDog(Dog dog) {
        return dog.isDoingFine() && dog.getDogHunger() < 25;
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if (dogIn.isTame() && this.level() > 0) { // Dog requirements
            if (playerIn.isShiftKeyDown() && stack.isEmpty()) { // Player requirements

                if (dogIn.canInteract(playerIn)) {

                    if (!playerIn.level.isClientSide) {
                        playerIn.displayClientMessage(ComponentUtil.translatable("talent.doggytalents.pack_puppy.version_migration"), false);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void set(AbstractDog dog, int preLevel) {
        // No need to drop anything if dog didn't have pack puppy
        if (preLevel > 0 && this.level == 0) {
            this.dropInventory(dog);
        }
    }

    @Override
    public void dropInventory(AbstractDog dogIn) {
        //TODO either drop inventory or save to respawn data, currently does both
        // No need to drop anything if dog didn't have pack puppy
        for (int i = 0; i < this.packPuppyHandler.getSlots(); ++i) {
            Containers.dropItemStack(dogIn.level, dogIn.getX(), dogIn.getY(), dogIn.getZ(), this.packPuppyHandler.getStackInSlot(i));
            this.packPuppyHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.merge(this.packPuppyHandler.serializeNBT());
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.packPuppyHandler.deserializeNBT(compound);
    }

    // Left in for backwards compatibility for versions <= 2.0.0.5
    @Override
    public void onRead(AbstractDog dogIn, CompoundTag compound) {
        this.packPuppyHandler.deserializeNBT(compound);
    }

    @Override
    public <T> LazyOptional<T> getCapability(AbstractDog dogIn, Capability<T> cap, Direction side) {
        if (cap == PackPuppyTalent.PACK_PUPPY_CAPABILITY) {
            return (LazyOptional<T>) this.lazyPackPuppyHandler;
        }
        return null;
    }

    @Override
    public void invalidateCapabilities(AbstractDog dogIn) {
        this.lazyPackPuppyHandler.invalidate();
    }

    @Override
    public boolean hasRenderer() {
        return ConfigHandler.ClientConfig.getConfig(ConfigHandler.CLIENT.RENDER_CHEST);
    }

    public static boolean hasInventory(AbstractDog dogIn) {
        return dogIn.isDoingFine() && dogIn.getTalent(DoggyTalents.PACK_PUPPY).isPresent();
    }

    public static @Nullable ItemStack findFood(Dog finder, Dog target) {
        var inventory = 
            target.getTalent(DoggyTalents.PACK_PUPPY)
            .map(
                (inst) -> inst.cast(PackPuppyTalent.class)
                .inventory()
            ).orElse(null);
        if (inventory == null)
            return null;

        for (int i = 0; i < inventory.getSlots(); i++) {
            var stack = inventory.getStackInSlot(i);
            if (FOOD_HANDLER.canConsume(finder, stack, target)) {
                return stack;
            }
        }
        return null;
    }

    public static class DogEatFromChestDogAction extends TriggerableAction {

        private Dog target;
        private int tickTillPathRecalc;
        private final int stopDist = 2;

        public DogEatFromChestDogAction(Dog dog, Dog target) {
            super(dog, false, false);
            this.target = target;
        }

        @Override
        public void onStart() {
            this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
        }

        @Override
        public void tick() {
            if (!this.stillValidTarget()) {
                setState(ActionState.FINISHED);
                return;
            }
            if (enoughEating()) {
                setState(ActionState.FINISHED);
                return;
            }
            
            if (this.dog.distanceToSqr(this.target) > stopDist*stopDist) {

                this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
                if (--this.tickTillPathRecalc <= 0) {
                    this.tickTillPathRecalc = 10;
                    if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                        //A Valid target is not that far away and is checked above.
                        //if (dog.distanceToSqr(target) > 400) return;
                        this.dog.getNavigation().moveTo(this.target, this.dog.getUrgentSpeedModifier());
                    }
                }
            } else {
                this.dog.getNavigation().stop();
                checkAndEat();
            }

        }

        @Override
        public void onStop() {
            
        }

        private boolean enoughEating() {
            var hunger = dog.getDogHunger();
            if (hunger < 80)
                return false;
            if (dog.isDogLowHealth() 
                && hunger < dog.getMaxHunger())
                return false;
            return true;
        }

        private void checkAndEat() {
            var food = findFood(dog, target);
            if (food != null) {
                FOOD_HANDLER.consume(dog, food, target);
            }
        }

        private boolean stillValidTarget() {  
            if (!this.target.isAlive())
                return false;
            if (dog.distanceToSqr(target) > 16*16) 
                return false;
            if (findFood(dog, target) == null)
                return false;
            return true;
        }

    }
    
    public static class ChestDogFoodHandler extends MeatFoodHandler {

        @Override
        public boolean canConsume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
            if (dog.isDogLowHealth()
                && stack.getItem() == DoggyItems.EGG_SANDWICH.get()
                && !dog.hasEffect(MobEffects.REGENERATION)) {
                return true;
            }

            if (super.canConsume(dog, stack, entityIn)) {
                var props = stack.getItem().getFoodProperties(stack, dog);
                return props != null && props.getNutrition() >= 5;
            }
                
            return false;
        }

        @Override
        public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
            if (dog.level().isClientSide) return InteractionResult.SUCCESS;
                
            var item = stack.getItem();
            var props = item.getFoodProperties();
            if (props == null) return InteractionResult.FAIL;
            int heal = props.getNutrition() * 5;

            dog.addHunger(heal);
            dog.consumeItemFromStack(entityIn, stack);

            if (item == DoggyItems.EGG_SANDWICH.get())
            for(var pair : props.getEffects()) {
                if (pair.getFirst() != null && dog.getRandom().nextFloat() < pair.getSecond()) {
                dog.addEffect(new MobEffectInstance(pair.getFirst()));
                }
            }

            if (dog.level() instanceof ServerLevel) {
                ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                    dog, new ItemStack(item));
            }
            dog.playSound(
                SoundEvents.GENERIC_EAT, 
                dog.getSoundVolume(), 
                (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
            );

            return InteractionResult.SUCCESS;
        }

    }

}
