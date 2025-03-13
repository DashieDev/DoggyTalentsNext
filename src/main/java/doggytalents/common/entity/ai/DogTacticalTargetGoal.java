package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.api.feature.DogMode;
import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

public class DogTacticalTargetGoal extends TargetGoal {

    private Dog dog;

    public DogTacticalTargetGoal(Dog dog) {
        super(dog, false, false);
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (dog.getMode() != DogMode.TACTICAL)
            return false;
        return dog.dogAttackManager.hasTaticalTarget();
    }

    @Override
    public boolean canContinueToUse() {
        if (!canUse())
            return false;
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
    }
    
}
