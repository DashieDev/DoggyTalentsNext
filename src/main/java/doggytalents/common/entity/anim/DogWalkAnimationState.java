package doggytalents.common.entity.anim;

import doggytalents.common.entity.Dog;
import net.minecraft.util.Mth;

public class DogWalkAnimationState {
    
    private final Dog dog;

    //Walking State 
    private SecondOrderDynamics<Float> speedDynamic = SecondOrderDynamics.single(1.2f, 0.5f, 0, 0);
    private float speed, speed0;
    private float position;

    //Running State
    private float runningValue = 0;
    private float runningValue0 = 0;

    //Banking State
    private float prevYRot = 0;
    private float bankBlend = 0;
    private SecondOrderDynamics<Float> bankingDynamic = SecondOrderDynamics.single(0.6f, 0.5f, 0, 0);
    private float banking0 = 0, banking = 0;

    public DogWalkAnimationState(Dog dog) {
        this.dog = dog;
    }

    public void setSpeed(float val) {
        this.speed = Mth.clamp(val, 0, 1);
    }

    public void update(float rawDeltaMove) {
        if (!this.dog.level().isClientSide())
            return;
        this.speed0 = this.speed;

        float new_speed = this.speedDynamic.update(1/20f, rawDeltaMove * 4);
        setSpeed(new_speed);
        
        updateRunningValue(rawDeltaMove);
        updateBankingValue();
        
        this.position = shouldResetPosition(speed0, runningValue0) ? 
            this.speed : this.position + this.speed;
    }

    private boolean shouldResetPosition(float speed0, float running0) {
        return speed0 < Mth.EPSILON && running0 <= this.runningThreshold();
    }

    private void updateRunningValue(float rawDeltaMove) {
        this.runningValue0 = this.runningValue;
        if (rawDeltaMove >= 0.2f) {
            this.runningValue += 0.05; //20 ticks to accel.
        } else {
            this.runningValue -= 0.1; //10 ticks to rit.
        }

        this.runningValue = Mth.clamp(this.runningValue, 0, 1);
    }

    private void updateBankingValue() {
        float yrot_velocity = Mth.wrapDegrees(dog.getYRot() - this.prevYRot);
        this.prevYRot = dog.getYRot();

        this.banking0 = this.banking;

        if (this.runningValue > 0.5f) {
            this.bankBlend = Mth.clamp(this.bankBlend + 0.05f, 0, 1);
        } else {
            this.bankBlend = Mth.clamp(this.bankBlend - 0.1f, 0, 1);
        }

        float bank_blend = this.bankBlend;
        
        if (bank_blend <= 0) {
            this.bankingDynamic.reset(0f);
            return;
        }

        final float min_speed = 11f;
        final float max_speed = 30f;
        float raw_bank_value = 
            (Mth.abs(yrot_velocity) - min_speed)/(max_speed - min_speed);
        raw_bank_value = Mth.sign(yrot_velocity) * Mth.clamp(raw_bank_value, 0, 1);

        float bank_value = this.bankingDynamic.update(1/20f, raw_bank_value);
        bank_value = bank_blend * Mth.clamp(bank_value, -1, 1);

        this.banking = bank_value;
    }

    public float speed() {
        return this.speed;
    }

    public float speed(float pticks) {
        return Mth.lerp(pticks, this.speed0, this.speed);
    }

    public float position() {
        return this.position;
    }

    public float position(float pticks) {
        return this.position - this.speed * (1.0F - pticks);
    }

    public float runningBlend(float pticks) {
        float run_val = Mth.lerp(pticks, this.runningValue0, this.runningValue);
        final float threshold = runningThreshold();
        if (run_val <= threshold)
            return 0;
        float ret = (run_val - threshold)/(1 - threshold);
        return Mth.clamp(ret, 0, 1);
    }

    private float runningThreshold() {
        return 0.5f;
    }

    public float bankValue(float pticks) {
        if (!isBanking())
            return 0;
        var ret = Mth.lerp(pticks, this.banking0, this.banking);
        return Mth.clamp(ret, -1, 1);
    }

    public boolean isBanking() {
        return !Mth.equal(this.banking, 0);
    }

    public float maxBankZRot() {
        return 45;
    }
    
    public boolean isMoving() {
        return this.speed > Mth.EPSILON;
    }

}