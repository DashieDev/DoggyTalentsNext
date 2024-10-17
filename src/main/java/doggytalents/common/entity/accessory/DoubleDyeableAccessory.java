package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.AccessoryType;
import doggytalents.common.item.DoubleDyeableAccessoryItem;
import doggytalents.common.util.ColourCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class DoubleDyeableAccessory extends Accessory {

    public DoubleDyeableAccessory(Supplier<? extends AccessoryType> typeIn, Supplier<? extends ItemLike> itemIn) {
        super(typeIn, itemIn);
    }

    @Override
    public AccessoryInstance getDefault() {
        return new DoubleDyeableAccessoryInstance(this, 0, 0);
    }

    @Override
    public AccessoryInstance createInstance(FriendlyByteBuf buf) {
        var bg_color = buf.readInt();
        var fg_color = buf.readInt();
        return new DoubleDyeableAccessoryInstance(this, bg_color, fg_color);
    }

    @Override
    public void write(AccessoryInstance instance, FriendlyByteBuf buf) {
        if (!(instance instanceof DoubleDyeableAccessoryInstance inst)) {
            buf.writeInt(0);
            buf.writeInt(0);
            return;
        }   
        buf.writeInt(inst.getBgColorInteger());
        buf.writeInt(inst.getFgColorInteger());
    }

    @Override
    public void write(AccessoryInstance instance, CompoundTag compound) {
        if (!(instance instanceof DoubleDyeableAccessoryInstance inst))
            return;
        compound.putInt("dtn_bg_color", inst.getBgColorInteger());
        compound.putInt("dtn_fg_color", inst.getFgColorInteger());
    }

    @Override
    public AccessoryInstance read(CompoundTag compound) {
        int bg_color = 0;
        if (compound.contains("dtn_bg_color")) {
            bg_color = compound.getInt("dtn_bg_color");
        } else if (compound.contains("color")) {
            bg_color = compound.getInt("color");
        }

        int fg_color = 0;
        if (compound.contains("dtn_fg_color")) {
            fg_color = compound.getInt("dtn_fg_color");
        } else if (compound.contains("bdhat_fg_color")) {
            fg_color = compound.getInt("bdhat_fg_color");
        }

        return new DoubleDyeableAccessoryInstance(this, bg_color, fg_color);
    }

    @Override
    public AccessoryInstance createFromStack(ItemStack stackIn) {
        var item = stackIn.getItem();
        if (item instanceof DoubleDyeableAccessoryItem doubleAccessory) {
            return new DoubleDyeableAccessoryInstance(this, doubleAccessory.getBgColor(stackIn), doubleAccessory.getFgColor(stackIn));
        }

        return this.getDefault();
    }

    @Override
    public ItemStack getReturnItem(AccessoryInstance instance) {
        ItemStack returnStack = super.getReturnItem(instance);

        if (!(instance instanceof DoubleDyeableAccessoryInstance inst))
            return returnStack;
        
        if (returnStack.getItem() instanceof DoubleDyeableAccessoryItem birthday) {
            birthday.setBgColor(returnStack, inst.getBgColorInteger());
            birthday.setFgColor(returnStack, inst.getFgColorInteger());
        }

        return returnStack;
    }

    public static class DoubleDyeableAccessoryInstance extends AccessoryInstance {

        private ColourCache bgColor;
        private ColourCache fgColor;

        public DoubleDyeableAccessoryInstance(Accessory typeIn, int bgColor, int fgColor) {
            this(typeIn, ColourCache.make(bgColor), ColourCache.make(fgColor));
        }

        public DoubleDyeableAccessoryInstance(Accessory type, ColourCache bgColor, ColourCache fgColor) {
            super(type);
            this.bgColor = bgColor;
            this.fgColor = fgColor;
        }

        public float[] getBgColor() {
            return this.bgColor.getFloatArray();
        }

        public float[] getFgColor() {
            return this.fgColor.getFloatArray();
        }

        public int getBgColorInteger() {
            return this.bgColor.get();
        }

        public int getFgColorInteger() {
            return this.fgColor.get();
        }

        @Override
        public AccessoryInstance copy() {
            return new DoubleDyeableAccessoryInstance(this.getAccessory(), this.bgColor, this.fgColor);
        }

    }
    
    

}
