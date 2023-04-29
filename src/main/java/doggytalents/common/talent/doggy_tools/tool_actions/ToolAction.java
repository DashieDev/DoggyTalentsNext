package doggytalents.common.talent.doggy_tools.tool_actions;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;

public class ToolAction extends TriggerableAction {

    protected DoggyToolsTalent talent;

    public ToolAction(Dog dog, boolean trivial, boolean canPause, DoggyToolsTalent talent) {
        super(dog, trivial, canPause);
        this.talent = talent;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void tick() {
    }

    @Override
    public void onStop() {
        //TODO reset hotslot.
    }
    
}
