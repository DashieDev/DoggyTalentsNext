package doggytalents.common.util;

import doggytalents.DoggyBlocks;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class DogBedUtil {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void setBedVariant(DogBedTileEntity dogBedTileEntity, ItemStack stack) {
        Pair<ICasingMaterial, IBeddingMaterial> materials = DogBedUtil.getMaterials(stack);

        dogBedTileEntity.setCasing(materials.getLeft());
        dogBedTileEntity.setBedding(materials.getRight());
    }

    public static ItemStack createRandomBed() {
        ICasingMaterial casing = DogBedMaterialManager.randomCasing();
        IBeddingMaterial bedding = DogBedMaterialManager.randomBedding();
        return DogBedUtil.createItemStack(casing, bedding);
    }

    public static Pair<ICasingMaterial, IBeddingMaterial> getMaterials(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("doggytalents");
        if (tag != null) {
            ICasingMaterial casingId = DogBedMaterialManager.getCasing(tag, "casingId");
            IBeddingMaterial beddingId = DogBedMaterialManager.getBedding(tag, "beddingId");

            return Pair.of(casingId, beddingId);
        }

        return Pair.of(null, null);
    }

    public static ItemStack createItemStack(ICasingMaterial casingId, IBeddingMaterial beddingId) {
        ItemStack stack = new ItemStack(DoggyBlocks.DOG_BED.get(), 1);

        CompoundTag tag = stack.getOrCreateTagElement("doggytalents");
        NBTUtil.putRegistryValue(tag, "casingId", DogBedMaterialManager.getKey(casingId));
        NBTUtil.putRegistryValue(tag, "beddingId", DogBedMaterialManager.getKey(beddingId));

        return stack;
    }

    public static ICasingMaterial getCasingFromStack(ItemStack stack) {
        for (var e : DogBedMaterialManager.getCasings().entrySet()) {
            var m = e.getValue();
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

    public static IBeddingMaterial getBeddingFromStack(ItemStack stack) {
        for (var e : DogBedMaterialManager.getBeddings().entrySet()) {
            var m = e.getValue();
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

    public static <T extends IForgeRegistryEntry<T>> T pickRandom(IForgeRegistry<T> registry) {
        Collection<T> values = registry.getValues();
        List<T> list = values instanceof List ? (List<T>) values : new ArrayList<>(values);
        return list.get(RANDOM.nextInt(list.size()));
    }
}
