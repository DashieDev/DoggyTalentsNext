package doggytalents.common.entity.ai;

import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;

import java.util.EnumSet;

public class DogFollowOwnerGoal extends Goal {

    private final Dog dog;
    private final Level world;
    private final double followSpeed;

    // If closer than stopDist stop moving towards owner
    private final float stopDist; 
     // If further than startDist moving towards owner
    private final float startDist;

    private LivingEntity owner;
    private int timeToRecalcPath;
    private int tickTillSearchForTp = 0;
    private float oldWaterCost;

    public DogFollowOwnerGoal(Dog dogIn, double speedIn, float minDistIn, float maxDistIn) {
        this.dog = dogIn;
        this.world = dogIn.level();
        this.followSpeed = speedIn;
        this.startDist = minDistIn;
        this.stopDist = maxDistIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.dog.getOwner();
        if (owner == null) {
            return false;
        } else if (!this.dog.getMode().shouldFollowOwner()) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else if (this.dog.isInSittingPose()) {
            return false;
        } else if (!this.dog.hasBone() && this.dog.distanceToSqr(owner) < this.getMinStartDistanceSq()) {
            return false;
        } else {
            this.owner = owner;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dog.getNavigation().isDone()) {
            return false;
        } else if (this.dog.isInSittingPose()) {
            return false;
        } else {
            return this.dog.distanceToSqr(this.owner) > this.stopDist * this.stopDist;
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.dog.getPathfindingMalus(PathType.WATER);
        this.dog.setDogFollowingSomeone(true);
    }

    @Override
    public void stop() {
        if (this.dog.hasBone()) {
            double distanceToOwner = this.owner.distanceToSqr(this.dog);
            if (distanceToOwner <= this.stopDist * this.stopDist) {
                IThrowableItem throwableItem = this.dog.getThrowableItem();
                ItemStack fetchItem = throwableItem != null ? throwableItem.getReturnStack(this.dog.getBoneVariant()) : this.dog.getBoneVariant();

                this.dog.spawnAtLocation(fetchItem, 0.0F);
                this.dog.setBoneVariant(ItemStack.EMPTY);
            }
        }

        this.owner = null;
        this.dog.getNavigation().stop();
        this.dog.setDogFollowingSomeone(false);
    }

    @Override
    public void tick() {
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.dog.isDogFlying() ? 5 : 10;
            DogUtil.moveToOwnerOrTeleportIfFarAway(
                dog, owner, this.followSpeed,
                144, 
                true, --this.tickTillSearchForTp <= 0, 
                400,
                dog.getMaxFallDistance());
            if (this.tickTillSearchForTp <= 0) this.tickTillSearchForTp = 10;
        }
    }

    public float getMinStartDistanceSq() {
        // Guard.Major Mode Goal now override this.
        // if (this.dog.isMode(EnumMode.GUARD)) {
        //     return 4F;
        // }

        return this.startDist * this.startDist;
    }
}
