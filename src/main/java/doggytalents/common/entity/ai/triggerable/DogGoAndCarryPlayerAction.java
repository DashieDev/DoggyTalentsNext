package doggytalents.common.entity.ai.triggerable;

import javax.annotation.Nonnull;

import doggytalents.common.entity.Dog;
import doggytalents.common.talent.WolfMountTalent;
import doggytalents.common.util.DogUtil;
import net.minecraft.world.entity.LivingEntity;

public class DogGoAndCarryPlayerAction extends TriggerableAction {
    
    private final @Nonnull LivingEntity owner;
    private int timeout;
    private int tickTillPathRecalc;
    private static final int force_ride_distance = 2;

    public DogGoAndCarryPlayerAction(Dog dog, LivingEntity owner) {
        super(dog, false, false);
        this.owner = owner;
    }

    @Override
    public void onStart() {
        this.timeout = 100;
        this.tickTillPathRecalc = 8;
    }

    @Override
    public void tick() {
        if (!WolfMountTalent.isValidCarryMeDog(dog)) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (!WolfMountTalent.isValidCarryMeTarget(owner)) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (--timeout <= 0) {
            this.setState(ActionState.FINISHED);
            return;
        }

        var dist_sqr = dog.distanceToSqr(this.owner);
        if (dist_sqr <= force_ride_distance * force_ride_distance) {
            owner.startRiding(dog);
            this.setState(ActionState.FINISHED);
            return;
        }

        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (--this.tickTillPathRecalc <= 0) {
            this.tickTillPathRecalc = 20;
            DogUtil.moveToOwnerOrTeleportIfFarAway(
                dog, owner, this.dog.getUrgentSpeedModifier(),
                400, 
                false, false, 
                400,
                dog.getMaxFallDistance());
        }
    
    }

    @Override
    public void onStop() {
    }

    @Override
    public boolean canOverrideSit() {
        return true;
    }

    @Override
    public boolean goBackToSitPosWhenFinished() {
        return false;
    }
    

}
