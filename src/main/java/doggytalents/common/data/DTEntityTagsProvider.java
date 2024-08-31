package doggytalents.common.data;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTags;
import doggytalents.common.lib.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.Tags;

public class DTEntityTagsProvider extends EntityTypeTagsProvider {

    public DTEntityTagsProvider(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @org.jetbrains.annotations.Nullable net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, Constants.MOD_ID, existingFileHelper);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "DoggyTalents Entity Tags";
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        createTag(DoggyTags.DOG_SHOULD_IGNORE, () -> EntityType.ENDERMAN);
        createTag(DoggyTags.DROP_SOY_WHEN_DOG_KILL, 
            () -> EntityType.CREEPER, 
            () -> EntityType.ZOMBIE,
            () -> EntityType.SKELETON,
            () -> EntityType.SPIDER);
        createTag(DoggyTags.MOB_RETRIEVER_MUST_IGNORE, 
            () -> EntityType.CREEPER);
        createTag(EntityTypeTags.IMPACT_PROJECTILES, 
            DoggyEntityTypes.DOG_ARROW_PROJ, DoggyEntityTypes.DOG_TRIDENT_PROJ);
        createTag(EntityTypeTags.ARROWS, DoggyEntityTypes.DOG_ARROW_PROJ);
        createTag(Tags.EntityTypes.CAPTURING_NOT_SUPPORTED, DoggyEntityTypes.DOG);
    }

    @SafeVarargs
    private final void createTag(TagKey<EntityType<?>> tag, Supplier<? extends EntityType<?>>... entities) {
        tag(tag).add(
            Arrays.stream(entities)
                .map(Supplier::get)
                .toArray(EntityType<?>[]::new));
    }
    
}
