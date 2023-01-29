package doggytalents.common.inventory.container;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;

import com.google.errorprone.annotations.Var;
import com.mojang.datafixers.util.Pair;

import doggytalents.DoggyAccessoryTypes;
import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyTalents;
import doggytalents.api.registry.AccessoryType;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.inventory.container.slot.DogAccessorySlot;
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

public class DogAccessoriesContainer extends AbstractContainerMenu {

    private static final AccessoryType[] TYPES = {
        DoggyAccessoryTypes.BAND.get(), DoggyAccessoryTypes.COLLAR.get(), 
        DoggyAccessoryTypes.CLOTHING.get(), DoggyAccessoryTypes.GLASSES.get()
    };

    private Dog dog;

    public DogAccessoriesContainer(int windowId, Inventory playerInventory, Dog dog) {
        super(DoggyContainerTypes.DOG_ACCESSORIES.get(), windowId);
        this.dog = dog;

        for (int i = 0; i < 2; ++i) {  
            final AccessoryType accessoryType = TYPES[i];
            this.addSlot(new DogAccessorySlot(dog, i, 17, 27 + i * 18, accessoryType));
        }

        for (int i = 2; i < 4; ++i) {  
            final AccessoryType accessoryType = TYPES[i];
            this.addSlot(new DogAccessorySlot(dog, i, 138, 27 + (i-2) * 18, accessoryType));
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
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
    
}
