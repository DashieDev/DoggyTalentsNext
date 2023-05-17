package doggytalents.common.entity.ai;


import doggytalents.ChopinLogger;
import doggytalents.DoggyItems;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.MeatFoodHandler;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

/**
 * @author DashieDev
 */
public class DogEatFromChestDogGoal extends Goal {

    private Dog dog;

    private int tickTillPathRecalc;
    private float oldWaterCost;

    private final double speedModifier;
    private final float stopDist;

    private List<Dog> chestDogs;
    //There is some caching going on here, 
    //Due to this::chestDog only set to null when the chestDog no longer valid.
    //dog will remember the dog that he ate from,
    //And the next time he is hungry, he will check that dog first.
    //Also the owner would probably have one dog has the food storage, so 
    //The chance of CACHE HIT is way greater than CACHE MISS.
    //Good good.
    private Dog chestDog = null;
    private int tickTillNextChestDogSearch = 0;
    private final int SEARCH_RADIUS = 12; //TODO

    private static final ChestDogFoodHandler FOOD_VALIDATOR = new ChestDogFoodHandler();

    public DogEatFromChestDogGoal(Dog dog, double speedModifier) {
        this.dog = dog;
        this.speedModifier = speedModifier;
        this.stopDist = 1.5f;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.dog.getDogHunger() >= 50) {
            return false;
        }
        this.invalidateChestDogCache();
        this.inspectNearbyChestDogsForFood();
        if (this.chestDog == null) return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.chestDog == null) return false;
        return this.dog.getDogHunger() <= 80 || (this.dog.isDogLowHealth() && this.dog.getDogHunger() < this.dog.getMaxHunger() );
    }

    @Override
    public void start() {
        this.tickTillPathRecalc = 0;
        this.oldWaterCost = this.dog.getPathfindingMalus(BlockPathTypes.WATER);
        this.dog.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.dog.getNavigation().stop();
        this.dog.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        this.dog.setBegging(false);
    }

    @Override
    public void tick() {
        this.invalidateChestDogCache();
        if (this.chestDog == null) return;
        if (!this.chestDog.isAlive() || this.chestDog.isDefeated() ) {
            this.chestDog = null; return;
        }
        if (this.dog.distanceToSqr(this.chestDog) > stopDist*stopDist) {
            this.moveToChestDog();
        } else {
            //TODO maintain some space ??
            //this.dog.getNavigation().stop();
            this.checkAndEatFromChestDog();
        }
    }

    private void moveToChestDog() {
        this.dog.getLookControl().setLookAt(this.chestDog, 10.0F, this.dog.getMaxHeadXRot());
        if (--this.tickTillPathRecalc <= 0) {
            this.tickTillPathRecalc = 10;
            if (!this.checkChestDogForFood(this.chestDog)) {
                this.chestDog = null; return;
            }
            if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                // if (this.dog.distanceToSqr(this.owner) >= 144.0D) {
                //     DogUtil.guessAndTryToTeleportToOwner(dog, 4);
                // } else {
                DogUtil.moveToIfReachOrElse(dog, 
                    this.chestDog.blockPosition(), speedModifier, 
                    1, 1, (d) -> {
                        this.chestDog = null;
                    });
                //}
            }
        }
    }

    private void inspectNearbyChestDogsForFood() {
        
        if (--this.tickTillNextChestDogSearch <= 0) {
            this.tickTillNextChestDogSearch = 10;
            
            if (this.chestDog == null) {
                this.fetchChestDogs();
                //this.chooseNearestChestDog();
                this.chooseRandomChestDog();
            }
            
            if (!this.checkChestDogForFood(this.chestDog)) {
                this.chestDog = null;
            }
        }
    }

    private void chooseNearestChestDog() {
        
        if (this.chestDogs == null) return;
        if (this.chestDogs.isEmpty()) return;

        var target = this.chestDogs.get(0); 
        double mindistanceSqr = target.distanceToSqr(dog);
        
        for (var i : this.chestDogs) {
            var d = i.distanceToSqr(dog);
            if (d < mindistanceSqr) {
                target = i;
                mindistanceSqr = d;
            }
        }
        
        this.chestDog = target;
    }

    private void invalidateChestDogCache() {
        if (chestDog != null && !chestDog.isAlive()) {
            this.chestDog = null;
        }
        if (chestDog != null && chestDog.isDefeated()) {
            this.chestDog = null;
        }
    }

    private void chooseRandomChestDog() {
        if (this.chestDogs == null) return;
        if (this.chestDogs.isEmpty()) return;

        int rIndx = this.dog.getRandom().nextInt(this.chestDogs.size());
        this.chestDog = this.chestDogs.get(rIndx);
    }

    private void fetchChestDogs() {
        this.chestDogs = 
        this.dog.level.getEntitiesOfClass(
            Dog.class, 
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS), 
            d -> isChestDog(d)
        );
    }

    private boolean isChestDog(Dog chestDog) {
        return chestDog.getDogLevel(DoggyTalents.PACK_PUPPY) > 0;
    }

    private boolean checkChestDogForFood(Dog chestDog) {
        if (chestDog == null) return false;
        if (!isChestDog(chestDog)) return false;
        var chestDogOwner = chestDog.getOwner();
        if (chestDogOwner == null) return false;
        if (chestDogOwner != this.dog.getOwner()) return false;
        if (chestDog.distanceToSqr(this.dog) > SEARCH_RADIUS*SEARCH_RADIUS) return false;
        
        PackPuppyItemHandler inventory = 
            chestDog.getTalent(DoggyTalents.PACK_PUPPY)
            .map(
                (inst) -> inst.cast(PackPuppyTalent.class)
                .inventory()
            ).orElse(null);
        
        if (inventory == null) return false;
        ChopinLogger.lwn(this.dog, "checking valid : " + this.chestDog.getName().getString());

        return
            InventoryUtil.findStack(
                inventory, 
                (stack) -> {
                    boolean willEat = 
                        FOOD_VALIDATOR.canConsume(chestDog, stack, null);

                    if (willEat) {
                        return true;
                    }
                    return false;
                }
            ) != null;
    }

    private void checkAndEatFromChestDog() {
        if (
            this.dog.distanceToSqr(this.chestDog) <= 5
            && this.dog.hasLineOfSight(this.chestDog)
        ) {
            PackPuppyItemHandler inventory =
                this.chestDog.getTalent(DoggyTalents.PACK_PUPPY)
                .map(
                    (inst) -> inst.cast(PackPuppyTalent.class)
                    .inventory()
                ).orElse(null);
            
            if (inventory == null) return;

            //Priotize Egg Sandwich when low health.
            if (
                this.dog.isDogLowHealth()
                && !dog.hasEffect(MobEffects.REGENERATION)
            ) 
            for (int i = 0; i < inventory.getSlots(); ++i) {
                var stack = inventory.getStackInSlot(i);
                if (stack.getItem() == DoggyItems.EGG_SANDWICH.get()) {
                    FOOD_VALIDATOR.consume(dog, stack, null);
                    return;
                }
            }

            for (int i = 0; i < inventory.getSlots(); i++) {

                ItemStack stack = inventory.getStackInSlot(i);
                boolean willEat = 
                    FOOD_VALIDATOR.canConsume(chestDog, stack, null);

                if (willEat) {
                    FOOD_VALIDATOR.consume(dog, stack, null);
                    return;
                }
            }
        }
    }



    public static class ChestDogFoodHandler extends MeatFoodHandler {

        @Override
        public boolean canConsume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
            return super.canConsume(dog, stack, entityIn);
        }

        @Override
        public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
            if (dog.level.isClientSide) return InteractionResult.SUCCESS;
                
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

            if (dog.level instanceof ServerLevel) {
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
