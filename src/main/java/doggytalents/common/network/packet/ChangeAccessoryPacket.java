package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.api.registry.Accessory;
import doggytalents.common.entity.Dog;
import doggytalents.common.item.AccessoryItem;
import doggytalents.common.network.packet.data.ChangeAccessoriesData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class ChangeAccessoryPacket extends DogPacket<ChangeAccessoriesData> {

    @Override
    public void encode(ChangeAccessoriesData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
        buf.writeBoolean(data.add);
        buf.writeBoolean(data.wolfArmorSlot);
        buf.writeInt(data.slotId);
    }

    @Override
    public ChangeAccessoriesData decode(FriendlyByteBuf buf) {
        int dog_id = buf.readInt();
        boolean add =buf.readBoolean();
        boolean wolf_armor = buf.readBoolean();
        int slot_id = buf.readInt();
        return new ChangeAccessoriesData(dog_id, add, slot_id, wolf_armor);
    }

    @Override
    public void handleDog(Dog dog, ChangeAccessoriesData data, Supplier<Context> ctx) {
        var sender = ctx.get().getSender();
        if (!dog.canInteract(sender)) return;
        if (data.add) {
            var inventory = sender.getInventory();
            var items = inventory.items;
            if (data.slotId >= items.size()) return;
            var item = items.get(data.slotId);
            if (item.getItem() instanceof AccessoryItem accessoryItem) {
                var inst = accessoryItem.createInstance(dog, item, sender);
                if (inst == null) return;
                if (dog.addAccessory(inst)) {
                    dog.consumeItemFromStack(dog, item);
                }
                
                
            } else if (item.getItem() == Items.WOLF_ARMOR) {
                handleSetWolfArmor(sender, dog, item);
            }
        } else {
            if (data.wolfArmorSlot) {
                handleUnsetWolfArmor(sender, dog, data);
                return;
            }
            var accessories = dog.getAccessories();
            if (data.slotId >= accessories.size()) return;
            var toRemove = accessories.get(data.slotId);
            if (toRemove == null) return;
            var inventory = sender.getInventory();
            var retItem = toRemove.getReturnItem();
            if (retItem == null) return;
            if (inventory.getFreeSlot() < 0) return;

            inventory.add(toRemove.getReturnItem());
            dog.dogSyncedDataManager.accessories().remove(data.slotId);
            dog.dogSyncedDataManager.setAccessoriesDirty();
        }
    }

    private static void handleSetWolfArmor(ServerPlayer sender, Dog dog, ItemStack toConsume) {
        if (dog.hasWolfArmor())
            return;
        if (!toConsume.is(Items.WOLF_ARMOR))
            return;
        dog.setWolfArmor(toConsume.copyWithCount(1));
        dog.consumeItemFromStack(dog, toConsume);
    }

    private static void handleUnsetWolfArmor(ServerPlayer sender, Dog dog, ChangeAccessoriesData data) {
        if (!dog.hasWolfArmor())
            return;
        var inventory = sender.getInventory();
        if (inventory.getFreeSlot() < 0) return;

        dog.playSound(SoundEvents.ARMOR_UNEQUIP_WOLF);

        var wolf_armor0 = dog.wolfArmor();
        dog.setWolfArmor(ItemStack.EMPTY);
        inventory.add(wolf_armor0.copyWithCount(1));
    }
    
}
