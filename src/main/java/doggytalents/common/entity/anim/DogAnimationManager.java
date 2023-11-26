package doggytalents.common.entity.anim;


import doggytalents.api.enu.forward_imitate.anim.AnimationState;
import doggytalents.ChopinLogger;
import doggytalents.common.entity.Dog;
import net.minecraft.util.Mth;

public class DogAnimationManager {

    private static final int SYNC_INTERVAL_TICK = 7;
    public static final int MAX_LATENCY_ALLOWED = 10;

    //Client
    public final DogAnimationState animationState
        = new DogAnimationState();
    public boolean needRefresh = false;

    //Common
    private boolean started = false;
    private int animationTime;
    private final Dog dog;
    private boolean looping = false;
    private int tickTillSync = 0;

    public DogAnimationManager(Dog dog) { this.dog = dog; }

    public void onAnimationChange(DogAnimation anim) {
        animationTime = 0;
        if (anim != DogAnimation.NONE) {
            started = true;
            looping = anim.looping();
            this.animationTime = anim.getLengthTicks();
            animationState.start(dog.tickCount);
            tickTillSync = SYNC_INTERVAL_TICK;
        } else {
            started = false;
            looping = false;
            animationState.stop();
            if (dog.level.isClientSide)
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
                if (--tickTillSync <= 0) {
                    tickTillSync = SYNC_INTERVAL_TICK;
                    this.dog.setAnimSyncTime(this.animationTime);
                }
                --this.animationTime;
            }
        }
        if (started && (this.dog.level().isClientSide) && !looping) {
            if (this.animationTime <= 0) {
                this.animationTime = 0;
            } else {
                --this.animationTime;
            }
        }
    }

    public void onSyncTimeUpdated() {
        if (this.dog.level().isClientSide)
            resolveLatencyIfNeeded();
    }

    private void resolveLatencyIfNeeded() {
        if (!started || looping)
            return;
        
        int correctTime = dog.getAnimSyncTime();
        int currentTime = this.animationTime;
        int latencyAbs = Mth.abs(correctTime - currentTime);
        if (latencyAbs <= MAX_LATENCY_ALLOWED)
            return;

        ChopinLogger.lwn(dog, "Resolving a latency of " + latencyAbs + " ticks");
        this.animationTime = correctTime;
        var anim = dog.getAnim();
        int correctPassedTime = 
            anim.getLengthTicks() - correctTime;
        
        animationState.resolveLatency(dog.tickCount, 
            correctPassedTime, 
            anim.getSpeedModifier());
    }

    public boolean started() {
        return this.started;
    }

}
