package doggytalents.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket<D> {

    public void encode(D data, PacketBuffer buf);

    public D decode(PacketBuffer buf);

    public void handle(D data, Supplier<NetworkEvent.Context> ctx);
}
