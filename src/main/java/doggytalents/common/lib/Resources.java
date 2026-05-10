package doggytalents.common.lib;

import doggytalents.common.util.Util;
import net.minecraft.resources.Identifier;

public class Resources {

    public static final Identifier GUI_FOOD_BOWL = getGui("food_bowl");
    public static final Identifier GUI_PACK_PUPPY = getGui("pack_puppy");
    public static final Identifier GUI_TREAT_BAG = getGui("treat_bag");
    public static final Identifier GUI_RADAR = getGui("radar");
    public static final Identifier DTN_PACK_ICON = Util.getResource("dtn_pack_icon.png");

    public static final Identifier DOG_INVENTORY = getGui("dog_inventory");
    public static final Identifier INVENTORY_BUTTON = getGui("dog_button");
    public static final Identifier SMALL_WIDGETS = getGui("small_widgets");
    public static final Identifier DOGGY_ARMOR_GUI = getGui("dog_amor_gui");
    public static final Identifier RICE_MILL_GUI = getGui("rice_mill_gui");
    public static final Identifier DOGGY_TOOLS_GUI = getGui("doggy_tools_gui");
    public static final Identifier STYLE_ADD_REMOVE = getGui("style_add_remove");
    public static final Identifier HAMBURGER = getGui("hamburger");

    // Vanilla wolf
    public static final Identifier ENTITY_WOLF = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf_tame.png");
    public static final Identifier ENTITY_WOLF_WILD = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf.png");
    public static final Identifier ENTITY_WOLF_ANGRY = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf_angry.png");
    public static final Identifier ENTITY_WOLF_COLLAR = Util.getResource(Constants.VANILLA_ID, "textures/entity/wolf/wolf_collar.png");

    public static final Identifier DOG_CLASSICAL = getEntity("dog", "classical/wolf_pale");
    public static final Identifier DOG_MYSTERY = getEntity("dog", "classical/compl/wolf_missing");
    public static final Identifier OKAMI_AMATERASU = getEntity("dog", "custom/ammy_divine_mouthopen");
    public static final Identifier SOL_HOPE = getEntity("dog", "custom/sol_hope");

    public static final Identifier COLLAR_DEFAULT = getEntity("dog", "doggy_collar");
    public static final Identifier COLLAR_THICC = getEntity("dog", "doggy_collar_thicc");
    public static final Identifier COLLAR_GOLDEN = getEntity("dog", "doggy_collar_0");
    public static final Identifier COLLAR_SPOTTED = getEntity("dog", "doggy_collar_1");
    public static final Identifier COLLAR_MULTICOLORED = getEntity("dog", "doggy_collar_2");
    public static final Identifier CLOTHING_LEATHER_JACKET = getEntity("dog", "doggy_leather_jacket");
    public static final Identifier GUARD_SUIT = getEntity("dog", "doggy_guard_suit");
    public static final Identifier GLASSES_SUNGLASSES = getEntity("dog", "doggy_sunglasses");
    public static final Identifier TANTAN_CAPE = getEntity("dog", "doggy_cape");
    public static final Identifier DYEABLE_CAPE = getEntity("dog", "doggy_cape1");
    public static final Identifier PIANIST_SUIT = getEntity("dog", "pianist_suit");
    public static final Identifier CONAN_SUIT = getEntity("dog", "conan_suit");
    public static final Identifier FIREFIGHTER_SUIT_BLACK = getEntity("dog", "firefighter_suit_black");
    public static final Identifier FIREFIGHTER_SUIT_KAKI = getEntity("dog", "firefighter_suit_kaki");
    public static final Identifier FIREFIGHTER_SUIT_RED = getEntity("dog", "firefighter_suit_red");
    public static final Identifier FIREFIGHTER_SUIT_BLUE = getEntity("dog", "firefighter_suit_blue");
    public static final Identifier FIREFIGHTER_SUIT_BLACK_LEGLESS = getEntity("dog", "firefighter_suit_black_legless");
    public static final Identifier FIREFIGHTER_SUIT_KAKI_LEGLESS = getEntity("dog", "firefighter_suit_kaki_legless");
    public static final Identifier FIREFIGHTER_SUIT_RED_LEGLESS = getEntity("dog", "firefighter_suit_red_legless");
    public static final Identifier DEATH_HOOD = getEntity("dog", "death_hood");
    public static final Identifier RADIO_BAND = getEntity("dog", "doggy_radio_collar");
    
    public static final Identifier TALENT_RESCUE = getEntity("dog/talents", "rescue");
    public static final Identifier TALENT_CHEST = getEntity("dog", "doggy_chest");
    public static final Identifier FISHER_HAT = getEntity("dog", "fisher_hat");
    public static final Identifier TORCH_DOG = getEntity("dog", "torch_dog");
    public static final Identifier TORCH_DOG_UNLIT = getEntity("dog", "torch_dog_unlit");
    public static final Identifier BOW_TIE = getEntity("dog", "doggy_bowtie");
    public static final Identifier DYABLE_BOW_TIE = getEntity("dog", "bowtie_coloured");
    public static final Identifier SMARTY_GLASSES = getEntity("dog", "smarty_glasses");
    public static final Identifier WIG = getEntity("dog", "wig");
    public static final Identifier BACH_WIG = getEntity("dog", "bach_wig");
    public static final Identifier BEASTARS_UNIFORM_MALE = getEntity("dog", "beastars_uniform_male");
    public static final Identifier BEASTARS_UNIFORM_FEMALE = getEntity("dog", "beastars_uniform_female");
    
    public static final Identifier TENGU_MASK = getEntity("dog", "tengu_mask");
    public static final Identifier KITSUNE_MASK = getEntity("dog", "kitsune_mask");
    public static final Identifier BIRTHDAY_HAT_BG = getEntity("dog", "birthday_hat_bg");
    public static final Identifier BIRTHDAY_HAT_FG = getEntity("dog", "birthday_hat_fg");
    public static final Identifier DYABLE_ORB_BG = getEntity("dog", "dyable_orb_bg");
    public static final Identifier DYABLE_ORB_FG = getEntity("dog", "dyable_orb_fg");
    public static final Identifier STRIPED_SCARF_BG = getEntity("dog", "striped_scarf_bg");
    public static final Identifier STRIPED_SCARF_FG = getEntity("dog", "striped_scarf_fg");
    public static final Identifier DEER_ANTLERS = getEntity("dog", "deer_antlers");
    public static final Identifier BUNNY_EARS = getEntity("dog", "bunny_ears");

    public static final Identifier DEMON_HORNS = getEntity("dog", "demon_horns");
    public static final Identifier WITCH_HAT = getEntity("dog", "witch_hat");
    public static final Identifier PLAGUE_DOC_MASK = getEntity("dog", "plague_doctor_mask");
    public static final Identifier BAKER_HAT = getEntity("dog", "baker_hat");
    public static final Identifier CHEF_HAT = getEntity("dog", "chef_hat");
    public static final Identifier LAB_COAT = getEntity("dog", "lab_coat");
    public static final Identifier CERE_GARB = getEntity("dog", "ceremonial_garb");
    public static final Identifier CERE_GARB_OVERLAY = getEntity("dog", "ceremonial_garb_overlay");
    public static final Identifier DOGGY_CONTACTS_BG = getEntity("dog", "doggy_contacts_bg");
    public static final Identifier DOGGY_CONTACTS_FG = getEntity("dog", "doggy_contacts_fg");
    public static final Identifier DOG_PROPELLAR = getEntity("dog", "dog_propeller_hat");
    public static final Identifier DOG_FEDORA = getEntity("dog", "dog_fedora");
    public static final Identifier DOG_FLATCAP = getEntity("dog", "dog_flatcap");
    public static final Identifier FIREFIGHTER_HAT_RED = getEntity("dog", "firefighter_hat_red");
    public static final Identifier FIREFIGHTER_HAT_BLUE = getEntity("dog", "firefighter_hat_blue");
    public static final Identifier DRAGON_COSTUME_HEAD = getEntity("dog", "dragon_costume_head");
    public static final Identifier DRAGON_COSTUME_SUIT = getEntity("dog", "dragon_costume_suit");
    public static final Identifier DRAGON_COSTUME_WINGS = getEntity("dog", "dragon_costume_wings");

    public static final Identifier SUPERDOG_SUIT = getEntity("dog", "superdog_suit");
    public static final Identifier FLYING_CAPE = getEntity("dog", "flying_cape");
    public static final Identifier BAT_WINGS = getEntity("dog", "bat_wings");
    public static final Identifier CROW_WINGS = getEntity("dog", "crow_wings");
    public static final Identifier DIVINE_RETRIBUTION = getEntity("dog", "divine_retribution");
    public static final Identifier SOUL_REFLECTOR = getEntity("dog", "soul_reflector");
    public static final Identifier ANGEL_WINGS = getEntity("dog", "angel_wings");
    public static final Identifier ANGEL_HALO = getEntity("dog", "angel_halo");
    public static final Identifier YETI_GOOSE = getEntity("dog", "goose");
    public static final Identifier MIDI_KEYBOARD = getEntity("dog", "midi_keyboard");
    public static final Identifier MIDI_KEYBOARD_OVERLAY = getEntity("dog", "midi_keyboard_overlay");
    public static final Identifier DOG_CHRISTMAS_TREE = getEntity("dog", "dog_christmas_tree");
    public static final Identifier DOG_CHRISTMAS_STAR = getEntity("dog", "dog_christmas_star");
    public static final Identifier DOG_GIFT_COSTUME = getEntity("dog", "dog_gift_costume");
    public static final Identifier DOG_GIFT_COSTUME_OVERLAY = getEntity("dog", "dog_gift_costume_overlay");

    public static final Identifier CHI_ORB = getEntity("dog", "chi_orb");
    public static final Identifier CHU_ORB = getEntity("dog", "chu_orb");
    public static final Identifier KO_ORB = getEntity("dog", "ko_orb");
    public static final Identifier GI_ORB = getEntity("dog", "gi_orb");
    public static final Identifier TEI_ORB = getEntity("dog", "tei_orb");
    public static final Identifier REI_ORB = getEntity("dog", "rei_orb");
    public static final Identifier SHIN_ORB = getEntity("dog", "shin_orb");
    public static final Identifier JIN_ORB = getEntity("dog", "jin_orb");
    public static final Identifier KA_ORB = getEntity("dog", "ka_orb");
    public static final Identifier SUI_ORB = getEntity("dog", "sui_orb");
    public static final Identifier MOKU_ORB = getEntity("dog", "moku_orb");

    public static final Identifier HOT_DOG = getEntity("dog", "hot_dog");
    public static final Identifier GIANT_STICK = getEntity("dog", "giant_stick");
    public static final Identifier SNORKEL = getEntity("dog", "snorkel");
    public static final Identifier CHRISTMAS_HAT = getEntity("dog", "christmas_hat");

    public static final Identifier HEAD_BAND_BLANK = getEntity("dog", "head_band_blank");
    public static final Identifier HEAD_BAND_MYSTERY = getEntity("dog", "head_band_mystery");
    public static final Identifier HEAD_BAND_HIGHHH = getEntity("dog", "head_band_highhh");

    public static final Identifier IRON_HELMET = getEntity("dog", "armor/iron_helmet");
    public static final Identifier DIAMOND_HELMET = getEntity("dog", "armor/diamond_helmet");
    public static final Identifier GOLDEN_HELMET = getEntity("dog", "armor/golden_helmet");
    public static final Identifier CHAINMAIL_HELMET = getEntity("dog", "armor/chainmail_helmet");
    public static final Identifier LEATHER_HELMET = getEntity("dog", "armor/leather_helmet");
    public static final Identifier TURTLE_HELMET = getEntity("dog", "armor/turtle_helmet");
    public static final Identifier NETHERITE_HELMET = getEntity("dog", "armor/netherite_helmet");

    //LEGACY ARMOR
    public static final Identifier IRON_BOOTS = getEntity("dog", "armor/iron_boots");
    public static final Identifier DIAMOND_BOOTS = getEntity("dog", "armor/diamond_boots");
    public static final Identifier GOLDEN_BOOTS = getEntity("dog", "armor/golden_boots");
    public static final Identifier CHAINMAIL_BOOTS = getEntity("dog", "armor/chainmail_boots");
    public static final Identifier LEATHER_BOOTS = getEntity("dog", "armor/leather_boots");
    public static final Identifier NETHERITE_BOOTS = getEntity("dog", "armor/netherite_boots");
    public static final Identifier IRON_BODY_PIECE = getEntity("dog", "armor/iron_body");
    public static final Identifier DIAMOND_BODY_PIECE = getEntity("dog", "armor/diamond_body");
    public static final Identifier GOLDEN_BODY_PIECE = getEntity("dog", "armor/golden_body");
    public static final Identifier CHAINMAIL_BODY_PIECE = getEntity("dog", "armor/chainmail_body");
    public static final Identifier LEATHER_BODY_PIECE = getEntity("dog", "armor/leather_body");
    public static final Identifier NETHERITE_BODY_PIECE = getEntity("dog", "armor/netherite_body");

    public static final Identifier DEFAULT_DOG_ARMOR = getEntity("dog", "armor/default_dog_armor");

    public static final Identifier INCAPACITATED_BLOOD = getEntity("dog", "doggy_hurt/blood");
    public static final Identifier INCAPACITATED_BURN = getEntity("dog", "doggy_hurt/burn");
    public static final Identifier INCAPACITATED_POISON = getEntity("dog", "doggy_hurt/poison");
    public static final Identifier INCAPACITATED_DROWN = getEntity("dog", "doggy_hurt/drown");
    public static final Identifier INCAPACITATED_LESS_GRAPHIC = getEntity("dog", "doggy_hurt/less_graphic");

    public static final Identifier KANJI_NORMAL = getGui("levelkanji/normal");
    public static final Identifier KANJI_SUPER = getGui("levelkanji/super");
    public static final Identifier KANJI_MASTER = getGui("levelkanji/master");
    public static final Identifier KANJI_KAMI = getGui("levelkanji/kami");
    
    public static final Identifier KANJI_INCAP_BLOOD = getGui("fatalkanji/default");
    public static final Identifier KANJI_INCAP_BURN = getGui("fatalkanji/burnt");
    public static final Identifier KANJI_INCAP_POISON = getGui("fatalkanji/poison");
    public static final Identifier KANJI_INCAP_DROWN = getGui("fatalkanji/drown");
    public static final Identifier KANJI_INCAP_STARVE = getGui("fatalkanji/starve");
    public static final Identifier KANJI_MYSTERY = getGui("kanji/mystery/kanji");
    public static final Identifier KANJI_MYSTERY_BKG = getGui("kanji/mystery/background");

    public static final Identifier BANDAID_OVERLAY_HALF = getEntity("dog", "doggy_hurt/bandaid/overlay_half");
    public static final Identifier BANDAID_OVERLAY_FULL = getEntity("dog", "doggy_hurt/bandaid/overlay_full");

    public static final Identifier PIANO_GRAND_BLACK = Util.getResource("textures/misc/piano/grand_black.png");
    public static final Identifier PIANO_GRAND_WHITE = Util.getResource("textures/misc/piano/grand_white.png");
    public static final Identifier PIANO_UPRIGHT_BLACK = Util.getResource("textures/misc/piano/upright_black.png");
    public static final Identifier PIANO_UPRIGHT_BROWN = Util.getResource("textures/misc/piano/upright_brown.png");
    public static final Identifier PIANO_UPRIGHT_WHITE = Util.getResource("textures/misc/piano/upright_white.png");
    public static final Identifier DOG_PLUSHIE_TOY = Util.getResource("textures/misc/dog_plushie/dog_plushie_toy.png");
    public static final Identifier DOG_PLUSHIE_TOY_OVERLAY = Util.getResource("textures/misc/dog_plushie/plushie_overlay.png");
    public static final Identifier RICE_MILL_MODEL = Util.getResource("textures/block/rice_mill/rice_mill.png");
    public static final Identifier SAMOYED_PLUSHIE_TOY = Util.getResource("textures/misc/samoyed_plushie/samoyed_plushie_toy.png");

    public static Identifier getEntity(String type, String textureFileName) {
        return Util.getResource("textures/entity/" + type + "/" + textureFileName + ".png");
    }

    public static Identifier getGui(String textureFileName) {
        return Util.getResource("textures/gui/" + textureFileName + ".png");
    }
}
