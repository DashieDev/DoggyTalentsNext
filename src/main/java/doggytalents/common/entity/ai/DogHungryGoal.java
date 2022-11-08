package doggytalents.common.entity.ai;

import doggytalents.api.inferface.IThrowableItem;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.EnumSet;


//TODO When Hungry, Move to Owner, and then whine and tit head and look at owner for 3-5 sec
public class DogHungryGoal extends Goal {
    
    private final Dog dog;
    private final Level world;
    private final double followSpeed;
    private final float stopDist; // If closer than stopDist stop moving towards owner

    private LivingEntity owner;
    private int timeToRecalcPath;
    private float oldWaterCost;

    private int looktime;

    public DogHungryGoal(Dog dog, double speedIn, float minDistIn) {
        this.dog = dog;
        this.world = dog.level;
        this.followSpeed = speedIn;
        this.stopDist = 3;
        this.looktime = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.dog.getOwner();
        if (owner == null || this.looktime >= 60) {
            return false;
        } else if (!this.dog.isLowHunger()) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else if (this.dog.isInSittingPose()) {
            return false;
        } else {
            this.owner = owner;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.looktime <= 60; // Look time : 3s
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.dog.getPathfindingMalus(BlockPathTypes.WATER);
        this.dog.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.looktime = 0;
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
        this.dog.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        this.dog.setBegging(false);
    }

    @Override
    public void tick() {
        if (this.dog.distanceToSqr(this.owner) > stopDist) {
            this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) { // Is not leashed and is not a passenger
                    if (this.dog.distanceToSqr(this.owner) >= 144.0D) { // Further than 12 blocks away teleport
                        DogUtil.guessAndTryToTeleportToOwner(dog, 4);
                    } else {
                        this.dog.getNavigation().moveTo(this.owner, this.followSpeed);
                    }
                }
            }
        }  else {
            this.dog.setBegging(true);
            if (this.looktime == 0) {
                this.dog.getOwner().sendSystemMessage(Component.translatable("dog.msg.low_hunger." + this.dog.getRandom().nextInt(3), this.dog.getName()));
                this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
            }
            this.dog.getLookControl().setLookAt(this.owner);
            ++this.looktime;
            
        }
    }
}
