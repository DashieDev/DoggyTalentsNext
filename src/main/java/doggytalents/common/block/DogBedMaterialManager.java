package doggytalents.common.block;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.google.common.collect.Maps;

import doggytalents.api.impl.BeddingMaterial;
import doggytalents.api.impl.CasingMaterial;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.client.event.ClientEventHandler;
import doggytalents.common.util.NBTUtil;
import doggytalents.common.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
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
        var list = new ArrayList<>(beddingMap.entrySet());
        if (list.isEmpty()) {
            return NaniBedding.NULL;
        }
        return list.get(RANDOM.nextInt(list.size())).getValue();
    }

    public static ICasingMaterial randomCasing() {
        var list = new ArrayList<>(casingMap.entrySet());
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
        var blocks = ForgeRegistries.BLOCKS.tags().getTag(BlockTags.WOOL);
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
        var blocks = ForgeRegistries.BLOCKS.tags().getTag(BlockTags.PLANKS);
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
            return Component.translatable("dogbed.casing.missing", retStr);
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
            return Component.translatable("dogbed.bedding.missing", retStr);
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
