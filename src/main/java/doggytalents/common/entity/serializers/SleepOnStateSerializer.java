package doggytalents.common.entity.serializers;

import doggytalents.common.entity.DogPettingManager.DogPettingState;
import doggytalents.common.entity.DogPettingManager.DogPettingType;
import doggytalents.common.entity.DogSleepOnManager.DogSleepOnState;
import net.minecraft.network.FriendlyByteBuf;

public class SleepOnStateSerializer extends DogSerializer<DogSleepOnState> {

    @Override
    public void write(FriendlyByteBuf buf, DogSleepOnState value) {
        buf.writeBoolean(value.is_sleeping());
        buf.writeUUID(value.sleeper());
        buf.writeVec3(value.sleep_pos());
    }

    @Override
    public DogSleepOnState read(FriendlyByteBuf buf) {
        var is_sleeping = buf.readBoolean();
        var sleeper = buf.readUUID();
        var sleep_pos = buf.readVec3();
        return new DogSleepOnState(sleeper, is_sleeping, sleep_pos);
    }

    @Override
    public DogSleepOnState copy(DogSleepOnState value) {
        return new DogSleepOnState(value.sleeper(), value.is_sleeping(), value.sleep_pos());
    }

}