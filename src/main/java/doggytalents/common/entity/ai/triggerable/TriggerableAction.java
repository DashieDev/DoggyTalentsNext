package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;

public abstract class TriggerableAction {
    
    private Dog dog;
    private boolean running;
    private final boolean isTrivial;

    public TriggerableAction(Dog dog, boolean trivial) {
        this.dog = dog;
        this.isTrivial = trivial;
    }
    
    public abstract void onStart();

    public abstract void tick();

    public abstract void onStop();

    public boolean isRunning() {
        return this.running;
    } 

    //TODO Use this on the Promise too
    public final void start() {
        this.running = true;
        this.onStart();
    }

    public final void stop() {
        this.running = false;
        this.onStop();
    }

    public boolean isTrivial() {
        return this.isTrivial;
    }

}
