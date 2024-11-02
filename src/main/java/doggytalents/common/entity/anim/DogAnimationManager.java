package doggytalents.common.entity.anim;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
    private boolean holdOnLastTick = false;
    private boolean isHolding = false;
    private int tickTillSync = 0;

    //Common - Debug
    private boolean isDebug = false;

    public DogAnimationManager(Dog dog) { this.dog = dog; }

    public void onAnimationChange(DogAnimation anim) {
        animationTime = 0;
        this.isHolding = false;
        if (anim != DogAnimation.NONE) {
            started = true;
            looping = anim.looping();
            holdOnLastTick = anim.holdOnLastTick();
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
                if (this.holdOnLastTick)
                    this.isHolding = true;
                else
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
                if (this.holdOnLastTick)
                    this.isHolding = true;
            } else {
                --this.animationTime;
            }
        }

        if (isDebug)
            tickDebug();
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

    public boolean isHolding() {
        return this.isHolding;
    }

    public void save(CompoundTag tag) {
        var debug_state = dog.getDogAnimDebugState();
        if (debug_state.isNone())
            return;
        var debug_tag = new CompoundTag();
        debug_tag.putInt("anim_id", debug_state.anim_id());
        debug_tag.putInt("timestamp", debug_state.timestamp());
        debug_tag.putFloat("yrot", debug_state.yrot());
        tag.put("dtnDogAnimDebug", debug_tag);
    }

    public void load(CompoundTag tag) {
        if (!tag.contains("dtnDogAnimDebug", Tag.TAG_COMPOUND))
            return;
        var debug_tag = tag.getCompound("dtnDogAnimDebug");
        int anim_id = debug_tag.getInt("anim_id");
        int timestamp = debug_tag.getInt("timestamp");
        float yrot = debug_tag.getFloat("yrot");
        var debug_state = new DogAnimDebugState(anim_id, timestamp, yrot);
        setDogAnimDebugState(debug_state);
    }

    private void tickDebug() {
        dog.yBodyRot = dog.getDogAnimDebugState().yrot();
        dog.yHeadRot = dog.yBodyRot;
        dog.setYRot(dog.yBodyRot);
        dog.yBodyRotO = dog.yBodyRot;
        dog.yHeadRotO = dog.yHeadRot;
        dog.yRotO = dog.getYRot();
    }

    public void onDebugUpdate(DogAnimDebugState state) {
        this.isDebug = !state.isNone();
    }

    public void setDogAnimDebugState(DogAnimDebugState state) {
        dog.setDogAnimDebugState(state);
        if (state.isNone())
            dog.setAnim(DogAnimation.NONE);
    }

    public DogAnimDebugState getFreezeDebugState(DogAnimation anim) {
        int timestamp = anim.getLengthTicks() - this.animationTime;
        timestamp = Mth.clamp(timestamp, 0, anim.getLengthTicks());
        var current_state = dog.getDogAnimDebugState();
        return new DogAnimDebugState(anim.getId(), timestamp, current_state.yrot());
    }

    public static record DogAnimDebugState(int anim_id, int timestamp, float yrot) {
        
        public static final DogAnimDebugState NONE = new DogAnimDebugState(-1, 0, 0);

        public boolean isNone() {
            if (this == NONE)
                return true;
            return this.anim_id < 0 || this.anim_id == DogAnimation.NONE.getId();
        }

    }

}
//emgniypocpots_redrehtac