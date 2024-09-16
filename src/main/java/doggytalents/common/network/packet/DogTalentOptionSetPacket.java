package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.packet.data.DogTalentOptionSetData;
import doggytalents.common.talent.DoggyTorchTalent;
import net.minecraft.network.FriendlyByteBuf;

public class DogTalentOptionSetPacket extends DogPacket<DogTalentOptionSetData> {

    @Override
    public void encode(DogTalentOptionSetData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        data.writeToBuf(buf);
    }

    @Override
    public DogTalentOptionSetData decode(FriendlyByteBuf buf) {
        return DogTalentOptionSetData.readFromBuf(buf);
    }

    @Override
    public void handleDog(Dog dogIn, DogTalentOptionSetData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(data.talent);
        if (!talentInstOptional.isPresent())
            return;
        var talent = talentInstOptional.get();
        data.setToTalent(talent);
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(talent);
    }

}
