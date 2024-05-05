package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.RescueDogRenderData;
import doggytalents.common.talent.RescueDogTalent;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class RescueDogRenderPacket extends DogPacket<RescueDogRenderData> {

    @Override
    public void encode(RescueDogRenderData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public RescueDogRenderData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new RescueDogRenderData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, RescueDogRenderData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.RESCUE_DOG);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof RescueDogTalent rescue))
            return;
        rescue.updateFromPacket(data);
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(rescue);
    }
}
