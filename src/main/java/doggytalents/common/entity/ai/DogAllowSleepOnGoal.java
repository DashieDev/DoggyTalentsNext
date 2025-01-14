package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogSleepOnManager;
import doggytalents.common.entity.anim.DogPose;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogAllowSleepOnGoal extends Goal {
    
    private final Dog dog;
    private int timeout = 60;
    private int sitUpTime = 0;
    private boolean isRestingPeriod = false;

    public DogAllowSleepOnGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.dog.isDefeated())
            return false;
        if (!this.dog.isOrderedToSit())
            return false;
        if (!this.dog.canDoIdileAnim())
            return false;
        if (dog.isOnFire())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (!this.dog.onGround())
            return false;
        var sleep_on_manger = dog.sleepOnManager;
        if (!sleep_on_manger.isSleepOnRequested())
            return false;
        if (!DogSleepOnManager.getServer(dog.level()).isSleepCondition(dog).ok())
            return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dog.isDefeated())
            return false;
        if (!this.dog.isOrderedToSit())
            return false;
        if (!this.dog.canContinueDoIdileAnim())
            return false;
        if (!this.dog.isInSittingPose())
            return false;
        if (!this.dog.onGround())
            return false;
        if (!DogSleepOnManager.getServer(dog.level()).isSleepCondition(dog).ok())
            return false;
        return sitUpTime >= 0;
    }

    @Override
    public void start() {
        this.timeout = DogAnimation.LIE_SIDEWAY_LOOP.getLengthTicks() * 3;
        this.isRestingPeriod = true;
        this.sitUpTime = DogAnimation.LIE_SIDEWAY_END.getLengthTicks();
        this.dog.setAnimForIdle(DogAnimation.LIE_SIDEWAY_START);
        this.dog.sleepOnManager.setSleepOnReady(true);
    }

    @Override
    public void stop() {
        var anim = dog.getAnim();
        if (!anim.interupting()) {
            this.dog.setAnim(DogAnimation.NONE);
        }
        DogSleepOnManager.onSleepGoalStop(dog);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.isRestingPeriod) {
            if ((dog.getAnim() == DogAnimation.LIE_SIDEWAY_START && dog.animationManager.isHolding())
                || dog.getAnim() == DogAnimation.NONE) {
                this.dog.setAnim(DogAnimation.LIE_SIDEWAY_LOOP);
            }
        } else {
            --this.sitUpTime;
        }
        if (this.isRestingPeriod) {
            updateRestingPeriod();
        }
    }

    private void updateRestingPeriod() {
        boolean is_sleeping_on = 
            this.dog.getSleepOnState().is_sleeping();
        if (!is_sleeping_on) {
            timeout = Math.max(0, timeout - 1);
        } else {
            timeout = 60;
        }
        boolean finished = !is_sleeping_on && this.timeout <= 0;  
        if (finished) {
            this.isRestingPeriod = false;
            this.dog.setAnim(DogAnimation.LIE_SIDEWAY_END);
            this.dog.sleepOnManager.onSleepOnGoalStop();
            return;
        }
    }

}
