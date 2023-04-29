package doggytalents.common.network.packet;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.DoggyItems;
import doggytalents.client.screen.ConductingBoneScreen;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.ConductingBoneData.ResponseDogsData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.util.DogUtil;

import static doggytalents.common.network.packet.data.ConductingBoneData.*;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;

/**
 * @Author DashieDev
 */
public class ConductingBonePackets {
    public static class RequestDogsPacket implements IPacket<RequestDogsData> {

        @Override
        public void encode(RequestDogsData data,
                FriendlyByteBuf buf) {
        }

        @Override
        public RequestDogsData decode(FriendlyByteBuf buf) {
            return new RequestDogsData();
        }

        @Override
        public void handle(RequestDogsData data,
            Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                LogicalSide side = ctx.get().getDirection().getReceptionSide();
    
                if (side.isServer()) {
                    var sender = ctx.get().getSender();
                    var storage = 
                        DogLocationStorage.get(sender.level);
                    var dogLs = 
                        storage.getDogs(sender, sender.level.dimension())
                        .map(dogLoc -> Pair.of(dogLoc.getDogId(), dogLoc.getDogName()))
                        .collect(Collectors.toList());

                    PacketHandler.send(
                        PacketDistributor.PLAYER.with(() -> sender), 
                        new ResponseDogsData(dogLs)
                    );
                }
    
            });
    
            ctx.get().setPacketHandled(true);
            
        }
        
    }

    public static class ResponseDogsPackets implements IPacket<ResponseDogsData> {

        @Override
        public void encode(ResponseDogsData data, FriendlyByteBuf buf) {
            int size = data.entries.size();
            buf.writeInt(size);
            for (int i = 0; i < size; ++i) {
                var payload = data.entries.get(i);
                var uuid = payload.getLeft();
                var name = payload.getRight();
                if (uuid == null) uuid = Util.NIL_UUID; 
                if (name == null) name = "noname"; 
                buf.writeUUID(uuid);
                buf.writeUtf(name);
            }
            
        }

        @Override
        public ResponseDogsData decode(FriendlyByteBuf buf) {
            int size = buf.readInt();
            var newDogsLs = new ArrayList<Pair<UUID, String>>(size);
            for (int i = 0; i < size; ++i) {
                UUID id = buf.readUUID();
                String name = buf.readUtf();
                var payload = Pair.of(id, name);
                newDogsLs.add(payload);
            }
            return new ResponseDogsData(newDogsLs);
        }

        @Override
        public void handle(ResponseDogsData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                    Minecraft mc = Minecraft.getInstance();
                    if (mc.screen != null && mc.screen instanceof ConductingBoneScreen scr) {
                        scr.assignResponse(data.entries);
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }

    }

    public static class RequestDistantTeleportDogPacket implements IPacket<RequestDistantTeleportDogData> {

        @Override
        public void encode(RequestDistantTeleportDogData data, FriendlyByteBuf buf) {
            buf.writeUUID(data.dogUUID);
            buf.writeBoolean(data.toBed);
        }

        @Override
        public RequestDistantTeleportDogData decode(FriendlyByteBuf buf) {
            var uuid = buf.readUUID();
            var toBed = buf.readBoolean();
            return new RequestDistantTeleportDogData(uuid, toBed);
        }

        @Override
        public void handle(RequestDistantTeleportDogData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                LogicalSide side = ctx.get().getDirection().getReceptionSide();
    
                if (side.isServer()) {
                    var sender = ctx.get().getSender();

                    //Make sure the owner actually have the item in hand serverside.
                    var item = sender.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                    if (item != DoggyItems.CONDUCTING_BONE.get()) return;

                    //And is not on cooldown
                    if (sender.getCooldowns().isOnCooldown(DoggyItems.CONDUCTING_BONE.get())) return;

                    var uuid = data.dogUUID;
                    if (uuid == null) return; 
                    var storage = 
                        DogLocationStorage.get(sender.level);
                    var dogData = storage.getData(uuid);
                    
                    //not exist and make sure it is the same owner serverside.
                    if (dogData == null) return;
                    if (!sender.getUUID().equals(dogData.getOwnerId())) return;

                    if (!data.toBed) {
                        DogUtil.attemptToTeleportDogNearbyOrSendPromise(uuid, sender);
                    } else {
                        if (sender.level instanceof ServerLevel sL) {
                            var e = sL.getEntity(data.dogUUID);
                            if (e instanceof Dog d) {
                                DogUtil.attemptToTeleportDogToBedOrSendPromise(d);
                            }
                        }
                    }

                    sender.getCooldowns().addCooldown(DoggyItems.CONDUCTING_BONE.get(), 20);

                }

            });

            ctx.get().setPacketHandled(true);
        }

    }
}
