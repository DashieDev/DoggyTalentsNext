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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.Register;
import net.minecraftforge.network.handling.IPayloadContext;
import net.minecraftforge.network.handling.IPayloadHandler;
import doggytalents.common.lib.Constants;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.PacketDistributor.PacketTarget;

public class DTNNetworkHandler {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/network");

    public static final CustomPacketPayload.Type<DTNNetworkPayload<?>> CHANNEL_ID =
        new CustomPacketPayload.Type<>(Util.getResource("payload_channel"));
    private static Map<Integer, PacketCodec<?>> PACKET_MAP = Maps.newHashMap(); 
    private static Map<Class<?>, Integer> DATACLASS_ID_MAP = Maps.newHashMap();

    public static void init() {}

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
            
            private final IPayloadContext wrapped_ctx;

            public Context(IPayloadContext ctx) {
                this.wrapped_ctx = ctx;
            }
            
            public void enqueueWork(Runnable runnable) {
                wrapped_ctx.enqueueWork(runnable);
            }

            public @Nullable ServerPlayer getSender() {
                if (isServerRecipent())
                    return (ServerPlayer) this.wrapped_ctx.player();
                return null;
            }

            public boolean isClientRecipent() {
                return !isServerRecipent();
            }

            public boolean isServerRecipent() {
                return this.wrapped_ctx.flow() == PacketFlow.SERVERBOUND;
            }

            public void setPacketHandled(boolean x) {}

        }
    }

    public static void onRegisterPayloadEvent(RegisterPayloadHandlersEvent event) {
        var registerer = event.registrar(Constants.PROTOCOL_VERSION);
        
        StreamCodec
            <FriendlyByteBuf, DTNNetworkPayload<?>> 
            rw_stream_codec = 
            StreamCodec.of(DTNNetworkHandler::toBuf, DTNNetworkHandler::fromBuf);
        
        IPayloadHandler<DTNNetworkPayload<?>> payload_handler = 
            DTNNetworkHandler::handlePayload;

        registerer.commonBidirectional(CHANNEL_ID, rw_stream_codec, payload_handler);
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

    private static void handlePayload(DTNNetworkPayload<?> payload, IPayloadContext context) {
        if (payload == ERROR_DATA
            || payload.data == null || payload.codec == null) {
            LOGGER.error("Recieved error data!");
            return;
        }
        var ctx = new NetworkEvent.Context(context);
        handleForCodec(payload, ctx);
    }

    private static <T> void handleForCodec(DTNNetworkPayload<T> payload, NetworkEvent.Context ctx) {
        payload.codec.consume(payload.data, ctx);
    }

    private static class DTNNetworkPayload<T> implements CustomPacketPayload {

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
