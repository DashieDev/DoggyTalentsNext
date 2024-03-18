package doggytalents.forge_imitate.network;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import doggytalents.common.util.Util;
import doggytalents.forge_imitate.network.ForgeNetworkHandler.NetworkEvent.Context;
import doggytalents.forge_imitate.network.PacketDistributor.PacketTarget;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;

public class ForgeNetworkHandler {

    public static ResourceLocation CHANNEL_ID = Util.getResource("channel");
    private static Map<Integer, PacketCodec<?>> PACKET_MAP = Maps.newHashMap(); 
    private static Map<Class<?>, Integer> DATACLASS_ID_MAP = Maps.newHashMap();

    public synchronized <D> void registerMessage(int id, Class<D> dataClass, 
        BiConsumer<D, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, D> decoder, 
        BiConsumer<D, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_MAP.put(id, new PacketCodec<>(dataClass, encoder, decoder, messageConsumer));
        DATACLASS_ID_MAP.put(dataClass, id);
    }

    public <D> void send(PacketTarget<?> target, D data) {
        var dataId = DATACLASS_ID_MAP.get(data.getClass());
        if (dataId == null)
            return;
        var codec = (PacketCodec<D>) PACKET_MAP.get(dataId);
        if (codec == null)
            return;
        var buf = PacketByteBufs.create();
        buf.writeInt(dataId);
        codec.encoder.accept(data, buf);
        target.sendPacket(buf, CHANNEL_ID);
    }

    private static class PacketCodec<T> {

        public final Class<T> dataClass;
        public final BiConsumer<T, FriendlyByteBuf> encoder;
        public final Function<FriendlyByteBuf, T> decoder;
        public final BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer;
        
        PacketCodec(Class<T> dataClass, 
            BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, 
            BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
            this.dataClass = dataClass;
            this.encoder = encoder;
            this.decoder = decoder;
            this.messageConsumer = messageConsumer;
        }

        public void decodeAndConsume(FriendlyByteBuf buf, Context ctx) {
            T data = decoder.apply(buf);
            this.messageConsumer.accept(data, () -> ctx);
        }

    }

    public static class NetworkEvent {
        public static class Context {
            
            private @Nullable ServerPlayer sender;
            private BlockableEventLoop<?> executor;
            private boolean isClientRecipent = false;
            
            public void enqueueWork(Runnable runnable) {
                executor.execute(runnable);
            }

            public @Nullable ServerPlayer getSender() {
                return this.sender;
            }

            public boolean isClientRecipent() {
                return this.isClientRecipent;
            }

            public boolean isServerRecipent() {
                return !this.isClientRecipent;
            }

            public void setPacketHandled(boolean val) {}

        }
    }

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(
            CHANNEL_ID, 
            (server, player, handler, buff, response) -> {
                int id = buff.readInt();
                var packet = PACKET_MAP.get(id);
                if (packet == null)
                    return;
                var ctx = new Context();
                ctx.sender = player;
                ctx.executor = server;
                packet.decodeAndConsume(buff, ctx);
            }
        );
    }

    public static void onToClientPacket(BlockableEventLoop<?> exec, FriendlyByteBuf buf) {
        int id = buf.readInt();
        var packet = ForgeNetworkHandler.PACKET_MAP.get(id);
        if (packet == null)
            return;
        var ctx = new Context();
        ctx.sender = null;
        ctx.isClientRecipent = true;
        ctx.executor = exec;
        packet.decodeAndConsume(buf, ctx);
    }
}
