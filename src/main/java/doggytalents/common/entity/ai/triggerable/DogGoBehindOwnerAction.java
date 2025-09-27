package doggytalents.common.entity.ai.triggerable;

import javax.annotation.Nonnull;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
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
        if (!this.owner.isAlive() 
            || this.owner.distanceToSqr(dog) > 16*16) {
            this.setState(ActionState.FINISHED); return;
        }
        getBehindOwnerPos();
        if (this.targetPos == null) {
            this.setState(ActionState.FINISHED); return;
        }
        this.dog.getNavigation().stop();
        DogUtil.moveToIfReachOrElse(dog, targetPos, 
            dog.getUrgentSpeedModifier(), 1, 1, d -> { this.targetPos = null; });
        if (this.targetPos == null) {
            this.setState(ActionState.FINISHED); return;
        }
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
        var owner_pos0 = owner.position();
        float a1 = owner.getYHeadRot();
        float dx1 = Mth.sin(a1*Mth.DEG_TO_RAD);
        float dz1 = -Mth.cos(a1*Mth.DEG_TO_RAD);
        
        var vec_back = new Vec3(dx1, 0, dz1);
        double pX = owner_pos0.x, pY = owner_pos0.y , pZ = owner_pos0.z;
        for (int i = 0; i < 3; ++i) {
            pX += vec_back.x; pZ += vec_back.z;
            var bpos = BlockPos.containing(pX, pY, pZ);
            if (DogUtil.isWalkNodeEvaluatorBlockedPos(dog, bpos)) {
                ++pY;
                continue;
            }
            if (DogUtil.isWalkNodeEvaluatorOpenPos(dog, bpos.below())) {
                --pY;
                continue;
            }
        }
        
        var bpos = BlockPos.containing(pX, pY, pZ);
        this.targetPos = DogUtil.isWalkNodeEvaluatorOpenPos(dog, bpos) ? bpos : null;
    }
    
}
