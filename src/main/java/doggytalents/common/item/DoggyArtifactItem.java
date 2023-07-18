package doggytalents.common.item;

import java.util.function.Supplier;

import doggytalents.common.artifacts.DoggyArtifact;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class DoggyArtifactItem extends Item {

    private final Supplier<DoggyArtifact> artifactSupplier;

    public DoggyArtifactItem(Supplier<DoggyArtifact> artifactSupplier, Properties props) {
        super(props);
        this.artifactSupplier = artifactSupplier;
    }

    public DoggyArtifact createArtifact() {
        return this.artifactSupplier.get();
    }

    public static CompoundTag writeCompound(DoggyArtifactItem item) {
        var id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return null;
        var artifactTag = new CompoundTag();
        artifactTag.putString("type", id.toString());
        return artifactTag;
    }

    public static DoggyArtifactItem readCompound(CompoundTag tag) {
        var id_str = tag.getString("type");
        var item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id_str));
        if (item == null) return null;
        if (!(item instanceof DoggyArtifactItem artifactItem))
            return null;
        return artifactItem;
    }
}
