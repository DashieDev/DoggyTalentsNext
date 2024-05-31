package doggytalents.common.data;

import com.google.gson.JsonObject;
import doggytalents.DoggyBlocks;
import doggytalents.DoggyItems;
import doggytalents.DoggyRecipeSerializers;
import doggytalents.common.item.VariantChangerItem;
import doggytalents.common.util.Util;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DTRecipeProvider extends RecipeProvider {

    public DTRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(DoggyItems.THROW_BONE.get())
            .pattern(" X ")
            .pattern("XYX")
            .pattern(" X ").define('X', Items.BONE)
            .define('Y', Items.SLIME_BALL)
            .unlockedBy("has_bone", has(Items.BONE))
            .save(consumer);
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.THROW_BONE.get())
            .requires(DoggyItems.THROW_BONE_WET.get(), 1)
            .unlockedBy("has_throw_bone", has(DoggyItems.THROW_BONE.get()))
            .save(consumer, Util.getResource("throw_bone_wet"));
            
        ShapedRecipeBuilder.shaped(DoggyItems.THROW_STICK.get(), 1)
            .pattern(" X ")
            .pattern("XYX")
            .pattern(" X ")
            .define('X', Items.STICK)
            .define('Y', Items.SLIME_BALL)
            .unlockedBy("has_slime_ball", has(Items.SLIME_BALL))
            .save(consumer);
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.THROW_STICK.get(), 1)
            .requires(DoggyItems.THROW_STICK_WET.get(), 1)
            .unlockedBy("has_throw_stick", has(DoggyItems.THROW_STICK.get()))
            .save(consumer, Util.getResource("throw_stick_wet"));
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.SUPER_TREAT.get(), 5)
            .requires(DoggyItems.TRAINING_TREAT.get(), 5)
            .requires(Items.GOLDEN_APPLE, 1)
            .unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE))
            .save(consumer);
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.KAMI_TREAT.get(), 1)
            .requires(DoggyItems.MASTER_TREAT.get(), 5)
            .requires(Blocks.END_STONE, 1)
            .unlockedBy("has_master_treat", has(DoggyItems.MASTER_TREAT.get()))
            .save(consumer);
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.BREEDING_BONE.get(), 2)
            .requires(DoggyItems.MASTER_TREAT.get(), 1)
            .requires(Items.COOKED_BEEF, 1)
            .requires(Items.COOKED_PORKCHOP, 1)
            .requires(Items.COOKED_CHICKEN, 1)
            .requires(Items.COOKED_COD, 1)
            .unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP))
            .save(consumer);
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.MASTER_TREAT.get(), 5)
            .requires(DoggyItems.SUPER_TREAT.get(), 5)
            .requires(Items.DIAMOND, 1)
            .unlockedBy("has_master_treat", has(DoggyItems.SUPER_TREAT.get()))
            .save(consumer);
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.TRAINING_TREAT.get(), 3)
            .requires(Items.STRING)
            .requires(Items.BONE)
            .requires(Items.GUNPOWDER)
            .requires(Items.SUGAR)
            .requires(DoggyItems.UNCOOKED_RICE.get())
            .unlockedBy("has_dtn_rice_grains", has(DoggyItems.RICE_GRAINS.get()))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.WHISTLE.get(), 1)
            .pattern("IRI")
            .pattern("II ")
            .define('I', Items.IRON_INGOT)
            .define('R', Items.REDSTONE)
            .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyBlocks.FOOD_BOWL.get(), 1)
            .pattern("XXX")
            .pattern("XYX")
            .pattern("XXX")
            .define('X', Items.IRON_INGOT)
            .define('Y', Items.BOWL)
            .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyBlocks.DOG_BATH.get(), 1)
            .pattern("XXX")
            .pattern("XYX")
            .pattern("XXX")
            .define('X', Items.IRON_INGOT)
            .define('Y', Items.WATER_BUCKET)
            .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.WOOL_COLLAR.get(), 1)
            .pattern("SSS")
            .pattern("S S")
            .pattern("SSS")
            .define('S', Items.STRING)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.WOOL_COLLAR_THICC.get(), 1)
            .requires(DoggyItems.WOOL_COLLAR.get(), 2)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.TREAT_BAG.get(), 1)
            .pattern("LCL")
            .pattern("LLL")
            .define('L', Items.LEATHER)
            .define('C', DoggyItems.ENERGIZER_STICK.get())
            .unlockedBy("has_leather", has(Items.LEATHER))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.GUARD_SUIT.get(), 1)
            .pattern("S S")
            .pattern("BWB")
            .pattern("BWB")
            .define('S', Items.STRING)
            .define('W', Blocks.WHITE_WOOL)
            .define('B', Blocks.BLACK_WOOL)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.LEATHER_JACKET.get(), 1)
            .pattern("L L")
            .pattern("LWL")
            .pattern("LWL")
            .define('L', Items.LEATHER)
            .define('W', ItemTags.WOOL)
            .unlockedBy("has_leather", has(Items.LEATHER))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.SPOTTED_COLLAR.get(), 1)
            .pattern("BWB")
            .pattern("WCW")
            .pattern("BSB")
            .define('C', DoggyItems.WOOL_COLLAR.get())
            .define('B', Items.BLACK_DYE)
            .define('W', Items.WHITE_DYE)
            .define('S', Items.STRING)
            .unlockedBy("has_wool_collar", has(DoggyItems.WOOL_COLLAR.get()))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.SPOTTED_COLLAR.get(), 1)
            .pattern("WBW")
            .pattern("BCB")
            .pattern("WSW")
            .define('C', DoggyItems.WOOL_COLLAR.get())
            .define('B', Items.BLACK_DYE)
            .define('W', Items.WHITE_DYE).define('S', Items.STRING)
            .unlockedBy("has_wool_collar", has(DoggyItems.WOOL_COLLAR.get()))
            .save(consumer, Util.getResource("spotted_collar_alt"));
            
        ShapelessRecipeBuilder.shapeless(DoggyItems.MULTICOLOURED_COLLAR.get(), 1)
            .requires(DoggyItems.WOOL_COLLAR.get())
            .requires(Items.STRING)
            .requires(Items.BLUE_DYE)
            .requires(Items.LIME_DYE)
            .requires(Items.YELLOW_DYE)
            .requires(Items.ORANGE_DYE)
            .requires(Items.RED_DYE)
            .requires(Items.PURPLE_DYE)
            .unlockedBy("has_wool_collar", has(DoggyItems.WOOL_COLLAR.get()))
            .save(consumer);
            
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
            
        ShapedRecipeBuilder.shaped(DoggyItems.ENERGIZER_STICK.get(), 1)
            .pattern("SW")
            .pattern("WS").define('W', Items.WHEAT).define('S', Items.SUGAR).unlockedBy("has_sugar", has(Items.SUGAR)).save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.EGG_SANDWICH.get(), 3)
            .pattern(" B ")
            .pattern("EEE")
            .pattern(" B ")
            .define('B', Items.BREAD)
            .define('E', Items.EGG)
            .unlockedBy("has_egg", has(Items.EGG))
            .save(consumer);
                    
        ShapedRecipeBuilder.shaped(DoggyItems.TANTAN_CAPE.get(), 1)
            .pattern("S S")
            .pattern("RBR")
            .pattern("BYB")
            .define('R', Items.RED_WOOL)
            .define('S', Items.STRING)
            .define('B', Items.BLUE_WOOL)
            .define('Y', Items.YELLOW_WOOL)
            .unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.CAPE_COLOURED.get(), 1)
            .pattern("S S")
            .pattern("LWL")
            .pattern("WLW")
            .define('L', Items.LEATHER)
            .define('W', Items.WHITE_WOOL)
            .define('S', Items.STRING)
            .unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.BOWTIE.get(), 4)  
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
            
        ShapedRecipeBuilder.shaped(DoggyItems.BACH_WIG.get(), 1)
            .pattern("SSS")
            .pattern("SBS")
            .pattern("S S")
            .define('S', Items.STRING)
            .define('B', Items.BONE)
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
            
        ShapedRecipeBuilder.shaped(DoggyItems.SUNGLASSES.get(), 1)
            .pattern("S S")
            .pattern("GSG")
            .define('S', Items.STICK)
            .define('G', Blocks.BLACK_STAINED_GLASS_PANE)
            .unlockedBy("has_stick", has(Items.STICK)).save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.RADIO_COLLAR.get(), 1)
            .pattern("XX")
            .pattern("YX")
            .define('X', Items.IRON_INGOT)
            .define('Y', Items.REDSTONE)
            .unlockedBy("has_redstone", has(Items.REDSTONE)).save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.CONAN_SUIT.get(), 1)
            .pattern("BZB")
            .pattern("LCL")
            .pattern("R R")
            .define('L', Items.LIGHT_BLUE_WOOL)
            .define('Z', DoggyItems.BOWTIE.get())
            .define('R', Items.RED_WOOL)
            .define('B', Items.BLUE_WOOL)
            .define('C', DoggyItems.CAPE_COLOURED.get())
            .unlockedBy("has_wool", has(Items.WHITE_WOOL))
            .save(consumer); 

        ShapedRecipeBuilder.shaped(DoggyItems.BEASTARS_UNIFORM_FEMALE.get(), 1)
            .pattern("WBW")
            .pattern("WLW")
            .pattern("CCC")
            .define('W', Items.WHITE_WOOL)
            .define('B', DoggyItems.BOWTIE.get())
            .define('C', Items.WHITE_CARPET)
            .define('L', DoggyItems.CAPE_COLOURED.get())
            .unlockedBy("has_wool", has(Items.WHITE_WOOL))
            .save(consumer); 

        ShapedRecipeBuilder.shaped(DoggyItems.BEASTARS_UNIFORM_MALE.get(), 1)
            .pattern("BCB")
            .pattern("WGW")
            .pattern("R R")
            .define('B', Items.BLUE_WOOL)
            .define('W', Items.WHITE_WOOL)
            .define('R', Items.BROWN_WOOL)
            .define('G', Items.GRAY_WOOL)
            .define('C', DoggyItems.CAPE_COLOURED.get())
            .unlockedBy("has_wool", has(Items.WHITE_WOOL))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.CONDUCTING_BONE.get(), 1)
            .pattern(" B ")
            .pattern("RBR")
            .pattern("PEP")
            .define('P', Items.SHULKER_SHELL)
            .define('E', Items.END_CRYSTAL)
            .define('B', Items.BONE)
            .define('R', Items.NETHERITE_INGOT)
            .unlockedBy("has_bone", has(Items.BONE))
            .save(consumer);  
        
        ShapedRecipeBuilder.shaped(DoggyItems.SHRINKING_MALLET.get(), 1)
            .pattern("GGG")
            .pattern("GFG")
            .pattern(" B ")
            .define('G', Items.GOLD_INGOT)
            .define('F', Items.REDSTONE_BLOCK)
            .define('B', Items.BONE)
            .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.MAGNIFYING_BONE.get(), 1)
            .pattern(" GF")
            .pattern("GPG")
            .pattern("BG ")
            .define('G', Items.GOLD_INGOT)
            .define('F', Items.REDSTONE_BLOCK)
            .define('B', Items.BONE)
            .define('P', Items.GLASS_PANE)
            .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.CANINE_TRACKER.get(), 1)
            .pattern(" GC")
            .pattern("GMG")
            .pattern(" G ")
            .define('G', Items.GOLD_INGOT)
            .define('C', DoggyItems.RADIO_COLLAR.get())
            .define('M', Items.MAP)
            .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.BANDAID.get(), 5)
            .requires(Items.PAPER, 2)
            .requires(DoggyItems.KOJI.get())
            .requires(Items.BONE_MEAL)
            .unlockedBy("has_koji", has(DoggyItems.KOJI.get()))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.FEATHERED_MANTLE.get(), 1)
            .pattern(" FP")
            .pattern("FTF")
            .pattern("PF ")
            .define('F', Items.FEATHER)
            .define('P', Items.PHANTOM_MEMBRANE)
            .define('T', DoggyItems.SUPER_TREAT.get())
            .unlockedBy("has_feather", has(Items.FEATHER))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.EMPTY_LOCATOR_ORB.get(), 1)
            .pattern(" C ")
            .pattern("SRS")
            .pattern(" G ")
            .define('C', Items.WHITE_CARPET)
            .define('G', Items.GLASS)
            .define('R', DoggyItems.RADIO_COLLAR.get())
            .define('S', Items.STRING)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.CHI_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.MAGENTA_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.CHU_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.LIGHT_BLUE_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.KO_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.PURPLE_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.GI_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.BLUE_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.TEI_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.YELLOW_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.REI_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.LIME_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.SHIN_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.GREEN_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.JIN_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.RED_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.DYED_ORB.get(), 1)
            .requires(DoggyItems.EMPTY_LOCATOR_ORB.get())
            .requires(Items.PAPER)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.GENDER_BONE.get(), 1)
            .pattern(" LB")
            .pattern("MAL")
            .pattern("PM ")
            .define('L', Items.LAPIS_LAZULI)
            .define('B', Items.BLUE_DYE)
            .define('A', DoggyItems.AMNESIA_BONE.get())
            .define('P', Items.PINK_DYE)
            .define('M', Items.AMETHYST_SHARD)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        SpecialRecipeBuilder.special(DoggyRecipeSerializers.DOG_BED.get()).save(consumer, Util.getResourcePath("dog_bed"));
        SpecialRecipeBuilder.special(DoggyRecipeSerializers.DOUBLE_DYABLE.get()).save(consumer, Util.getResourcePath("birthday_hat"));

        ShapedRecipeBuilder.shaped(DoggyItems.HOT_DOG.get(), 1)
            .pattern("RTY")
            .pattern("BCB")
            .define('R', Items.RED_DYE)
            .define('Y', Items.YELLOW_DYE)
            .define('B', Items.BREAD)
            .define('C', DoggyItems.SAUSAGE.get())
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .unlockedBy("has_bread", has(Items.BREAD))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.GIANT_STICK.get(), 1)
            .pattern(" S ")
            .pattern(" S ")
            .pattern(" S ")
            .define('S', Items.STICK)
            .unlockedBy("has_stick", has(Items.STICK))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.GOLDEN_A_FIVE_WAGYU.get(), 1)
            .pattern(" GT")
            .pattern("GSG")
            .pattern(" G ")
            .define('G', Items.GOLD_NUGGET)
            .define('S', Items.COOKED_BEEF)
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .unlockedBy("has_cooked_beef", has(Items.COOKED_BEEF))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.SUSSY_SICKLE.get(), 1)
            .pattern("III")
            .pattern(" SI")
            .pattern("S  ")
            .define('I', Items.IRON_INGOT)
            .define('S', Items.STICK)
            .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.SNORKEL.get(), 1)
            .pattern("  B")
            .pattern("GIG")
            .define('I', Items.IRON_INGOT)
            .define('G', Items.GLASS_PANE)
            .define('B', Items.BAMBOO)            
            .unlockedBy("has_bamboo", has(Items.BAMBOO))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.HEAD_BAND_BLANK.get(), 1)
            .pattern("L")
            .pattern("P")
            .define('L', Items.LEAD)
            .define('P', Items.PAPER)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.FRISBEE.get(), 1)
            .pattern("SBS")
            .define('S', ItemTags.SLABS)
            .define('B', DoggyItems.THROW_BONE.get())
            .unlockedBy("has_slime_ball", has(Items.SLIME_BALL))
            .save(consumer); 

        ShapedRecipeBuilder.shaped(DoggyItems.SAUSAGE.get(), 3)
            .pattern("SSS")
            .define('S', Items.COOKED_PORKCHOP)
            .unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.KITSUNE_MASK.get(), 1)
            .requires(DoggyItems.HEAD_BAND_BLANK.get())
            .requires(Items.RED_DYE)
            .requires(Items.WHITE_DYE, 2)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.TENGU_MASK.get(), 1)
            .requires(DoggyItems.HEAD_BAND_BLANK.get())
            .requires(Items.RED_DYE, 2)
            .requires(Items.BLACK_DYE)
            .unlockedBy("has_string", has(Items.STRING))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.BAKER_HAT.get(), 1)
            .pattern("WWW")
            .pattern("WCW")
            .define('C', Items.LEATHER_HELMET)
            .define('W', ItemTags.WOOL)
            .unlockedBy("has_bread", has(Items.BREAD))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.CHEF_HAT.get(), 1)
            .pattern("WWW")
            .pattern("WWW")
            .pattern("WCW")
            .define('C', Items.LEATHER_HELMET)
            .define('W', ItemTags.WOOL)
            .unlockedBy("has_leather_helmet", has(Items.LEATHER_HELMET))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.DEMON_HORNS.get(), 1)
            .pattern("SRS")
            .pattern("BCB")
            .define('C', Items.LEATHER_HELMET)
            .define('S', Items.STICK)
            .define('R', Items.NETHER_WART)
            .define('B', Items.BLACK_DYE)
            .unlockedBy("has_leather_helmet", has(Items.LEATHER_HELMET))
            .save(consumer);
        
        ShapelessRecipeBuilder.shapeless(DoggyItems.UNCOOKED_RICE_BOWL.get(), 1)
            .requires(Items.BOWL)
            .requires(DoggyItems.UNCOOKED_RICE.get(), 5)
            .unlockedBy("has_dtn_rice_grains", has(DoggyItems.RICE_GRAINS.get()))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.PLAGUE_DOC_MASK.get(), 1)
            .pattern("CWC")
            .pattern(" S ")
            .pattern("LFL")
            .define('C', Items.BLACK_CARPET)
            .define('W', Items.BLACK_WOOL)
            .define('L', Items.LEATHER)
            .define('F', ItemTags.FLOWERS)
            .define('S', DoggyItems.SNORKEL.get())
            .unlockedBy("has_leather", has(Items.LEATHER))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.SALMON_SUSHI.get(), 1)
            .pattern("S")
            .pattern("R")
            .define('S', Items.SALMON)
            .define('R', DoggyItems.RICE_BOWL.get())
            .unlockedBy("has_rice_bowl", has(DoggyItems.RICE_BOWL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.CROW_WINGS.get(), 1)
            .pattern("FBF")
            .pattern("BEB")
            .pattern("FBF")
            .define('F', Items.FEATHER)
            .define('B', Items.BLACK_DYE)
            .define('E', Items.ELYTRA)
            .unlockedBy("has_elytra", has(Items.ELYTRA))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.CROW_WINGS.get(), 1)
            .pattern("FBF")
            .pattern("BEB")
            .pattern("FBF")
            .define('B', Items.FEATHER)
            .define('F', Items.BLACK_DYE)
            .define('E', Items.ELYTRA)
            .unlockedBy("has_elytra", has(Items.ELYTRA))
            .save(consumer, Util.getResource("crow_wings_alt"));

        ShapedRecipeBuilder.shaped(DoggyItems.FLYING_CAPE.get(), 1)
            .pattern(" W ")
            .pattern("WEW")
            .pattern(" W ")
            .define('W', ItemTags.WOOL)
            .define('E', Items.ELYTRA)
            .unlockedBy("has_elytra", has(Items.ELYTRA))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.SUPERDOG_SUIT.get(), 1)
            .requires(Items.YELLOW_DYE)
            .requires(Items.RED_DYE)
            .requires(Items.BLUE_DYE)
            .requires(Items.IRON_INGOT)
            .requires(DoggyItems.LEATHER_JACKET.get())
            .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.BAT_WINGS.get(), 1)
            .pattern("BLB")
            .pattern("LEL")
            .pattern("BLB")
            .define('B', Items.BROWN_DYE)
            .define('L', Items.LEATHER)
            .define('E', Items.ELYTRA)
            .unlockedBy("has_elytra", has(Items.ELYTRA))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.WITCH_HAT.get(), 1)
            .pattern(" W ")
            .pattern("WPW")
            .pattern("CCC")
            .define('W', Items.BLACK_WOOL)
            .define('C', Items.BLACK_CARPET)
            .define('P', Items.POTION)
            .unlockedBy("has_potion", has(Items.POTION))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.ONIGIRI.get(), 1)
            .pattern("R")
            .pattern("K")
            .define('R', DoggyItems.RICE_BOWL.get())
            .define('K', Items.DRIED_KELP)
            .unlockedBy("has_rice_bowl", has(DoggyItems.RICE_BOWL.get()))
            .save(consumer);
            
        ShapedRecipeBuilder.shaped(DoggyItems.DIVINE_RETRIBUTON.get(), 1)
            .pattern("MPM")
            .pattern("CXC")
            .pattern("BPB")
            .define('C', Items.COPPER_INGOT)
            .define('P', Items.PHANTOM_MEMBRANE)
            .define('M', Items.MAGMA_CREAM)
            .define('B', Items.BLAZE_POWDER)
            .define('X', DoggyItems.SAKE.get())
            .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.SOUL_REFLECTOR.get(), 1)
            .pattern(" S ")
            .pattern("SXS")
            .pattern(" S ")
            .define('S', Items.SOUL_SAND)
            .define('X', DoggyItems.DIVINE_RETRIBUTON.get())
            .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.LAB_COAT.get(), 1)
            .pattern(" C ")
            .pattern(" W ")
            .pattern("WWW")
            .define('C', DoggyItems.CAPE_COLOURED.get())
            .define('W', ItemTags.WOOL)
            .unlockedBy("has_wool", has(ItemTags.WOOL))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.BIRTHDAY_HAT.get(), 1)
            .pattern(" P ")
            .pattern("PTP")
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .define('P', Items.PAPER)
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(consumer, Util.getResource("birthday_hat_alt"));

        ShapedRecipeBuilder.shaped(DoggyBlocks.DOG_BED.get(), 1)
            .pattern("WDW")
            .pattern("WDW")
            .pattern("WWW")
            .define('W', Blocks.SPRUCE_PLANKS)
            .define('D', Blocks.WHITE_WOOL)
            .unlockedBy("has_wool", has(ItemTags.WOOL))
            .save(consumer, Util.getResource("dog_bed_def"));

        ShapedRecipeBuilder.shaped(DoggyItems.SOY_MILK.get(), 1)
            .pattern("SSS")
            .pattern("SBS")
            .define('S', DoggyItems.SOY_BEANS_DRIED.get())
            .define('B', Items.BOWL)
            .unlockedBy("has_dtn_soy_beans", has(DoggyItems.SOY_BEANS.get()))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.TOFU.get(), 8)
            .pattern("SSS")
            .pattern("SSS")
            .define('S', DoggyItems.SOY_MILK.get())
            .unlockedBy("has_dtn_soy_beans", has(DoggyItems.SOY_BEANS.get()))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.MISO_PASTE.get(), 1)
            .requires(DoggyItems.SOY_BEANS.get())
            .requires(DoggyItems.KOJI.get())
            .requires(Items.RED_MUSHROOM)
            .unlockedBy("has_dtn_soy_beans", has(DoggyItems.SOY_BEANS.get()))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.NATTO.get(), 1)
            .requires(DoggyItems.SOY_BEANS.get())
            .requires(DoggyItems.KOJI.get())
            .requires(Items.BROWN_MUSHROOM)
            .unlockedBy("has_dtn_soy_beans", has(DoggyItems.SOY_BEANS.get()))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.KOJI.get(), 3)
            .requires(DoggyItems.KOJI.get())
            .requires(DoggyItems.UNCOOKED_RICE.get())
            .requires(Items.SUGAR)
            .unlockedBy("has_dtn_koji", has(DoggyItems.KOJI.get()))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.MISO_SOUP.get(), 1)
            .requires(DoggyItems.MISO_PASTE.get())
            .requires(DoggyItems.TOFU.get())
            .requires(Items.BOWL)
            .requires(Items.DRIED_KELP)
            .unlockedBy("has_dtn_soy_beans", has(DoggyItems.SOY_BEANS.get()))
            .save(consumer);

        ShapelessRecipeBuilder.shapeless(DoggyItems.EDAMAME.get(), 3)
            .requires(DoggyItems.SOY_PODS.get(), 3)
            .requires(Items.SUGAR)
            .unlockedBy("has_dtn_soy_pods", has(DoggyItems.SOY_PODS.get()))
            .save(consumer);

        registerTripleCooking(consumer, 
            Ingredient.of(DoggyItems.SOY_PODS.get()), 
            DoggyItems.SOY_PODS_DRIED.get(), 0.35F, 200, "has_dtn_soy_pods", 
            has(DoggyItems.SOY_PODS.get()));

        registerTripleCooking(consumer, 
            Ingredient.of(DoggyItems.SOY_BEANS.get()), 
            DoggyItems.SOY_BEANS_DRIED.get(), 0.1F, 100, "has_dtn_soy_beans", 
            has(DoggyItems.SOY_BEANS.get()));

        registerTripleCooking(consumer, 
            Ingredient.of(DoggyItems.UNCOOKED_RICE_BOWL.get()), 
            DoggyItems.RICE_BOWL.get(), 0.1F, 100, "has_dtn_uncooked_rice_bowl", 
            has(DoggyItems.UNCOOKED_RICE_BOWL.get()));

        registerTripleCooking(consumer, 
            Ingredient.of(DoggyItems.TOFU.get()), 
            DoggyItems.ABURAAGE.get(), 0.1F, 100, "has_dtn_tofu", 
            has(DoggyItems.TOFU.get()));

        ShapelessRecipeBuilder.shapeless(DoggyItems.RICE_GRAINS.get(), 3)
            .requires(DoggyItems.RICE_WHEAT.get())
            .unlockedBy("has_dtn_rice_wheat", has(DoggyItems.RICE_WHEAT.get()))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.GYUDON.get(), 1)
            .pattern("BOB")
            .pattern(" R ")
            .define('R', DoggyItems.RICE_BOWL.get())
            .define('B', Items.COOKED_BEEF)
            .define('O', DoggyItems.ONSEN_TAMAGO.get())
            .unlockedBy("has_rice_bowl", has(DoggyItems.RICE_BOWL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.OYAKODON.get(), 1)
            .pattern("COC")
            .pattern(" R ")
            .define('R', DoggyItems.RICE_BOWL.get())
            .define('C', Items.EGG)
            .define('O', Items.COOKED_CHICKEN)
            .unlockedBy("has_rice_bowl", has(DoggyItems.RICE_BOWL.get()))
            .save(consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.NATTO_RICE.get(), 1)
            .pattern("N")
            .pattern("B")
            .define('N', DoggyItems.NATTO.get())
            .define('B', DoggyItems.RICE_BOWL.get())
            .unlockedBy("has_rice_bowl", has(DoggyItems.RICE_BOWL.get()))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.CERE_GARB.get(), 1)
            .pattern(" L ")
            .pattern("CWC")
            .pattern(" C ")
            .define('L', Items.LEAD)
            .define('W', DoggyItems.WOOL_COLLAR.get())
            .define('C', ItemTags.WOOL_CARPETS)
            .unlockedBy("has_lead", has(Items.LEAD))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.DOGGY_CONTACTS.get(), 1)
            .pattern("GKG")
            .define('G', Items.GLASS_PANE)
            .define('K', DoggyItems.SAKE.get())
            .unlockedBy("has_dtn_koji", has(DoggyItems.KOJI.get()))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.SCENT_TREAT.get(), 1)
            .pattern(" W ")
            .pattern("WTW")
            .pattern(" W ")
            .define('W', ItemTags.WOOL)
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .unlockedBy("has_dtn_training_treat", has(DoggyItems.TRAINING_TREAT.get()))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.FEDORA.get(), 1)
            .pattern(" BW")
            .pattern("WCW")
            .pattern(" T ")
            .define('B', Items.BLACK_WOOL)
            .define('W', Items.BROWN_WOOL)
            .define('C', Items.LEATHER_HELMET)
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .unlockedBy("has_leather_helmet", has(Items.LEATHER_HELMET))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.FLATCAP.get(), 1)
            .pattern("  C")
            .pattern("CTC")
            .define('C', Items.WHITE_CARPET)
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .unlockedBy("has_white_carpet", has(Items.WHITE_CARPET))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyItems.PROPELLER_HAT.get(), 1)
            .pattern("BBB")
            .pattern("RIL")
            .pattern("GTY")
            .define('B', Items.BAMBOO)
            .define('G', Items.GREEN_WOOL)
            .define('L', Items.BLUE_WOOL)
            .define('R', Items.RED_WOOL)
            .define('Y', Items.YELLOW_WOOL)
            .define('I', Items.IRON_INGOT)
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
            .save(consumer);

        registerVariantChanger(DoggyItems.VARIANT_CHANGER_PALE, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_CHESNUT, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_STRIPED, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_WOODS, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_RUSTY, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_ASHEN, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_SNOWY, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_SPOTTED, consumer);
        registerVariantChanger(DoggyItems.VARIANT_CHANGER_BLACK, consumer);
        
        ShapedRecipeBuilder.shaped(DoggyItems.DOG_PLUSHIE_TOY.get(), 1)
            .pattern("WW ")
            .pattern("SCG")
            .pattern("WTW")
            .define('G', Items.LIGHT_GRAY_WOOL)
            .define('W', Items.WHITE_WOOL)
            .define('S', Items.STRING)
            .define('C', DoggyItems.WOOL_COLLAR.get())
            .define('T', DoggyItems.TRAINING_TREAT.get())
            .unlockedBy("has_white_wool", has(Items.WHITE_WOOL))
            .save(consumer);

        ShapedRecipeBuilder.shaped(DoggyBlocks.RICE_MILL.get(), 1)
            .pattern("FLF")
            .pattern("LCL")
            .pattern("FDF")
            .define('F', ItemTags.FENCES)
            .define('L', ItemTags.LOGS)
            .define('C', Items.COBBLESTONE_SLAB)
            .define('D', DoggyItems.RICE_WHEAT.get())
            .unlockedBy("has_paddy_rice_dtn", has(DoggyItems.RICE_WHEAT.get()))
            .save(consumer);
    }

    private void registerTripleCooking(Consumer<FinishedRecipe> consumer, Ingredient input, Item output,
        float xp, int lengthTicks,
        String unlockedByStr, InventoryChangeTrigger.TriggerInstance trigger) {
        var baseNameId = ForgeRegistries.ITEMS.getKey(output).getPath();
        SimpleCookingRecipeBuilder.smelting(input, 
            
            output, 
            xp, lengthTicks)
            .unlockedBy(unlockedByStr, trigger)
            .save(consumer, Util.getResource(baseNameId + "_smelting"));

        SimpleCookingRecipeBuilder.campfireCooking(input, 
        
        output, 
        xp, lengthTicks)
        .unlockedBy(unlockedByStr, trigger)
            .save(consumer, Util.getResource(baseNameId + "_camping"));
            
        SimpleCookingRecipeBuilder.smoking(input, 
        
        output, 
        xp, lengthTicks/2)
        .unlockedBy(unlockedByStr, trigger)
            .save(consumer, Util.getResource(baseNameId) + "_smoking");
    }

    private void registerVariantChanger(Supplier<VariantChangerItem> itemSup, Consumer<FinishedRecipe>  consumer) {
        var item = itemSup.get();
        ShapelessRecipeBuilder.shapeless(item, 1)
            .requires(DoggyItems.CONDUCTING_BONE.get(), 1)
            .requires(VariantChangerItem.REPR_ITEM.get(item.variant), 1)
            .unlockedBy("has_amnesia_bone", has(DoggyItems.AMNESIA_BONE.get()))
            .save(consumer);
    }
    
    // @Override
    // protected void saveAdvancement(HashCache cache, JsonObject advancementJson, Path pathIn) {
    //     //NOOP - We dont replace any of the advancement things yet...
    // }
}
