package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;

public abstract class TriggerableAction {
    
    protected Dog dog;
    private ActionState state = ActionState.PENDING;
    private final boolean isTrivial;
    private final boolean canPause;

    public TriggerableAction(Dog dog, boolean trivial, boolean canPause) {
        this.dog = dog;
        this.isTrivial = trivial;
        this.canPause = canPause;
    }
    
    public abstract void onStart();

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

    public boolean isIdleAction() {
        return false;
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
