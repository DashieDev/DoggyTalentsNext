package doggytalents.common.entity.ai.triggerable;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction.ActionState;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogTriggerableGoal extends Goal {

    private static final EnumSet<Flag> IDLE_FLAGS = EnumSet.of(Goal.Flag.LOOK);

    Dog dog;
    final boolean trivial;

    public DogTriggerableGoal(Dog dog, boolean trivial) {
        this.dog = dog;
        this.trivial = trivial;
        //Temporary leave out Jump flag for now
        //Because most goal which lock only the Jump flag
        //is intended to be able to augment other goals.
        //An action just need to lock flag MOVE and LOOK btw
        //Like DogFollowOwner and most Action-like Goals.
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
        if (dog.hasDelayedActionStart())
            return;
        switch (action.getState()) {
            case PENDING :
                action.setState(ActionState.RUNNING);
                //Allow checks on start.
                action.onStart();
                break;
            case PAUSED :
                action.setState(ActionState.RUNNING);
                //action.onResume()
                action.tick();
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
        if (!this.trivial) {
            var stashed_action = dog.getStashedTriggerableAction();
            if (stashed_action != null) {
                if (!stashed_action.canPause()) dog.setStashedTriggerableAction(null);
                stashed_action.setState(ActionState.PAUSED);
            }
        }
        var action = dog.getTriggerableAction();
        if (action != null) {
            if (action.isTrivial() != trivial) return;
            if (!action.canPause()) dog.triggerAction(null);
            action.setState(ActionState.PAUSED);
        }
        
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
        
}
