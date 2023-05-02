package doggytalents.common.talent.doggy_tools;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.common.collect.Maps;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.talent.doggy_tools.tool_actions.DogFarmerAction;
import doggytalents.common.talent.doggy_tools.tool_actions.ToolAction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ToolActionEntries {

    public static Map<Item, BiFunction<Dog, DoggyToolsTalent, ToolAction>> MAPPINGS;

    static {
        MAPPINGS = Maps.newHashMap();
        MAPPINGS.put(Items.STONE_HOE, (dog, talent) -> {
            return new DogFarmerAction(dog, talent);
        });      
    }

    public static Map<Item, ToolAction> getToolActionMapFor(Dog dog, DoggyToolsTalent tools) {
        Map<Item, ToolAction> map = Maps.newHashMap();
        for (var entry : MAPPINGS.entrySet()) {
            map.put(entry.getKey(), entry.getValue().apply(dog, tools));
        }
        return map;
    }
    
}
