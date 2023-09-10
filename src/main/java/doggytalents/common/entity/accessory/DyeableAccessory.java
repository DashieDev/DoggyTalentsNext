package doggytalents.common.entity.accessory;

import doggytalents.DoggyTalentsNext;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IColoredObject;
import doggytalents.api.inferface.IDogAlteration;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.AccessoryType;
import doggytalents.common.util.ColourCache;
import doggytalents.common.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DyeableAccessory extends Accessory {

    public DyeableAccessory(Supplier<? extends AccessoryType> typeIn, Supplier<? extends ItemLike> itemIn) {
        super(typeIn, itemIn);
    }

    @Override
    public AccessoryInstance createInstance(FriendlyByteBuf buf) {
        return this.create(buf.readInt());
    }

    @Override
    public void write(AccessoryInstance instance, FriendlyByteBuf buf) {
        DyeableAccessoryInstance exact = instance.cast(DyeableAccessoryInstance.class);
        buf.writeInt(exact.getColorInteger());
    }

    @Override
    public void write(AccessoryInstance instance, CompoundTag compound) {
        DyeableAccessoryInstance exact = instance.cast(DyeableAccessoryInstance.class);
        compound.putInt("color", exact.getColorInteger());
    }

    @Override
    public AccessoryInstance read(CompoundTag compound) {
        return this.create(compound.getInt("color"));
    }

    @Override
    public AccessoryInstance getDefault() {
        return this.create(0);
    }

    @Override
    public ItemStack getReturnItem(AccessoryInstance instance) {
        DyeableAccessoryInstance exact = instance.cast(DyeableAccessoryInstance.class);

        ItemStack returnStack = super.getReturnItem(instance);
        if (returnStack.getItem() instanceof DyeableLeatherItem) {
            ((DyeableLeatherItem) returnStack.getItem()).setColor(returnStack, exact.getColorInteger());
        } else {
            DoggyTalentsNext.LOGGER.info("Unable to set set dyable accessory color.");
        }

        return returnStack;
    }

    public AccessoryInstance create(int color) {
        return new DyeableAccessoryInstance(color);
    }

    @Override
    public AccessoryInstance createFromStack(ItemStack stackIn) {
        Item item = stackIn.getItem();
        if (item instanceof DyeableLeatherItem) {
            return this.create(((DyeableLeatherItem) item).getColor(stackIn));
        }

        return this.getDefault();
    }

    public class DyeableAccessoryInstance extends AccessoryInstance implements IDogAlteration, IColoredObject {

        private ColourCache color;

        public DyeableAccessoryInstance(int colorIn) {
            this(ColourCache.make(colorIn));
        }

        public DyeableAccessoryInstance(ColourCache colorIn) {
            super(DyeableAccessory.this);
            this.color = colorIn;
        }

        @Override
        public float[] getColor() {
            return this.color.getFloatArray();
        }

        public int getColorInteger() {
            return this.color.get();
        }

        @Override
        public AccessoryInstance copy() {
            return new DyeableAccessoryInstance(this.color);
        }

    }

}
