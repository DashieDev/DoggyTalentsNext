package doggytalents.common.entity.serializers;

import doggytalents.common.entity.DogIncapacitatedMananger;
import doggytalents.common.entity.DogIncapacitatedMananger.DefeatedType;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class IncapacitatedSyncSerializer implements EntityDataSerializer<IncapacitatedSyncState> {

    @Override
    public void write(FriendlyByteBuf buf, IncapacitatedSyncState state) {
        buf.writeInt(state.type.getId());
    }

    @Override
    public IncapacitatedSyncState read(FriendlyByteBuf buf) {
        var type = DefeatedType.byId(buf.readInt());
        return new IncapacitatedSyncState(type);
    }

    @Override
    public IncapacitatedSyncState copy(IncapacitatedSyncState value) {
        return value.copy();
    }

}
