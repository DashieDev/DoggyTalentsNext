package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.registry.Accessory;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import doggytalents.common.entity.accessory.NonLaAccessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class NonLaAccessoryItem extends AccessoryItem {

    public NonLaAccessoryItem(Supplier<? extends NonLaAccessory> type, Properties properties) {
        super(type, properties);
    }

}
