package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.accessory.DoubleDyableAccessory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DoggyContactsItem extends DoubleDyableAccessoryItem {

    public DoggyContactsItem(Supplier<? extends DoubleDyableAccessory> accessoryIn, Properties properties) {
        super(accessoryIn, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(ComponentUtil.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    
}
