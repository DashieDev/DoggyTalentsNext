package doggytalents.client.data;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.common.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DTItemModelProvider extends ItemModelProvider {

    public DTItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "DoggyTalents Item Models";
    }

    @Override
    protected void registerModels() {
        handheld(DoggyItems.SHRINKING_MALLET);
        handheld(DoggyItems.MAGNIFYING_BONE);

        handheld(DoggyItems.TRAINING_TREAT);
        handheld(DoggyItems.SUPER_TREAT);
        handheld(DoggyItems.MASTER_TREAT);
        handheld(DoggyItems.DIRE_TREAT);
        handheld(DoggyItems.BREEDING_BONE);
        handheld(DoggyItems.ENERGIZER_STICK);
        handheld(DoggyItems.CONDUCTING_BONE);

        generated(DoggyItems.CREATIVE_CANINE_TRACKER);
        generated(DoggyItems.CANINE_TRACKER);

        generated(DoggyItems.TANTAN_CAPE);
        generated(DoggyItems.CAPE_COLOURED);
        generated(DoggyItems.CREATIVE_COLLAR);
        generated(DoggyItems.DOGGY_CHARM);
        generated(DoggyItems.GUARD_SUIT);
        generated(DoggyItems.LEATHER_JACKET);
        generated(DoggyItems.MULTICOLOURED_COLLAR);
        generated(DoggyItems.AMNESIA_BONE);
        generated(DoggyItems.RADIO_COLLAR);
        generated(DoggyItems.SPOTTED_COLLAR);
        generated(DoggyItems.SUNGLASSES);       
        generated(DoggyItems.THROW_BONE);
        generated(DoggyItems.THROW_BONE_WET);
        generated(DoggyItems.THROW_STICK);
        generated(DoggyItems.THROW_STICK_WET);
        generated(DoggyItems.TREAT_BAG);
        generated(DoggyItems.WHISTLE);
        generated(DoggyItems.WOOL_COLLAR);
        generated(DoggyItems.BOWTIE);
        generated(DoggyItems.PIANIST_SUIT);
        generated(DoggyItems.SMARTY_GLASSES);
        generated(DoggyItems.WIG);
        generated(DoggyItems.BACH_WIG);
        generated(DoggyItems.DEATH_HOOD);
        generated(DoggyItems.CONAN_SUIT);
        generated(DoggyItems.BEASTARS_UNIFORM_FEMALE);
        generated(DoggyItems.BEASTARS_UNIFORM_MALE);
        generated(DoggyItems.EGG_SANDWICH);
        generated(DoggyItems.BANDAID);
        generated(DoggyItems.FEATHERED_MANTLE);
        generated(DoggyItems.EMPTY_LOCATOR_ORB);

        generated(DoggyItems.CHI_ORB);
        generated(DoggyItems.CHU_ORB);
        generated(DoggyItems.KO_ORB);
        generated(DoggyItems.GI_ORB);
        generated(DoggyItems.TEI_ORB);
        generated(DoggyItems.REI_ORB);
        generated(DoggyItems.SHIN_ORB);
        generated(DoggyItems.JIN_ORB);

        generated(DoggyItems.GENDER_BONE);

        generated(DoggyItems.MUSIC_DISC_BWV_849_FUGUE_KIMIKO);
        generated(DoggyItems.MUSIC_DISC_BWV_1080_FUGUE_11_KIMIKO);
        generated(DoggyItems.MUSIC_DISC_OKAMI_1);
        generated(DoggyItems.MUSIC_DISC_CHOPIN_OP64_NO1);

        blockItem(DoggyBlocks.DOG_BATH);
        blockItem(DoggyBlocks.DOG_BED);
        blockItem(DoggyBlocks.FOOD_BOWL);
    }

    private ResourceLocation itemTexture(Supplier<? extends ItemLike> item) {
        return modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item));
    }

    private String name(Supplier<? extends ItemLike> item) {
        return ForgeRegistries.ITEMS.getKey(item.get().asItem()).getPath();
    }

    private ItemModelBuilder blockItem(Supplier<? extends Block> block) {
        return blockItem(block, "");
    }

    private ItemModelBuilder radar(Supplier<? extends ItemLike> item) {
        return radar(item, itemTexture(item));
    }

    private ItemModelBuilder radar(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        ItemModelBuilder builder = generated(item, texture);
        builder.transforms().transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0, 0, 55F).translation(0, 4F, 0.5F).scale(0.85F);
        builder.transforms().transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(0, 0, -55F).translation(0, 4F, 0.5F).scale(0.85F);
        builder.transforms().transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).translation(-3.13F, 3.2F, 1.13F).scale(0.8F);
        builder.transforms().transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).translation(-3.13F, 3.2F, 1.13F).scale(0.8F);
        return builder;
    }

    private ItemModelBuilder generated(Supplier<? extends ItemLike> item) {
        return generated(item, itemTexture(item));
    }

    private ItemModelBuilder generated(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/generated")).texture("layer0", texture);
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item) {
        return handheld(item, itemTexture(item));
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/handheld")).texture("layer0", texture);
    }

    private ItemModelBuilder blockItem(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(name(block), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(block) + suffix));
    }
}
