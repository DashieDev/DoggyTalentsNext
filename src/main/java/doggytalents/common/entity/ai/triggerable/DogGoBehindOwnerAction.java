package doggytalents.common.entity.ai.triggerable;

import javax.annotation.Nonnull;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class DogGoBehindOwnerAction extends TriggerableAction {

    private final @Nonnull LivingEntity owner;

    private BlockPos targetPos = null;
    private int holdTimeLeft;
    
    public DogGoBehindOwnerAction(Dog dog, @Nonnull LivingEntity owner) {
        super(dog, false, false);
        this.owner = owner;
    }

    @Override
    public void onStart() {
        getBehindOwnerPos();
        if (this.targetPos == null) {
            this.setState(ActionState.FINISHED); return;
        }
        this.dog.getNavigation().moveTo(
            this.targetPos.getX(), this.targetPos.getY(),
            this.targetPos.getZ(),
            dog.getUrgentSpeedModifier());
        this.holdTimeLeft = 40;
    }

    @Override
    public void tick() {
        if (this.targetPos == null) {
            this.setState(ActionState.FINISHED); return;
        }

        if (this.dog.getNavigation().isDone()) {
            --this.holdTimeLeft;
        }
        if (this.holdTimeLeft <= 0) {
            this.setState(ActionState.FINISHED); return;
        }
    }

    @Override
    public void onStop() {
    }

    private void getBehindOwnerPos() {
        var a1 = owner.getYHeadRot();
        var dx1 = Mth.sin(a1*Mth.DEG_TO_RAD);
        var dz1 = -Mth.cos(a1*Mth.DEG_TO_RAD);
        var offset = new Vec3i(Mth.ceil(3*dx1), 0, Mth.ceil(3*dz1));
        var owner_b0 = owner.blockPosition();
        this.targetPos = owner_b0.offset(offset);
    }
    
}
