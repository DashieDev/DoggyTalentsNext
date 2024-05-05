package doggytalents.forge_imitate.network;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PacketDistributor<T> {

    public static PacketDistributor<Void> SERVER = new PacketDistributor<>(new ToServerPacketTarget());
    public static PacketDistributor<Entity> TRACKING_ENTITY = new PacketDistributor<>(
        new ToClientPacketTarget<Entity>(PlayerLookup::tracking));
    public static PacketDistributor<ServerPlayer> PLAYER = new PacketDistributor<>(
        new ToClientSinglePacketTarget()
    );

    //TO DO
    public static PacketDistributor<Entity> TRACKING_ENTITY_AND_SELF = new PacketDistributor<>(
        new ToClientPacketTarget<Entity>(PlayerLookup::tracking));

    private PacketTarget<T> defaultTarget;
    
    PacketDistributor(PacketTarget<T> target) {
        this.defaultTarget = target;
    }

    public PacketTarget<T> noArg() {
        return this.defaultTarget.acceptArg(null);
    }

    public PacketTarget<T> with(Supplier<T> arg) {
        return this.defaultTarget.acceptArg(arg.get());
    }
    
    public static abstract class PacketTarget<T> {
        
        public static enum Type { TO_CLIENT, TO_SERVER }
        protected Type type;
        
        public abstract void sendPacket(CustomPacketPayload payload);

        public Type getType() { return type; }

        public PacketTarget<T> acceptArg(T arg) {
            return this;
        }

    }

    public static class ToClientPacketTarget<T> extends PacketTarget<T> {
        
        private final Function<T, Collection<ServerPlayer>> playerListSup;
        private T arg;

        public ToClientPacketTarget(Function<T, Collection<ServerPlayer>> playerListSup) {
            this.type = Type.TO_CLIENT;
            this.playerListSup = playerListSup;
        }

        @Override
        public void sendPacket(CustomPacketPayload payload) {
            var playerList = playerListSup.apply(arg);
            for (var player : playerList) {
                ServerPlayNetworking.send(player, payload);
            }
        }

        @Override
        public PacketTarget<T> acceptArg(T arg) {
            var ret = new ToClientPacketTarget<T>(this.playerListSup);
            ret.arg = arg;
            return ret;
        }

    }

    public static class ToClientSinglePacketTarget extends PacketTarget<ServerPlayer> {
        
        private ServerPlayer arg;

        public ToClientSinglePacketTarget() {
            this.type = Type.TO_CLIENT;
        }

        @Override
        public void sendPacket(CustomPacketPayload payload) {
            ServerPlayNetworking.send(arg, payload);
        }

        @Override
        public PacketTarget<ServerPlayer> acceptArg(ServerPlayer arg) {
            var ret = new ToClientSinglePacketTarget();
            ret.arg = arg;
            return ret;
        }

    }

    public static class ToServerPacketTarget extends PacketTarget<Void> {

        private final Consumer<CustomPacketPayload> clientSender = 
            (p) -> {
                ClientPlayNetworking.send(p);
            };
        
        public ToServerPacketTarget() {
            this.type = Type.TO_SERVER;
        }

        @Override
        public void sendPacket(CustomPacketPayload payload) {
            clientSender.accept(payload);
        }

        @Override
        public PacketTarget<Void> acceptArg(Void arg) {
            return this;
        }
    }

}
