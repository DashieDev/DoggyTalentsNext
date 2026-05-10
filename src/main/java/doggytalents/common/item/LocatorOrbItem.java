package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.registry.Accessory;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class LocatorOrbItem extends AccessoryItem {

    private Supplier<? extends LocatorOrbAccessory> orbType;

    public LocatorOrbItem(Supplier<? extends LocatorOrbAccessory> type, Properties properties) {
        super(type, properties);
        this.orbType = type;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<net.minecraft.network.chat.Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId() + ".description";
        components.accept(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withColor(orbType.get().getOrbColor())
        ));
    }
    
}
