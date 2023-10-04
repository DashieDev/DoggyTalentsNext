package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.DoggyItems;
import doggytalents.api.registry.Accessory;
import doggytalents.common.entity.accessory.HeadBandAccessory;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class HeadBandItem extends AccessoryItem{
    public HeadBandItem(Supplier<? extends HeadBandAccessory> type, Properties properties) {
        super(type, properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        if (this == DoggyItems.HEAD_BAND_BLANK.get())
            return;
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id));
    }

}
