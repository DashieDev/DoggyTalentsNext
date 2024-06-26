package doggytalents.client.data;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
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
        handheld(DoggyItems.KAMI_TREAT);
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
        generated(DoggyItems.WOOL_COLLAR_THICC);
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

        generated(DoggyItems.HOT_DOG);
        generated(DoggyItems.GIANT_STICK);
        generated(DoggyItems.GOLDEN_A_FIVE_WAGYU);
        generated(DoggyItems.SUSSY_SICKLE);
        generated(DoggyItems.SNORKEL);
        generated(DoggyItems.TENGU_MASK);
        generated(DoggyItems.KITSUNE_MASK);
        generated(DoggyItems.DEMON_HORNS);
        generated(DoggyItems.WITCH_HAT);
        generated(DoggyItems.PLAGUE_DOC_MASK);
        generated(DoggyItems.BAKER_HAT);
        generated(DoggyItems.CHEF_HAT);
        generated(DoggyItems.SAUSAGE);
        generated(DoggyItems.LAB_COAT);
        contactLens(DoggyItems.DOGGY_CONTACTS);

        
        generated(DoggyItems.RICE_BOWL);
        generated(DoggyItems.SALMON_SUSHI);
        generated(DoggyItems.ONIGIRI);

        generated(DoggyItems.CHI_ORB);
        generated(DoggyItems.CHU_ORB);
        generated(DoggyItems.KO_ORB);
        generated(DoggyItems.GI_ORB);
        generated(DoggyItems.TEI_ORB);
        generated(DoggyItems.REI_ORB);
        generated(DoggyItems.SHIN_ORB);
        generated(DoggyItems.JIN_ORB);
        dyableOrb(DoggyItems.DYED_ORB);

        generated(DoggyItems.GENDER_BONE);
        generated(DoggyItems.STARTER_BUNDLE);

        generated(DoggyItems.HEAD_BAND_BLANK);
        generated(DoggyItems.HEAD_BAND_MYSTERY);
        generated(DoggyItems.HEAD_BAND_HIGHHH);

        generated(DoggyItems.SUPERDOG_SUIT);
        generated(DoggyItems.FLYING_CAPE);
        generated(DoggyItems.BAT_WINGS);
        generated(DoggyItems.CROW_WINGS);
        generated(DoggyItems.DIVINE_RETRIBUTON);
        generated(DoggyItems.SOUL_REFLECTOR);
        generated(DoggyItems.ANGEL_WINGS);
        generated(DoggyItems.ANGEL_HALO);
        generated(DoggyItems.YETI_GOOSE);

        generated(DoggyItems.MUSIC_DISC_BWV_849_FUGUE_KIMIKO);
        generated(DoggyItems.MUSIC_DISC_BWV_1080_FUGUE_11_KIMIKO);
        generated(DoggyItems.MUSIC_DISC_OKAMI_1);
        generated(DoggyItems.MUSIC_DISC_CHOPIN_OP64_NO1);

        generated(DoggyItems.FRISBEE);
        wetFrisbee(DoggyItems.FRISBEE_WET);
        birthdayHat(DoggyItems.BIRTHDAY_HAT);
        generated(DoggyItems.PROPELLER_HAT);
        generated(DoggyItems.FEDORA);
        generated(DoggyItems.FLATCAP);
        
        ceremonialGarb(DoggyItems.CERE_GARB);

        generated(DoggyItems.RICE_GRAINS);
        generated(DoggyItems.RICE_WHEAT);
        generated(DoggyItems.KOJI);
        generated(DoggyItems.UNCOOKED_RICE);
        generated(DoggyItems.UNCOOKED_RICE_BOWL);
        generated(DoggyItems.SOY_BEANS);
        generated(DoggyItems.SOY_PODS);
        generated(DoggyItems.SOY_BEANS_DRIED);
        generated(DoggyItems.SOY_PODS_DRIED);
        generated(DoggyItems.EDAMAME);
        generated(DoggyItems.EDAMAME_UNPODDED);
        generated(DoggyItems.SOY_MILK);
        generated(DoggyItems.TOFU);
        generated(DoggyItems.ABURAAGE);
        generated(DoggyItems.MISO_PASTE);
        generated(DoggyItems.MISO_SOUP);
        generated(DoggyItems.NATTO);
        generated(DoggyItems.NATTO_RICE);
        generated(DoggyItems.ONSEN_TAMAGO);
        generated(DoggyItems.GYUDON);
        generated(DoggyItems.OYAKODON);
        generated(DoggyItems.SAKE);

        generated(DoggyItems.SCENT_TREAT);
        scentTreatDrooled(DoggyItems.DROOL_SCENT_TREAT, DoggyItems.SCENT_TREAT);

        generated(DoggyItems.GRAND_PIANO_BLACK);
        generated(DoggyItems.GRAND_PIANO_WHITE);
        generated(DoggyItems.UPRIGHT_PIANO_BLACK);
        generated(DoggyItems.UPRIGHT_PIANO_BROWN);
        plushieToy(DoggyItems.DOG_PLUSHIE_TOY);

        blockItem(DoggyBlocks.DOG_BATH);
        blockItem(DoggyBlocks.DOG_BED);
        blockItem(DoggyBlocks.FOOD_BOWL);
        generated(DoggyBlocks.RICE_MILL);
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

    private ItemModelBuilder wetFrisbee(Supplier<? extends ItemLike> item) {
        return generated2(item, modLoc(ModelProvider.ITEM_FOLDER + "/frisbee"), modLoc(ModelProvider.ITEM_FOLDER + "/frisbee_overlay"));
    }

    private ItemModelBuilder birthdayHat(Supplier<? extends ItemLike> item) {
        return generated2(item, modLoc(ModelProvider.ITEM_FOLDER + "/birthday_hat_bg"), modLoc(ModelProvider.ITEM_FOLDER + "/birthday_hat_fg"));
    }

    private ItemModelBuilder ceremonialGarb(Supplier<? extends ItemLike> item) {
        return generated2(item, modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item)), modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item) + "_overlay"));
    }

    private ItemModelBuilder plushieToy(Supplier<? extends ItemLike> item) {
        return generated2(item, modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item)), modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item) + "_overlay"));
    }

    private ItemModelBuilder scentTreatDrooled(Supplier<? extends ItemLike> item, Supplier<? extends ItemLike> base) {
        return generated2(item, modLoc(ModelProvider.ITEM_FOLDER + "/" + name(base)), modLoc(ModelProvider.ITEM_FOLDER + "/" + name(base) + "_overlay"));
    }    

    private ItemModelBuilder contactLens(Supplier<? extends ItemLike> item) {
        return generated2(item, modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item)), modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item) + "_overlay"));
    }

    private ItemModelBuilder dyableOrb(Supplier<? extends ItemLike> item) {
        return generated2(item, modLoc(ModelProvider.ITEM_FOLDER + "/locator_orb_dyable_bg"), modLoc(ModelProvider.ITEM_FOLDER + "/locator_orb_dyable_fg"));
    }

    private ItemModelBuilder generated2(Supplier<? extends ItemLike> item, ResourceLocation tex0, ResourceLocation tex1) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/generated"))
            .texture("layer0", tex0)
            .texture("layer1", tex1);
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
