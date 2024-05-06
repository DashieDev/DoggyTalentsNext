package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.DogMountData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

/*
 * When dog dismount or mount from player, other players recieve the updated 
 * info and dismount the dog in their side via 
 * net.minecraft.server.level.ChunkMap$TrackedEntity
 * Foreach time an entity broadcast an update, it will send every connection in its
 * "seenBy" list field. However, the instance tracking the player doesn't count that player
 * itself as a "seenBy" entry. So when a dog dismount/mount to a player, it won't send update to 
 * that player, which cause the dog to be desynchronized. The solution is to explicitly send a packet
 * to that player when the dog dismount from them serverside, so the client can dismount/mount
 *  the dog there. Also startRiding and stopRiding is only meant to be called serverside.
 */
public class DogMountPacket implements IPacket<DogMountData> {

    @Override
    public void encode(DogMountData data, FriendlyByteBuf buf) {
        buf.writeInt(data.dogId);
        buf.writeBoolean(data.mount);
    }

    @Override
    public DogMountData decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        boolean mount = buf.readBoolean();
        return new DogMountData(id, mount);
    }

    @Override
    public void handle(DogMountData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {

            if (ctx.get().isClientRecipent()) { 
                ClientEventHandler.onDogMountEvent(data);
            }

        });

        ctx.get().setPacketHandled(true);
    }
    
}
