package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;

public abstract class TriggerableAction {
    
    protected Dog dog;
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
        started = true;
    }

    public final void stop() {
        onStop();
        started = false;
        this.dog.getNavigation().stop();
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

    public boolean shouldPersistAfterSit() {
        return false;
    }

    public final boolean canPause() {
        return this.canPause;
    }

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
