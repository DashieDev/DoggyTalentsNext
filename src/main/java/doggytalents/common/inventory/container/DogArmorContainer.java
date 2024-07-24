package doggytalents.common.inventory.container;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import com.mojang.datafixers.util.Pair;

import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyTalents;
import doggytalents.api.forge_imitate.inventory.SlotItemHandler;
import doggytalents.api.impl.DogArmorItemHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.inventory.PackPuppyItemHandler;
import doggytalents.common.inventory.container.slot.DogInventorySlot;
import doggytalents.common.talent.DoggyArmorTalent;
import doggytalents.common.talent.PackPuppyTalent;
import doggytalents.common.util.Util;
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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DogArmorContainer extends AbstractContainerMenu {
    private Level world;
    private Player player;
    private Dog dog;

    public static final ResourceLocation BLOCK_ATLAS = Util.getVanillaResource("textures/atlas/blocks.png");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = Util.getVanillaResource("item/empty_armor_slot_helmet");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = Util.getVanillaResource("item/empty_armor_slot_chestplate");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = Util.getVanillaResource("item/empty_armor_slot_leggings");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = Util.getVanillaResource("item/empty_armor_slot_boots");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = Util.getVanillaResource("item/empty_armor_slot_shield");
    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    //Server method
    public DogArmorContainer(int windowId, Inventory playerInventory, Dog dog) {
        super(DoggyContainerTypes.DOG_ARMOR.get(), windowId);
        this.world = playerInventory.player.level();
        this.player = playerInventory.player;
        this.dog = dog;

        var dogArmors = dog.dogArmors();
        
        //TODO 3 -> 4
        for (int i = 0; i < 2; ++i) {  
            final EquipmentSlot equipmentslot = SLOT_IDS[i];
            var dogSlot = DogArmorItemHandler.DogArmorSlots.byEquipment(equipmentslot);
            this.addSlot(new SlotItemHandler(dogArmors, dogSlot.slotId, 17, 27 + i * 18) {
                public void set(ItemStack p_219985_) {
                    var itemstack = this.getItem();
                    super.set(p_219985_);
                    dog.onEquipItem(equipmentslot, itemstack, p_219985_);
                }
    
                public int getMaxStackSize() {
                   return 1;
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
                }
    
             });
        }

        for (int i = 2; i < 4; ++i) {  
            final EquipmentSlot equipmentslot = SLOT_IDS[i];
            var dogSlot = DogArmorItemHandler.DogArmorSlots.byEquipment(equipmentslot);
            this.addSlot(new SlotItemHandler(dogArmors, dogSlot.slotId, 138, 27 + (i-2) * 18) {

                @Override
                public void set(ItemStack p_219985_) {
                    var itemstack = this.getItem();
                    super.set(p_219985_);
                    dog.onEquipItem(equipmentslot, itemstack, p_219985_);
                }
                
                @Override
                public int getMaxStackSize() {
                   return 1;
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
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
            else if (!moveItemStackTo(itemstack1, 0, 4, false)) {
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
        return dog.isDoingFine()
            && dog.getDogLevel(DoggyTalents.DOGGY_ARMOR.get()) > 0;
    }

    public Dog getDog() {
        return this.dog;
    }
}
