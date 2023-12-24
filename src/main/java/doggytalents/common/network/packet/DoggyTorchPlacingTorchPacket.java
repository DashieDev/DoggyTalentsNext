package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DoggyTorchPlacingTorchData;
import doggytalents.common.talent.DoggyTorchTalent;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.network.NetworkEvent.Context;

public class DoggyTorchPlacingTorchPacket extends DogPacket<DoggyTorchPlacingTorchData> {

    @Override
    public void encode(DoggyTorchPlacingTorchData data, PacketBuffer buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public DoggyTorchPlacingTorchData decode(PacketBuffer buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new DoggyTorchPlacingTorchData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, DoggyTorchPlacingTorchData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.DOGGY_TORCH);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof DoggyTorchTalent torchTalent))
            return;
        torchTalent.updateFromPacket(data);
    }

}
