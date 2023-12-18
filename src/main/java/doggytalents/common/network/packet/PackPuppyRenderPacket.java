package doggytalents.common.network.packet;

import java.util.function.Supplier;

import doggytalents.DoggyTalents;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.PackPuppyRenderData;
import doggytalents.common.talent.PackPuppyTalent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PackPuppyRenderPacket extends DogPacket<PackPuppyRenderData> {

    @Override
    public void encode(PackPuppyRenderData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.val);
    }

    @Override
    public PackPuppyRenderData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean val = buf.readBoolean();
        return new PackPuppyRenderData(entityId, val);
    }

    @Override
    public void handleDog(Dog dogIn, PackPuppyRenderData data, Supplier<Context> ctx) {
        var talentInstOptional = dogIn.getTalent(DoggyTalents.PACK_PUPPY);
        if (!talentInstOptional.isPresent())
            return;
        var talentInst = talentInstOptional.get();
        if (!(talentInst instanceof PackPuppyTalent packPup))
            return;
        packPup.updateFromPacket(data);
    }
}
