package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.GatePasserData;
import doggytalents.common.talent.GatePasserTalent;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class GatePasserPacket extends DogPacket<GatePasserData> {
    
    @Override
    public void encode(GatePasserData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.allowPassingGate);
    }

    @Override
    public GatePasserData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new GatePasserData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, GatePasserData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.GATE_PASSER);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof GatePasserTalent gate))
            return;
        gate.updateFromPacket(data);
        
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(gate);
    }

}
