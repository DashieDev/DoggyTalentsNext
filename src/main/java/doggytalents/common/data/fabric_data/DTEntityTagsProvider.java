package doggytalents.common.data.fabric_data;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import doggytalents.DoggyTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.EntityTypeTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class DTEntityTagsProvider extends EntityTypeTagProvider {

    public DTEntityTagsProvider(FabricDataOutput output, CompletableFuture<Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void addTags(Provider provider) {
        createTag(DoggyTags.DOG_SHOULD_IGNORE, () -> EntityType.ENDERMAN);
        createTag(DoggyTags.DROP_SOY_WHEN_DOG_KILL, 
            () -> EntityType.CREEPER, 
            () -> EntityType.ZOMBIE,
            () -> EntityType.SKELETON,
            () -> EntityType.SPIDER);
        createTag(DoggyTags.MOB_RETRIEVER_MUST_IGNORE, 
            () -> EntityType.CREEPER);
    }

    @SafeVarargs
    private final void createTag(TagKey<EntityType<?>> tag, Supplier<? extends EntityType<?>>... entities) {
        getOrCreateTagBuilder(tag).add(
            Arrays.stream(entities)
                .map(Supplier::get)
                .toArray(EntityType<?>[]::new));
    }
    
}
