package doggytalents.common.network.packet;

import java.util.Optional;
import java.util.function.Supplier;

import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.packet.data.DogInterruptibleSoundData;
import doggytalents.common.util.NetworkUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public class DogInterruptibleSoundPacket implements IPacket<DogInterruptibleSoundData> {

    @Override
    public void encode(DogInterruptibleSoundData data, FriendlyByteBuf buf) {
        buf.writeInt(data.dogId());
        if (!data.sound().isPresent()) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            buf.writeFloat(data.volume());
            buf.writeFloat(data.pitch());
            NetworkUtil.writeSoundEventToBuf(buf, data.sound().get());
        }
    }

    @Override
    public DogInterruptibleSoundData decode(FriendlyByteBuf buf) {
        int dog_id = buf.readInt();
        boolean play = buf.readBoolean();
        if (!play) {
            return DogInterruptibleSoundData.stop(dog_id);
        }
        float vol = buf.readFloat();
        float pitch = buf.readFloat();
        var sound = NetworkUtil.readSoundEventFromBuf(buf);
        return new DogInterruptibleSoundData(dog_id, Optional.ofNullable(sound), vol, pitch);
    }

    @Override
    public void handle(DogInterruptibleSoundData data, Supplier<Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (!ctx.get().isClientRecipent())
                return;
            var mc = Minecraft.getInstance();
            var e = mc.level.getEntity(data.dogId());
            if (!(e instanceof Dog dog))
                return;
            dog.dogSoundManager.onDogInterruptableSoundUpdate(data);
        });

        ctx.get().setPacketHandled(true);
    }
    
}
