package doggytalents.common.entity.serializers;

import doggytalents.common.entity.DogPettingManager.DogPettingState;
import doggytalents.common.entity.DogPettingManager.DogPettingType;
import net.minecraft.network.FriendlyByteBuf;

public class PettingStateSerializer extends DogSerializer<DogPettingState> {

    @Override
    public void write(FriendlyByteBuf buf, DogPettingState value) {
        buf.writeBoolean(value.is_petting());
        buf.writeUUID(value.petting_id());
        buf.writeInt(value.type().getId());
    }

    @Override
    public DogPettingState read(FriendlyByteBuf buf) {
        var petting = buf.readBoolean();
        var petting_id = buf.readUUID();
        var petting_type = DogPettingType.fromId(buf.readInt());
        return new DogPettingState(petting_id, petting, petting_type);
    }

    @Override
    public DogPettingState copy(DogPettingState value) {
        return new DogPettingState(value.petting_id(), value.is_petting(), value.type());
    }

}