package doggytalents.common.network.packet;

import java.util.ArrayList;
import java.util.function.Supplier;

import doggytalents.client.screen.DogNewInfoScreen.DogNewInfoScreen;
import doggytalents.client.screen.DogNewInfoScreen.store.UIActionTypes;
import doggytalents.client.screen.DogNewInfoScreen.store.slice.ActiveTabSlice;
import doggytalents.client.screen.framework.Store;
import doggytalents.client.screen.framework.UIAction;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogGroupsManager;
import doggytalents.common.entity.DogGroupsManager.DogGroup;
import doggytalents.common.network.packet.data.DogGroupsData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;

public class DogGroupPackets {
    
    public static class EDIT extends DogPacket<DogGroupsData.EDIT> {

        @Override
        public void encode(DogGroupsData.EDIT data, FriendlyByteBuf buf) {
            super.encode(data, buf);
            buf.writeBoolean(data.add);
            buf.writeUtf(data.group.name, DogGroupsManager.MAX_GROUP_STRLEN);
            buf.writeInt(data.group.color);
        }

        @Override
        public DogGroupsData.EDIT decode(FriendlyByteBuf buf) {
            int id = buf.readInt();
            boolean add = buf.readBoolean();
            String group = buf.readUtf(DogGroupsManager.MAX_GROUP_STRLEN);
            int color = buf.readInt();
            return new DogGroupsData.EDIT(id, new DogGroup(group, color), add);
        }

        @Override
        public void handleDog(Dog dogIn, DogGroupsData.EDIT data,
                Supplier<Context> ctx) {
            if (dogIn.level().isClientSide) return;
            var sender = ctx.get().getSender();
            if (!dogIn.canInteract(sender)) return;
            var groups = dogIn.getGroups();
            boolean update = false;
            if (data.add) {
                update = groups.add(data.group);
            } else {
                update = groups.remove(data.group);
            }

            if (update)
            PacketHandler.send(
                PacketDistributor.PLAYER.with(() -> sender), 
                new DogGroupsData.UPDATE(dogIn.getId(), new ArrayList<>(groups.getGroupsReadOnly()))
            );
        }
        
    }

    public static class FETCH_REQUEST extends DogPacket<DogGroupsData.FETCH_REQUEST> {

        @Override
        public DogGroupsData.FETCH_REQUEST decode(FriendlyByteBuf buf) {
            return new DogGroupsData.FETCH_REQUEST(buf.readInt());
        }

        @Override
        public void handleDog(Dog dogIn, DogGroupsData.FETCH_REQUEST data,
                Supplier<Context> ctx) {
            if (dogIn.level().isClientSide) return;
            var groups = dogIn.getGroups();
            var sender = ctx.get().getSender();
            PacketHandler.send(
                PacketDistributor.PLAYER.with(() -> sender), 
                new DogGroupsData.UPDATE(dogIn.getId(), new ArrayList<>(groups.getGroupsReadOnly()))
            );
        }
        
    }

    public static class UPDATE implements IPacket<DogGroupsData.UPDATE> {

        @Override
        public void encode(DogGroupsData.UPDATE data, FriendlyByteBuf buf) {
            buf.writeInt(data.dogId);
            buf.writeInt(data.groups.size());
            for (var group : data.groups) {
                buf.writeUtf(group.name, DogGroupsManager.MAX_GROUP_STRLEN);
                buf.writeInt(group.color);
            }
        }

        @Override
        public DogGroupsData.UPDATE decode(FriendlyByteBuf buf) {
            int id = buf.readInt();
            int size = buf.readInt();
            var groups = new ArrayList<DogGroup>(size);
            for (int i = 0; i < size; ++i) {
                String group = buf.readUtf(DogGroupsManager.MAX_GROUP_STRLEN);
                int color = buf.readInt();
                groups.add(new DogGroup(group, color));
            }
            return new DogGroupsData.UPDATE(id, groups);
        }

        @Override
        public void handle(DogGroupsData.UPDATE data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (!ctx.get().getDirection().getReceptionSide().isClient()) return;
                var mc = Minecraft.getInstance();
                var level = mc.level;
                if (level == null) return;
                var e = level.getEntity(data.dogId);
                if (!(e instanceof Dog d)) return;
                var groups = d.getGroups();
                groups.clear();
                for (var group : data.groups) {
                    groups.add(group);
                }

                ActiveTabSlice.dispatchGroupUpdates();
            });

            ctx.get().setPacketHandled(true);
        }
        
    }

}
