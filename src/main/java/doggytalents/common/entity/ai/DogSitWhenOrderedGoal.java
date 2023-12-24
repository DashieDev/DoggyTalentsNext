package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction.ActionState;
import net.minecraft.entity.ai.goal.SitWhenOrderedToGoal;

public class DogSitWhenOrderedGoal extends SitWhenOrderedToGoal {

    Dog dog;

    //Fix dog repeatedly sitting and standing if
    //owner logged out without orderingDogToSit.
    private boolean ownerOffline;

    public DogSitWhenOrderedGoal(Dog dog) {
        super(dog);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        var action = dog.getTriggerableAction();
        if (
            action != null 
            && action.canPreventSit() 
            && action.getState() == ActionState.RUNNING
            && !dog.forceSit()
        ) {
            return false;
        }
        //Passaenger dog always wants to sit down.
        if (dog.isPassenger()) return true;
        var mode = this.dog.getMode();
        if (mode.canWander() && !mode.shouldAttack() && dog.isWanderResting())
            return true;
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

        //Fix dog repeatedly sitting and standing if
        //owner logged out without orderingDogToSit.
        this.ownerOffline = this.dog.getOwner() == null;
    }

    @Override
    public boolean canContinueToUse() {
        var action = dog.getTriggerableAction();
        if (action != null && action.canPreventSit() && !dog.forceSit()) {
            return false;
        }
        if (this.dog.isOrderedToSit() || this.dog.isPassenger()) {
            return true;
        }
        var mode = this.dog.getMode();
        if (mode.canWander() && !mode.shouldAttack() && dog.isWanderResting())
            return true;

        //Fix dog repeatedly sitting and standing if
        //owner logged out without orderingDogToSit.
        if (this.ownerOffline) {
            this.ownerOffline = this.dog.getOwner() == null;
        }
        return ownerOffline;
    }
    
}
