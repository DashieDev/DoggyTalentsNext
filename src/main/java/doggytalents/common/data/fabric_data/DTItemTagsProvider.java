package doggytalents.common.data.fabric_data;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.DoggyTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class DTItemTagsProvider extends ItemTagProvider {

    public DTItemTagsProvider(FabricDataOutput output, CompletableFuture<Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(Provider arg) {
        createTag(DoggyTags.BEG_ITEMS_TAMED, DoggyItems.THROW_STICK, DoggyItems.THROW_BONE, () -> Items.BONE, DoggyItems.FRISBEE);
        //appendToTag(DoggyTags.TREATS);
        createTag(DoggyTags.BEG_ITEMS_UNTAMED, DoggyItems.TRAINING_TREAT, () -> Items.BONE);
        createTag(DoggyTags.BREEDING_ITEMS, DoggyItems.BREEDING_BONE);
        createTag(DoggyTags.PACK_PUPPY_BLACKLIST, DoggyItems.THROW_BONE, DoggyItems.THROW_BONE_WET, DoggyItems.THROW_STICK, DoggyItems.THROW_STICK_WET);
        createTag(DoggyTags.TREATS, DoggyItems.TRAINING_TREAT, DoggyItems.SUPER_TREAT, DoggyItems.MASTER_TREAT, DoggyItems.KAMI_TREAT);
        // getOrCreateTagBuilder(ItemTags.MUSIC).add(
        //     DoggyItems.MUSIC_DISC_BWV_1080_FUGUE_11_KIMIKO.get(), 
        //     DoggyItems.MUSIC_DISC_BWV_849_FUGUE_KIMIKO.get(),
        //     DoggyItems.MUSIC_DISC_OKAMI_1.get(),
        //     DoggyItems.MUSIC_DISC_CHOPIN_OP64_NO1.get()
        // );
        getOrCreateTagBuilder(ItemTags.DYEABLE).add(
            DoggyItems.WOOL_COLLAR.get(),
            DoggyItems.WOOL_COLLAR_THICC.get(),
            DoggyItems.CAPE_COLOURED.get(),
            DoggyItems.BOWTIE.get(),
            DoggyItems.WIG.get(),
            DoggyItems.FRISBEE.get(),
            DoggyItems.BAKER_HAT.get(),
            DoggyItems.CHEF_HAT.get(),
            DoggyItems.LAB_COAT.get(),
            DoggyItems.FRISBEE_WET.get(),
            DoggyItems.FLYING_CAPE.get(),
            DoggyItems.CERE_GARB.get(),
            DoggyItems.DOG_PLUSHIE_TOY.get(),
            DoggyBlocks.DOG_BATH.get().asItem()
        );
    }

    @SafeVarargs
    private final void createTag(TagKey<Item> tag, Supplier<? extends ItemLike>... items) {
        getOrCreateTagBuilder(tag).add(Arrays.stream(items).map(Supplier::get).map(ItemLike::asItem).toArray(Item[]::new));
    }

    // private final void appendToTag(TagKey<Item> tag, TagKey<Item> toAppend) {
    //     getOrCreateTagBuilder(tag).addTags(toAppend);
    // }
    
}
