package doggytalents.common.entity.anim;


import doggytalents.common.entity.Dog;

public class DogAnimationManager {
    
    //Client
    public final DogAnimationState animationState
        = new DogAnimationState();
    public boolean needRefresh = false;

    //Common
    private boolean started = false;
    private int animationTime;
    private final Dog dog;
    private boolean looping = false;

    public DogAnimationManager(Dog dog) { this.dog = dog; }

    public void onAnimationChange(DogAnimation anim) {
        animationTime = 0;
        if (anim != DogAnimation.NONE) {
            started = true;
            looping = anim.looping();
            this.animationTime = anim.getLengthTicks();
            animationState.start(dog.tickCount);
        } else {
            started = false;
            looping = false;
            animationState.stop();
            if (dog.level().isClientSide)
                this.needRefresh = true;
        }
    }

    public void tick() {
        if (started && !this.dog.getAnim().freeHead()) {
            this.dog.xRotO = 0;
            this.dog.yBodyRot = this.dog.yHeadRot;
            this.dog.setXRot(0);
            this.dog.resetBeggingRotation();
        }
        if (started && (!this.dog.level().isClientSide) && !looping) {
            if (this.animationTime <= 0) {
                this.animationTime = 0;
                this.dog.setAnim(DogAnimation.NONE);
            } else {
                --this.animationTime;
            }
        }
    }

    public boolean started() {
        return this.started;
    }

}
