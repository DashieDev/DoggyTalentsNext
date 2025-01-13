package doggytalents.common.entity.serializers;

import doggytalents.common.entity.DogSleepOnManager.DogSleepOnState;
import net.minecraft.network.FriendlyByteBuf;

public class SleepOnStateSerializer extends DogSerializer<DogSleepOnState> {

    @Override
    public void write(FriendlyByteBuf buf, DogSleepOnState value) {
        buf.writeBoolean(value.is_sleeping());
        buf.writeUUID(value.sleeper());
        buf.writeFloat(value.sleep_yrot());
    }

    @Override
    public DogSleepOnState read(FriendlyByteBuf buf) {
        var is_sleeping = buf.readBoolean();
        var sleeper = buf.readUUID();
        var sleep_yrot = buf.readFloat();
        return new DogSleepOnState(sleeper, is_sleeping, sleep_yrot);
    }

    @Override
    public DogSleepOnState copy(DogSleepOnState value) {
        return new DogSleepOnState(value.sleeper(), value.is_sleeping(), value.sleep_yrot());
    }

}