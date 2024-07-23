package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.FisherDogData;
import doggytalents.common.talent.FisherDogTalent;
import doggytalents.forge_imitate.network.ForgeNetworkHandler.NetworkEvent.Context;
import net.minecraft.network.FriendlyByteBuf;

public class FisherDogPacket extends DogPacket<FisherDogData> {

    @Override
    public void encode(FisherDogData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public FisherDogData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new FisherDogData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, FisherDogData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.FISHER_DOG);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof FisherDogTalent fishTalent))
            return;
        fishTalent.updateFromPacket(data);
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(fishTalent);
    }

}

