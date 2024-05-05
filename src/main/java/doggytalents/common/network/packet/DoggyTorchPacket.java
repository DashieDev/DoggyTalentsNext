package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DoggyTorchData;
import doggytalents.common.talent.DoggyTorchTalent;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class DoggyTorchPacket extends DogPacket<DoggyTorchData> {

    @Override
    public void encode(DoggyTorchData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.type.getId());
        buf.writeBoolean(data.val);
    }

    @Override
    public DoggyTorchData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        var type = DoggyTorchData.Type.fromId(buf.readInt());
        boolean val = buf.readBoolean();
        return new DoggyTorchData(entityId, val, type);
    }

    @Override
    public void handleDog(Dog dogIn, DoggyTorchData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.DOGGY_TORCH);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof DoggyTorchTalent torchTalent))
            return;
        torchTalent.updateFromPacket(data);
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(torchTalent);
    }

}
