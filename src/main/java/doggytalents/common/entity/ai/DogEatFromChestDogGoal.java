package doggytalents.common.entity.ai;


import doggytalents.ChopinLogger;
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

public class DogEatFromChestDogGoal extends Goal {

    private Dog dog;

    private int timeToRecalcPath;
    private float oldWaterCost;

    private final double speedModifier;
    private final float stopDist;

    private List<Dog> chestDogs;
    private Dog chestDog = null;
    private int tickTillNextChestDogSearch = 0;
    private final int SEARCH_RADIUS = 12; //TODO

    public DogEatFromChestDogGoal(Dog dog, double speedModifier) {
        this.dog = dog;
        this.speedModifier = speedModifier;
        this.stopDist = 3;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.dog.getDogHunger() >= 50) {
            return false;
        }
        this.findChestDog();
        if (this.chestDog == null) return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.chestDog == null) return false;
        return this.dog.getDogHunger() <= 80 || (this.dog.getHealth() <= 6 && this.dog.getDogHunger() < 120 );
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
        if (this.dog.distanceToSqr(this.chestDog) > stopDist) {
            this.dog.getLookControl().setLookAt(this.chestDog, 10.0F, this.dog.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                if (!this.validChestDog(this.chestDog)) {
                    this.chestDog = null; return;
                }
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                    // if (this.dog.distanceToSqr(this.owner) >= 144.0D) {
                    //     DogUtil.guessAndTryToTeleportToOwner(dog, 4);
                    // } else {
                    this.dog.getNavigation().moveTo(this.chestDog, this.speedModifier);
                    //}
                }
            }
        } else {
            this.checkAndEatFromChestDog();
        }
    }

    private void findChestDog() {
        if (--this.tickTillNextChestDogSearch <= 0) {
            this.tickTillNextChestDogSearch = 10;
            this.chestDogs = 
                this.dog.level.getEntitiesOfClass(
                    Dog.class, 
                    dog.getBoundingBox().inflate(SEARCH_RADIUS), 
                    d -> validChestDog(d)
                );
            this.chooseChestDog();
        }
    }

    private void chooseChestDog() {
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

    private boolean validChestDog(Dog dog) {
        if (dog == null) return false;
        if (dog.getDogLevel(DoggyTalents.PACK_PUPPY) <= 0) return false;
        if (dog.getOwner() == null) return false;
        
        PackPuppyItemHandler inventory = 
            dog.getTalent(DoggyTalents.PACK_PUPPY)
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
                        FoodHandler.getMatch(dog, stack, dog.getOwner());

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
