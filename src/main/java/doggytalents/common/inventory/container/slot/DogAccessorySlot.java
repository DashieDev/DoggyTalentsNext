package doggytalents.common.inventory.container.slot;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.kinds.ListBox.Instance;

import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.api.registry.AccessoryType;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AccessoryItem;

public class DogAccessorySlot extends Slot {
    private static Container emptyInventory = new SimpleContainer(0);
    private final int index;
    private AccessoryType type;
    private AccessoryInstance accessoryInstance;
    private Dog dog;

    public DogAccessorySlot(Dog dog, int index, int xPosition, int yPosition, AccessoryType type) {
        super(emptyInventory, index, xPosition, yPosition);
        this.index = index;
        this.type = type;
        this.dog = dog;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.isEmpty())
            return false;
        var item = stack.getItem();
        if (item instanceof AccessoryItem accessoryItem) {
            Accessory accessory = accessoryItem.type.get();
            if (accessory == null) return false;
            var type = accessory.getType();
            return type == this.type;
        }
        return false;
    }

    @Override
    @NotNull
    public ItemStack getItem() {
        if (this.accessoryInstance == null) return ItemStack.EMPTY;
        return this.accessoryInstance.getReturnItem();
    }

    // Override if your IItemHandler does not implement IItemHandlerModifiable
    @Override
    public void set(@NotNull ItemStack newStack) {
        var newItem = newStack.getItem();
        if (newStack.isEmpty()) {
            this.accessoryInstance = null;
            return;
        }
        if (!(newItem instanceof AccessoryItem)) return;
        Accessory newAccessory = ((AccessoryItem) newItem).type.get();
        if (newAccessory == null) return; //??
        if (this.accessoryInstance != null) {
            var oldAccessory = this.accessoryInstance.getAccessory();
            if (oldAccessory == newAccessory) return;
            if (!dog.level.isClientSide) {
                //Remove oldAccesory
                AccessoryInstance toRemove = null;
                for (var inst : dog.getAccessories()) {
                    if (inst.getAccessory() == oldAccessory) {
                        toRemove = inst;
                        break;
                    }
                }
                if (toRemove != null) { 
                    dog.getAccessories().remove(toRemove);
                    dog.markAccessoriesDirty();
                }
            }
        }
        if (!dog.level.isClientSide) {
            var newInst = newAccessory.getDefault();
            if (newInst != null) {
                this.dog.addAccessory(newInst);
            }
        }
        this.accessoryInstance = newAccessory.getDefault();
    }

    // Override if your IItemHandler does not implement IItemHandlerModifiable
    @Override
    public void initialize(ItemStack stack) {
        for (var inst : dog.getAccessories()) {
            if (inst.getAccessory().getType() == this.type) {
                this.accessoryInstance = inst.getAccessory().getDefault();
                break;
            }
        }
    }

    @Override
    public void onQuickCraft(@NotNull ItemStack oldStackIn, @NotNull ItemStack newStackIn) {

    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return 1;
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        if (this.accessoryInstance == null) return false;
        return dog.canInteract(playerIn);
    }

    @Override
    @NotNull
    public ItemStack remove(int amount) {
        if (this.accessoryInstance == null) return ItemStack.EMPTY;
        var ret = this.accessoryInstance.getReturnItem();
        var oldAccessory = this.accessoryInstance.getAccessory();
            if (!dog.level.isClientSide) {
                //Remove oldAccesory
                AccessoryInstance toRemove = null;
                for (var inst : dog.getAccessories()) {
                    if (inst.getAccessory() == oldAccessory) {
                        toRemove = inst;
                        break;
                    }
                }
                if (toRemove != null) { 
                    dog.getAccessories().remove(toRemove);
                    dog.markAccessoriesDirty();
                }
            }
        this.accessoryInstance = null;
        return ret;
    }

}
