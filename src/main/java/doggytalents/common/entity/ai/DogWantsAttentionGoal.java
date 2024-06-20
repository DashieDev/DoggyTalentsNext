package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.DogVariants;
import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import doggytalents.common.variant.DogVariant;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogWantsAttentionGoal extends Goal {
    
    private final Dog dog;
    private LivingEntity owner;
    private int lastStopTick;
    private int cooldownTime = 10 * 20;

    private static enum Phase { GO_TO_OWNER, BEG_FOR_ATTENTION }
    private Phase phase = Phase.GO_TO_OWNER;

    private int goToOwnerTimeout;
    private int tickTillPathRecalc;
    private boolean whinedToAttention;

    private int tickAnim;
    private int stopTick;

    public DogWantsAttentionGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!dog.canDoIdileAnim()) return false;
        if (dog.isOnFire()) return false;
        if (dog.isLowHunger()) return false;
        if (!this.dog.onGround()) return false;

        if (this.dog.tickCount - this.lastStopTick < this.cooldownTime)
            return false;

        if (this.dog.dogVariant() != DogVariants.CHESTNUT.get())
            return false;
        if (this.dog.getRandom().nextFloat() >= 0.01f)
            return false;

        var owner = this.dog.getOwner();
        if (owner == null)
            return false;

        this.owner = owner;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dog.isLowHunger())
            return false;
        if (this.phase == Phase.GO_TO_OWNER) {
            if (this.goToOwnerTimeout <= 0)
                return false;
        } else {
            if (!dog.canContinueDoIdileAnim()) return false;
            if (dog.getAnim() != DogAnimation.PLAY_WITH_MEH) return false;
            if (this.dog.distanceToSqr(owner) > 16) return false;
            return this.dog.tickCount < this.stopTick;
        }
        return true;
    }

    @Override
    public void start() {
        this.phase = Phase.GO_TO_OWNER;
        this.goToOwnerTimeout = 200;
        this.whinedToAttention = false;
    }

    @Override
    public void tick() {
        if (this.phase == Phase.GO_TO_OWNER) {
            tickGoToOwner();
        } else {
            tickBegForAttention();
        }
    }

    private void tickGoToOwner() {
        this.dog.getLookControl().setLookAt(owner);
        if (--this.goToOwnerTimeout <= 0) {
            this.goToOwnerTimeout = 0;
            return;
        }

        var d0 = dog.distanceToSqr(owner);
        boolean closeEnough = d0 < 6;
            
        if (--this.tickTillPathRecalc <= 0) {
            if (!closeEnough)
                dog.getNavigation().moveTo(owner, 1);
            this.tickTillPathRecalc = 20;
        }
        
        if (closeEnough) {
            if (!dog.getNavigation().isDone()) {
                dog.getNavigation().stop();
            }
            if (!whinedToAttention) {
                whinedToAttention = true;
                this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
            }
            checkAndSwitchToAttention();
        }
    }

    private void checkAndSwitchToAttention() {
        if (!DogUtil.checkIfOwnerIsLooking(dog, owner))
            return;
        
        this.phase = Phase.BEG_FOR_ATTENTION;
        this.tickAnim = 0;
        this.stopTick = dog.tickCount + DogAnimation.PLAY_WITH_MEH.getLengthTicks();
        this.dog.setAnim(DogAnimation.PLAY_WITH_MEH);
    }

    private void tickBegForAttention() {
        this.dog.getLookControl().setLookAt(owner);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.lastStopTick = dog.tickCount;
        this.cooldownTime = 20 * (1 + dog.getRandom().nextInt(3));
        if (this.dog.getAnim() == DogAnimation.PLAY_WITH_MEH) {
            this.dog.setAnim(DogAnimation.NONE);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

}
