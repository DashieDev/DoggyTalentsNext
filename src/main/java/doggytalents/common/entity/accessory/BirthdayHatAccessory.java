package doggytalents.common.entity.accessory;

import java.util.function.Supplier;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyTalentsNext;
import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.client.entity.render.AccessoryModelManager.Entry;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.IAccessoryHasModel;
import doggytalents.common.item.BirthdayHatItem;
import doggytalents.common.item.DyableBirthdayHatItem;
import doggytalents.common.util.ColourCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class BirthdayHatAccessory extends Accessory implements IAccessoryHasModel  {
    
    public BirthdayHatAccessory(Supplier<? extends ItemLike> itemIn) {
        super(DoggyAccessoryTypes.HEAD, itemIn);
    }

    @Override
    public Entry getRenderEntry() {
        return AccessoryModelRenderEntries.BIRTHDAY_HAT;
    }

    @Override
    public AccessoryInstance getDefault() {
        return new Inst(this, 0, 0);
    }

    @Override
    public AccessoryInstance createInstance(FriendlyByteBuf buf) {
        var bg_color = buf.readInt();
        var fg_color = buf.readInt();
        return new Inst(this, bg_color, fg_color);
    }

    @Override
    public void write(AccessoryInstance instance, FriendlyByteBuf buf) {
        if (!(instance instanceof Inst inst)) {
            buf.writeInt(0);
            buf.writeInt(0);
            return;
        }   
        buf.writeInt(inst.getColorInteger());
        buf.writeInt(inst.getFgColorInteger());
    }

    @Override
    public void write(AccessoryInstance instance, CompoundTag compound) {
        if (!(instance instanceof Inst inst))
            return;
        compound.putInt("color", inst.getColorInteger());
        compound.putInt("bdhat_fg_color", inst.getFgColorInteger());
    }

    @Override
    public AccessoryInstance read(CompoundTag compound) {
        var bg_color = compound.getInt("color");
        var fg_color = compound.getInt("bdhat_fg_color");
        return new Inst(this, bg_color, fg_color);
    }

    @Override
    public AccessoryInstance createFromStack(ItemStack stackIn) {
        var item = stackIn.getItem();
        if (item instanceof DyableBirthdayHatItem birthday) {
            return new Inst(this, birthday.getColor(stackIn), birthday.getForegroundColor(stackIn));
        }

        return this.getDefault();
    }

    @Override
    public ItemStack getReturnItem(AccessoryInstance instance) {
        ItemStack returnStack = super.getReturnItem(instance);

        if (!(instance instanceof Inst inst))
            return returnStack;
        if (returnStack.getItem() instanceof DyableBirthdayHatItem birthday) {
            birthday.setColor(returnStack, inst.getColorInteger());
            birthday.setForegroundColor(returnStack, inst.getFgColorInteger());
        } else {
        }

        return returnStack;
    }

    public static class Inst extends AccessoryInstance implements IColoredObject {

        private ColourCache bgColor;
        private ColourCache fgColor;

        public Inst(Accessory typeIn, int bgColor, int fgColor) {
            this(typeIn, ColourCache.make(bgColor), ColourCache.make(fgColor));
        }

        public Inst(Accessory type, ColourCache bgColor, ColourCache fgColor) {
            super(type);
            this.bgColor = bgColor;
            this.fgColor = fgColor;
        }

        @Override
        public float[] getColor() {
            return this.bgColor.getFloatArray();
        }

        public float[] getFgColor() {
            return this.fgColor.getFloatArray();
        }

        public int getColorInteger() {
            return this.bgColor.get();
        }

        public int getFgColorInteger() {
            return this.fgColor.get();
        }

        @Override
        public AccessoryInstance copy() {
            return new Inst(this.getAccessory(), this.bgColor, this.fgColor);
        }
        
    }
    
}
