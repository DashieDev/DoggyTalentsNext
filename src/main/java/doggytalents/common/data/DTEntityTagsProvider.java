package doggytalents.common.data;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import doggytalents.DoggyTags;
import doggytalents.common.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DTEntityTagsProvider extends EntityTypeTagsProvider {

    public DTEntityTagsProvider(DataGenerator gen, @org.jetbrains.annotations.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
        super(gen, Constants.MOD_ID, existingFileHelper);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "DoggyTalents Entity Tags";
    }

    @Override
    public void addTags() {
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
        tag(tag).add(
            Arrays.stream(entities)
                .map(Supplier::get)
                .toArray(EntityType<?>[]::new));
    }
    
}
