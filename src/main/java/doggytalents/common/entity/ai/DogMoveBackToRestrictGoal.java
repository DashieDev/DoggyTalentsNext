package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;

public class DogMoveBackToRestrictGoal extends MoveTowardsRestrictionGoal {

    private Dog dog;

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
}
