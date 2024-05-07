package doggytalents.common.item;

import java.util.List;
import java.util.function.Supplier;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.entity.accessory.DoubleDyableAccessory;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.util.Util;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class DoubleDyableAccessoryItem extends AccessoryItem {
    
    private Supplier<? extends DoubleDyableAccessory> accessory;

    public DoubleDyableAccessoryItem(Supplier<? extends DoubleDyableAccessory> accessoryIn, Properties properties) {
        super(accessoryIn, properties);
        this.accessory = accessoryIn;
    }

    @Override
    public AccessoryInstance createInstance(AbstractDog dogIn, ItemStack stack, Player playerIn) {
        return this.accessory.get().createFromStack(stack);
    }

    public int getBgColor(ItemStack stack) { 
        var defColor = getDefaultBgColor();
        var tag = ItemUtil.getTag(stack);
        if (tag == null)
            return defColor;
        if (tag.contains("doggytalents_bg_color", Tag.TAG_INT)) {
            return tag.getInt("doggytalents_bg_color");
        } else if (tag.contains("color", Tag.TAG_INT)) {
            return tag.getInt("color");
        }
        return defColor;
    }

    public int getFgColor(ItemStack stack) { 
        var defColor = getDefaultFgColor();
        var tag = ItemUtil.getTag(stack);
        if (tag == null)
            return defColor;
        if (tag.contains("doggytalents_fg_color", Tag.TAG_INT)) {
            return tag.getInt("doggytalents_fg_color");
        } else if (tag.contains("dtn_bdhat_foreground", Tag.TAG_INT)) {
            return tag.getInt("dtn_bdhat_foreground");
        }
        return defColor;
    }

    public void setBgColor(ItemStack stack, int color) {
        var tag = ItemUtil.getTag(stack);
        tag.putInt("doggytalents_bg_color", color);
        ItemUtil.putTag(stack, tag);
    }

    public void setFgColor(ItemStack stack, int color) {
        var tag = ItemUtil.getTag(stack);
        tag.putInt("doggytalents_fg_color", color);
        ItemUtil.putTag(stack, tag);
    }

    public static ItemStack copyAndSetColorForStack(ItemStack stack, List<DyeColor> dye, boolean fg_color) {
        var item = stack.getItem();
        if (!(item instanceof DoubleDyableAccessoryItem accessory))
            return ItemStack.EMPTY;
        stack = stack.copyWithCount(1);
        int color = Util.colorDye(-1, dye);
        if (fg_color) {
            accessory.setFgColor(stack, color);
        } else {
            accessory.setBgColor(stack, color);
        }
        return stack;
    }

    public int getDefaultBgColor() { return 0xffffffff; }
    public int getDefaultFgColor() { return getDefaultBgColor(); }

}
