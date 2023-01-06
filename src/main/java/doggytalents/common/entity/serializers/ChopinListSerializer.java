package doggytalents.common.entity.serializers;

import java.util.ArrayList;
import java.util.List;

import doggytalents.DoggyAccessories;
import doggytalents.DoggyRegistries;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.util.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class ChopinListSerializer implements EntityDataSerializer<List<String>>  {

    @Override
    public void write(FriendlyByteBuf buf, List<String> value) {
        buf.writeInt(value.size());

        for (String inst : value) {
            buf.writeUtf(inst);
        }
    }

    @Override
    public List<String> read(FriendlyByteBuf buf) {
        int size = buf.readInt();
        List<String> newInst = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            var type = buf.readUtf();
            newInst.add(type);
        }

        return newInst;
    }

    @Override
    public List<String> copy(List<String> value) {
        List<String> newInst = new ArrayList<>(value.size());

        for (String inst : value) {
            newInst.add(new String(inst));
        }

        return newInst;
    }
    
}
