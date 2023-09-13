package doggytalents.common.block;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import doggytalents.api.impl.BeddingMaterial;
import doggytalents.api.impl.CasingMaterial;
import doggytalents.api.impl.MissingBeddingMaterial;
import doggytalents.api.impl.MissingCasingMissing;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class DogBedMaterialManager {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    
    private static final Map<ResourceLocation, IBeddingMaterial> beddingMap = Maps.newConcurrentMap();
    private static final Map<ResourceLocation, ICasingMaterial> casingMap = Maps.newConcurrentMap();
    private static final Map<IBeddingMaterial, ResourceLocation> beddingKeyMap = Maps.newConcurrentMap();
    private static final Map<ICasingMaterial, ResourceLocation> casingKeyMap = Maps.newConcurrentMap();
    
    public static Map<ResourceLocation, IBeddingMaterial> getBeddings() {
        return beddingMap;
    }
    public static Map<ResourceLocation, ICasingMaterial> getCasings() {
        return casingMap;
    }

    public static IBeddingMaterial getBedding(ResourceLocation loc) {
        var ret = beddingMap.get(loc);
        if (ret == null)
            return MissingBeddingMaterial.NULL;
        return ret;
    }

    public static ICasingMaterial getCasing(ResourceLocation loc) {
        var ret = casingMap.get(loc);
        if (ret == null)
            return MissingCasingMissing.NULL;
        return ret;
    }

    public static IBeddingMaterial getBedding(CompoundTag tag, String id) {
        var loc = NBTUtil.getResourceLocation(tag, id);
        return getBedding(loc);
    }

    public static ICasingMaterial getCasing(CompoundTag tag, String id) {
        var loc = NBTUtil.getResourceLocation(tag, id);
        return getCasing(loc);
    }

    public static ResourceLocation getKey(IBeddingMaterial loc) {
        var ret = beddingKeyMap.get(loc);
        return ret;
    }

    public static ResourceLocation getKey(ICasingMaterial loc) {
        var ret = casingKeyMap.get(loc);
        return ret;
    }

    public static IBeddingMaterial randomBedding() {
        var list = new ArrayList<>(beddingMap.entrySet());
        if (list.isEmpty()) {
            return MissingBeddingMaterial.NULL;
        }
        return list.get(RANDOM.nextInt(list.size())).getValue();
    }

    public static ICasingMaterial randomCasing() {
        var list = new ArrayList<>(casingMap.entrySet());
        if (list.isEmpty()) {
            return MissingCasingMissing.NULL;
        }
        return list.get(RANDOM.nextInt(list.size())).getValue();
    }

    public static void refresh() {
        beddingMap.clear();
        casingMap.clear();
        
        populateBedding();
        populateCasing();
    }

    private static void populateBedding() {
        var blocks = ForgeRegistries.BLOCKS.tags().getTag(BlockTags.WOOL);
        for (var block : blocks) {
            var id = ForgeRegistries.BLOCKS.getKey(block);
            var value = new BeddingMaterial(() -> block);
            beddingMap.put(id, value);
            beddingKeyMap.put(value, id);
        }
    }

    private static void populateCasing() {
        var blocks = ForgeRegistries.BLOCKS.tags().getTag(BlockTags.PLANKS);
        for (var block : blocks) {
            var id = ForgeRegistries.BLOCKS.getKey(block);
            var value = new CasingMaterial(() -> block);
            casingMap.put(id, value);
            casingKeyMap.put(value, id);
        }
    }

    public static void onTagsUpdated(TagsUpdatedEvent event) {
        refresh();
    }
    
}
