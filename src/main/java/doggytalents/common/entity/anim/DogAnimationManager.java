package doggytalents.common.entity.anim;

import org.checkerframework.checker.units.qual.s;

import doggytalents.common.entity.Dog;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AnimationState;

public class DogAnimationManager {
    
    //Client
    public final AnimationState animationState
        = new AnimationState();
    public boolean needRefresh = false;

    //Server
    private boolean started = false;
    private int animationTime;
    private boolean isRunning = false;
    private final Dog dog;

    public DogAnimationManager(Dog dog) { this.dog = dog; }

    public void onAnimationChange(DogAnimation anim) {
        animationTime = 0;
        this.isRunning = false;
        if (anim == DogAnimation.NONE) {
            started = false;
            animationState.stop();
            if (dog.level.isClientSide)
                this.needRefresh = true;
            return;
        }
        if (anim == DogAnimation.RUNNING) {
            this.isRunning = true;
        }
        
        started = true;
        this.animationTime = anim.getLengthTicks();
        animationState.start(dog.tickCount);
    }

    public void tick() {
        if (!this.dog.level.isClientSide)
        if (this.isRunning) {
            if (this.dog.getNavigation().isDone() || !this.dog.isOnGround()) {
                if (this.dog.getAnim() == DogAnimation.RUNNING)
                    this.dog.setAnim(DogAnimation.BREAK_FROM_RUNNING);
            }
            return;
        } else {
            if (!this.dog.getNavigation().isDone() && this.dog.isOnGround()) {
                this.dog.setAnim(DogAnimation.RUNNING);
            }
        }

        if (started && !this.isRunning) {
            --this.animationTime;
            if (this.animationTime <= 0) {
                this.animationTime = 0;
                this.dog.setAnim(DogAnimation.NONE);
            }

            this.dog.setXRot(0);
            this.dog.yBodyRot = this.dog.yHeadRot;
            
        }
    }

    public boolean isRunning() { return this.isRunning; }

    public boolean started() {
        return this.started;
    }

}
