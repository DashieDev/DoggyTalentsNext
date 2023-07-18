package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.network.packet.data.ChangeArtifactData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class ChangeArtifactPacket extends DogPacket<ChangeArtifactData> {

    @Override
    public void encode(ChangeArtifactData data, FriendlyByteBuf buf) {
        buf.writeInt(data.entityId);
        buf.writeBoolean(data.add);
        buf.writeInt(data.slotId);
    }
    
    @Override
    public ChangeArtifactData decode(FriendlyByteBuf buf) {
        int dog_id = buf.readInt();
        boolean add =buf.readBoolean();
        int slot_id = buf.readInt();
        return new ChangeArtifactData(dog_id, add, slot_id);
    }

    @Override
    public void handleDog(Dog dog, ChangeArtifactData data, Supplier<Context> ctx) {
        
        var sender = ctx.get().getSender();
        if (!dog.canInteract(sender)) return;
        if (data.add) {
            var inventory = sender.getInventory();
            var items = inventory.items;
            if (data.slotId < 0 || data.slotId >= items.size()) return;
            var item = items.get(data.slotId);
            if (item.getItem() instanceof DoggyArtifactItem artifact) {
                if (dog.addArtifact(artifact)) {
                    dog.consumeItemFromStack(dog, item);
                }
            }
        } else {
            var artifacts = dog.getArtifactsList();
            if (data.slotId < 0 || data.slotId >= artifacts.size()) return;
            var toRemove = artifacts.get(data.slotId);
            if (toRemove == null) return;
            var inventory = sender.getInventory();
            if (inventory.getFreeSlot() < 0) return;
            var retItem = new ItemStack(toRemove);
        
            inventory.add(retItem);
            dog.removeArtifact(data.slotId);
            dog.markArtifactsDirty();
        }

    }
    
}
