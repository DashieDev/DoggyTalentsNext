package doggytalents.common.entity.serializers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ChopinTestStringSerializer implements EntityDataSerializer<String>  {

    @Override
    public void write(FriendlyByteBuf buf, String str) {
        buf.writeUtf(str);
    }

    @Override
    public String read(FriendlyByteBuf buf) {
        return buf.readUtf();
    }

    @Override
    public String copy(String str) {
        return new String(str);
    }
    
}
