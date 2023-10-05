package doggytalents.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import doggytalents.api.impl.BeddingMaterial;
import doggytalents.api.impl.CasingMaterial;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TagsUpdatedEvent.UpdateCause;
import net.minecraftforge.registries.ForgeRegistries;

public class DogBedMaterialManager {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    public static final ResourceLocation NANI_KEY = Util.getResource("textures/block/dog_bed_nani");
    public static final ResourceLocation NANI_TEXTURE = Util.getResource("block/dog_bed_casing_nani");

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
            return new NaniBedding(loc);
        return ret;
    }

    public static ICasingMaterial getCasing(ResourceLocation loc) {
        var ret = casingMap.get(loc);
        if (ret == null)
            return new NaniCasing(loc);
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
        if (loc == null) {
            return NANI_KEY;
        }
        if (loc instanceof NaniBedding nani) {
            return nani.missingLoc().isPresent() ?
                nani.missingLoc().get()
                : NANI_KEY;
        } 
        var ret = beddingKeyMap.get(loc);
        if (ret == null) {
            return NANI_KEY;
        }
        return ret;
    }

    public static ResourceLocation getKey(ICasingMaterial loc) {
        if (loc == null) {
            return NANI_KEY;
        }
        if (loc instanceof NaniCasing nani) {
            return nani.missingLoc().isPresent() ?
                nani.missingLoc().get()
                : NANI_KEY;
        } 
        var ret = casingKeyMap.get(loc);
        if (ret == null) {
            return NANI_KEY;
        }
        return ret;
    }

    public static IBeddingMaterial randomBedding() {
        var list = beddingMap.entrySet().stream()
            .filter(x -> !(x.getValue() instanceof NaniBedding))
            .collect(Collectors.toList());
        if (list.isEmpty()) {
            return NaniBedding.NULL;
        }
        
        return list.get(RANDOM.nextInt(list.size())).getValue();
    }

    public static ICasingMaterial randomCasing() {
        var list = casingMap.entrySet().stream()
            .filter(x -> !(x.getValue() instanceof NaniCasing))
            .collect(Collectors.toList());
        if (list.isEmpty()) {
            return NaniCasing.NULL;
        }
        return list.get(RANDOM.nextInt(list.size())).getValue();
    }

    public static void refresh(UpdateCause cause) {
        beddingMap.clear();
        casingMap.clear();
        
        populateBedding(cause);
        populateCasing(cause);
    }

    private static void populateBedding(UpdateCause cause) {
        var blocks = fetchBeddingBlocks();
        for (var block : blocks) {
            var id = ForgeRegistries.BLOCKS.getKey(block);
            var value = (IBeddingMaterial) new BeddingMaterial(() -> block);
            if (cause == UpdateCause.CLIENT_PACKET_RECEIVED) {
                if (!ClientEventHandler.vertifyBlockTexture(value.getTexture()))
                    value = new NaniBedding(id);
            }
            beddingMap.put(id, value);
            beddingKeyMap.put(value, id);
        }
    }

    private static void populateCasing(UpdateCause cause) {
        var blocks = fetchCasingBlocks();
        for (var block : blocks) {
            var id = ForgeRegistries.BLOCKS.getKey(block);
            var value = (ICasingMaterial) new CasingMaterial(() -> block);
            if (cause == UpdateCause.CLIENT_PACKET_RECEIVED) {
                if (!ClientEventHandler.vertifyBlockTexture(value.getTexture()))
                    value = new NaniCasing(id);
            }
            casingMap.put(id, value);
            casingKeyMap.put(value, id);
        }
    }

    private static List<Block> fetchCasingBlocks() {
        var tags = ForgeRegistries.BLOCKS.tags();
        var planks = tags.getTag(BlockTags.PLANKS)
            .stream().collect(Collectors.toList());
        var logs = tags.getTag(BlockTags.LOGS)
            .stream().collect(Collectors.toList());
        var ret = new ArrayList<Block>(planks.size() + logs.size());
        ret.addAll(planks);
        ret.addAll(logs);
        return ret;
    }

    private static List<Block> fetchBeddingBlocks() {
        var tags = ForgeRegistries.BLOCKS.tags();
        var wools = tags.getTag(BlockTags.WOOL)
            .stream().collect(Collectors.toList());
        var ret = new ArrayList<Block>(wools.size());
        ret.addAll(wools);
        return ret;
    }

    public static void onTagsUpdated(TagsUpdatedEvent event) {
        refresh(event.getUpdateCause());
    }

    public static class NaniCasing extends ICasingMaterial {

        public static final NaniCasing NULL = new NaniCasing(null);

        private Optional<ResourceLocation> missingLoc;

        public NaniCasing(ResourceLocation loc) {
            if (loc == null)
                this.missingLoc = Optional.empty();
            else
                this.missingLoc = Optional.of(loc);
        }

        @Override
        public ResourceLocation getTexture() {
            return NANI_TEXTURE;
        }

        @Override
        public Component getTooltip() {
            String retStr = "nani?";
            if (missingLoc.isPresent()) {
                retStr = missingLoc.get().toString();
            }
            return Component.translatable(retStr)
                .withStyle(ChatFormatting.RED);
        }

        @Override
        public Ingredient getIngredient() {
            return Ingredient.EMPTY;
        }

        public Optional<ResourceLocation> missingLoc() {
            return this.missingLoc;
        }
        
    }

    public static class NaniBedding extends IBeddingMaterial {

        public static final NaniBedding NULL = new NaniBedding(null);

        private Optional<ResourceLocation> missingLoc;

        public NaniBedding(ResourceLocation loc) {
            if (loc == null)
                this.missingLoc = Optional.empty();
            else
                this.missingLoc = Optional.of(loc);
        }

        @Override
        public ResourceLocation getTexture() {
            return NANI_TEXTURE;
        }

        @Override
        public Component getTooltip() {
            String retStr = "nani?";
            if (missingLoc.isPresent()) {
                retStr = missingLoc.get().toString();
            }
            return Component.translatable(retStr)
                .withStyle(ChatFormatting.RED);
        }

        @Override
        public Ingredient getIngredient() {
            return Ingredient.EMPTY;
        }

        public Optional<ResourceLocation> missingLoc() {
            return this.missingLoc;
        }
        
    }
    
}
