package doggytalents.common.entity.ai;


import doggytalents.DoggyTalents;
import doggytalents.api.feature.FoodHandler;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.InventoryUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;
import java.util.List;

/**
 * @author DashieDev
 */
public class DogEatFromChestDogGoal extends Goal {

    private Dog dog;

    private int timeToRecalcPath;
    private float oldWaterCost;

    private final double speedModifier;
    private final float stopDist;

    private List<Dog> chestDogs;
    //There is some caching going on here, 
    //Due to this::chestDog only set to null when the chestDog no longer valid.
    //dog will remember the dog that he ate from,
    //And the next time he is hungry, he will check that dog first. Good good.
    private Dog chestDog = null;
    private int tickTillNextChestDogSearch = 0;
    private final int SEARCH_RADIUS = 12; //TODO

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
        this.inspectNearbyChestDogsForFood();
        if (this.chestDog == null) return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.chestDog == null) return false;
        return this.dog.getDogHunger() <= 80 || (this.dog.getHealth() <= 6 && this.dog.getDogHunger() < this.dog.getMaxHunger() );
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
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
        if (this.dog.distanceToSqr(this.chestDog) > stopDist*stopDist) {
            this.dog.getLookControl().setLookAt(this.chestDog, 10.0F, this.dog.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                if (this.chestDog == null) return;
                if (!this.validChestDog(this.chestDog)) {
                    this.chestDog = null; return;
                }
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                    // if (this.dog.distanceToSqr(this.owner) >= 144.0D) {
                    //     DogUtil.guessAndTryToTeleportToOwner(dog, 4);
                    // } else {
                    DogUtil.moveToIfReachOrElse(dog, 
                        this.chestDog.blockPosition(), speedModifier, 
                        1, (d) -> {
                            this.chestDog = null;
                        });
                    //}
                }
            }
        } else {
            this.checkAndEatFromChestDog();
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
            
            if (!this.validChestDog(this.chestDog)) {
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

    private boolean validChestDog(Dog chestDog) {
        if (chestDog == null) return false;
        if (!isChestDog(chestDog)) return false;
        if (chestDog.getOwner() == null) return false;
        if (chestDog.getOwner() != this.dog.getOwner()) return false;
        if (chestDog.distanceToSqr(this.dog) > SEARCH_RADIUS*SEARCH_RADIUS) return false;
        
        PackPuppyItemHandler inventory = 
            chestDog.getTalent(DoggyTalents.PACK_PUPPY)
            .map(
                (inst) -> inst.cast(PackPuppyTalent.class)
                .inventory()
            ).orElse(null);
        
        if (inventory == null) return false;

        return
            InventoryUtil.findStack(
                inventory, 
                (stack) -> {
                    var foodHandler = 
                        FoodHandler.getMatch(chestDog, stack, chestDog.getOwner());

                    if (foodHandler.isPresent()) {
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

            for (int i = 0; i < inventory.getSlots(); i++) {

                ItemStack stack = inventory.getStackInSlot(i);
                var foodHandler = 
                    FoodHandler.getMatch(dog, stack, dog.getOwner());

                if (foodHandler.isPresent()) {
                    foodHandler.get().consume(dog, stack, dog.getOwner());
                    return;
                }
            }
        }
    }






}
