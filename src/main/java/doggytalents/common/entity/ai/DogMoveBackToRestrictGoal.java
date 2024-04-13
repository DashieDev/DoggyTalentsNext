package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.phys.Vec3;

public class DogMoveBackToRestrictGoal extends MoveTowardsRestrictionGoal {

    private Dog dog;
    private int tickTillAttemptTp;

    public DogMoveBackToRestrictGoal(Dog dog) {
        super(dog, 1f);
        this.dog = dog;
    }   

    @Override
    public boolean canUse() {
        if (this.dog.isDefeated())
            return false;
        if (!this.dog.getMode().canWander())
            return false;
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        tickTillAttemptTp = 2;
    }

    @Override
    public void tick() {
        if (teleportBackIfNeeded())
            return;
        super.tick();
    }

    @Override
    public void stop() {
        super.stop();
        this.dog.getNavigation().stop();
    }

    private boolean teleportBackIfNeeded() {
        if (!dog.hasRestriction())
            return false;
        var center = dog.getRestrictCenter();
        if (center == null)
            return false;
        if (dog.distanceToSqr(Vec3.atBottomCenterOf(center)) < 20*20)
            return false;
        if (--tickTillAttemptTp > 0)
            return false;
        tickTillAttemptTp = 10;
        if (!dog.level().hasChunkAt(center))
            return false;
        return DogUtil.guessAndTryToTeleportToBlockPos(dog, center, Mth.floor(dog.getRestrictRadius()));
    }
}
