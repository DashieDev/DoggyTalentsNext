package doggytalents.common.network;

import java.util.function.Supplier;

import doggytalents.DoggyTalentsNext;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PacketDistributor<T> {

    public static PacketDistributor<Void> SERVER = new PacketDistributor<>(new ToServerPacketTarget());
    public static PacketDistributor<ServerPlayer> PLAYER = new PacketDistributor<>(
        new ToClientSinglePacketTarget()
    );

    public static PacketDistributor<Entity> TRACKING_ENTITY
        = new PacketDistributor<>(new ToClientTrackingEntityTarget());
    public static PacketDistributor<Entity> TRACKING_ENTITY_AND_SELF
        = new PacketDistributor<>(new ToClientTrackingEntityAndSelfTarget());
    
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
        
        public abstract void sendPacket(Object data);

        public Type getType() { return type; }

        public PacketTarget<T> acceptArg(T arg) {
            return this;
        }

    }

    public static class ToServerPacketTarget extends PacketTarget<Void> {
        
        public ToServerPacketTarget() {
            this.type = Type.TO_SERVER;
        }

        @Override
        public void sendPacket(Object payload) {
            DTNNetworkHandler.HANDLER.send(payload, net.minecraftforge.network.PacketDistributor.SERVER.noArg());
        }

        @Override
        public PacketTarget<Void> acceptArg(Void arg) {
            return this;
        }
    }

    public static class ToClientSinglePacketTarget extends PacketTarget<ServerPlayer> {
        
        private ServerPlayer arg;

        public ToClientSinglePacketTarget() {
            this.type = Type.TO_CLIENT;
        }

        @Override
        public void sendPacket(Object payload) {
            if (arg == null)
                return;
            DTNNetworkHandler.HANDLER.send(payload, net.minecraftforge.network.PacketDistributor.PLAYER.with(arg));
        }

        @Override
        public PacketTarget<ServerPlayer> acceptArg(ServerPlayer arg) {
            var ret = new ToClientSinglePacketTarget();
            ret.arg = arg;
            return ret;
        }

    }

    public static class ToClientTrackingEntityTarget extends PacketTarget<Entity> {

        private Entity arg;

        public ToClientTrackingEntityTarget() {
            this.type = Type.TO_CLIENT;
        }

        @Override
        public void sendPacket(Object payload) {
            if (arg == null)
                return;
            DTNNetworkHandler.HANDLER.send(payload, net.minecraftforge.network.PacketDistributor.TRACKING_ENTITY.with(arg));
        }

        @Override
        public PacketTarget<Entity> acceptArg(Entity arg) {
            var ret = new ToClientTrackingEntityTarget();
            ret.arg = arg;
            return ret;
        }
        
    }

    public static class ToClientTrackingEntityAndSelfTarget extends PacketTarget<Entity> {

        private Entity arg;

        public ToClientTrackingEntityAndSelfTarget() {
            this.type = Type.TO_CLIENT;
        }

        @Override
        public void sendPacket(Object payload) {
            if (arg == null)
                return;
                DTNNetworkHandler.HANDLER.send(payload, net.minecraftforge.network.PacketDistributor.TRACKING_ENTITY_AND_SELF.with(arg));
        }

        @Override
        public PacketTarget<Entity> acceptArg(Entity arg) {
            var ret = new ToClientTrackingEntityAndSelfTarget();
            ret.arg = arg;
            return ret;
        }
        
    }

    

}
