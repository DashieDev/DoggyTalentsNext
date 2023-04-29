package doggytalents.common.talent.doggy_tools;

import java.util.Map;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import net.minecraft.world.item.Item;

public class ToolAndActionEntries {

    public static Map<Item, ToolAndActionEntry> MAPPINGS;

    static {
    }

    public static interface ToolAndActionEntry {

        public TriggerableAction getTriggerableAction();
        public boolean shouldUse(Dog dog);

    }
    
}
