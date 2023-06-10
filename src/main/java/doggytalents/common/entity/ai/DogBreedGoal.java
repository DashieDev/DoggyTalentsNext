package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.EntityUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class DogBreedGoal extends Goal {

    private static final TargetingConditions breedPredicate = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight(); // TODO check this works
    private final Dog dog;
    private final Level world;
    private final double moveSpeed;

    protected Dog targetMate;
    private int spawnBabyDelay;

    public DogBreedGoal(Dog dog, double moveSpeed) {
        this.dog = dog;
        this.world = dog.level;
        this.moveSpeed = moveSpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!dog.isDoingFine()) return false;
        if (!this.dog.isInLove()) {
            return false;
        } else {
            this.targetMate = this.getNearbyMate();
            return this.targetMate != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!dog.isDoingFine()) return false;
        if (!this.targetMate.isDoingFine()) return false;
        return this.targetMate.isInLove() && this.spawnBabyDelay < 60;
    }

    @Override
    public void stop() {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    @Override
    public void tick() {
        this.dog.getLookControl().setLookAt(this.targetMate, 10.0F, this.dog.getMaxHeadXRot());
        this.dog.getNavigation().moveTo(this.targetMate, this.moveSpeed);
        ++this.spawnBabyDelay;
        if (this.spawnBabyDelay >= 60 && this.dog.distanceToSqr(this.targetMate) < 9.0D) {
            this.dog.spawnChildFromBreeding((ServerLevel) this.world, this.targetMate);
        }
    }

    @Nullable
    private Dog getNearbyMate() {
        var entities = this.world.getEntitiesOfClass(
            Dog.class, this.dog.getBoundingBox().inflate(8.0D), this::filterEntities
        );
        return EntityUtil.getClosestTo(this.dog, entities);
    }

    private boolean filterEntities(Dog dog) {
        return breedPredicate.test(this.dog, dog) && this.dog.canMate(dog);
    }
}
