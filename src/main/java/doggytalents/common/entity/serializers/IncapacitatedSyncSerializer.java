package doggytalents.common.entity.serializers;

import doggytalents.common.entity.DogIncapacitatedMananger;
import doggytalents.common.entity.DogIncapacitatedMananger.BandaidState;
import doggytalents.common.entity.DogIncapacitatedMananger.DefeatedType;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;

public class IncapacitatedSyncSerializer implements IDataSerializer<IncapacitatedSyncState> {

    @Override
    public void write(PacketBuffer buf, IncapacitatedSyncState state) {
        buf.writeInt(state.type.getId());
        buf.writeInt(state.bandaid.getId());
        buf.writeInt(state.poseId);
    }

    @Override
    public IncapacitatedSyncState read(PacketBuffer buf) {
        var type = DefeatedType.byId(buf.readInt());
        var bandaid = BandaidState.byId(buf.readInt());
        var poseId = buf.readInt();
        return new IncapacitatedSyncState(type, bandaid, poseId);
    }

    @Override
    public IncapacitatedSyncState copy(IncapacitatedSyncState value) {
        return value.copy();
    }

}
