package doggytalents.common.talent.doggy_tools.tool_actions;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import net.minecraft.world.item.ItemStack;

public abstract class ToolAction extends TriggerableAction {

    protected DoggyToolsTalent talent;

    public ToolAction(Dog dog, DoggyToolsTalent talent) {
        super(dog, true, false);
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
        // future: reset hotslot after use
    }

    public abstract boolean shouldUse(ItemStack stack);
    
}
