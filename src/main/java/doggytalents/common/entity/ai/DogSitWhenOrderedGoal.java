package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;

public class DogSitWhenOrderedGoal extends SitWhenOrderedToGoal {

    Dog dog;

    public DogSitWhenOrderedGoal(Dog dog) {
        super(dog);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        var action = dog.getTriggerableAction();
        if (action != null && action.canPreventSit()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        var stashed_action = dog.getStashedTriggerableAction();
        if (stashed_action != null && !stashed_action.shouldPersistAfterSit()) {
             dog.setStashedTriggerableAction(null);
        }
        var action = dog.getTriggerableAction();
        if (action != null && !action.shouldPersistAfterSit()) {
            dog.triggerAction(null);
        }
    }

    @Override
    public boolean canContinueToUse() {
        var action = dog.getTriggerableAction();
        if (action != null && action.canPreventSit()) {
            return false;
        }
        return super.canContinueToUse();
    }
    
}
