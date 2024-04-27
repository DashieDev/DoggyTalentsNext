package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DoggyToolsPickFirstData;
import doggytalents.common.talent.DoggyTorchTalent;
import doggytalents.common.talent.doggy_tools.DoggyToolsTalent;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class DoggyToolsPickFirstPacket extends DogPacket<DoggyToolsPickFirstData> {

    @Override
    public void encode(DoggyToolsPickFirstData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public DoggyToolsPickFirstData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new DoggyToolsPickFirstData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, DoggyToolsPickFirstData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.DOGGY_TOOLS);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof DoggyToolsTalent toolsTalent))
            return;
        toolsTalent.updateFromPacket(data);
        
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(toolsTalent);
    }

}
