package doggytalents.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import doggytalents.DoggyTags;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.client.block.model.DogBedModel;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.TagUtil;
import doggytalents.common.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent.UpdateCause;

public class DogBedMaterialManager {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    public static final Identifier NANI_KEY = Util.getResource("textures/block/dog_bed_nani");
    public static final Identifier NANI_TEXTURE = Util.getResource("block/dog_bed_casing_nani");

    private static final Map<Identifier, IBeddingMaterial> beddingMap = Maps.newConcurrentMap();
    private static final Map<Identifier, ICasingMaterial> casingMap = Maps.newConcurrentMap();
    
    public static Map<Identifier, IBeddingMaterial> getBeddings() {
        return beddingMap;
    }
    public static Map<Identifier, ICasingMaterial> getCasings() {
        return casingMap;
    }

    public static IBeddingMaterial getBedding(Identifier loc) {
        var ret = beddingMap.get(loc);
        if (ret == null)
            return new NaniBedding(loc);
        return ret;
    }

    public static ICasingMaterial getCasing(Identifier loc) {
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

    public static Identifier getKey(IBeddingMaterial loc) {
        var key = loc.getSaveKey();
        if (key == null)
            return NANI_KEY;
        return key;
    }

    public static Identifier getKey(ICasingMaterial loc) {
        var key = loc.getSaveKey();
        if (key == null)
            return NANI_KEY;
        return key;
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

        var specific_beddings = TagUtil.queryAllValuesForTag(
            BuiltInRegistries.BLOCK, DoggyTags.DOG_BED_BEDDINGS);
        var specific_casings = TagUtil.queryAllValuesForTag(
            BuiltInRegistries.BLOCK, DoggyTags.DOG_BED_CASINGS);
        boolean specific_mode = !specific_beddings.isEmpty() && !specific_casings.isEmpty();

        populateBedding(cause, specific_mode ? 
            Optional.of(specific_beddings) : Optional.empty());
        populateCasing(cause, specific_mode ? 
            Optional.of(specific_casings) : Optional.empty());
        
        if (cause == UpdateCause.CLIENT_PACKET_RECEIVED 
            && ConfigHandler.CLIENT.DOG_BED_CLEAR_CACHE_AUTO.get()) {
            DogBedModel.clearCache();
        }
    }

    private static void populateBedding(UpdateCause cause, Optional<List<Block>> specific) {
        var blocks = specific.isPresent() ? 
            new ArrayList<>(specific.get()) : fetchBeddingBlocksAuto();
        for (var block : blocks) {
            if (!(block.asItem() instanceof BlockItem))
                continue;
            var id = BuiltInRegistries.BLOCK.getKey(block);
            var value = (IBeddingMaterial) new BeddingMaterial(id, () -> block);
            if (cause == UpdateCause.CLIENT_PACKET_RECEIVED) {
                if (!ClientEventHandler.vertifyBlockTexture(value.getTexture()))
                    value = new NaniBedding(id);
            }
            beddingMap.put(id, value);
        }
    }

    private static void populateCasing(UpdateCause cause, Optional<List<Block>> specific) {
        var blocks = specific.isPresent() ? 
            new ArrayList<>(specific.get()) : fetchCasingBlocksAuto();
        for (var block : blocks) {
            if (!(block.asItem() instanceof BlockItem))
                continue;
            var id = BuiltInRegistries.BLOCK.getKey(block);
            var value = (ICasingMaterial) new CasingMaterial(id, () -> block);
            if (cause == UpdateCause.CLIENT_PACKET_RECEIVED) {
                if (!ClientEventHandler.vertifyBlockTexture(value.getTexture()))
                    value = new NaniCasing(id);
            }
            casingMap.put(id, value);
        }
    }

    private static List<Block> fetchCasingBlocksAuto() {
        var planks = TagUtil.queryAllValuesForTag(
            BuiltInRegistries.BLOCK, BlockTags.PLANKS);
        var logs = TagUtil.queryAllValuesForTag(
            BuiltInRegistries.BLOCK, BlockTags.LOGS);
        var ret = new ArrayList<Block>(planks.size() + logs.size());
        ret.addAll(planks);
        ret.addAll(logs);
        return ret;
    }

    private static List<Block> fetchBeddingBlocksAuto() {
        var wools = TagUtil.queryAllValuesForTag(
            BuiltInRegistries.BLOCK, BlockTags.WOOL);
        var ret = new ArrayList<Block>(wools.size());
        ret.addAll(wools);
        return ret;
    }

    public static void onTagsUpdated(TagsUpdatedEvent event) {
        refresh(event.getUpdateCause());
    }

    public static class NaniCasing extends ICasingMaterial {

        public static final NaniCasing NULL = new NaniCasing(null);

        private Optional<Identifier> missingLoc;

        public NaniCasing(Identifier loc) {
            if (loc == null)
                this.missingLoc = Optional.empty();
            else
                this.missingLoc = Optional.of(loc);
        }

        @Override
        public Identifier getTexture() {
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
        public Optional<Ingredient> getIngredient() {
            return Optional.empty();
        }

        public Optional<Identifier> missingLoc() {
            return this.missingLoc;
        }

        @Override
        public Identifier getSaveKey() {
            return missingLoc().orElse(null);
        }

        @Override
        public boolean isNani() {
            return true;
        }
        
    }

    public static class NaniBedding extends IBeddingMaterial {

        public static final NaniBedding NULL = new NaniBedding(null);

        private Optional<Identifier> missingLoc;

        public NaniBedding(Identifier loc) {
            if (loc == null)
                this.missingLoc = Optional.empty();
            else
                this.missingLoc = Optional.of(loc);
        }

        @Override
        public Identifier getTexture() {
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
        public Optional<Ingredient> getIngredient() {
            return Optional.empty();
        }

        public Optional<Identifier> missingLoc() {
            return this.missingLoc;
        }

        @Override
        public boolean isNani() {
            return true;
        }

        @Override
        public Identifier getSaveKey() {
            return missingLoc().orElse(null);
        }
        
    }
    
}
