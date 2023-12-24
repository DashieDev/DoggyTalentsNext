package doggytalents.common.entity.serializers;

import java.util.ArrayList;
import java.util.List;

import doggytalents.common.item.DoggyArtifactItem;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class DoggyArtifactsSerializer implements IDataSerializer<List<DoggyArtifactItem>> {

    @Override
    public void write(PacketBuffer buf, List<DoggyArtifactItem> value) {
        buf.writeInt(value.size());
        for (var x : value) {
            buf.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, x);
        }
    }

    @Override
    public List<DoggyArtifactItem> read(PacketBuffer buf) {
        int size = buf.readInt();
        var list = new ArrayList<DoggyArtifactItem>(size);
        for (int i = 0; i < size; ++i) {
            var item = buf.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
            if (item instanceof DoggyArtifactItem artifactItem) {
                list.add(artifactItem);
            }
        }
        return list;
    }

    @Override
    public List<DoggyArtifactItem> copy(List<DoggyArtifactItem> value) {
        List<DoggyArtifactItem> newInst = new ArrayList<>(value.size());

        for (var inst : value) {
            newInst.add(inst);
        }

        return newInst;
    }
}
