package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogTriggerableGoal extends Goal {

    Dog dog;

    public DogTriggerableGoal(Dog dog) {
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        return dog.getTriggerableAction() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void tick() {
        var action = dog.getTriggerableAction();
        if (action.isRunning()) action.tick();
        else dog.triggerAction(null);
    }
    
}
