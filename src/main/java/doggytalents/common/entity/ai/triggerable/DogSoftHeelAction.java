package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.world.entity.LivingEntity;

public class DogSoftHeelAction extends TriggerableAction {

    private LivingEntity owner;
    private int timeToRecalcPath;
    private int timeOut;

    public DogSoftHeelAction(Dog dog, LivingEntity owner) {
        super(dog, false, false);
        this.owner = owner;
    }

    @Override
    public void onStart() {
        timeOut = 200;
    }

    @Override
    public void tick() {
        double d0 = this.dog.distanceToSqr(owner);
        if (d0 < 4 || --timeOut <= 0) {
            this.setState(ActionState.FINISHED);
            return;
        }
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            DogUtil.moveToOwnerOrTeleportIfFarAway(
                dog, owner, dog.getUrgentSpeedModifier(),
                256, 
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
    
}
