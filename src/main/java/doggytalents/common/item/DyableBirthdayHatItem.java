package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.accessory.DyeableAccessory;
import doggytalents.common.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DyableBirthdayHatItem extends AccessoryItem implements IDyeableArmorItem {
    
    private Supplier<? extends Accessory> type;
    
    public DyableBirthdayHatItem(Supplier<? extends Accessory> type, Properties properties) {
        super(type, properties);
        this.type = type;
    }

    @Override
    public AccessoryInstance createInstance(AbstractDog dogIn, ItemStack stack, Player playerIn) {
        return this.type.get().createFromStack(stack);
    }

    public int getForegroundColor(ItemStack stack) {
        var color = this.getDefaultColor(stack);
        var tag = stack.getTag();
        if (tag == null)
            return color;
        if (!tag.contains("dtn_bdhat_foreground", 99))
            return color;
        return tag.getInt("dtn_bdhat_foreground");
    }

    public void setForegroundColor(ItemStack stack, int color) {
        var tag = stack.getOrCreateTag();
        tag.putInt("dtn_bdhat_foreground", color);
    }

    @Override
    public void clearColor(ItemStack stack) {
        IDyeableArmorItem.super.clearColor(stack);
        var tag = stack.getTag();
        if (tag == null)
            return;
        if (tag.contains("dtn_bdhat_foreground", 99)) {
            tag.remove("dtn_bdhat_foreground");
        }
    }

    public static ItemStack dyeForegroundColorStack(ItemStack stack, List<DyeColor> dye) {
        var newStack = stack.copy();
        newStack.setCount(1);
        var item = stack.getItem();
        if (!(item instanceof DyableBirthdayHatItem birthdayHat))
            return ItemStack.EMPTY;
        int color = Util.colorDye(-1, dye);
        birthdayHat.setForegroundColor(newStack, color);
        return newStack;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    
}
