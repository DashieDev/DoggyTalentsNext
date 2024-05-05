package doggytalents.common.network.packet;

import doggytalents.DoggyTalentsNext;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.Talent;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogTalentData;
import doggytalents.common.util.DogUtil;
import net.minecraft.network.FriendlyByteBuf;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogTalentPacket extends DogPacket<DogTalentData> {

    @Override
    public void encode(DogTalentData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeId(DoggyTalentsAPI.TALENTS.get(), data.talent);
    }

    @Override
    public DogTalentData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Talent talent = buf.readById(DoggyTalentsAPI.TALENTS.get());
        return new DogTalentData(entityId, talent);
    }

    @Override
    public void handleDog(Dog dogIn, DogTalentData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        if (!DogUtil.playerCanTrainTalent(ctx.get().getSender(), data.talent)) {
            // DoggyTalents2.LOGGER.info("{} tried to level a disabled talent ({})",
            //         ctx.get().getSender().getGameProfile().getName(),
            //         data.talent.getRegistryName());
            return;
        }

        if (!data.talent.isDogEligible(dogIn))
            return;

        int level = dogIn.getDogLevel(data.talent);

        if (level < data.talent.getMaxLevel() && dogIn.canSpendPoints(data.talent.getLevelCost(level + 1))) {
            dogIn.setTalentLevel(data.talent, level + 1);
        }
    }
}
