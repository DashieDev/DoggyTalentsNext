package doggytalents;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.registry.Accessory;
import doggytalents.common.artifacts.FeatheredMantleArtifact;
import doggytalents.common.entity.accessory.AngelHalo;
import doggytalents.common.entity.accessory.AngelWings;
import doggytalents.common.entity.accessory.BakerHat;
import doggytalents.common.entity.accessory.CeremonialGarb;
import doggytalents.common.entity.accessory.ChefHat;
import doggytalents.common.entity.accessory.DyeableAccessory;
import doggytalents.common.entity.accessory.Fedora;
import doggytalents.common.entity.accessory.FlatCap;
import doggytalents.common.entity.accessory.GiantStick;
import doggytalents.common.entity.accessory.HeadBandAccessory;
import doggytalents.common.entity.accessory.LabCoat;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import doggytalents.common.entity.accessory.Propellar;
import doggytalents.common.entity.accessory.Wig;
import doggytalents.common.entity.accessory.KitsuneMask.KitsuneMaskItem;
import doggytalents.common.entity.accessory.TenguMask.TenguMaskItem;
import doggytalents.common.item.*;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.event.RegisterColorHandlersEvent;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(() -> BuiltInRegistries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item> THROW_BONE = registerThrowBone("throw_bone");
    public static final RegistryObject<Item> THROW_BONE_WET = registerThrowBoneWet("throw_bone_wet");
    public static final RegistryObject<Item> THROW_STICK = registerThrowStick("throw_stick");
    public static final RegistryObject<Item> THROW_STICK_WET = registerThrowStickWet("throw_stick_wet");
    public static final RegistryObject<Item> TRAINING_TREAT = registerTreat("training_treat", DogLevel.Type.NORMAL, 20);
    public static final RegistryObject<Item> SUPER_TREAT = registerTreat("super_treat", DogLevel.Type.NORMAL, 40);
    public static final RegistryObject<Item> MASTER_TREAT = registerTreat("master_treat", DogLevel.Type.NORMAL, 60);
    public static final RegistryObject<Item> KAMI_TREAT = registerTreat("kami_treat", DogLevel.Type.KAMI, 30);
    public static final RegistryObject<Item> BREEDING_BONE = register("breeding_bone");
    public static final RegistryObject<Item> DOGGY_CHARM = registerWith("doggy_charm", DoggyCharmItem::new, 1);
    public static final RegistryObject<AccessoryItem> RADIO_COLLAR = registerAccessory("radio_collar", DoggyAccessories.RADIO_BAND);
    public static final RegistryObject<DyeableAccessoryItem> WOOL_COLLAR = registerAccessoryDyed("wool_collar", DoggyAccessories.DYEABLE_COLLAR);
    public static final RegistryObject<DyeableAccessoryItem> WOOL_COLLAR_THICC = registerAccessoryDyed("wool_collar_thicc", DoggyAccessories.DYEABLE_COLLAR_THICC);
    public static final RegistryObject<AccessoryItem> CREATIVE_COLLAR = register("creative_collar",  () -> new AccessoryItem(DoggyAccessories.GOLDEN_COLLAR, createInitialProp()) 
        { @Override public boolean isFoil(ItemStack stack) { return true; } } );
    public static final RegistryObject<AccessoryItem> SPOTTED_COLLAR = registerAccessory("spotted_collar", DoggyAccessories.SPOTTED_COLLAR);
    public static final RegistryObject<AccessoryItem> MULTICOLOURED_COLLAR = registerAccessory("multicoloured_collar", DoggyAccessories.MULTICOLORED_COLLAR);
    public static final RegistryObject<Item> CANINE_TRACKER = registerWith("canine_tracker", CanineTrackerItem::new, 1);
    public static final RegistryObject<Item> CONDUCTING_BONE = registerWithFireResistant("conducting_bone", ConductingBoneItem::new, 1);
    public static final RegistryObject<Item> CREATIVE_CANINE_TRACKER = registerWith("creative_canine_tracker", props -> new CanineTrackerItem(props) 
        { @Override public boolean isFoil(ItemStack stack) { return true; } }, 1);
    public static final RegistryObject<WhistleItem> WHISTLE = registerWith("whistle", WhistleItem::new, 1);
    public static final RegistryObject<Item> TREAT_BAG = registerWith("treat_bag", TreatBagItem::new, 1);
    public static final RegistryObject<Item> ENERGIZER_STICK = register("energizer_stick", EnergizerStick::new);
    public static final RegistryObject<Item> EGG_SANDWICH = register("egg_sandwich", EggSandwichItem::new);
    public static final RegistryObject<Item> RICE_BOWL = register("rice_bowl", RiceBowlItem::new);
    public static final RegistryObject<Item> UNCOOKED_RICE_BOWL = register("uncooked_rice_bowl", UncookedRiceBowlItem::new);
    public static final RegistryObject<Item> SALMON_SUSHI = register("salmon_sushi", SalmonSushiItem::new);
    public static final RegistryObject<Item> ONIGIRI = register("onigiri", OnigiriItem::new);
    public static final RegistryObject<Item> SAUSAGE = register("sausage", SausageItem::new);
    public static final RegistryObject<Item> BANDAID = register("bandaid", BandaidItem::new);
    public static final RegistryObject<AccessoryItem> TANTAN_CAPE = registerAccessory("tantan_cape", DoggyAccessories.TANTAN_CAPE);
    public static final RegistryObject<DyeableAccessoryItem> CAPE_COLOURED = registerAccessoryDyed("cape_coloured", DoggyAccessories.DYEABLE_CAPE);
    public static final RegistryObject<AccessoryItem> SUNGLASSES = registerAccessory("sunglasses", DoggyAccessories.SUNGLASSES);
    public static final RegistryObject<AccessoryItem> GUARD_SUIT = registerAccessory("guard_suit", DoggyAccessories.GUARD_SUIT);
    public static final RegistryObject<AccessoryItem> PIANIST_SUIT = registerAccessory("pianist_suit", DoggyAccessories.PIANIST_SUIT);
    public static final RegistryObject<AccessoryItem> CONAN_SUIT = registerAccessory("conan_suit", DoggyAccessories.CONAN_SUIT);
    public static final RegistryObject<AccessoryItem> BEASTARS_UNIFORM_MALE = registerAccessory("beastars_uniform_male", DoggyAccessories.BEASTARS_UNIFORM_MALE);
    public static final RegistryObject<AccessoryItem> BEASTARS_UNIFORM_FEMALE = registerAccessory("beastars_uniform_female", DoggyAccessories.BEASTARS_UNIFORM_FEMALE);
    public static final RegistryObject<AccessoryItem> DEATH_HOOD = registerAccessory("death_hood", DoggyAccessories.DEATH_HOOD);
    public static final RegistryObject<DyeableAccessoryItem> BOWTIE = registerAccessoryDyed("bowtie", DoggyAccessories.BOWTIE);
    public static final RegistryObject<AccessoryItem> SMARTY_GLASSES = registerAccessory("smarty_glasses", DoggyAccessories.SMARTY_GLASSES);
    public static final RegistryObject<DyeableAccessoryItem> WIG = register("wig", () -> new Wig.WigItem(DoggyAccessories.WIG, createInitialProp()));
    public static final RegistryObject<AccessoryItem> BACH_WIG = registerAccessory("bach_wig", DoggyAccessories.BACH_WIG);
    public static final RegistryObject<AccessoryItem> LEATHER_JACKET = registerAccessory("leather_jacket", DoggyAccessories.LEATHER_JACKET_CLOTHING);
    public static final RegistryObject<Item> SHRINKING_MALLET = registerSizeBone("shrinking_mallet", DogResizeItem.Type.TINY);
    public static final RegistryObject<Item> MAGNIFYING_BONE = registerSizeBone("magnifying_bone", DogResizeItem.Type.BIG);
    public static final RegistryObject<Item> AMNESIA_BONE = registerWith("amnesia_bone", AmnesiaBoneItem::new, 1);
    public static final RegistryObject<Item> EMPTY_LOCATOR_ORB = registerWith("empty_locator_orb", EmptyLocatorOrbItem::new, 64);
    public static final RegistryObject<AccessoryItem> CHI_ORB = registerLocatorOrb("locator_orb_chi", DoggyAccessories.CHI_ORB);
    public static final RegistryObject<AccessoryItem> CHU_ORB = registerLocatorOrb("locator_orb_chu", DoggyAccessories.CHU_ORB);
    public static final RegistryObject<AccessoryItem> KO_ORB = registerLocatorOrb("locator_orb_ko", DoggyAccessories.KO_ORB);
    public static final RegistryObject<AccessoryItem> GI_ORB = registerLocatorOrb("locator_orb_gi", DoggyAccessories.GI_ORB);
    public static final RegistryObject<AccessoryItem> TEI_ORB = registerLocatorOrb("locator_orb_tei", DoggyAccessories.TEI_ORB);
    public static final RegistryObject<AccessoryItem> REI_ORB = registerLocatorOrb("locator_orb_rei", DoggyAccessories.REI_ORB);
    public static final RegistryObject<AccessoryItem> SHIN_ORB = registerLocatorOrb("locator_orb_shin", DoggyAccessories.SHIN_ORB);
    public static final RegistryObject<AccessoryItem> JIN_ORB = registerLocatorOrb("locator_orb_jin", DoggyAccessories.JIN_ORB);
    public static final Supplier<AccessoryItem> DYED_ORB = register("locator_orb_dyable", () -> new DyableOrbItem(DoggyAccessories.DYED_ORB, createInitialProp()));
    public static final RegistryObject<Item> GENDER_BONE = registerTool("gender_bone", GenderBoneItem::new, 10);
    public static final RegistryObject<Item> GOLDEN_A_FIVE_WAGYU = register("golden_a_five_wagyu", GoldenAFiveWagyuItem::new);
    public static final RegistryObject<SwordItem> SUSSY_SICKLE = register("sussy_sickle", SussySickleItem::new);
    public static final RegistryObject<AccessoryItem> SNORKEL = registerSnorkel("snorkel", DoggyAccessories.SNORKEL);
    public static final RegistryObject<Item> STARTER_BUNDLE = registerWith("starter_bundle", StarterBundleItem::new, 1);
    
    public static final RegistryObject<AccessoryItem> HEAD_BAND_BLANK = registerHeadBand("head_band_blank", DoggyAccessories.HEAD_BAND_BlANK);
    public static final RegistryObject<AccessoryItem> HEAD_BAND_MYSTERY = registerHeadBand("head_band_mystery", DoggyAccessories.HEAD_BAND_MYSTERY);
    public static final RegistryObject<AccessoryItem> HEAD_BAND_HIGHHH = registerHeadBand("head_band_highhh", DoggyAccessories.HEAD_BAND_HIGHHH);
    public static final RegistryObject<DyeableAccessoryItem> BAKER_HAT = register("baker_hat", () -> new BakerHat.BakerHatItem(DoggyAccessories.BAKER_HAT, createInitialProp()));
    public static final RegistryObject<DyeableAccessoryItem> CHEF_HAT = register("chef_hat", () -> new ChefHat.ChefHatItem(DoggyAccessories.CHEF_HAT, createInitialProp()));
    public static final RegistryObject<DyeableAccessoryItem> LAB_COAT = register("lab_coat", () -> new LabCoat.LabCoatItem(DoggyAccessories.LAB_COAT, createInitialProp()));

    public static final RegistryObject<AccessoryItem> SUPERDOG_SUIT = registerAccessory("superdog_suit", DoggyAccessories.SUPERDOG_SUIT);
    public static final RegistryObject<DyeableAccessoryItem> FLYING_CAPE = registerAccessoryDyed("flying_cape", DoggyAccessories.FLYING_CAPE);
    public static final RegistryObject<AccessoryItem> BAT_WINGS = registerAccessory("bat_wings", DoggyAccessories.BAT_WINGS);
    public static final RegistryObject<AccessoryItem> CROW_WINGS = registerAccessory("crow_wings", DoggyAccessories.CROW_WINGS);
    public static final RegistryObject<AccessoryItem> DIVINE_RETRIBUTON = register("divine_retribution", () -> new FieryReflectorItem(DoggyAccessories.DIVINE_RETRIBUTION, createInitialProp()));
    public static final RegistryObject<AccessoryItem> SOUL_REFLECTOR = register("soul_reflector", () -> new FieryReflectorItem(DoggyAccessories.SOUL_REFLECTOR, createInitialProp()));
    public static final Supplier<DyeableAccessoryItem> ANGEL_WINGS = register("angel_wings", () -> new AngelWings.Item(DoggyAccessories.ANGEL_WINGS, createInitialProp()));
    public static final Supplier<AccessoryItem> ANGEL_HALO = register("angel_halo", () -> new AngelHalo.AngelHaloItem(DoggyAccessories.ANGEL_HALO, createInitialProp()));

    public static final RegistryObject<AccessoryItem> TENGU_MASK = register("tengu_mask", () -> new TenguMaskItem(DoggyAccessories.TENGU_MASK, createInitialProp()));
    public static final RegistryObject<AccessoryItem> DEMON_HORNS = register("demon_horns", () -> new DemonHornsItem(DoggyAccessories.DEMON_HORNS, createInitialProp()));
    public static final RegistryObject<AccessoryItem> WITCH_HAT = register("witch_hat", () -> new WitchHatItem(DoggyAccessories.WITCH_HAT, createInitialProp()));
    public static final RegistryObject<AccessoryItem> PLAGUE_DOC_MASK = register("plague_doctor_mask", () -> new PlagueDoctorMaskItem(DoggyAccessories.PLAGUE_DOC_MASK, createInitialProp()));
    public static final RegistryObject<AccessoryItem> BIRTHDAY_HAT = register("birthday_hat", () -> new DyableBirthdayHatItem(DoggyAccessories.BIRTHDAY_HAT, createInitialProp()));
    public static final RegistryObject<AccessoryItem> PROPELLER_HAT = register("propeller_hat", () -> new Propellar.PropellerHatItem(DoggyAccessories.PROPELLAR, createInitialProp()));
    public static final RegistryObject<AccessoryItem> FEDORA = register("fedora", () -> new Fedora.FedoraItem(DoggyAccessories.FEDORA, createInitialProp()));
    public static final RegistryObject<DyeableAccessoryItem> FLATCAP = register("flatcap", () -> new FlatCap.FlatCapItem(DoggyAccessories.FLATCAP, createInitialProp()));

    public static final RegistryObject<AccessoryItem> KITSUNE_MASK = register("kitsune_mask", () -> new KitsuneMaskItem(DoggyAccessories.KITSUNE_MASK, createInitialProp()));

    public static final RegistryObject<AccessoryItem> HOT_DOG = register("hot_dog",() -> new HotDogAccessoryItem(DoggyAccessories.HOT_DOG, createInitialProp()));
    public static final RegistryObject<AccessoryItem> GIANT_STICK = register("giant_stick",() -> new GiantStickAccessoryItem(DoggyAccessories.GIANT_STICK, createInitialProp()));
    public static final RegistryObject<DyeableAccessoryItem> CERE_GARB = register("ceremonial_garb", () -> new CeremonialGarb.Item(DoggyAccessories.CERE_GARB, createInitialProp()));
    public static final RegistryObject<AccessoryItem> DOGGY_CONTACTS = register("doggy_contacts", () -> new DoggyContactsItem(DoggyAccessories.DOGGY_CONTACTS, createInitialProp()));
    
    public static final RegistryObject<Item> FRISBEE = registerFrisbee("frisbee");
    public static final RegistryObject<Item> FRISBEE_WET = registerFrisbeeWet("frisbee_wet");


    public static final RegistryObject<Item> RICE_GRAINS = register("rice_grains", 
        () -> new RiceGrainsItem(DoggyBlocks.RICE_CROP.get(), createInitialProp()));
    public static final RegistryObject<Item> RICE_WHEAT = register("rice_wheat",
        () -> new RiceWheatItem(createInitialProp()));
    public static final RegistryObject<Item> KOJI = register("koji", KojiItem::new);
    public static final RegistryObject<Item> SOY_BEANS = register("soy_beans", 
        () -> new BlockItem(DoggyBlocks.SOY_CROP.get(), createInitialProp()));
    public static final RegistryObject<Item> SOY_PODS = register("soy_pods", 
        () -> new SoyPodsItem(createInitialProp()));
    public static final RegistryObject<Item> UNCOOKED_RICE = register("uncooked_rice");
    public static final RegistryObject<Item> SOY_PODS_DRIED = register("soy_pods_dried",  SoyPodsDriedItem::new);
    public static final RegistryObject<Item> SOY_BEANS_DRIED = register("soy_beans_dried");
    public static final RegistryObject<Item> EDAMAME = register("edamame", EdamameItem::new);
    public static final RegistryObject<Item> EDAMAME_UNPODDED = register("edamame_unpodded", EdamameUnpoddedItem::new);
    public static final RegistryObject<Item> MISO_PASTE = register("miso_paste", MisoPasteItem::new);
    public static final RegistryObject<Item> MISO_SOUP = register("miso_soup", MisoSoupItem::new);
    public static final RegistryObject<Item> SOY_MILK = register("soy_milk", SoyMilkItem::new);
    public static final RegistryObject<Item> TOFU = register("tofu", TofuItem::new);
    public static final RegistryObject<Item> ABURAAGE = register("aburaage", AburaageItem::new);
    public static final RegistryObject<Item> NATTO = register("natto", NattoItem::new);
    public static final RegistryObject<Item> NATTO_RICE = register("natto_rice", NattoRiceItem::new);
    public static final RegistryObject<Item> ONSEN_TAMAGO = register("onsen_tamago", OnsenTamagoItem::new);
    public static final RegistryObject<Item> GYUDON = register("gyudon", GyudonItem::new);
    public static final RegistryObject<Item> OYAKODON = register("oyakodon", OyakodonItem::new);
    public static final RegistryObject<Item> SAKE = register("sake", SakeItem::new);

    public static final RegistryObject<Item> SCENT_TREAT = register("scent_treat", ScentTreatItem::new);
    public static final RegistryObject<Item> DROOL_SCENT_TREAT = register("drool_scent_treat", DroolScentTreatItem::new);

    public static final RegistryObject<DoggyArtifactItem> FEATHERED_MANTLE = registerWith("feathered_mantle", 
        props -> new DoggyArtifactItem(
            () -> new FeatheredMantleArtifact(), props), 1);

    public static final RegistryObject<Item> MUSIC_DISC_BWV_1080_FUGUE_11_KIMIKO = register("disc_bwv_1080_fugue_11", 
        () -> new RecordItem(13, DoggySounds.BWV_1080_FUGUE_11_KIMIKO.get() , 
        (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 292*20));
    public static final RegistryObject<Item> MUSIC_DISC_BWV_849_FUGUE_KIMIKO = register("disc_bwv_849_fugue", 
        () -> new RecordItem(13, DoggySounds.BWV_849_FUGUE_KIMIKO.get() , 
        (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 160*20));
    public static final RegistryObject<Item> MUSIC_DISC_OKAMI_1 = register("disc_okami_ryoshima_coast_arr", 
        () -> new RecordItem(13, DoggySounds.OKAMI_RYOSHIMA_COAST_ARR.get() , 
        (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 111*20));
    public static final RegistryObject<Item> MUSIC_DISC_CHOPIN_OP64_NO1 = register("disc_chopin_op64_no1", 
        () -> new ChopinRecordItem(13, () -> DoggySounds.CHOPIN_OP64_NO1.get() , 
        (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 132*20));

    public static final RegistryObject<PianoItem> GRAND_PIANO_BLACK = register("grand_piano_black_item", 
        () -> new PianoItem(DoggyEntityTypes.GRAND_PIANO_BLACK));
    public static final RegistryObject<PianoItem> GRAND_PIANO_WHITE = register("grand_piano_white_item", 
        () -> new PianoItem(DoggyEntityTypes.GRAND_PIANO_WHITE));
    public static final RegistryObject<PianoItem> UPRIGHT_PIANO_BLACK = register("upright_piano_black_item", 
        () -> new PianoItem(DoggyEntityTypes.UPRIGHT_PIANO_BLACK));
    public static final RegistryObject<PianoItem> UPRIGHT_PIANO_BROWN = register("upright_piano_brown_item", 
        () -> new PianoItem(DoggyEntityTypes.UPRIGHT_PIANO_BROWN));
    public static final RegistryObject<DogPlushieItem> DOG_PLUSHIE_TOY = register("dog_plushie_toy_item", 
        () -> new DogPlushieItem());

    private static Item.Properties createInitialProp() {
        return new Item.Properties();
    }

    private static RegistryObject<Item> registerThrowBone(final String name) {
        return register(name, () -> new ThrowableItem(THROW_BONE_WET, () -> Items.BONE, createInitialProp().stacksTo(2)));
    }

    private static RegistryObject<Item> registerThrowStick(final String name) {
        return register(name, () -> new ThrowableItem(THROW_STICK_WET, THROW_STICK, createInitialProp().stacksTo(8)));
    }

    private static RegistryObject<Item> registerFrisbee(final String name) {
        return register(name, () -> new FrisbeeItem(FRISBEE_WET, FRISBEE, createInitialProp().stacksTo(1)));
    }

    private static RegistryObject<Item> registerThrowBoneWet(final String name) {
        return register(name, () -> new DroolBoneItem(THROW_BONE, createInitialProp().stacksTo(1)));
    }

    private static RegistryObject<Item> registerThrowStickWet(final String name) {
        return register(name, () -> new DroolBoneItem(THROW_STICK, createInitialProp().stacksTo(1)));
    }

    private static RegistryObject<Item> registerFrisbeeWet(final String name) {
        return register(name, () -> new FrisbeeDroolItem(FRISBEE, createInitialProp().stacksTo(1)));
    }

    private static RegistryObject<Item> registerSizeBone(final String name, final DogResizeItem.Type typeIn) {
        return register(name, () -> new DogResizeItem(typeIn, createInitialProp().stacksTo(1).durability(10)));
    }

    private static RegistryObject<Item> registerTreat(final String name, final DogLevel.Type typeIn, int maxLevel) {
        return register(name, () -> new TreatItem(maxLevel, typeIn, createInitialProp()));
    }

    private static RegistryObject<DyeableAccessoryItem> registerAccessoryDyed(final String name, Supplier<? extends DyeableAccessory> type) {
        return register(name, () -> new DyeableAccessoryItem(type, createInitialProp()));
    }

    private static RegistryObject<AccessoryItem> registerAccessory(final String name, Supplier<? extends Accessory> type) {
        return register(name, () -> new AccessoryItem(type, createInitialProp()));
    }

    private static RegistryObject<AccessoryItem> registerSnorkel(final String name, Supplier<? extends Accessory> type) {
        return register(name, () -> new SnorkelAccessoryItem(type, createInitialProp()));
    }

    private static RegistryObject<AccessoryItem> registerLocatorOrb(final String name, Supplier<? extends LocatorOrbAccessory> type) {
        return register(name, () -> new LocatorOrbItem(type, createInitialProp()));
    }

    private static RegistryObject<AccessoryItem> registerHeadBand(final String name, Supplier<? extends HeadBandAccessory> type) {
        return register(name, () -> new HeadBandItem(type, createInitialProp()));
    }

    private static <T extends Item> RegistryObject<T> registerWith(final String name, Function<Item.Properties, T> itemConstructor, int maxStackSize) {
        return register(name, () -> itemConstructor.apply(createInitialProp().stacksTo(maxStackSize)));
    }
    
    private static <T extends Item> RegistryObject<T> registerWithFireResistant(final String name, Function<Item.Properties, T> itemConstructor, int maxStackSize) {
        return register(name, () -> itemConstructor.apply(createInitialProp().stacksTo(maxStackSize).fireResistant()));
    }

    private static <T extends Item> RegistryObject<T> registerTool(final String name, Function<Item.Properties, T> itemConstructor, int durability) {
        return register(name, () -> itemConstructor.apply(createInitialProp().stacksTo(1).durability(durability)));
    }

    private static <T extends Item> RegistryObject<T> register(final String name, Function<Item.Properties, T> itemConstructor) {
        return register(name, () -> itemConstructor.apply(createInitialProp()));
    }

    private static RegistryObject<Item> register(final String name) {
        return registerWith(name, (Function<Item.Properties, Item.Properties>) null);
    }

    private static RegistryObject<Item> registerWith(final String name, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        Item.Properties prop = createInitialProp();
        return register(name, () -> new Item(extraPropFunc != null ? extraPropFunc.apply(prop) : prop));
    }

    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ITEMS.register(name, sup);
    }

    public static void registerItemColours(final RegisterColorHandlersEvent.Item event) {
        var itemColors = event.getItemColors();
        Util.acceptOrElse(DoggyItems.WOOL_COLLAR, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.WOOL_COLLAR_THICC, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.CAPE_COLOURED, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.BOWTIE, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.WIG, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.FRISBEE, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.BAKER_HAT, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.CHEF_HAT, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.LAB_COAT, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.FRISBEE_WET, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.BIRTHDAY_HAT, (item) -> {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return ((DyableBirthdayHatItem) stack.getItem()).getFgColor(stack);
                }
                return tintIndex > 0 ? -1 : ((DyableBirthdayHatItem) stack.getItem()).getBgColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.FLYING_CAPE, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.CERE_GARB, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex != 1 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.ANGEL_WINGS, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ItemUtil.getDyeColorForStack(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.DOG_PLUSHIE_TOY, (item) -> {
            event.register((stack, tintIndex) -> {
                return tintIndex != 1 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
             }, item);
        }, DoggyBlocks::logError);
        Util.acceptOrElse(DoggyItems.DOGGY_CONTACTS, (item) -> {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return ((DoubleDyableAccessoryItem) stack.getItem()).getFgColor(stack);
                }
                return tintIndex > 0 ? -1 : ((DoubleDyableAccessoryItem) stack.getItem()).getBgColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyItems.DYED_ORB, (item) -> {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return ((DoubleDyableAccessoryItem) stack.getItem()).getFgColor(stack);
                }
                return tintIndex > 0 ? -1 : ((DoubleDyableAccessoryItem) stack.getItem()).getBgColor(stack);
             }, item);
        }, DoggyBlocks::logError);

        Util.acceptOrElse(DoggyBlocks.DOG_BATH, (item) -> {
            itemColors.register((stack, tintIndex) -> {
                return 4159204;
             }, item.asItem());
        }, DoggyBlocks::logError);
    }
}
