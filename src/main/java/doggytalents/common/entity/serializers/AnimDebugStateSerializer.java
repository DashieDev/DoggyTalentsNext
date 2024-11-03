package doggytalents.common.entity.serializers;

import doggytalents.common.entity.anim.DogAnimationManager.DogAnimDebugState;
import net.minecraft.network.FriendlyByteBuf;

public class AnimDebugStateSerializer extends DogSerializer<DogAnimDebugState> {
    
    @Override
    public void write(FriendlyByteBuf buf, DogAnimDebugState value) {
        if (value.isNone()) {
            buf.writeInt(-1);
        } else {
            buf.writeInt(value.anim().getId());
            buf.writeInt(value.timestamp());
            buf.writeFloat(value.yRot());
        }
    }

    @Override
    public DogAnimDebugState read(FriendlyByteBuf buf) {
        int anim_id = buf.readInt();
        if (anim_id < 0)
            return DogAnimDebugState.NONE;
        int timestamp = buf.readInt();
        float yRot = buf.readFloat();
        return DogAnimDebugState.of(anim_id, timestamp, yRot);
    }

    @Override
    public DogAnimDebugState copy(DogAnimDebugState value) {
        if (value.isNone())
            return DogAnimDebugState.NONE;
        return DogAnimDebugState.of(value.anim(), value.timestamp(), value.yRot());
    }

}
