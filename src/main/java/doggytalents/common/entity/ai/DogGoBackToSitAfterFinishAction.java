package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.Optional;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class DogGoBackToSitAfterFinishAction extends Goal {
    
    private final Dog dog;
    private double oldRangeSense = 16;
    private static final double TEMP_HIGH_SENSE = 32;
    private Optional<Integer> lastPathCalcAt = Optional.empty();

    private int pathCalcDelay = 0;
    private boolean hasDelayCalc = false;
    private Optional<BlockPos> delayedCalcTo = Optional.empty();

    public DogGoBackToSitAfterFinishAction(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        var sit_pos_optional = checkAndInvalidateSitPos();
        if (!sit_pos_optional.isPresent())
            return false;
        
        return true;
    }

    private Optional<BlockPos> checkAndInvalidateSitPos() {
        var sit_pos_optional = this.dog.getDogSitOverridePos();
        if (!sit_pos_optional.isPresent())
            return Optional.empty();
        var sit_pos = sit_pos_optional.get();
        
        int sit_timestamp = dog.getDogSitOverridePosTimestamp();
        int time_since_save = dog.tickCount - sit_timestamp;
        if (time_since_save > 30 * 20) {
            this.dog.clearDogSitOverridePos();
            return Optional.empty();
        }
        
        final double max_validated_dist = 20;
        if (dog.distanceToSqr(Vec3.atBottomCenterOf(sit_pos)) 
            > max_validated_dist * max_validated_dist) {
            this.dog.clearDogSitOverridePos();
            return Optional.empty();
        }

        return Optional.of(sit_pos);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.hasDelayCalc && this.dog.getNavigation().isDone())
            return false;
        return true;
    }

    @Override
    public void start() {
        var attrib = this.dog.getAttribute(Attributes.FOLLOW_RANGE);
        if (attrib != null) {
            this.oldRangeSense = attrib.getValue();
            attrib.setBaseValue(TEMP_HIGH_SENSE);
        }
        
        var sit_pos_optional = dog.getDogSitOverridePos();
        if (!sit_pos_optional.isPresent())
            return;
        var sit_pos = sit_pos_optional.get();
        
        if (!maySetCalcPathDelayed(sit_pos)) {
            calcPath(sit_pos);
        }
    }

    private boolean maySetCalcPathDelayed(BlockPos pos) {
        if (!lastPathCalcAt.isPresent())
            return false;
        int time_since_last = dog.tickCount - lastPathCalcAt.get();
        if (time_since_last > 20)
            return false;

        this.hasDelayCalc = true;
        this.delayedCalcTo = Optional.of(pos);
        this.pathCalcDelay = Math.max(0, 20 - time_since_last);
        return true;
    }

    @Override
    public void tick() {
        if (!this.hasDelayCalc)
            return;
        if (this.pathCalcDelay > 0) {
            --this.pathCalcDelay;
        }
        if (this.pathCalcDelay <= 0) {
            this.hasDelayCalc = false;
            this.delayedCalcTo.ifPresent(x -> calcPath(x));
        }
    }

    public void calcPath(BlockPos pos) {
        final var nav = this.dog.getNavigation();
        nav.moveTo(
            pos.getX() + 0.5,
            pos.getY(),
            pos.getZ() + 0.5
        , 1);
        if (nav instanceof DogPathNavigation dog_nav) {
            dog_nav.setDogMoveInTargetNode();
        }
        this.lastPathCalcAt = Optional.of(dog.tickCount);
    }

    @Override
    public void stop() {
        var attrib = this.dog.getAttribute(Attributes.FOLLOW_RANGE);
        if (attrib != null) {
            attrib.setBaseValue(this.oldRangeSense);
        }
        this.dog.setOrderedToSit(true);
        dog.clearDogSitOverridePos();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

}
