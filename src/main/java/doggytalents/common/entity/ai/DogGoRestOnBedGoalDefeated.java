package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.List;

import doggytalents.DoggyBlocks;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DogGoRestOnBedGoalDefeated extends Goal {

    private final int SEARCH_RADIUS = 5;

    private Dog dog;
    private int tickTillSearch = 10;
    private int tickTillPathRecalc = 0;
    private BlockPos targetBed;

    public DogGoRestOnBedGoalDefeated(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.dog.isDefeated())
            return false;
        if (this.dog.isOrderedToSit()) 
            return false;
        targetBed = null;
        if (--tickTillSearch <= 0) {
            tickTillSearch = 10;
            targetBed = this.searchForBed();
        }
        if (targetBed == null)
            return false;
    
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.dog.isDefeated())
            return false;
        if (this.dog.isOrderedToSit()) 
            return false;
        if (this.targetBed == null)
            return false;
        return true;
    }

    @Override
    public void start() {
        this.tickTillPathRecalc = 0;
        this.dog.setInSittingPose(false);
    }

    @Override
    public void stop() {
        this.dog.getNavigation().stop();
        this.dog.setInSittingPose(false);
        if (dog.getAnim() == DogAnimation.LIE_DOWN_IDLE) {
            dog.setAnim(DogAnimation.NONE);
        }
    }

    @Override
    public void tick() {
        idleIfLyingElseInvalidate();
        validateTarget();
        if (this.targetBed == null)
            return;
        var nav = dog.getNavigation();
        var d_targetBed = dog.distanceToSqr(Vec3.atBottomCenterOf(targetBed));
        var onBed = d_targetBed < 0.35;
        if (!onBed && --this.tickTillPathRecalc <= 0) {
            this.tickTillPathRecalc = 10;
            nav.moveTo(targetBed.getX() + 0.5, 
                targetBed.getY(), targetBed.getZ() + 0.5, 1);
        }   
        if (  
            nav.isDone() && !onBed
        ) {
            dog.getMoveControl().setWantedPosition(targetBed.getX() + 0.5, 
            targetBed.getY(), targetBed.getZ() + 0.5, 1);
        }
        if (d_targetBed < 1) {
            if (!this.dog.isInSittingPose()) {
                this.dog.setSitAnim(DogAnimation.LYING_DOWN);
                this.dog.setInSittingPose(true);
            }
        } else {
            this.dog.setInSittingPose(false);
        }
    }

    private void idleIfLyingElseInvalidate() {
        if (dog.getDogPose() == DogPose.LYING_2 && dog.getAnim() == DogAnimation.NONE) {
            this.dog.setAnim(DogAnimation.LIE_DOWN_IDLE);
            return;
        }
        if (dog.getDogPose() != DogPose.LYING_2 && dog.getAnim() == DogAnimation.LIE_DOWN_IDLE) {
            this.dog.setAnim(DogAnimation.NONE);
            return;
        }
    }

    private int tickTillValidateNeabyDogs = 5;
    private void validateTarget() {
        if (targetBed == null) return;
        int invalidate_dist = (SEARCH_RADIUS + 1) * (SEARCH_RADIUS + 1);
        if (this.dog.distanceToSqr(Vec3.atBottomCenterOf(targetBed)) > invalidate_dist) {
            targetBed = null;
            return;
        }
        var state = this.dog.level().getBlockState(targetBed);
        if (!isBed(state)) {
            this.targetBed = null;
            return;
        }
        if (--this.tickTillValidateNeabyDogs <= 0) {
            this.tickTillValidateNeabyDogs = 5;
            var bed_center = Vec3.atBottomCenterOf(targetBed);
            var nearby_defeated_dogs = dog.level()
                .getEntitiesOfClass(Dog.class, 
                new AABB(bed_center.add(-2, -2, -2), bed_center.add(2, 2, 2)),
                    filter_dog -> filter_dog.isDefeated());
            if (!this.noDogsIsNearby(bed_center, nearby_defeated_dogs)) {
                targetBed = null;
                return;
            }
        }
    }

    private BlockPos searchForBed() {        
        var nearby_defeated_dogs = dog.level()
            .getEntitiesOfClass(Dog.class, 
            dog.getBoundingBox()
                .inflate(SEARCH_RADIUS + 1, 2, SEARCH_RADIUS + 1), 
                filter_dog -> filter_dog.isDefeated());
        
        var dog_b0 = dog.blockPosition();
        for (var bpos : BlockPos.betweenClosed(
            dog_b0.offset(-SEARCH_RADIUS, -1, -SEARCH_RADIUS), 
            dog_b0.offset(SEARCH_RADIUS, 1, SEARCH_RADIUS))
        ) {
            var state = dog.level().getBlockState(bpos);
            if (isBed(state) && noDogsIsNearby(Vec3.atBottomCenterOf(bpos), nearby_defeated_dogs))
                return bpos;
        }
        return null;
    }

    private boolean isBed(BlockState state) {
        var block = state.getBlock();
        if (block == DoggyBlocks.DOG_BED.get())
            return true;
        if (state.is(BlockTags.BEDS))
            return true;
        return false;
    }

    private boolean noDogsIsNearby(Vec3 pos, List<Dog> nearbys) {
        for (var dog : nearbys) {
            if (dog == this.dog) continue;
            if (dog.distanceToSqr(pos) < 0.35)
                return false;
        }
        return true;
    }
    
}
