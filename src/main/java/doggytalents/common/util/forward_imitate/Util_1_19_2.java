package doggytalents.common.util.forward_imitate;

import java.util.function.IntFunction;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

public class Util_1_19_2 {
    
    public static ItemStack copyWithCount(ItemStack target, int count) {
        var new_stack = target.copy();
        new_stack.setCount(count);
        return new_stack;
    }

    public static int mapDuration(MobEffectInstance inst, IntFunction<Integer> mapper) {
        var duration = inst.getDuration();
        return mapper.apply(duration);
    }    
}
