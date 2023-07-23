package doggytalents.common.data;

import com.google.gson.JsonObject;
import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.DoggyRecipeSerializers;
import doggytalents.common.util.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.nio.file.Path;
import java.util.function.Consumer;

public class DTRecipeProvider extends RecipeProvider {

    public DTRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "DoggyTalents Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        //TODO
        ShapedRecipeBuilder.shaped(DoggyItems.THROW_BONE.get()).pattern(" X ").pattern("XYX").pattern(" X ").define('X', Items.BONE).define('Y', Items.SLIME_BALL).unlockedBy("has_bone", has(Items.BONE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.THROW_BONE.get()).requires(DoggyItems.THROW_BONE_WET.get(), 1).unlockedBy("has_throw_bone", has(DoggyItems.THROW_BONE.get())).save(consumer, Util.getResource("throw_bone_wet"));
        ShapedRecipeBuilder.shaped(DoggyItems.THROW_STICK.get(), 1).pattern(" X ").pattern("XYX").pattern(" X ").define('X', Items.STICK).define('Y', Items.SLIME_BALL).unlockedBy("has_slime_ball", has(Items.SLIME_BALL)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.THROW_STICK.get(), 1).requires(DoggyItems.THROW_STICK_WET.get(), 1).unlockedBy("has_throw_stick", has(DoggyItems.THROW_STICK.get())).save(consumer, Util.getResource("throw_stick_wet"));
        ShapelessRecipeBuilder.shapeless(DoggyItems.SUPER_TREAT.get(), 5).requires(DoggyItems.TRAINING_TREAT.get(), 5).requires(Items.GOLDEN_APPLE, 1).unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.DIRE_TREAT.get(), 1).requires(DoggyItems.MASTER_TREAT.get(), 5).requires(Blocks.END_STONE, 1).unlockedBy("has_master_treat", has(DoggyItems.MASTER_TREAT.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.BREEDING_BONE.get(), 2).requires(DoggyItems.MASTER_TREAT.get(), 1).requires(Items.COOKED_BEEF, 1).requires(Items.COOKED_PORKCHOP, 1).requires(Items.COOKED_CHICKEN, 1).requires(Items.COOKED_COD, 1).unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.MASTER_TREAT.get(), 5).requires(DoggyItems.SUPER_TREAT.get(), 5).requires(Items.DIAMOND, 1).unlockedBy("has_master_treat", has(DoggyItems.SUPER_TREAT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.TRAINING_TREAT.get(), 1).pattern("TUV").pattern("XXX").pattern("YYY").define('T', Items.STRING).define('U', Items.BONE).define('V', Items.GUNPOWDER).define('X', Items.SUGAR).define('Y', Items.WHEAT).unlockedBy("has_wheat", has(Items.WHEAT)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.AMNESIA_BONE.get(), 1)
            .pattern(" RN")
            .pattern("WBR")
            .pattern("SW ")
            .define('S', Items.SOUL_SOIL)
            .define('W', Items.NETHER_WART)
            .define('B', Items.BONE)
            .define('R', Items.BLAZE_ROD)
            .define('N', Items.NETHERITE_INGOT)
            .unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT))
            .save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.WHISTLE.get(), 1).pattern("IRI").pattern("II ").define('I', Items.IRON_INGOT).define('R', Items.REDSTONE).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyBlocks.FOOD_BOWL.get(), 1).pattern("XXX").pattern("XYX").pattern("XXX").define('X', Items.IRON_INGOT).define('Y', Items.BONE).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyBlocks.DOG_BATH.get(), 1).pattern("XXX").pattern("XYX").pattern("XXX").define('X', Items.IRON_INGOT).define('Y', Items.WATER_BUCKET).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.ENERGIZER_STICK.get(), 1)
            .pattern("SW")
            .pattern("WS").define('W', Items.WHEAT).define('S', Items.SUGAR).unlockedBy("has_sugar", has(Items.SUGAR)).save(consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.EGG_SANDWICH.get(), 1)
            .pattern(" B ")
            .pattern("EME")
            .pattern(" B ")
            .define('B', Items.BREAD)
            .define('E', Items.EGG)
            .define('M', Items.MILK_BUCKET)
            .unlockedBy("has_egg", has(Items.EGG))
            .save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.WOOL_COLLAR.get(), 1).pattern("SSS").pattern("S S").pattern("SSS").define('S', Items.STRING).unlockedBy("has_stick", has(Items.STRING)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.TREAT_BAG.get(), 1).pattern("LCL").pattern("LLL").define('L', Items.LEATHER).define('C', DoggyItems.ENERGIZER_STICK.get()).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.CAPE.get(), 1).pattern("S S").pattern("LWL").pattern("WLW").define('L', Items.LEATHER).define('S', Items.STRING).define('W', ItemTags.WOOL).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.CAPE_COLOURED.get(), 1).pattern("S S").pattern("LLL").pattern("LLL").define('L', Items.LEATHER).define('S', Items.STRING).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.GUARD_SUIT.get(), 1).pattern("S S").pattern("BWB").pattern("BWB").define('S', Items.STRING).define('W', Blocks.WHITE_WOOL).define('B', Blocks.BLACK_WOOL).unlockedBy("has_string", has(Items.STRING)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.PIANIST_SUIT.get(), 1)
            .pattern("GWG")
            .pattern("GDG")
            .pattern("BWB")
            .define('G', Blocks.GRAY_WOOL)
            .define('D', DoggyItems.CAPE_COLOURED.get())
            .define('W', Blocks.WHITE_WOOL)
            .define('B', Blocks.BLACK_WOOL)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.BOWTIE.get(), 1)
            .pattern("W W")
            .pattern("WSW")
            .define('W', Blocks.WHITE_WOOL)
            .define('S', Items.STRING)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.WIG.get(), 1)
            .pattern(" W ")
            .pattern("WCW")
            .pattern(" W ")
            .define('W', Blocks.WHITE_WOOL)
            .define('C', DoggyItems.WOOL_COLLAR.get())
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.SMARTY_GLASSES.get(), 1)
            .requires(DoggyItems.SUNGLASSES.get())
            .requires(Items.REDSTONE)
            .unlockedBy("has_redstone", has(Items.REDSTONE))
            .save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.DEATH_HOOD.get(), 1)
            .requires(DoggyItems.CAPE_COLOURED.get())
            .requires(Items.SOUL_TORCH)
            .requires(Items.NETHER_WART)
            .unlockedBy("has_soul_torch", has(Items.SOUL_TORCH))
            .save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.CONAN_SUIT.get(), 1)
            .requires(DoggyItems.CAPE_COLOURED.get())
            .requires(Items.PAPER)
            .requires(Items.RED_DYE)
            .requires(Items.BLUE_DYE)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.BEASTARS_UNIFORM_FEMALE.get(), 1)
            .requires(DoggyItems.CAPE_COLOURED.get())
            .requires(Items.PAPER)
            .requires(Items.RED_DYE)
            .requires(Items.PURPLE_DYE)
            .requires(Items.PINK_DYE)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.BEASTARS_UNIFORM_MALE.get(), 1)
            .requires(DoggyItems.CAPE_COLOURED.get())
            .requires(Items.PAPER)
            .requires(Items.RED_DYE)
            .requires(Items.PURPLE_DYE)
            .requires(Items.BLUE_DYE)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.LEATHER_JACKET.get(), 1).pattern("L L").pattern("LWL").pattern("LWL").define('L', Items.LEATHER).define('W', ItemTags.WOOL).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.SPOTTED_COLLAR.get(), 1).pattern("BWB").pattern("WCW").pattern("BSB").define('C', DoggyItems.WOOL_COLLAR.get()).define('B', Items.BLACK_DYE).define('W', Items.WHITE_DYE).define('S', Items.STRING).unlockedBy("has_wool_collar", has(DoggyItems.WOOL_COLLAR.get())).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.SPOTTED_COLLAR.get(), 1).pattern("WBW").pattern("BCB").pattern("WSW").define('C', DoggyItems.WOOL_COLLAR.get()).define('B', Items.BLACK_DYE).define('W', Items.WHITE_DYE).define('S', Items.STRING).unlockedBy("has_wool_collar", has(DoggyItems.WOOL_COLLAR.get())).save(consumer, Util.getResource("spotted_collar_alt"));
        ShapelessRecipeBuilder.shapeless(DoggyItems.MULTICOLOURED_COLLAR.get(), 1).requires(DoggyItems.WOOL_COLLAR.get()).requires(Items.STRING).requires(Items.BLUE_DYE).requires(Items.LIME_DYE).requires(Items.YELLOW_DYE).requires(Items.ORANGE_DYE).requires(Items.RED_DYE).requires(Items.PURPLE_DYE).unlockedBy("has_wool_collar", has(DoggyItems.WOOL_COLLAR.get())).save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.SUNGLASSES.get(), 1).pattern("S S").pattern("GSG").define('S', Items.STICK).define('G', Blocks.GLASS_PANE).unlockedBy("has_stick", has(Items.STICK)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.SHRINKING_MALLET.get(), 1)
            .pattern(" G ")
            .pattern("GFG")
            .pattern(" B ")
            .define('G', Items.GOLD_INGOT)
            .define('F', Items.FIRE_CORAL)
            .define('B', Items.BONE)
            .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
            .save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.MAGNIFYING_BONE.get(), 1)
            .pattern(" GF")
            .pattern("GPG")
            .pattern("BG ")
            .define('G', Items.GOLD_INGOT)
            .define('F', Items.FIRE_CORAL)
            .define('B', Items.BONE)
            .define('P', Items.GLASS_PANE)
            .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
            .save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.RADIO_COLLAR.get(), 1).pattern("XX").pattern("YX").define('X', Items.IRON_INGOT).define('Y', Items.REDSTONE).unlockedBy("has_redstone", has(Items.REDSTONE)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.RADAR.get(), 1).requires(Items.MAP, 1).requires(Items.REDSTONE, 1).requires(DoggyItems.RADIO_COLLAR.get(), 1).unlockedBy("has_redstone", has(Items.REDSTONE)).save(consumer);
        ShapedRecipeBuilder.shaped(DoggyItems.CONDUCTING_BONE.get(), 1).pattern(" B ").pattern("RBR").pattern("PEP").define('P', Items.SHULKER_SHELL).define('E', Items.END_CRYSTAL).define('B', Items.BONE).define('R', Items.NETHERITE_INGOT).unlockedBy("has_shulker_shell", has(Items.SHULKER_SHELL)).save(consumer);
        ShapelessRecipeBuilder.shapeless(DoggyItems.BANDAID.get(), 1)
            .requires(DoggyItems.TRAINING_TREAT.get())
            .requires(Items.PAPER)
            .requires(Items.SLIME_BALL)
            .requires(Items.BONE_MEAL, 2)
            .requires(Items.STRING)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(consumer);

        SpecialRecipeBuilder.special(DoggyRecipeSerializers.DOG_BED.get()).save(consumer, Util.getResourcePath("dog_bed"));
    }

    // @Override
    // protected void saveAdvancement(HashCache cache, JsonObject advancementJson, Path pathIn) {
    //     //NOOP - We dont replace any of the advancement things yet...
    // }
}
