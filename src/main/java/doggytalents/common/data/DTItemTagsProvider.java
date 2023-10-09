package doggytalents.common.data;

import doggytalents.DoggyItems;
import doggytalents.DoggyTags;
import doggytalents.common.lib.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class DTItemTagsProvider extends ItemTagsProvider {

    public DTItemTagsProvider(PackOutput generatorIn, CompletableFuture<HolderLookup.Provider> provider , CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider , ExistingFileHelper existingFileHelper) {
        super(generatorIn, provider, blockTagProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "DoggyTalents Item Tags";
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        createTag(DoggyTags.BEG_ITEMS_TAMED, DoggyItems.THROW_STICK, DoggyItems.THROW_BONE, () -> Items.BONE);
        appendToTag(DoggyTags.TREATS);
        createTag(DoggyTags.BEG_ITEMS_UNTAMED, DoggyItems.TRAINING_TREAT, () -> Items.BONE);
        createTag(DoggyTags.BREEDING_ITEMS, DoggyItems.BREEDING_BONE);
        createTag(DoggyTags.PACK_PUPPY_BLACKLIST, DoggyItems.THROW_BONE, DoggyItems.THROW_BONE_WET, DoggyItems.THROW_STICK, DoggyItems.THROW_STICK_WET);
        createTag(DoggyTags.TREATS, DoggyItems.TRAINING_TREAT, DoggyItems.SUPER_TREAT, DoggyItems.MASTER_TREAT, DoggyItems.DIRE_TREAT);
        createTag(DoggyTags.DOG_BOOSTING_FOOD, () -> Items.GOLDEN_APPLE, () -> Items.ENCHANTED_GOLDEN_APPLE, () -> Items.GOLDEN_CARROT, DoggyItems.EGG_SANDWICH);
        tag(ItemTags.MUSIC_DISCS).add(
            DoggyItems.MUSIC_DISC_BWV_1080_FUGUE_11_KIMIKO.get(), 
            DoggyItems.MUSIC_DISC_BWV_849_FUGUE_KIMIKO.get(),
            DoggyItems.MUSIC_DISC_OKAMI_1.get(),
            DoggyItems.MUSIC_DISC_CHOPIN_OP64_NO1.get()
        );
    }

    @SafeVarargs
    private final void createTag(TagKey<Item> tag, Supplier<? extends ItemLike>... items) {
        tag(tag).add(Arrays.stream(items).map(Supplier::get).map(ItemLike::asItem).toArray(Item[]::new));
    }

    @SafeVarargs
    private final void appendToTag(TagKey<Item> tag, TagKey<Item>... toAppend) {
        tag(tag).addTags(toAppend);
    }
}
