package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.ChopinLogger;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

/**
 * @author DashieDev
 */
public class DogLowHealthGoal extends Goal {

    private static final float LOW_HEALTH = 6;

    private final Dog dog;
    private final Level world;
    private final double followSpeed;
    private final float stopDist;

    private LivingEntity owner;
    private int timeToRecalcPath;
    private int tickTillSearchForTp = 0;
    private float oldWaterCost;

    private boolean whine = false;

    public DogLowHealthGoal(Dog dogIn, double speedIn, float minDistIn) {
        this.dog = dogIn;
        this.world = dogIn.level;
        this.followSpeed = speedIn;
        this.stopDist = 1.5f;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.dog.getOwner();
        if (owner == null) {
            return false;
        } else if (!this.isDogLowHealth()) {
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
        return this.isDogLowHealth();
    }

    
    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.dog.getPathfindingMalus(BlockPathTypes.WATER);
        this.dog.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.whine = true;
        DogUtil.searchAndTeleportToOwner(dog, 4);
        ChopinLogger.l("Low Health started!");
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

    //TODO : Group the msg when dog msg owner about him being hurt based on how the dog was previously hurt, 
    //and make the dog choose accordingly
    @Override
    public void tick() {
        if (this.dog.distanceToSqr(this.owner) > stopDist*stopDist) {
            this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                //The dog always stays close to the owner, and tp when a little bit further
                //So the path is not that long, so interval = 3 is ok
                this.timeToRecalcPath = 3;
                DogUtil.moveToOrTeleportIfFarAwayIfReachOrElse(
                    dog, owner, this.followSpeed,
                    25, false, 
                    --this.tickTillSearchForTp <= 0,
                     400, 
                    dog.getMaxFallDistance());
                if (this.tickTillSearchForTp <= 0) this.tickTillSearchForTp = 10;
            }
        }  else {
            if (this.whine) {
                this.whine = false;
                this.dog.getOwner().sendSystemMessage(Component.translatable("dog.msg.low_health." + this.dog.getRandom().nextInt(3), this.dog.getName()));
                this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
            }
            this.dog.getLookControl().setLookAt(this.owner);
            
        }
    }

    private boolean isDogLowHealth() {
        return this.dog.getHealth() < LOW_HEALTH;
    }
}
