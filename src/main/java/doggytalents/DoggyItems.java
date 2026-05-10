package doggytalents;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.registry.Accessory;
import doggytalents.common.artifacts.FeatheredMantleArtifact;
import doggytalents.common.data.DTMusicProvider;
import doggytalents.common.entity.accessory.AngelHalo;
import doggytalents.common.entity.accessory.AngelWings;
import doggytalents.common.entity.accessory.BakerHat;
import doggytalents.common.entity.accessory.CeremonialGarb;
import doggytalents.common.entity.accessory.ChefHat;
import doggytalents.common.entity.accessory.DragonCostumeHead;
import doggytalents.common.entity.accessory.DragonCostumeHead.DragonCostumeHeadItem;
import doggytalents.common.entity.accessory.DragonCostumeSuit.DragonCostumeSuitItem;
import doggytalents.common.entity.accessory.DragonCostumeWings.DragonCostumeWingsItem;
import doggytalents.common.entity.accessory.DyeableAccessory;
import doggytalents.common.entity.accessory.Fedora;
import doggytalents.common.entity.accessory.FlatCap;
import doggytalents.common.entity.accessory.GiantStick;
import doggytalents.common.entity.accessory.HeadBandAccessory;
import doggytalents.common.entity.accessory.LabCoat;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import doggytalents.common.entity.accessory.MidiKeyboard;
import doggytalents.common.entity.accessory.Propellar;
import doggytalents.common.entity.accessory.Wig;
import doggytalents.common.entity.accessory.YetiGoose;
import doggytalents.common.entity.accessory.KitsuneMask.KitsuneMaskItem;
import doggytalents.common.entity.accessory.TenguMask.TenguMaskItem;
import doggytalents.common.item.*;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.ItemUtil;
import doggytalents.common.util.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);

    //DTN Main ==========================
    public static final Supplier<Item> STARTER_BUNDLE = registerWith("starter_bundle", StarterBundleItem::new, 1);
    public static final Supplier<Item> DOGGY_CHARM = registerWith("doggy_charm", DoggyCharmItem::new, 1);
    public static final Supplier<Item> TRAINING_TREAT = registerTreat("training_treat", DogLevel.Type.NORMAL, 20);
    public static final Supplier<Item> SUPER_TREAT = registerTreat("super_treat", DogLevel.Type.NORMAL, 40);
    public static final Supplier<Item> MASTER_TREAT = registerTreat("master_treat", DogLevel.Type.NORMAL, 60);
    public static final Supplier<Item> KAMI_TREAT = registerTreat("kami_treat", DogLevel.Type.KAMI, 30);
    public static final Supplier<Item> BANDAID = register("bandaid", BandaidItem::new);
    public static final Supplier<WhistleItem> WHISTLE = registerWith("whistle", WhistleItem::new, 1);
    public static final Supplier<Item> CANINE_TRACKER = registerWith("canine_tracker", CanineTrackerItem::new, 1);
    public static final Supplier<Item> AMNESIA_BONE = registerWith("amnesia_bone", AmnesiaBoneItem::new, 1);
    public static final Supplier<Item> TREAT_BAG = registerWith("treat_bag", TreatBagItem::new, 1);
    public static final Supplier<Item> CONDUCTING_BONE = registerWithFireResistant("conducting_bone", ConductingBoneItem::new, 1);
    public static final Supplier<Item> SHRINKING_MALLET = registerSizeBone("shrinking_mallet", DogResizeItem.Type.TINY);
    public static final Supplier<Item> MAGNIFYING_BONE = registerSizeBone("magnifying_bone", DogResizeItem.Type.BIG);
    public static final Supplier<Item> GENDER_BONE = registerTool("gender_bone", GenderBoneItem::new, 10);
    public static final Supplier<Item> BREEDING_BONE = register("breeding_bone");
    public static final Supplier<Item> SCENT_TREAT = register("scent_treat", ScentTreatItem::new);
    public static final Supplier<Item> THROW_BONE = registerThrowBone("throw_bone");
    public static final Supplier<Item> THROW_STICK = registerThrowStick("throw_stick");
    public static final Supplier<Item> FRISBEE = registerFrisbee("frisbee");

    //DTN Agriculture ==========================
    public static final Supplier<Item> RICE_WHEAT = register("rice_wheat",
        (props) -> new RiceWheatItem(props));
    public static final Supplier<Item> RICE_GRAINS = register("rice_grains", 
        (props) -> new RiceGrainsItem(DoggyBlocks.RICE_CROP.get(), props));
    public static final Supplier<Item> UNCOOKED_RICE = register("uncooked_rice");
    public static final Supplier<Item> KOJI = register("koji", KojiItem::new);
    public static final Supplier<Item> SAKE = register("sake", SakeItem::new);
    public static final Supplier<Item> UNCOOKED_RICE_BOWL = register("uncooked_rice_bowl", UncookedRiceBowlItem::new);
    public static final Supplier<Item> RICE_BOWL = register("rice_bowl", RiceBowlItem::new);
    public static final Supplier<Item> ONIGIRI = register("onigiri", OnigiriItem::new);
    public static final Supplier<Item> SALMON_SUSHI = register("salmon_sushi", SalmonSushiItem::new);

    public static final Supplier<Item> SOY_PODS = register("soy_pods", 
        (props) -> new SoyPodsItem(props));
    public static final Supplier<Item> SOY_BEANS = register("soy_beans", 
        (props) -> new BlockItem(DoggyBlocks.SOY_CROP.get(), props));
    public static final Supplier<Item> SOY_PODS_DRIED = register("soy_pods_dried",  SoyPodsDriedItem::new);
    public static final Supplier<Item> SOY_BEANS_DRIED = register("soy_beans_dried");
    public static final Supplier<Item> EDAMAME = register("edamame", EdamameItem::new);
    public static final Supplier<Item> EDAMAME_UNPODDED = register("edamame_unpodded", EdamameUnpoddedItem::new);
    public static final Supplier<Item> SOY_MILK = register("soy_milk", SoyMilkItem::new);
    public static final Supplier<Item> TOFU = register("tofu", TofuItem::new);
    public static final Supplier<Item> ABURAAGE = register("aburaage", AburaageItem::new);

    public static final Supplier<Item> MISO_PASTE = register("miso_paste", MisoPasteItem::new);
    public static final Supplier<Item> NATTO = register("natto", NattoItem::new);
    public static final Supplier<Item> ONSEN_TAMAGO = register("onsen_tamago", OnsenTamagoItem::new);
    public static final Supplier<Item> MISO_SOUP = register("miso_soup", MisoSoupItem::new);
    public static final Supplier<Item> NATTO_RICE = register("natto_rice", NattoRiceItem::new);
    public static final Supplier<Item> GYUDON = register("gyudon", GyudonItem::new);
    public static final Supplier<Item> OYAKODON = register("oyakodon", OyakodonItem::new);
    public static final Supplier<Item> EGG_SANDWICH = register("egg_sandwich", EggSandwichItem::new);
    public static final Supplier<Item> GOLDEN_A_FIVE_WAGYU = register("golden_a_five_wagyu", GoldenAFiveWagyuItem::new);
    public static final Supplier<Item> SAUSAGE = register("sausage", SausageItem::new);

    //DTN Style ==========================
    public static final Supplier<AccessoryItem> BIRTHDAY_HAT = register("birthday_hat", (props) -> new DyableBirthdayHatItem(DoggyAccessories.BIRTHDAY_HAT, props));
    public static final Supplier<DyeableAccessoryItem> BAKER_HAT = register("baker_hat", (props) -> new BakerHat.BakerHatItem(DoggyAccessories.BAKER_HAT, props));
    public static final Supplier<DyeableAccessoryItem> CHEF_HAT = register("chef_hat", (props) -> new ChefHat.ChefHatItem(DoggyAccessories.CHEF_HAT, props));
    public static final Supplier<DyeableAccessoryItem> FLATCAP = register("flatcap", (props) -> new FlatCap.FlatCapItem(DoggyAccessories.FLATCAP, props));
    public static final Supplier<AccessoryItem> BACH_WIG = registerAccessory("bach_wig", DoggyAccessories.BACH_WIG);
    public static final Supplier<AccessoryItem> FIREFIGHTER_HAT_RED = registerAccessory("firefighter_hat_red", DoggyAccessories.FIREFIGHTER_HAT_RED);
    public static final Supplier<AccessoryItem> FIREFIGHTER_HAT_BLUE = registerAccessory("firefighter_hat_blue", DoggyAccessories.FIREFIGHTER_HAT_BLUE);
    public static final Supplier<DyeableAccessoryItem> WIG = register("wig", (props) -> new Wig.WigItem(DoggyAccessories.WIG, props));
    public static final Supplier<AccessoryItem> FEDORA = register("fedora", (props) -> new Fedora.FedoraItem(DoggyAccessories.FEDORA, props));
    public static final Supplier<AccessoryItem> WITCH_HAT = register("witch_hat", (props) -> new WitchHatItem(DoggyAccessories.WITCH_HAT, props));
    public static final Supplier<AccessoryItem> PROPELLER_HAT = register("propeller_hat", (props) -> new Propellar.PropellerHatItem(DoggyAccessories.PROPELLAR, props));
    public static final Supplier<AccessoryItem> DYED_ORB = register("locator_orb_dyable", (props) -> new DyableOrbItem(DoggyAccessories.DYED_ORB, props));
    public static final Supplier<AccessoryItem> STRIPED_SCARF = register("striped_scarf", (props) -> new StripedScarfItem(DoggyAccessories.STRIPED_SCARF, props));
    public static final Supplier<DyeableAccessoryItem> BOWTIE = register("bowtie", (props) -> new DualDyableAccessoryItem(DoggyAccessories.BOWTIE, DoggyAccessories.HEAD_BOW, props));

    public static final Supplier<DyeableAccessoryItem> WOOL_COLLAR = registerAccessoryDyed("wool_collar", DoggyAccessories.DYEABLE_COLLAR);
    public static final Supplier<DyeableAccessoryItem> WOOL_COLLAR_THICC = registerAccessoryDyed("wool_collar_thicc", DoggyAccessories.DYEABLE_COLLAR_THICC);
    public static final Supplier<AccessoryItem> RADIO_COLLAR = registerAccessory("radio_collar", DoggyAccessories.RADIO_BAND);
    public static final Supplier<AccessoryItem> CREATIVE_COLLAR = register("creative_collar",  (props) -> new AccessoryItem(DoggyAccessories.GOLDEN_COLLAR, props) 
        { @Override public boolean isFoil(ItemStack stack) { return true; } } );
    public static final Supplier<AccessoryItem> SPOTTED_COLLAR = registerAccessory("spotted_collar", DoggyAccessories.SPOTTED_COLLAR);
    public static final Supplier<AccessoryItem> MULTICOLOURED_COLLAR = registerAccessory("multicoloured_collar", DoggyAccessories.MULTICOLORED_COLLAR);
    public static final Supplier<AccessoryItem> DEMON_HORNS = register("demon_horns", (props) -> new DemonHornsItem(DoggyAccessories.DEMON_HORNS, props));
    public static final Supplier<AccessoryItem> DEER_ANTLERS = register("deer_antlers", (props) -> new DeerAntlersItem(DoggyAccessories.DEER_ANTLERS, props));

    public static final Supplier<Item> EMPTY_LOCATOR_ORB = registerWith("empty_locator_orb", EmptyLocatorOrbItem::new, 64);
    public static final Supplier<AccessoryItem> JIN_ORB = registerLocatorOrb("locator_orb_jin", DoggyAccessories.JIN_ORB);
    public static final Supplier<AccessoryItem> TEI_ORB = registerLocatorOrb("locator_orb_tei", DoggyAccessories.TEI_ORB);
    public static final Supplier<AccessoryItem> REI_ORB = registerLocatorOrb("locator_orb_rei", DoggyAccessories.REI_ORB);
    public static final Supplier<AccessoryItem> SHIN_ORB = registerLocatorOrb("locator_orb_shin", DoggyAccessories.SHIN_ORB);
    public static final Supplier<AccessoryItem> CHU_ORB = registerLocatorOrb("locator_orb_chu", DoggyAccessories.CHU_ORB);
    public static final Supplier<AccessoryItem> GI_ORB = registerLocatorOrb("locator_orb_gi", DoggyAccessories.GI_ORB);
    public static final Supplier<AccessoryItem> KO_ORB = registerLocatorOrb("locator_orb_ko", DoggyAccessories.KO_ORB);
    public static final Supplier<AccessoryItem> CHI_ORB = registerLocatorOrb("locator_orb_chi", DoggyAccessories.CHI_ORB);
    public static final Supplier<AccessoryItem> KA_ORB = registerLocatorOrb("locator_orb_ka", DoggyAccessories.KA_ORB);
    public static final Supplier<AccessoryItem> SUI_ORB = registerLocatorOrb("locator_orb_sui", DoggyAccessories.SUI_ORB);
    public static final Supplier<AccessoryItem> MOKU_ORB = registerLocatorOrb("locator_orb_moku", DoggyAccessories.MOKU_ORB);

    public static final Supplier<AccessoryItem> DIVINE_RETRIBUTON = register("divine_retribution", (props) -> new FieryReflectorItem(DoggyAccessories.DIVINE_RETRIBUTION, props));
    public static final Supplier<AccessoryItem> SOUL_REFLECTOR = register("soul_reflector", (props) -> new FieryReflectorItem(DoggyAccessories.SOUL_REFLECTOR, props));
    public static final Supplier<DyeableAccessoryItem> CERE_GARB = register("ceremonial_garb", (props) -> new CeremonialGarb.Item(DoggyAccessories.CERE_GARB, props));
    public static final Supplier<AccessoryItem> DOGGY_CONTACTS = register("doggy_contacts", (props) -> new DoggyContactsItem(DoggyAccessories.DOGGY_CONTACTS, props));
    public static final Supplier<AccessoryItem> SUNGLASSES = registerAccessory("sunglasses", DoggyAccessories.SUNGLASSES);
    public static final Supplier<AccessoryItem> SMARTY_GLASSES = registerAccessory("smarty_glasses", DoggyAccessories.SMARTY_GLASSES);
    public static final Supplier<AccessoryItem> SNORKEL = registerSnorkel("snorkel", DoggyAccessories.SNORKEL);
    public static final Supplier<AccessoryItem> HEAD_BAND_BLANK = registerHeadBand("head_band_blank", DoggyAccessories.HEAD_BAND_BlANK);
    public static final Supplier<AccessoryItem> HEAD_BAND_MYSTERY = registerHeadBand("head_band_mystery", DoggyAccessories.HEAD_BAND_MYSTERY);

    public static final Supplier<AccessoryItem> KITSUNE_MASK = register("kitsune_mask", (props) -> new KitsuneMaskItem(DoggyAccessories.KITSUNE_MASK, props));
    public static final Supplier<AccessoryItem> TENGU_MASK = register("tengu_mask", (props) -> new TenguMaskItem(DoggyAccessories.TENGU_MASK, props));
    public static final Supplier<AccessoryItem> PLAGUE_DOC_MASK = register("plague_doctor_mask", (props) -> new PlagueDoctorMaskItem(DoggyAccessories.PLAGUE_DOC_MASK, props));
    public static final Supplier<AccessoryItem> CROW_WINGS = registerAccessory("crow_wings", DoggyAccessories.CROW_WINGS);
    public static final Supplier<AccessoryItem> BAT_WINGS = registerAccessory("bat_wings", DoggyAccessories.BAT_WINGS);
    public static final Supplier<DyeableAccessoryItem> FLYING_CAPE = registerAccessoryDyed("flying_cape", DoggyAccessories.FLYING_CAPE);
    public static final Supplier<DyeableAccessoryItem> LAB_COAT = register("lab_coat", (props) -> new LabCoat.LabCoatItem(DoggyAccessories.LAB_COAT, props));
    public static final Supplier<AccessoryItem> HOT_DOG = register("hot_dog",(props) -> new HotDogAccessoryItem(DoggyAccessories.HOT_DOG, props));
    public static final Supplier<AccessoryItem> SUPERDOG_SUIT = registerAccessory("superdog_suit", DoggyAccessories.SUPERDOG_SUIT);
    public static final Supplier<AccessoryItem> DRAGON_COSTUME_HEAD = register("dragon_costume_head", (props) -> new DragonCostumeHeadItem(DoggyAccessories.DRAGON_COSTUME_HEAD, props));
    public static final Supplier<AccessoryItem> DRAGON_COSTUME_SUIT = register("dragon_costume_suit", (props) -> new DragonCostumeSuitItem(DoggyAccessories.DRAGON_COSTUME_SUIT, props));
    public static final Supplier<AccessoryItem> DRAGON_COSTUME_WINGS = register("dragon_costume_wings", (props) -> new DragonCostumeWingsItem(DoggyAccessories.DRAGON_COSTUME_WINGS, props));

    public static final Supplier<DyeableAccessoryItem> CAPE_COLOURED = registerAccessoryDyed("cape_coloured", DoggyAccessories.DYEABLE_CAPE);
    public static final Supplier<AccessoryItem> TANTAN_CAPE = registerAccessory("tantan_cape", DoggyAccessories.TANTAN_CAPE);
    public static final Supplier<AccessoryItem> LEATHER_JACKET = registerAccessory("leather_jacket", DoggyAccessories.LEATHER_JACKET_CLOTHING);
    public static final Supplier<AccessoryItem> GUARD_SUIT = registerAccessory("guard_suit", DoggyAccessories.GUARD_SUIT);
    public static final Supplier<AccessoryItem> DEATH_HOOD = registerAccessory("death_hood", DoggyAccessories.DEATH_HOOD);
    public static final Supplier<AccessoryItem> PIANIST_SUIT = registerAccessory("pianist_suit", DoggyAccessories.PIANIST_SUIT);
    public static final Supplier<AccessoryItem> BEASTARS_UNIFORM_MALE = registerAccessory("beastars_uniform_male", DoggyAccessories.BEASTARS_UNIFORM_MALE);
    public static final Supplier<AccessoryItem> BEASTARS_UNIFORM_FEMALE = registerAccessory("beastars_uniform_female", DoggyAccessories.BEASTARS_UNIFORM_FEMALE);
    public static final Supplier<AccessoryItem> CONAN_SUIT = registerAccessory("conan_suit", DoggyAccessories.CONAN_SUIT);
    public static final Supplier<AccessoryItem> FIREFIGHTER_SUIT_BLACK = registerDualAccessory("firefighter_suit_black", DoggyAccessories.FIREFIGHTER_SUIT_BLACK, DoggyAccessories.FIREFIGHTER_SUIT_BLACK_LEGLESS);
    public static final Supplier<AccessoryItem> FIREFIGHTER_SUIT_KAKI = registerDualAccessory("firefighter_suit_kaki", DoggyAccessories.FIREFIGHTER_SUIT_KAKI, DoggyAccessories.FIREFIGHTER_SUIT_KAKI_LEGLESS);
    public static final Supplier<AccessoryItem> FIREFIGHTER_SUIT_RED = registerDualAccessory("firefighter_suit_red", DoggyAccessories.FIREFIGHTER_SUIT_RED, DoggyAccessories.FIREFIGHTER_SUIT_RED_LEGLESS);
    public static final Supplier<AccessoryItem> FIREFIGHTER_SUIT_BLUE = registerAccessory("firefighter_suit_blue", DoggyAccessories.FIREFIGHTER_SUIT_BLUE);
    
    public static final Supplier<AccessoryItem> ANGEL_HALO = register("angel_halo", (props) -> new AngelHalo.AngelHaloItem(DoggyAccessories.ANGEL_HALO, props));
    public static final Supplier<DyeableAccessoryItem> ANGEL_WINGS = register("angel_wings", (props) -> new AngelWings.Item(DoggyAccessories.ANGEL_WINGS, props));
    public static final Supplier<AccessoryItem> GIANT_STICK = register("giant_stick",(props) -> new GiantStickAccessoryItem(DoggyAccessories.GIANT_STICK, props));
    public static final Supplier<DyeableAccessoryItem> MIDI_KEYBOARD = register("midi_keyboard", (props) -> new MidiKeyboard.Item(DoggyAccessories.MIDI_KEYBOARD, props));
    public static final Supplier<AccessoryItem> YETI_GOOSE = register("goose", (props) -> new YetiGoose.YetiGooseItem(DoggyAccessories.YETI_GOOSE, props));
    public static final Supplier<AccessoryItem> HEAD_BAND_HIGHHH = registerHeadBand("head_band_highhh", DoggyAccessories.HEAD_BAND_HIGHHH);

    public static final Supplier<AccessoryItem> CHRISTMAS_HAT = register("christmas_hat", (props) -> new ChristmasHatItem(DoggyAccessories.CHRISTMAS_HAT, props));
    public static final Supplier<AccessoryItem> DOG_CHRISTMAS_TREE = registerAccessory("dog_christmas_tree", DoggyAccessories.DOG_CHRISTMAS_TREE);
    public static final Supplier<AccessoryItem> DOG_CHRISTMAS_STAR = registerAccessory("dog_christmas_star", DoggyAccessories.DOG_CHRISTMAS_STAR);
    public static final Supplier<AccessoryItem> DOG_GIFT_COSTUME = register("dog_gift_costume", (props) -> new DogPresentCostumeItem(DoggyAccessories.DOG_GIFT_COSTUME, props));
    public static final Supplier<AccessoryItem> BUNNY_EARS = register("bunny_ears", (props) -> new BunnyEarsItem(DoggyAccessories.BUNNY_EARS, props));

    //DTN Misc ==========================
    public static final Supplier<Item> THROW_BONE_WET = registerThrowBoneWet("throw_bone_wet");
    public static final Supplier<Item> THROW_STICK_WET = registerThrowStickWet("throw_stick_wet");
    public static final Supplier<Item> FRISBEE_WET = registerFrisbeeWet("frisbee_wet");
    public static final Supplier<Item> DROOL_SCENT_TREAT = register("drool_scent_treat", DroolScentTreatItem::new);
    public static final Supplier<Item> ENERGIZER_STICK = register("energizer_stick", EnergizerStick::new);
    public static final Supplier<Item> EASTER_EGG_CANDY = register("easter_egg_candy", EasterEggCandyItem::new);
    public static final Supplier<DoggyArtifactItem> FEATHERED_MANTLE = registerWith("feathered_mantle", 
        props -> new DoggyArtifactItem(
            () -> new FeatheredMantleArtifact(), props), 1);
    public static final Supplier<Item> CREATIVE_CANINE_TRACKER = registerWith("creative_canine_tracker", props -> new CanineTrackerItem(props) 
        { @Override public boolean isFoil(ItemStack stack) { return true; } }, 1);
    public static final Supplier<Item> SUSSY_SICKLE = register("sussy_sickle", SussySickleItem::new);
    public static final Supplier<DogPlushieItem> DOG_PLUSHIE_TOY = register("dog_plushie_toy_item", 
        (props) -> new DogPlushieItem(props));
    public static final Supplier<SamoyedPlushieItem> SAMOYED_PLUSHIE_TOY = register("samoyed_plushie_toy_item", 
        (props) -> new SamoyedPlushieItem(props));

    public static final Supplier<Item> MUSIC_DISC_CHOPIN_OP64_NO1 = register("disc_chopin_op64_no1", 
        (props) -> new ChopinRecordItem(
        props.stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(DTMusicProvider.CHOPIN_OP64_NO_1), 132*20));
    public static final Supplier<PianoItem> GRAND_PIANO_WHITE = register("grand_piano_white_item", 
        (props) -> new PianoItem(props, DoggyEntityTypes.GRAND_PIANO_WHITE));
    public static final Supplier<PianoItem> GRAND_PIANO_BLACK = register("grand_piano_black_item", 
        (props) -> new PianoItem(props, DoggyEntityTypes.GRAND_PIANO_BLACK));
    public static final Supplier<PianoItem> UPRIGHT_PIANO_BLACK = register("upright_piano_black_item", 
        (props) -> new PianoItem(props, DoggyEntityTypes.UPRIGHT_PIANO_BLACK));
    public static final Supplier<PianoItem> UPRIGHT_PIANO_BROWN = register("upright_piano_brown_item", 
        (props) -> new PianoItem(props, DoggyEntityTypes.UPRIGHT_PIANO_BROWN));
    public static final Supplier<PianoItem> UPRIGHT_PIANO_WHITE = register("upright_piano_white_item", 
        (props) -> new PianoItem(props, DoggyEntityTypes.UPRIGHT_PIANO_WHITE));

    //DTN Debug (Not available via Creative Inventory) ==========================
    public static final Supplier<Item> DOG_ANIM_DEBUG = register("dog_anim_debug_stick", DogAnimDebugItem::new); 

    private static Item.Properties createInitialProp() {
        return new Item.Properties();
    }

    private static Supplier<Item> registerThrowBone(final String name) {
        return register(name, (props) -> new ThrowableItem(THROW_BONE_WET, () -> Items.BONE, props.stacksTo(2)));
    }

    private static Supplier<Item> registerThrowStick(final String name) {
        return register(name, (props) -> new ThrowableItem(THROW_STICK_WET, THROW_STICK, props.stacksTo(8)));
    }

    private static Supplier<Item> registerFrisbee(final String name) {
        return register(name, (props) -> new FrisbeeItem(FRISBEE_WET, FRISBEE, props.stacksTo(1)));
    }

    private static Supplier<Item> registerThrowBoneWet(final String name) {
        return register(name, (props) -> new DroolBoneItem(THROW_BONE, props.stacksTo(1)));
    }

    private static Supplier<Item> registerThrowStickWet(final String name) {
        return register(name, (props) -> new DroolBoneItem(THROW_STICK, props.stacksTo(1)));
    }

    private static Supplier<Item> registerFrisbeeWet(final String name) {
        return register(name, (props) -> new FrisbeeDroolItem(FRISBEE, props.stacksTo(1)));
    }

    private static Supplier<Item> registerSizeBone(final String name, final DogResizeItem.Type typeIn) {
        return register(name, (props) -> new DogResizeItem(typeIn, props.stacksTo(1).durability(10)));
    }

    private static Supplier<Item> registerTreat(final String name, final DogLevel.Type typeIn, int maxLevel) {
        return register(name, (props) -> new TreatItem(maxLevel, typeIn, props));
    }

    private static Supplier<DyeableAccessoryItem> registerAccessoryDyed(final String name, Supplier<? extends DyeableAccessory> type) {
        return register(name, (props) -> new DyeableAccessoryItem(type, props));
    }

    private static Supplier<AccessoryItem> registerAccessory(final String name, Supplier<? extends Accessory> type) {
        return register(name, (props) -> new AccessoryItem(type, props));
    }

    private static Supplier<AccessoryItem> registerDualAccessory(final String name, Supplier<? extends Accessory> primary, Supplier<? extends Accessory> secondary) {
        return register(name, (props) -> new DualAccessoryItem(primary, secondary, props));
    }

    private static Supplier<AccessoryItem> registerSnorkel(final String name, Supplier<? extends Accessory> type) {
        return register(name, (props) -> new SnorkelAccessoryItem(type, props));
    }

    private static Supplier<AccessoryItem> registerLocatorOrb(final String name, Supplier<? extends LocatorOrbAccessory> type) {
        return register(name, (props) -> new LocatorOrbItem(type, props));
    }

    private static Supplier<AccessoryItem> registerHeadBand(final String name, Supplier<? extends HeadBandAccessory> type) {
        return register(name, props -> new HeadBandItem(type, props));
    }

    private static <T extends Item> Supplier<T> registerWith(final String name, Function<Item.Properties, T> itemConstructor, int maxStackSize) {
        return register(name, (props) -> itemConstructor.apply(props.stacksTo(maxStackSize)));
    }
    
    private static <T extends Item> Supplier<T> registerWithFireResistant(final String name, Function<Item.Properties, T> itemConstructor, int maxStackSize) {
        return register(name, (props) -> itemConstructor.apply(props.stacksTo(maxStackSize).fireResistant()));
    }

    private static <T extends Item> Supplier<T> registerTool(final String name, Function<Item.Properties, T> itemConstructor, int durability) {
        return register(name, (props) -> itemConstructor.apply(props.stacksTo(1).durability(durability)));
    }

    // private static <T extends Item> Supplier<T> register(final String name, Function<Item.Properties, T> itemConstructor) {
    //     return registerLegacy(name, () -> itemConstructor.apply(createInitialProp()));
    // }

    private static Supplier<Item> register(final String name) {
        return registerWith(name, (Function<Item.Properties, Item.Properties>) null);
    }

    private static Supplier<Item> registerWith(final String name, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        //Item.Properties prop = createInitialProp();
        return register(name, (props) -> new Item(extraPropFunc != null ? extraPropFunc.apply(props) : props));
    }

    // private static <T extends Item> Supplier<T> registerLegacy(final String name, final Supplier<T> sup) {
    //     return ITEMS.register(name, sup);
    // }

    private static <T extends Item> Supplier<T> register(final String name, final Function<Item.Properties, T> sup) {
        return ITEMS.registerItem(name, sup);
    }

    // Item color system was removed in 26.1; item tinting now uses data-driven ItemTintSource.
    // Dyeable accessories need to be migrated to the new system.
    // public static void registerItemColours(final RegisterColorHandlersEvent.ItemTintSources event) {
    // }
}
