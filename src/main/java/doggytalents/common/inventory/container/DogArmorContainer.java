package doggytalents.common.inventory.container;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;

import com.google.errorprone.annotations.Var;

import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.inventory.container.slot.DogInventorySlot;
import doggytalents.common.talent.DoggyArmorTalent;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DogArmorContainer extends AbstractContainerMenu {
    private Level world;
    private Player player;
    private Dog dog;
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, /*EquipmentSlot.LEGS,*/ EquipmentSlot.FEET};

    //Server method
    public DogArmorContainer(int windowId, Inventory playerInventory, Dog dog) {
        super(DoggyContainerTypes.DOG_ARMOR.get(), windowId);
        this.world = playerInventory.player.level;
        this.player = playerInventory.player;
        this.dog = dog;

        var dogArmors = dog.getTalent(DoggyTalents.DOGGY_ARMOR)
            .map((inst) -> inst.cast(DoggyArmorTalent.class).getArmors())
            .orElse(null);
        
        if (dogArmors == null) return;

        for (int i = 0; i < 3; ++i) {  
            final EquipmentSlot equipmentslot = SLOT_IDS[i];
            this.addSlot(new SlotItemHandler(dogArmors, i, 8, 17 + i * 18) {
                public void set(ItemStack p_219985_) {
                    var itemstack = this.getItem();
                        dog.setItemSlot(equipmentslot, p_219985_);
                        super.set(p_219985_);
                        dog.onEquipItem(equipmentslot, itemstack, p_219985_);
                }
    
                public int getMaxStackSize() {
                   return 1;
                }
    
                public boolean mayPlace(ItemStack p_39746_) {
                   return p_39746_.canEquip(equipmentslot, dog);
                }
    
             });
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 4) { // TODO will change when dog have 4 armor
                if (!moveItemStackTo(itemstack1, 4, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!moveItemStackTo(itemstack1, 0, 3, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public Dog getDog() {
        return this.dog;
    }
}
