package doggytalents.common.entity.serializers;

import java.util.ArrayList;
import java.util.List;

import doggytalents.common.item.DoggyArtifactItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class DoggyArtifactsSerializer implements EntityDataSerializer<List<DoggyArtifactItem>> {

    @Override
    public void write(FriendlyByteBuf buf, List<DoggyArtifactItem> value) {
        buf.writeInt(value.size());
        for (var x : value) {
            buf.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, x);
        }
    }

    @Override
    public List<DoggyArtifactItem> read(FriendlyByteBuf buf) {
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
