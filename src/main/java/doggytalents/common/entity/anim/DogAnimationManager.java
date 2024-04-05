package doggytalents.common.entity.anim;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.util.Mth;

public class DogAnimationManager {

    private static final int SYNC_INTERVAL_TICK = 7;
    private static final int MIN_VAL_MAX_LATENCY = 7;

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
            if (dog.level().isClientSide)
                this.needRefresh = true;
        }
    }

    public void tick() {
        if (started && !this.dog.getAnim().freeHead()) {
            this.dog.yBodyRot = this.dog.yHeadRot;
            this.dog.resetBeggingRotation();
            if (!this.dog.getAnim().freeHeadXRotOnly()) {
                this.dog.setXRot(0);
                this.dog.xRotO = 0;
            }
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
        
        int configMaxLatency = ConfigHandler.CLIENT.MAX_ANIMATION_LATENCY_ALLOWED.get();
        if (configMaxLatency < 0)
            return;
        int maxLatencyAllowed = MIN_VAL_MAX_LATENCY;
        if (configMaxLatency > MIN_VAL_MAX_LATENCY)
            maxLatencyAllowed = configMaxLatency;

        int correctTime = dog.getAnimSyncTime();
        int currentTime = this.animationTime;
        int latencyAbs = Mth.abs(correctTime - currentTime);
        if (latencyAbs <= maxLatencyAllowed)
            return;
            
        var anim = dog.getAnim();
        this.animationTime = Mth.clamp(correctTime, 0, anim.getLengthTicks());
        int correctPassedTime = 
            anim.getLengthTicks() - this.animationTime;
        
        animationState.resolveLatency(dog.tickCount, 
            correctPassedTime, 
            anim.getSpeedModifier());
    }

    public boolean started() {
        return this.started;
    }

}
//emgniypocpots_redrehtac