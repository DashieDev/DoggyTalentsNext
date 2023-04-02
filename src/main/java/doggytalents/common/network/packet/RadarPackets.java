package doggytalents.common.network.packet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import doggytalents.client.entity.render.world.RadarLocateRenderer;
import doggytalents.client.screen.ConductingBoneScreen;
import doggytalents.client.screen.RadarScreen;
import doggytalents.common.item.RadarItem;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.RadarData.RequestDogsData;
import doggytalents.common.network.packet.data.RadarData.RequestPosUpdateData;
import doggytalents.common.network.packet.data.RadarData.ResponseDogsData;
import doggytalents.common.network.packet.data.RadarData.ResponsePosUpdateData;
import doggytalents.common.network.packet.data.RadarData.StartLocatingData;
import doggytalents.common.storage.DogLocationStorage;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;

public class RadarPackets {
    
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
                if (!side.isServer()) return;
                
                var sender = ctx.get().getSender();
                var storage = 
                    DogLocationStorage.get(sender.level);
                var dogLs = 
                    storage.getDogs(sender, sender.level.dimension())
                    .filter(dogLoc -> dogLoc.shouldDisplay(sender.level, sender, InteractionHand.MAIN_HAND))
                    .map(
                        dogLoc -> Triple.of(
                            dogLoc.getDogId(), 
                            dogLoc.getDogName(), 
                            new BlockPos(dogLoc.getPos())
                        )
                    )
                    .collect(Collectors.toList());

                PacketHandler.send(
                    PacketDistributor.PLAYER.with(() -> sender), 
                    new ResponseDogsData(dogLs)
                );
    
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
                var name = payload.getMiddle();
                var pos = payload.getRight();
                if (uuid == null) uuid = Util.NIL_UUID; 
                if (name == null) name = "noname";
                if (pos == null) pos = BlockPos.ZERO;
                buf.writeUUID(uuid);
                buf.writeUtf(name);
                buf.writeBlockPos(pos);
            }
            
        }

        @Override
        public ResponseDogsData decode(FriendlyByteBuf buf) {
            int size = buf.readInt();
            var newDogsLs = new ArrayList<Triple<UUID, String, BlockPos>>(size);
            for (int i = 0; i < size; ++i) {
                UUID id = buf.readUUID();
                String name = buf.readUtf();
                BlockPos pos = buf.readBlockPos();
                var payload = Triple.of(id, name, pos);
                newDogsLs.add(payload);
            }
            return new ResponseDogsData(newDogsLs);
        }

        @Override
        public void handle(ResponseDogsData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                    Minecraft mc = Minecraft.getInstance();
                    if (mc.screen != null && mc.screen instanceof RadarScreen scr) {
                        scr.assignResponse(data.entries);
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }

    }

    public static class StartLocatingPacket implements IPacket<StartLocatingData> {
        @Override
        public void encode(StartLocatingData data, FriendlyByteBuf buf) {
            buf.writeUUID(data.uuid);
            buf.writeUtf(data.name);
            buf.writeBlockPos(data.pos);
        }

        @Override
        public StartLocatingData decode(FriendlyByteBuf buf) {
            var uuid = buf.readUUID();
            var name = buf.readUtf();
            var pos = buf.readBlockPos();
            return new StartLocatingData(uuid, name, pos);
        }

        @Override
        public void handle(StartLocatingData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                LogicalSide side = ctx.get().getDirection().getReceptionSide();

                if (!side.isServer()) return;
                var player = ctx.get().getSender();
                var stack = player.getMainHandItem();
                if (!(stack.getItem() instanceof RadarItem)) return;
                if (!stack.hasTag()) {
                    stack.setTag(new CompoundTag());
                }
                var tag = stack.getTag();
                if (tag == null) return;
                tag.putUUID("uuid", data.uuid);
                tag.putString("name", data.name);
                tag.putInt("posX", data.pos.getX());
                tag.putInt("posY",  data.pos.getY());
                tag.putInt("posZ",  data.pos.getZ());
            });

            ctx.get().setPacketHandled(true);
        }
    }

    public static class RequestPosUpdatePacket implements IPacket<RequestPosUpdateData> {

        @Override
        public void encode(RequestPosUpdateData data,
                FriendlyByteBuf buf) {
            buf.writeUUID(data.uuid);
            buf.writeBlockPos(data.pos);
        }

        @Override
        public RequestPosUpdateData decode(FriendlyByteBuf buf) {
            return new RequestPosUpdateData(buf.readUUID(), buf.readBlockPos());
        }

        @Override
        public void handle(RequestPosUpdateData data,
            Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                LogicalSide side = ctx.get().getDirection().getReceptionSide();
                if (!side.isServer()) return;

                var sender = ctx.get().getSender();
                var storage = 
                    DogLocationStorage.get(sender.level);
                
                var entry = storage.getData(data.uuid);
                if (entry == null) return;

                var owner_id = entry.getOwnerId();
                if (owner_id == null) return;
                if (!owner_id.equals(sender.getUUID()))
                    return;

                var correct_pos = entry.getPos();
                if (correct_pos == null) return;
                var correct_blockpos = new BlockPos(correct_pos);

                if (correct_blockpos.distSqr(data.pos) < 4) return;

                PacketHandler.send(
                    PacketDistributor.PLAYER.with(() -> sender), 
                    new ResponsePosUpdateData(data.uuid, correct_blockpos)
                );
    
            });
    
            ctx.get().setPacketHandled(true);
            
        }
        
    }

    public static class ResponsePosUpdatePacket implements IPacket<ResponsePosUpdateData> {

        @Override
        public void encode(ResponsePosUpdateData data, FriendlyByteBuf buf) {
            buf.writeUUID(data.uuid);
            buf.writeBlockPos(data.correctPos);
        }

        @Override
        public ResponsePosUpdateData decode(FriendlyByteBuf buf) {
            var uuid = buf.readUUID();
            var pos = buf.readBlockPos();
            return new ResponsePosUpdateData(uuid, pos);
        }

        @Override
        public void handle(ResponsePosUpdateData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                LogicalSide side = ctx.get().getDirection().getReceptionSide();
                if (!side.isClient()) return;
                
                RadarLocateRenderer.correctPos(data.uuid, data.correctPos);
            });

            ctx.get().setPacketHandled(true);
        }

    }




}
