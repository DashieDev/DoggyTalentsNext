package doggytalents.common.lib;

import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;

public class Resources {

    public static final ResourceLocation GUI_FOOD_BOWL = getGui("food_bowl");
    public static final ResourceLocation GUI_PACK_PUPPY = getGui("pack_puppy");
    public static final ResourceLocation GUI_TREAT_BAG = getGui("treat_bag");
    public static final ResourceLocation GUI_RADAR = getGui("radar");

    public static final ResourceLocation DOG_INVENTORY = getGui("dog_inventory");
    public static final ResourceLocation INVENTORY_BUTTON = getGui("dog_button");
    public static final ResourceLocation SMALL_WIDGETS = getGui("small_widgets");
    public static final ResourceLocation DOGGY_ARMOR_GUI = getGui("dog_amor_gui");
    public static final ResourceLocation DOGGY_TOOLS_GUI = getGui("doggy_tools_gui");
    public static final ResourceLocation STYLE_ADD_REMOVE = getGui("style_add_remove");
    public static final ResourceLocation HAMBURGER = getGui("hamburger");

    // Vanilla wolf
    public static final ResourceLocation ENTITY_WOLF = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf_tame.png");
    public static final ResourceLocation ENTITY_WOLF_WILD = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf.png");
    public static final ResourceLocation ENTITY_WOLF_ANGRY = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf_angry.png");
    public static final ResourceLocation ENTITY_WOLF_COLLAR = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf_collar.png");

    public static final ResourceLocation DOG_CLASSICAL = getEntity("dog", "custom/classical/wolf");
    public static final ResourceLocation DOG_MYSTERY = getEntity("dog", "custom/classical/wolf_nani");

    public static final ResourceLocation COLLAR_DEFAULT = getEntity("dog", "doggy_collar");
    public static final ResourceLocation COLLAR_GOLDEN = getEntity("dog", "doggy_collar_0");
    public static final ResourceLocation COLLAR_SPOTTED = getEntity("dog", "doggy_collar_1");
    public static final ResourceLocation COLLAR_MULTICOLORED = getEntity("dog", "doggy_collar_2");
    public static final ResourceLocation CLOTHING_LEATHER_JACKET = getEntity("dog", "doggy_leather_jacket");
    public static final ResourceLocation GUARD_SUIT = getEntity("dog", "doggy_guard_suit");
    public static final ResourceLocation GLASSES_SUNGLASSES = getEntity("dog", "doggy_sunglasses");
    public static final ResourceLocation TANTAN_CAPE = getEntity("dog", "doggy_cape");
    public static final ResourceLocation DYEABLE_CAPE = getEntity("dog", "doggy_cape1");
    public static final ResourceLocation PIANIST_SUIT = getEntity("dog", "pianist_suit");
    public static final ResourceLocation CONAN_SUIT = getEntity("dog", "conan_suit");
    public static final ResourceLocation DEATH_HOOD = getEntity("dog", "death_hood");
    public static final ResourceLocation RADIO_BAND = getEntity("dog", "doggy_radio_collar");
    
    public static final ResourceLocation TALENT_RESCUE = getEntity("dog/talents", "rescue");
    public static final ResourceLocation TALENT_CHEST = getEntity("dog", "doggy_chest");
    public static final ResourceLocation BOW_TIE = getEntity("dog", "doggy_bowtie");
    public static final ResourceLocation DYABLE_BOW_TIE = getEntity("dog", "bowtie_coloured");
    public static final ResourceLocation SMARTY_GLASSES = getEntity("dog", "smarty_glasses");
    public static final ResourceLocation WIG = getEntity("dog", "wig");
    public static final ResourceLocation BACH_WIG = getEntity("dog", "bach_wig");
    public static final ResourceLocation BEASTARS_UNIFORM_MALE = getEntity("dog", "beastars_uniform_male");
    public static final ResourceLocation BEASTARS_UNIFORM_FEMALE = getEntity("dog", "beastars_uniform_female");
    
    public static final ResourceLocation TENGU_MASK = getEntity("dog", "tengu_mask");
    public static final ResourceLocation KITSUNE_MASK = getEntity("dog", "kitsune_mask");
    public static final ResourceLocation BIRTHDAY_HAT_RED_WHITE = getEntity("dog", "birthday_hat_red_white");
    public static final ResourceLocation BIRTHDAY_HAT_YELLOW_BLUE = getEntity("dog", "birthday_hat_yellow_blue");
    public static final ResourceLocation DEMON_HORNS = getEntity("dog", "demon_horns");
    public static final ResourceLocation WITCH_HAT = getEntity("dog", "witch_hat");
    public static final ResourceLocation PLAGUE_DOC_MASK = getEntity("dog", "plague_doctor_mask");
    public static final ResourceLocation BAKER_HAT = getEntity("dog", "baker_hat");
    public static final ResourceLocation CHEF_HAT = getEntity("dog", "chef_hat");

    public static final ResourceLocation SUPERDOG_OVERLAY = getEntity("dog", "superdog_cape_overlay");
    public static final ResourceLocation SUPERDOG_MODEL = getEntity("dog", "superdog_model");

    public static final ResourceLocation CHI_ORB = getEntity("dog", "chi_orb");
    public static final ResourceLocation CHU_ORB = getEntity("dog", "chu_orb");
    public static final ResourceLocation KO_ORB = getEntity("dog", "ko_orb");
    public static final ResourceLocation GI_ORB = getEntity("dog", "gi_orb");
    public static final ResourceLocation TEI_ORB = getEntity("dog", "tei_orb");
    public static final ResourceLocation REI_ORB = getEntity("dog", "rei_orb");
    public static final ResourceLocation SHIN_ORB = getEntity("dog", "shin_orb");
    public static final ResourceLocation JIN_ORB = getEntity("dog", "jin_orb");

    public static final ResourceLocation HOT_DOG = getEntity("dog", "hot_dog");
    public static final ResourceLocation GIANT_STICK = getEntity("dog", "giant_stick");
    public static final ResourceLocation SNORKEL = getEntity("dog", "snorkel");
    
    public static final ResourceLocation HEAD_BAND_BLANK = getEntity("dog", "head_band_blank");
    public static final ResourceLocation HEAD_BAND_MYSTERY = getEntity("dog", "head_band_mystery");
    public static final ResourceLocation HEAD_BAND_HIGHHH = getEntity("dog", "head_band_highhh");

    public static final ResourceLocation IRON_HELMET = getEntity("dog", "armor/iron_helmet");
    public static final ResourceLocation DIAMOND_HELMET = getEntity("dog", "armor/diamond_helmet");
    public static final ResourceLocation GOLDEN_HELMET = getEntity("dog", "armor/golden_helmet");
    public static final ResourceLocation CHAINMAIL_HELMET = getEntity("dog", "armor/chainmail_helmet");
    public static final ResourceLocation LEATHER_HELMET = getEntity("dog", "armor/leather_helmet");
    public static final ResourceLocation TURTLE_HELMET = getEntity("dog", "armor/turtle_helmet");
    public static final ResourceLocation NETHERITE_HELMET = getEntity("dog", "armor/netherite_helmet");

    public static final ResourceLocation IRON_BOOTS = getEntity("dog", "armor/iron_boots");
    public static final ResourceLocation DIAMOND_BOOTS = getEntity("dog", "armor/diamond_boots");
    public static final ResourceLocation GOLDEN_BOOTS = getEntity("dog", "armor/golden_boots");
    public static final ResourceLocation CHAINMAIL_BOOTS = getEntity("dog", "armor/chainmail_boots");
    public static final ResourceLocation LEATHER_BOOTS = getEntity("dog", "armor/leather_boots");
    public static final ResourceLocation NETHERITE_BOOTS = getEntity("dog", "armor/netherite_boots");

    public static final ResourceLocation IRON_BODY_PIECE = getEntity("dog", "armor/iron_body");
    public static final ResourceLocation DIAMOND_BODY_PIECE = getEntity("dog", "armor/diamond_body");
    public static final ResourceLocation GOLDEN_BODY_PIECE = getEntity("dog", "armor/golden_body");
    public static final ResourceLocation CHAINMAIL_BODY_PIECE = getEntity("dog", "armor/chainmail_body");
    public static final ResourceLocation LEATHER_BODY_PIECE = getEntity("dog", "armor/leather_body");
    public static final ResourceLocation NETHERITE_BODY_PIECE = getEntity("dog", "armor/netherite_body");

    public static final ResourceLocation INCAPACITATED_BLOOD = getEntity("dog", "doggy_hurt/blood");
    public static final ResourceLocation INCAPACITATED_BURN = getEntity("dog", "doggy_hurt/burn");
    public static final ResourceLocation INCAPACITATED_POISON = getEntity("dog", "doggy_hurt/poison");
    public static final ResourceLocation INCAPACITATED_DROWN = getEntity("dog", "doggy_hurt/drown");
    public static final ResourceLocation INCAPACITATED_LESS_GRAPHIC = getEntity("dog", "doggy_hurt/less_graphic");

    public static final ResourceLocation KANJI_NORMAL = getGui("levelkanji/normal");
    public static final ResourceLocation KANJI_SUPER = getGui("levelkanji/super");
    public static final ResourceLocation KANJI_MASTER = getGui("levelkanji/master");
    public static final ResourceLocation KANJI_DIRE = getGui("levelkanji/dire");
    
    public static final ResourceLocation KANJI_INCAP_BLOOD = getGui("fatalkanji/default");
    public static final ResourceLocation KANJI_INCAP_BURN = getGui("fatalkanji/burnt");
    public static final ResourceLocation KANJI_INCAP_POISON = getGui("fatalkanji/poison");
    public static final ResourceLocation KANJI_INCAP_DROWN = getGui("fatalkanji/drown");
    public static final ResourceLocation KANJI_INCAP_STARVE = getGui("fatalkanji/starve");
    public static final ResourceLocation KANJI_MYSTERY = getGui("kanji/mystery/kanji");
    public static final ResourceLocation KANJI_MYSTERY_BKG = getGui("kanji/mystery/background");

    public static final ResourceLocation SPIN = getGui("spin");
    public static final ResourceLocation SPIN2 = getGui("spin2");
    public static final ResourceLocation SPIN3 = getGui("spin3");
    public static final ResourceLocation SPIN4 = getGui("spin4");

    public static final ResourceLocation BANDAID_OVERLAY_HALF = getEntity("dog", "doggy_hurt/bandaid/overlay_half");
    public static final ResourceLocation BANDAID_OVERLAY_FULL = getEntity("dog", "doggy_hurt/bandaid/overlay_full");

    public static ResourceLocation getEntity(String type, String textureFileName) {
        return Util.getResource("textures/entity/" + type + "/" + textureFileName + ".png");
    }

    public static ResourceLocation getGui(String textureFileName) {
        return Util.getResource("textures/gui/" + textureFileName + ".png");
    }
}
