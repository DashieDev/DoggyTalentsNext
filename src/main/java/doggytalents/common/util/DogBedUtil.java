package doggytalents.common.util;

import doggytalents.DoggyBlocks;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.api.registry.IDogBedMaterial;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.block.tileentity.DogBedTileEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

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
        CompoundTag tag = ItemUtil.getTagElement(stack, "doggytalents");
        if (tag != null) {
            ICasingMaterial casingId = DogBedMaterialManager.getCasing(tag, "casingId");
            IBeddingMaterial beddingId = DogBedMaterialManager.getBedding(tag, "beddingId");

            return Pair.of(casingId, beddingId);
        }

        return Pair.of(DogBedMaterialManager.NaniCasing.NULL, DogBedMaterialManager.NaniBedding.NULL);
    }

    public static ItemStack createItemStack(ICasingMaterial casingId, IBeddingMaterial beddingId) {
        ItemStack stack = new ItemStack(DoggyBlocks.DOG_BED.get(), 1);

        CompoundTag tag = new CompoundTag();
        NBTUtil.putRegistryValue(tag, "casingId", DogBedMaterialManager.getKey(casingId));
        NBTUtil.putRegistryValue(tag, "beddingId", DogBedMaterialManager.getKey(beddingId));
        var maintag = new CompoundTag();
        maintag.put("doggytalents", tag);
        ItemUtil.putTag(stack, maintag);

        return stack;
    }

    public static Optional<IBeddingMaterial> getBeddingFromStack(ItemStack stack) {
        return getBedMaterialFromStack(stack, DogBedMaterialManager::getBedding);
    }

    public static Optional<ICasingMaterial> getCasingFromStack(ItemStack stack) {
        return getBedMaterialFromStack(stack, DogBedMaterialManager::getCasing);
    }

    public static <T extends IDogBedMaterial> Optional<T> getBedMaterialFromStack(
        ItemStack stack, Function<ResourceLocation, T> bed_material_getter) {
        
        if (stack.isEmpty())
            return Optional.empty();
        
        var item = stack.getItem();
        if (!(item instanceof BlockItem block_item))
            return Optional.empty();
        var block = block_item.getBlock();
        if (block == null)
            return Optional.empty();
        
        final var id = BuiltInRegistries.BLOCK.getKey(block);
        final var material = bed_material_getter.apply(id);
        if (material.isNani())
            return Optional.empty();

        return material.getIngredient()
            .filter(ingredient -> ingredient.test(stack))
            .map(x -> material);
    }

    public static ItemStack createItemStackForced(Block casing, Block bedding) {
        ItemStack stack = new ItemStack(DoggyBlocks.DOG_BED.get(), 1);

        CompoundTag tag = new CompoundTag();
        NBTUtil.putRegistryValue(tag, "casingId", BuiltInRegistries.BLOCK.getKey(casing));
        NBTUtil.putRegistryValue(tag, "beddingId", BuiltInRegistries.BLOCK.getKey(bedding));
        var maintag = new CompoundTag();
        maintag.put("doggytalents", tag);
        ItemUtil.putTag(stack, maintag);

        return stack;
    }

    // public static <T> T pickRandom( Registry<T> registry) {
    //     Collection<T> values = registry.getValues();
    //     List<T> list = values instanceof List ? (List<T>) values : new ArrayList<>(values);
    //     return list.get(RANDOM.nextInt(list.size()));
    // }
}
