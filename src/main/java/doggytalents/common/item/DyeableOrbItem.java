package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;
import doggytalents.common.entity.accessory.DoubleDyeableAccessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class DyeableOrbItem extends DoubleDyeableAccessoryItem {
    
    public DyeableOrbItem(Supplier<? extends DoubleDyeableAccessory> type, Properties properties) {
        super(type, properties);
    }

    @Override
    public int getDefaultBgColor() {
        return 0xffffffff;
    }

}
