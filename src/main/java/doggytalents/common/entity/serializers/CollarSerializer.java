package doggytalents.common.entity.serializers;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.AccessoryInstance;
import doggytalents.common.util.Util;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;

import java.util.Optional;

public class CollarSerializer implements IDataSerializer<Optional<AccessoryInstance>> {

    @Override
    public void write(PacketBuffer buf, Optional<AccessoryInstance> value) {
        Util.acceptOrElse(value, (inst) -> {
            buf.writeBoolean(true);
            buf.writeRegistryIdUnsafe(DoggyTalentsAPI.ACCESSORIES.get(), inst.getAccessory());
            inst.getAccessory().write(inst, buf);
        }, () -> {
            buf.writeBoolean(false);
        });

    }

    @Override
    public Optional<AccessoryInstance> read(PacketBuffer buf) {
        if (buf.readBoolean()) {
            Accessory type = buf.readRegistryIdUnsafe(DoggyTalentsAPI.ACCESSORIES.get());
            return Optional.of(type.createInstance(buf));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<AccessoryInstance> copy(Optional<AccessoryInstance> value) {
        if (value.isPresent()) {
            return Optional.of(value.get().copy());
        }
        return Optional.empty();
    }

}
