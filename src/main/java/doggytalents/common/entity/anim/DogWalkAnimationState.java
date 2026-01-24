package doggytalents.common.entity.anim;

import org.joml.Vector3f;

import doggytalents.client.debug.DebugGraph;
import doggytalents.common.entity.Dog;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class DogWalkAnimationState {
    
    private final Dog dog;
    private float speed0;
    private float speed;
    private float position;
    public float runningValue = 0;
    public float runningValue0 = 0;
    public short runningTime = 0;
    public float brakeValue = -1;
    public float brakeValue0 = -1;
    public float prevDelteMove = 0;
    public float dampener = 0;
    public float prevYRot = 0;
    public float banking0 = 0, banking = 0;
    public float yrot_velocity;
    public SecondOrderDynamics<Float> bankingDynamic = SecondOrderDynamics.single(1f, 0.5f, 0, 0);

    public DogWalkAnimationState(Dog dog) {
        this.dog = dog;
    }

    public void setSpeed(float val) {
        this.speed = Mth.clamp(val, 0, 1);
    }

    public void update(float rawDeltaMove) {
        if (!this.isMoving())
            this.position = 0;
        if (!this.dog.level().isClientSide)
            return;
        this.prevDelteMove = rawDeltaMove;
        final float accel_modifier = dampener > 0 ? 0.2f : 0.4f;
        final float new_speed = Mth.clamp(rawDeltaMove * 4, 0, 1);
        this.speed0 = this.speed;
        setSpeed(this.speed + (new_speed - this.speed) * accel_modifier);
        this.position = this.position + this.speed;
        updateRunningValue();
        updateBrakeValue();
        updateBankingValue();
        if (dampener > 0) --dampener;
    }

    private void updateRunningValue() {
        this.runningValue0 = this.runningValue;;
        if (prevDelteMove >= 0.2f) {
            this.runningValue += 0.05; //20 ticks to accel.
        } else {
            this.runningValue -= 0.1; //10 ticks to rit.
        }

        this.runningValue = Mth.clamp(this.runningValue, 0, 1);
        if (this.runningValue >= 1)
            ++this.runningTime;
        else
            this.runningTime = 0;

        this.runningTime = (short) Math.max(0, this.runningTime);
    }

    //speed -> animSpeed

    private void updateBrakeValue() {
        this.brakeValue0 = this.brakeValue;
        if (this.runningTime >= 20 && brakeValue == -1) {
            brakeValue = brakeValue0 = -2;
        }
        if (this.brakeValue < -1
            && this.runningValue > 0.8f
            && this.prevDelteMove < 0.2f) {
            brakeValue = brakeValue0 = 0;
            this.dog.getOwner().sendSystemMessage(Component.literal("brake!"));
        }
        if (brakeValue >= 1) {
            brakeValue = brakeValue0 = -1;
            this.runningValue = this.runningValue0 = 0;
            this.position = 0;
            this.speed = this.speed0 = 0;
            this.dampener = 10;
            this.runningTime = 0;
        }
            
        if (0 <= brakeValue && brakeValue < 1) {
            brakeValue = Mth.clamp(brakeValue + 0.1f, 0, 1);
        }
        //
        this.brakeValue = -1;
    }

    public void updateBankingValue() {
        this.yrot_velocity = Mth.wrapDegrees(dog.getYRot() - this.prevYRot);
        this.prevYRot = dog.getYRot();

        this.banking0 = this.banking;

        float bank_blend = this.runningValue <= 0.5f ? 0
            : (this.runningValue - 0.5f)/(1 - 0.5f);
        
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

        DebugGraph.INSTANCE.recordValue("bank_raw", raw_bank_value);
        DebugGraph.INSTANCE.recordValue("bank_smooth", bank_value);
        this.banking = bank_value;
        

        // this.banking0 = this.banking;
        // boolean in_banking_state = this.runningValue > 0.5 && this.brakeValue <= 0;
        // if (in_banking_state && Mth.abs(yrot_velocity) > 11f) {
        //     final float max_bank_cap = this.bankingThreshold() + 1f;
        //     final float bank_velocity = Mth.sign(yrot_velocity) * 0.2f;
        //     this.banking += bank_velocity;
        //     //remap banking
        //     float banking_abs = Mth.abs(banking);
        //     if (banking_abs > this.bankingThreshold()) {
        //         float progress = (banking_abs - this.bankingThreshold());
        //         banking_abs = this.bankingThreshold() + progress * progress;
        //         this.banking = Mth.sign(banking) * banking_abs;
        //     }
        //     this.banking = Mth.clamp(this.banking, -max_bank_cap, max_bank_cap);
        // } else {
        //     if (this.banking != 0) {
        //         if (in_banking_state) {
        //             this.banking *= 0.97f;
        //         } else {
        //             this.banking *= 0.4f;
        //         }
        //     }
        //     if (Mth.equal(this.banking, 0))
        //         this.banking = 0;
        // }
        // if (dog.level().isClientSide) {
        //     DebugGraph.INSTANCE.recordValue("speed", this.banking);
        // }
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

    public float runningValue(float pticks) {
        return Mth.lerp(pticks, this.runningValue0, this.runningValue);
    }

    public float brakeValue(float pticks) {
        return Mth.lerp(pticks, this.brakeValue0, this.brakeValue);
    }

    public float bankValue(float pticks) {
        if (!isBanking())
            return 0;
        var ret = Mth.lerp(pticks, this.banking0, this.banking);
        ret += -Mth.sign(ret) * bankingThreshold();
        return Mth.clamp(ret, -1, 1);
    }

    public boolean isBanking() {
        return Mth.abs(this.banking) >= bankingThreshold();
    }

    public float maxBankZRot() {
        return 45;
    }

    public float bankingThreshold() {
        return 0;
    }
    
    public boolean isMoving() {
        return this.speed > Mth.EPSILON;
    }

}
