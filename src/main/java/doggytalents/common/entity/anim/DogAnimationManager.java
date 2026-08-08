package doggytalents.common.entity.anim;

import java.util.Objects;

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

    //Common
    private boolean started = false;
    private int animationTime;
    private final Dog dog;
    private boolean looping = false;
    private boolean holdOnLastTick = false;
    private boolean isHolding = false;
    private int tickTillSync = 0;
    private int blendTick = 0;
    private int blendDuration = 0;
    public DogCapturedStateForAnim capturedStateForAnim = DogCapturedStateForAnim.NONE;

    //Common - Debug
    private boolean isDebug = false;

    public DogAnimationManager(Dog dog) { this.dog = dog; }

    public void onAnimationChange(DogAnimation anim) {
        animationTime = 0;
        this.isHolding = false;
        this.blendTick = 0;
        this.blendDuration = 0;
        this.capturedStateForAnim = DogCapturedStateForAnim.NONE;
        if (anim != DogAnimation.NONE) {
            started = true;
            looping = anim.looping();
            holdOnLastTick = anim.holdOnLastTick();
            this.animationTime = anim.getLengthTicks();
            animationState.start(dog.tickCount);
            tickTillSync = SYNC_INTERVAL_TICK;
            if (!anim.isNone()) {
                this.blendTick = 0;
                this.blendDuration = anim.blend().blendTick();
                this.capturedStateForAnim = DogCapturedStateForAnim.capture(dog);
            }
        } else {
            started = false;
            looping = false;
            animationState.stop();
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

        if (started && this.blendTick < this.blendDuration) {
            this.blendTick++;
        }

        if (isDebug)
            tickDebug();
    }

    public void onSyncTimeUpdated() {
        if (this.dog.level().isClientSide) {
            int sync_time = this.dog.getAnimSyncTime();
            resolveLatencyIfNeeded(sync_time);
        } 
            
    }

    private void resolveLatencyIfNeeded(int syncTime) {
        if (syncTime < 0)
            return;
        if (!started || looping)
            return;
        
        int configMaxLatency = ConfigHandler.CLIENT.MAX_ANIMATION_LATENCY_ALLOWED.get();
        if (configMaxLatency < 0)
            return;
        int maxLatencyAllowed = MIN_VAL_MAX_LATENCY;
        if (configMaxLatency > MIN_VAL_MAX_LATENCY)
            maxLatencyAllowed = configMaxLatency;

        int correctTime = syncTime;
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

    public float getBlendProgress(float partialTicks) {
        if (!started || this.blendTick >= this.blendDuration) return 1.0f;
        float ret = Mth.clamp((this.blendTick + partialTicks) / (float) this.blendDuration, 0.0f, 1.0f);
        return Mth.equal(ret, 1) ? 1 : ret;
    }

    public boolean playingFullAnim(float pticks) {
        final var anim = this.dog.getAnim();
        return !anim.isNone() && (
            anim.blend().isNone()
            || getBlendProgress(pticks) >= 1
        );
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
        debug_tag.putInt("anim_id", debug_state.anim().getId());
        debug_tag.putInt("timestamp", debug_state.timestamp());
        debug_tag.putFloat("yrot", debug_state.yRot());
        tag.put("dtnDogAnimDebug", debug_tag);
    }

    public void load(CompoundTag tag) {
        if (!tag.contains("dtnDogAnimDebug", Tag.TAG_COMPOUND))
            return;
        var debug_tag = tag.getCompound("dtnDogAnimDebug");
        int anim_id = debug_tag.getInt("anim_id");
        int timestamp = debug_tag.getInt("timestamp");
        float yrot = debug_tag.getFloat("yrot");
        var debug_state = DogAnimDebugState.of(anim_id, timestamp, yrot);
        setDogAnimDebugState(debug_state);
    }

    private void tickDebug() {
        dog.yBodyRot = dog.getDogAnimDebugState().yRot();
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
        var old_state = dog.getDogAnimDebugState();
        dog.setDogAnimDebugState(state);
        if (state.isNone() && !old_state.isNone())
            dog.setAnim(DogAnimation.NONE);
        if (!state.isNone() && old_state.isNone())
            dog.dogAi.forceStopAllGoal();
    }

    public void setDebugFreezeYRot(float yrot) {
        var current_state = dog.getDogAnimDebugState();
        setDogAnimDebugState(DogAnimDebugState.of(current_state.anim(), 
            current_state.timestamp(), yrot));
    }

    public DogAnimDebugState getFreezeDebugState(DogAnimation anim) {
        int timestamp = anim.getLengthTicks() - this.animationTime;
        timestamp = Mth.clamp(timestamp, 0, anim.getLengthTicks());
        var current_state = dog.getDogAnimDebugState();
        return DogAnimDebugState.of(anim, timestamp, current_state.yRot());
    }

    public static record DogCapturedStateForAnim(
        float headXRot, float headYRot, DogPose pose,
        float shakeAnim, float begAnim
    ) {
        public static final DogCapturedStateForAnim NONE = 
            new DogCapturedStateForAnim(0, 0, DogPose.STAND, 0, 0);

        public static DogCapturedStateForAnim capture(Dog dog) {
            final float xrot = Mth.wrapDegrees(dog.getXRot());
            final float yheadrot = Mth.wrapDegrees(dog.yHeadRot);
            return new DogCapturedStateForAnim(
                xrot, yheadrot, dog.getDogPose(),
                dog.getDogClassicalShakeAnim(1), dog.getDogClassicalBegAnim(1)
            );
        }

        public boolean isNone() {
            return this == NONE;
        }
    }

    public static class DogAnimDebugState {
        
        public static final DogAnimDebugState NONE = new DogAnimDebugState();

        private DogAnimation anim = DogAnimation.NONE;
        private int timestamp = 0;
        private float yrot = 0;
    
        private DogAnimDebugState() {
            this.anim = DogAnimation.NONE;
            this.timestamp = 0;
            this.yrot = 0;
        }

        private DogAnimDebugState(DogAnimation anim, int timestamp, float yrot) {
            this.anim = anim;
            this.timestamp = timestamp;
            this.yrot = yrot;
        }

        public static DogAnimDebugState of(int animId, int timestamp, float yrot) {
            var anim = DogAnimation.byId(animId);
            return of(anim, timestamp, yrot);
        }

        public static DogAnimDebugState of(DogAnimation anim, int timestamp, float yrot) {
            if (anim == null || anim.isNone())
                return NONE;
            var ret = new DogAnimDebugState(anim, timestamp, yrot);
            if (ret.isNone())
                return NONE;
            return ret;
        }

        public boolean isNone() {
            if (this == NONE)
                return true;
            return this.anim.isNone();
        }

        public DogAnimation anim() {
            return this.anim;
        }

        public int timestamp() {
            return this.timestamp;
        }

        public float yRot() {
            return this.yrot;
        }

        @Override
        public boolean equals(Object obj) {
            if (this.isNone() && obj == NONE)
                return true;
            if (!(obj instanceof DogAnimDebugState other))
                return false;
            if (this.isNone() && other.isNone())
                return true;
            return
                this.anim == other.anim
                && this.timestamp == other.timestamp
                && this.yrot == other.yrot;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.anim.getId(), this.timestamp, this.yrot);
        }

    }

}
//emgniypocpots_redrehtac