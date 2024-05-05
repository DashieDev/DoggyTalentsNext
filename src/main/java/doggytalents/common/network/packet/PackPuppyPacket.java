package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.PackPuppyData;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

public class PackPuppyPacket extends DogPacket<PackPuppyData> {

    @Override
    public void encode(PackPuppyData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.type.getId());
        buf.writeBoolean(data.val);
    }

    @Override
    public PackPuppyData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        var type = PackPuppyData.Type.fromId(buf.readInt());
        boolean val = buf.readBoolean();
        return new PackPuppyData(entityId, type, val);
    }

    @Override
    public void handleDog(Dog dogIn, PackPuppyData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.PACK_PUPPY);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof PackPuppyTalent packPup))
            return;
        packPup.updateFromPacket(data);
        dogIn.dogSyncedDataManager.markTalentNeedRefresh(packPup);
    }
}
