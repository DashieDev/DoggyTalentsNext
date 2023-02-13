package doggytalents.common.entity.ai.triggerable;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction.ActionState;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogTriggerableGoal extends Goal {

    Dog dog;
    final boolean trivial;

    public DogTriggerableGoal(Dog dog, boolean trivial) {
        this.dog = dog;
        this.trivial = trivial;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        //If a dog have an action to proccess.
        var action = dog.getTriggerableAction();
        return action != null && action.isTrivial() == trivial;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void tick() {
        var action = dog.getTriggerableAction();
        if (action == null) return;
        switch (action.getState()) {
            case PENDING :
                action.setState(ActionState.RUNNING);
                //Allow checks on start.
                action.onStart();
                break;
            case RUNNING :
                action.tick();
                break;
            case FINISHED :
                dog.triggerAction(null);
                break;
            default :
                break;
        }
    }

    @Override
    public void stop() {
        //Stashed Action on trivial action manager.
        if (this.trivial) {
            var stashed_action = dog.getStashedTriggerableAction();
            if (stashed_action != null && stashed_action.canPause()) {
            } else {
                dog.setStashedTriggerableAction(null);
            }
        }
        var action = dog.getTriggerableAction();
        if (action != null && action.isTrivial() == trivial && action.canPause()) {
        } else {
            dog.triggerAction(null);
        }
        
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
        
}
