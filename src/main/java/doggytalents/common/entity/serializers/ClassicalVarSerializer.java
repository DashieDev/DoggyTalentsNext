package doggytalents.common.entity.serializers;

import doggytalents.common.entity.ClassicalVar;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ClassicalVarSerializer implements EntityDataSerializer<ClassicalVar> {

    @Override
    public void write(FriendlyByteBuf buf, ClassicalVar value) {
        buf.writeByte((byte) value.getIdInt());
    }

    @Override
    public ClassicalVar read(FriendlyByteBuf buf) {
        var valRaw = buf.readByte();
        var val = ClassicalVar.fromIdInt((int) valRaw);
        return val;
    }

    @Override
    public ClassicalVar copy(ClassicalVar value) {
        return value;
    }

}