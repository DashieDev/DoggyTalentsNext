package doggytalents.common.network.packet;

import java.util.ArrayList;
import java.util.function.Supplier;

import doggytalents.DoggyItems;
import doggytalents.DoggySounds;
import doggytalents.client.screen.HeelByGroupScreen;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogGroupsManager;
import doggytalents.common.entity.DogGroupsManager.DogGroup;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.HeelByGroupData;
import doggytalents.common.util.DogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;

public class HeelByGroupPackets {
    
    public static class REQUEST_GROUP_LIST implements IPacket<HeelByGroupData.REQUEST_GROUP_LIST> {

        @Override
        public void encode(HeelByGroupData.REQUEST_GROUP_LIST data,
                FriendlyByteBuf buf) {
        }

        @Override
        public HeelByGroupData.REQUEST_GROUP_LIST decode(FriendlyByteBuf buf) {
            return new HeelByGroupData.REQUEST_GROUP_LIST();
        }

        @Override
        public void handle(HeelByGroupData.REQUEST_GROUP_LIST data,
                Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                var side = ctx.get().getDirection().getReceptionSide();
                if (!side.isServer()) return;
                var sender = ctx.get().getSender();

                var dogsList = sender.level.getEntitiesOfClass(
                    Dog.class, 
                    sender.getBoundingBox().inflate(100D, 50D, 100D), 
                    dog -> dog.isAlive() && !dog.isDefeated() && dog.isOwnedBy(sender)
                );

                var groups_response = new ArrayList<DogGroup>();
                for (var dog : dogsList) {
                    var groups = dog.getGroups().getGroupsReadOnly();
                    for (var group : groups) {
                        if (!groups_response.contains(group)) {
                            groups_response.add(group);
                        }
                    }
                }

                PacketHandler.send(
                    PacketDistributor.PLAYER.with(() -> sender), 
                    new HeelByGroupData.RESPONSE_GROUP_LIST(groups_response)
                );
    
            });
    
            ctx.get().setPacketHandled(true);
        }

    }

    public static class RESPONSE_GROUP_LIST implements IPacket<HeelByGroupData.RESPONSE_GROUP_LIST> {

        @Override
        public void encode(HeelByGroupData.RESPONSE_GROUP_LIST data,
                FriendlyByteBuf buf) {
            int size = data.groups.size();
            buf.writeInt(size);
            for (int i = 0; i < size; ++i) {
                var group = data.groups.get(i);
                buf.writeInt(group.color);
                buf.writeUtf(group.name, DogGroupsManager.MAX_GROUP_STRLEN);
            }
        }

        @Override
        public HeelByGroupData.RESPONSE_GROUP_LIST decode(FriendlyByteBuf buf) {
            int size = buf.readInt();
            var groups = new ArrayList<DogGroup>(size);
            for (int i = 0; i < size; ++i) {
                int color = buf.readInt();
                String name = buf.readUtf(DogGroupsManager.MAX_GROUP_STRLEN);
                groups.add(new DogGroup(name, color));
            }

            return new HeelByGroupData.RESPONSE_GROUP_LIST(groups);
        }

        @Override
        public void handle(HeelByGroupData.RESPONSE_GROUP_LIST data,
                Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {

                if (ctx.get().getDirection().getReceptionSide().isClient()) { 
                    var mc = Minecraft.getInstance();
                    var screen = mc.screen;
                    if (screen instanceof HeelByGroupScreen groupScreen) {
                        groupScreen.assignResponse(data.groups);
                    }
                }

            });

            ctx.get().setPacketHandled(true);
        }
    }

    public static class REQUEST_HEEL implements IPacket<HeelByGroupData.REQUEST_HEEL> {

        @Override
        public void encode(HeelByGroupData.REQUEST_HEEL data,
            FriendlyByteBuf buf) {
            buf.writeBoolean(data.heelAndSit);
            var group = data.group;
            buf.writeInt(group.color);
            buf.writeUtf(group.name, DogGroupsManager.MAX_GROUP_STRLEN);
        }

        @Override
        public HeelByGroupData.REQUEST_HEEL decode(FriendlyByteBuf buf) {
            boolean heelAndSit = buf.readBoolean();
            int color = buf.readInt();
            String name = buf.readUtf(DogGroupsManager.MAX_GROUP_STRLEN);
            return new HeelByGroupData.REQUEST_HEEL(new DogGroup(name, color), heelAndSit);
        }

        @Override
        public void handle(HeelByGroupData.REQUEST_HEEL data,
            Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                var side = ctx.get().getDirection().getReceptionSide();
                if (!side.isServer()) return;
                var sender = ctx.get().getSender();
                if (sender.getCooldowns().isOnCooldown(DoggyItems.WHISTLE.get())) return;

                var dogs = sender.level.getEntitiesOfClass(
                    Dog.class, 
                    sender.getBoundingBox().inflate(100D, 50D, 100D), 
                    dog -> dog.isAlive() && !dog.isDefeated() && dog.isOwnedBy(sender)
                        && dog.getGroups().isGroup(data.group)
                );

                for (var dog : dogs) {
                    if (dog.isPassenger()) dog.stopRiding();
                    dog.clearTriggerableAction();
                    dog.setOrderedToSit(data.heelAndSit);
                }

                DogUtil.dynamicSearchAndTeleportToOwnwerInBatch(
                    sender.level, dogs, sender, 3
                );

                if (ConfigHandler.WHISTLE_SOUNDS)
                sender.level.playSound(null, sender.blockPosition(), DoggySounds.WHISTLE_LONG.get(), SoundSource.PLAYERS, 0.6F + sender.level.random.nextFloat() * 0.1F, 0.4F + sender.level.random.nextFloat() * 0.2F);
                sender.sendSystemMessage(Component.translatable("dogcommand.heel_by_group", 
                    Component.literal(data.group.name)
                    .withStyle(
                        Style.EMPTY
                        .withBold(true)
                        .withColor(data.group.color)
                    )
                ));
                sender.getCooldowns().addCooldown(DoggyItems.WHISTLE.get(), 40);    
            });
    
            ctx.get().setPacketHandled(true);
            
        }
    }

}
