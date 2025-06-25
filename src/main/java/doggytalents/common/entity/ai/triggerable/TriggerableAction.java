package doggytalents.common.entity.ai.triggerable;

import java.util.Optional;

import doggytalents.common.entity.Dog;

public abstract class TriggerableAction {
    
    protected final Dog dog;
    private ActionState state = ActionState.PENDING;
    private final boolean isTrivial;
    private final boolean canPause;
    private boolean started;

    public TriggerableAction(Dog dog, boolean trivial, boolean canPause) {
        this.dog = dog;
        this.isTrivial = trivial;
        this.canPause = canPause;
    }

    public final void start() {
        onStart();
        started = this.getState() == ActionState.RUNNING;
    }

    public final void stop() {
        onStop();
        started = false;
        this.dog.getNavigation().stop();
    }

    public final void doTick() {
        if (checkOwnerDistanceStop()) {
            this.setState(ActionState.FINISHED);
            return;
        }
        tick();
    }

    private boolean checkOwnerDistanceStop() {
        if (!this.dog.getMode().shouldFollowOwner())
            return false;
        if (!this.shouldStopAndFollowOwner())
            return false;
        float max_owner_dist = this.getDistanceForFollowOwner();
        var owner = this.dog.getOwner();
        if (owner == null)
            return false;
        return owner.distanceToSqr(this.dog) > max_owner_dist * max_owner_dist;
    }

    public boolean isStarted() {
        return started;
    }

    /**
     * Called when this Action is started by DogTriggerableGoal when it
     * updates.
     */
    public abstract void onStart();

    /**
     * Called to Update This Action when this 
     * is already at RUNNING state by DogTriggerableGoal when
     * it updates. Notice: this won't get called if this Action is not
     * already at RUNNING state when being updated by DogTriggerableGoal
     * so this won't be called during the tick when DogTriggerableGoal
     * set this Action state from PENDING to RUNNING and onStart() get called.
     */
    public abstract void tick();

    public abstract void onStop();

    //Only a non trivial action can override a trivial action.
    public final boolean isTrivial() {
        return this.isTrivial;
    }

    public boolean canPreventSit() {
        return false;
    }

    public boolean canOverrideSit() {
        return false;
    }

    public boolean goBackToSitPosWhenFinished() {
        return true;
    }

    public final boolean canPause() {
        return this.canPause;
    }

    public boolean shouldStopAndFollowOwner() {
        return false;
    }

    public float getDistanceForFollowOwner() {
        return 16;
    }

    public void onDogGoesOfflineWhileActive() {}

    public ActionState getState() {
        return this.state;
    }

    public void setState(ActionState state) {
        this.state = state;
    }

    public static enum ActionState {
        PENDING,
        RUNNING,
        FINISHED,
        PAUSED
    }

}
