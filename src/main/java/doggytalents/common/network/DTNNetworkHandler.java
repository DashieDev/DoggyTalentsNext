package doggytalents.common.network;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

import doggytalents.common.util.Util;
import doggytalents.forge_imitate.network.PacketDistributor.PacketTarget;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import doggytalents.common.lib.Constants;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class DTNNetworkHandler {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/network");

    public static final CustomPacketPayload.Type<DTNNetworkPayload<?>> CHANNEL_ID =
        new CustomPacketPayload.Type<>(Util.getResource("payload_channel"));
    private static Map<Integer, PacketCodec<?>> PACKET_MAP = Maps.newHashMap(); 
    private static Map<Class<?>, Integer> DATACLASS_ID_MAP = Maps.newHashMap();

    public static void init() {
        StreamCodec
            <FriendlyByteBuf, DTNNetworkPayload<?>> 
            rw_stream_codec = 
            StreamCodec.of(DTNNetworkHandler::toBuf, DTNNetworkHandler::fromBuf);

        PayloadTypeRegistry.playC2S().register(CHANNEL_ID, rw_stream_codec);
        PayloadTypeRegistry.playS2C().register(CHANNEL_ID, rw_stream_codec);

        ServerPlayNetworking.registerGlobalReceiver(CHANNEL_ID, DTNNetworkHandler::handlePayloadServer);
    }

    public static synchronized <D> void registerMessage(int id, Class<D> dataClass, 
        BiConsumer<D, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, D> decoder, 
        BiConsumer<D, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_MAP.put(id, new PacketCodec<>(dataClass, encoder, decoder, messageConsumer));
        DATACLASS_ID_MAP.put(dataClass, id);
    }

    public static <D> void send(PacketTarget<?> target, D data) {
        var dataId = DATACLASS_ID_MAP.get(data.getClass());
        if (dataId == null)
            return;
        PacketCodec<D> codec = null;
        try {
            codec = (PacketCodec<D>) PACKET_MAP.get(dataId);
        } catch (ClassCastException e) {
            codec = null;
            LOGGER.error("What ? [" + e + "]");
        }
        if (codec == null)
            return;
        var payload = new DTNNetworkPayload<D>(data, codec);
        target.sendPacket(payload);
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

        public void consume(T data, Context ctx) {
            this.messageConsumer.accept(data, () -> ctx);
        }

        public T decode(FriendlyByteBuf buf) {
            return decoder.apply(buf);
        }

        public void encode(FriendlyByteBuf buf, T data) {
            encoder.accept(data, buf);
        }

    }

    public static class NetworkEvent {
        public static class Context {
            
            private @Nullable ServerPlayer sender;
            private boolean isClientRecipent = false;
            
            public void enqueueWork(Runnable runnable) {
                //Fabric garanteed on proper run thread.
                runnable.run();
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

    private static final DTNNetworkPayload<Object> ERROR_DATA = new DTNNetworkPayload<Object>(null, null);

    private static DTNNetworkPayload<?> fromBuf(FriendlyByteBuf buf) {
        int id = buf.readInt();
        if (id < 0)
            return ERROR_DATA;
        var packet = DTNNetworkHandler.PACKET_MAP.get(id);
        if (packet == null)
            return ERROR_DATA;
        DTNNetworkPayload<?> ret = null;
        try {
            ret = decodeWithDecoder(packet, buf);
        } catch (Exception e) {
            LOGGER.error("Decoding packet failed with decoder_id "
                +"[" + id + "] " 
                + " data [" + packet.dataClass.getName() + "]"
                + " error_message [" + e.getMessage() + "]");
            ret = ERROR_DATA; 
        }
        return ret;
    }

    private static <T> DTNNetworkPayload<T> decodeWithDecoder(PacketCodec<T> codec, FriendlyByteBuf buf) {
        T data = codec.decode(buf);
        return new DTNNetworkPayload<T>(data, codec);
    }

    private static void toBuf(FriendlyByteBuf buf, DTNNetworkPayload<?> payload) {
        if (payload == ERROR_DATA) {
            buf.writeInt(-1);
            return;
        }
        var dataId = DATACLASS_ID_MAP.get(payload.data.getClass());
        if (dataId == null) {
            buf.writeInt(-1);
            return;
        }
        buf.writeInt(dataId);
        encodePayloadDataToBuf(payload, buf);
    }

    private static <T> void encodePayloadDataToBuf(DTNNetworkPayload<T> payload, FriendlyByteBuf buf) {
        payload.codec.encode(buf, payload.data);
    }

    private static void handlePayloadServer(DTNNetworkPayload<?> payload, ServerPlayNetworking.Context context) {
        var ctx = new NetworkEvent.Context();
        ctx.sender = context.player();
        ctx.isClientRecipent = false;
        handlePayloadContext(payload, ctx);
    }

    public static void handlePayloadClient(DTNNetworkPayload<?> payload) {
        var ctx = new NetworkEvent.Context();
        ctx.sender = null;
        ctx.isClientRecipent = true;
        handlePayloadContext(payload, ctx);
    }

    private static void handlePayloadContext(DTNNetworkPayload<?> payload, NetworkEvent.Context ctx) {
        if (payload == ERROR_DATA
            || payload.data == null || payload.codec == null) {
            LOGGER.error("Recieved error data!");
            return;
        }

        handleForCodec(payload, ctx);
    }

    private static <T> void handleForCodec(DTNNetworkPayload<T> payload, NetworkEvent.Context ctx) {
        payload.codec.consume(payload.data, ctx);
    }

    public static class DTNNetworkPayload<T> implements CustomPacketPayload {

        private final T data;
        private final PacketCodec<T> codec;

        public DTNNetworkPayload(T data, PacketCodec<T> codec) {
            this.data = data;
            this.codec = codec;
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return CHANNEL_ID;
        }

    }
}
