package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.CreeperSweeperData;
import doggytalents.common.talent.CreeperSweeperTalent;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class CreeperSweeperPacket extends DogPacket<CreeperSweeperData> {

    @Override
    public void encode(CreeperSweeperData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public CreeperSweeperData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new CreeperSweeperData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, CreeperSweeperData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.CREEPER_SWEEPER);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof CreeperSweeperTalent sweep))
            return;
        sweep.updateFromPacket(data);
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(sweep);
    }
}