package doggytalents.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;

import java.util.function.Supplier;

public interface IPacket<D> {

    public void encode(D data, FriendlyByteBuf buf);

    public D decode(FriendlyByteBuf buf);

    default void doHandle(D data, Context ctx) {
        handle(data, () -> ctx);
    }

    public void handle(D data, Supplier<Context> ctx);
}
