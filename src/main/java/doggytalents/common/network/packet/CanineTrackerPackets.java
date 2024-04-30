package doggytalents.common.network.packet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import doggytalents.client.entity.render.world.CanineTrackerLocateRenderer;
import doggytalents.client.screen.ConductingBoneScreen;
import doggytalents.client.screen.CanineTrackerScreen;
import doggytalents.common.item.CanineTrackerItem;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.CanineTrackerData.RequestDogsData;
import doggytalents.common.network.packet.data.CanineTrackerData.RequestPosUpdateData;
import doggytalents.common.network.packet.data.CanineTrackerData.ResponseDogsData;
import doggytalents.common.network.packet.data.CanineTrackerData.ResponsePosUpdateData;
import doggytalents.common.network.packet.data.CanineTrackerData.StartLocatingData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.util.ItemUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.PacketDistributor;

public class CanineTrackerPackets {
    
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
                //LogicalSide side = ctx.get().getDirection().getReceptionSide();
                if (!ctx.get().isServerRecipent()) return;
                
                var sender = ctx.get().getSender();
                var storage = 
                    DogLocationStorage.get(sender.level());
                var dogLs = 
                    storage.getDogs(sender, sender.level().dimension())
                    .filter(dogLoc -> dogLoc.shouldDisplay(sender.level(), sender, InteractionHand.MAIN_HAND))
                    .map(
                        dogLoc -> Triple.of(
                            dogLoc.getDogId(), 
                            dogLoc.getDogName(), 
                            new BlockPos(Mth.floor(dogLoc.getPos().x), Mth.floor(dogLoc.getPos().y), Mth.floor(dogLoc.getPos().z))
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

                if (ctx.get().isClientRecipent()) { 
                    Minecraft mc = Minecraft.getInstance();
                    if (mc.screen != null && mc.screen instanceof CanineTrackerScreen scr) {
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
        }

        @Override
        public StartLocatingData decode(FriendlyByteBuf buf) {
            var uuid = buf.readUUID();
            return new StartLocatingData(uuid);
        }

        @Override
        public void handle(StartLocatingData data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                //LogicalSide side = ctx.get().getDirection().getReceptionSide();

                if (!ctx.get().isServerRecipent()) return;
                var player = ctx.get().getSender();
                var stack = player.getMainHandItem();
                if (!(stack.getItem() instanceof CanineTrackerItem)) return;
                
                var storage = DogLocationStorage.get(player.level());
                var dogData = storage.getData(data.uuid);
                if (dogData == null) return;
                
                // if (!stack.hasTag()) {
                //     stack.setTag(new CompoundTag());
                // }
                var tag = ItemUtil.getTag(stack);
                if (tag == null) return;

                var pos = BlockPos.containing(dogData.getPos());
                tag.putUUID("uuid", data.uuid);
                tag.putString("name", dogData.getDogName());
                tag.putInt("posX", pos.getX());
                tag.putInt("posY",  pos.getY());
                tag.putInt("posZ",  pos.getZ());
                tag.putInt("locateColor", dogData.getLocateColor());
                ItemUtil.putTag(stack, tag);
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
                //LogicalSide side = ctx.get().getDirection().getReceptionSide();
                if (!ctx.get().isServerRecipent()) return;

                var sender = ctx.get().getSender();

                var stack = sender.getMainHandItem();
                if (!(stack.getItem() instanceof CanineTrackerItem)) return;

                var storage = 
                    DogLocationStorage.get(sender.level());
                
                var entry = storage.getData(data.uuid);
                if (entry == null) return;

                var owner_id = entry.getOwnerId();
                if (owner_id == null) return;
                if (!owner_id.equals(sender.getUUID()))
                    return;

                var correct_pos = entry.getPos();
                if (correct_pos == null) return;
                var correct_blockpos = new BlockPos(Mth.floor(correct_pos.x), Mth.floor(correct_pos.y), Mth.floor(correct_pos.z));

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

                //LogicalSide side = ctx.get().getDirection().getReceptionSide();
                if (!ctx.get().isClientRecipent()) return;
                
                CanineTrackerLocateRenderer.correctPos(data.uuid, data.correctPos);
            });

            ctx.get().setPacketHandled(true);
        }

    }




}
