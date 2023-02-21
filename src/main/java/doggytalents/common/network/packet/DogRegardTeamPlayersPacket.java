package doggytalents.common.network.packet;
    
import doggytalents.common.entity.Dog;
import doggytalents.common.network.packet.data.DogObeyData;
import doggytalents.common.network.packet.data.DogRegardTeamPlayersData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class DogRegardTeamPlayersPacket extends DogPacket<DogRegardTeamPlayersData> {

    @Override
    public void encode(DogRegardTeamPlayersData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeBoolean(data.regardTeamPlayers);
    }

    @Override
    public DogRegardTeamPlayersData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean regardTeamPlayers = buf.readBoolean();
        return new DogRegardTeamPlayersData(entityId, regardTeamPlayers);
    }

    @Override
    public void handleDog(Dog dogIn, DogRegardTeamPlayersData data, Supplier<Context> ctx) {
        if (!dogIn.canInteract(ctx.get().getSender())) {
            return;
        }

        dogIn.setRegardTeamPlayers(data.regardTeamPlayers);
    }
}
