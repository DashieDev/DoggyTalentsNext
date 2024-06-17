package doggytalents;

import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.Accessory.AccessoryRenderType;
import doggytalents.common.entity.accessory.*;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DoggyAccessories {

    public static final DeferredRegister<Accessory> ACCESSORIES = DeferredRegister.create(DoggyRegistries.Keys.ACCESSORIES_REGISTRY, Constants.MOD_ID);

    public static final Supplier<DyeableAccessory> DYEABLE_COLLAR = register("dyeable_collar", () -> new DyeableAccessory(DoggyAccessoryTypes.COLLAR, DoggyItems.WOOL_COLLAR).setDogStillNakedWhenWear(true).setModelTexture(Resources.COLLAR_DEFAULT).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<DyeableAccessory> DYEABLE_COLLAR_THICC = register("dyeable_collar_thicc", () -> new DyeableAccessory(DoggyAccessoryTypes.COLLAR, DoggyItems.WOOL_COLLAR_THICC).setDogStillNakedWhenWear(true).setModelTexture(Resources.COLLAR_THICC).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Collar> GOLDEN_COLLAR = register("golden_collar", () -> new Collar(DoggyItems.CREATIVE_COLLAR).setModelTexture(Resources.COLLAR_GOLDEN).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Collar> SPOTTED_COLLAR = register("spotted_collar", () -> new Collar(DoggyItems.SPOTTED_COLLAR).setModelTexture(Resources.COLLAR_SPOTTED).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Collar> MULTICOLORED_COLLAR = register("multicolored_collar", () -> new Collar(DoggyItems.MULTICOLOURED_COLLAR).setModelTexture(Resources.COLLAR_MULTICOLORED).setAccessoryRenderType(AccessoryRenderType.OVERLAY));

    public static final Supplier<Clothing> GUARD_SUIT = register("guard_suit", () -> new Clothing(DoggyItems.GUARD_SUIT).setModelTexture(Resources.GUARD_SUIT).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Clothing> LEATHER_JACKET_CLOTHING = register("leather_jacket_clothing", () -> new Clothing(DoggyItems.LEATHER_JACKET).setModelTexture(Resources.CLOTHING_LEATHER_JACKET).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Glasses> SUNGLASSES = register("sunglasses", () -> new Glasses(DoggyItems.SUNGLASSES).setModelTexture(Resources.GLASSES_SUNGLASSES).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Clothing> TANTAN_CAPE = register("tantan_cape", () -> new Clothing(DoggyItems.TANTAN_CAPE).setModelTexture(Resources.TANTAN_CAPE).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<DyeableAccessory> DYEABLE_CAPE = register("dyeable_cape", () -> new DyeableAccessory(DoggyAccessoryTypes.CLOTHING, DoggyItems.CAPE_COLOURED).setModelTexture(Resources.DYEABLE_CAPE).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Clothing> PIANIST_SUIT = register("pianist_suit", () -> new Clothing(DoggyItems.PIANIST_SUIT).setModelTexture(Resources.PIANIST_SUIT).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Clothing> DEATH_HOOD = register("death_hood", () -> new Clothing(DoggyItems.DEATH_HOOD).setModelTexture(Resources.DEATH_HOOD).setRenderTranslucent(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<BowTie> BOWTIE = register("bowtie", () -> new BowTie(DoggyItems.BOWTIE).setModelTexture(Resources.BOW_TIE).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<SmartyGlasses> SMARTY_GLASSES = register("smarty_glasses", () -> new SmartyGlasses(DoggyItems.SMARTY_GLASSES).setModelTexture(Resources.SMARTY_GLASSES).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<Wig> WIG = register("wig", () -> new Wig(DoggyItems.WIG).setModelTexture(Resources.WIG).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<BachWig> BACH_WIG = register("bach_wig", () -> new BachWig(DoggyItems.BACH_WIG).setModelTexture(Resources.BACH_WIG).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<Band> RADIO_BAND = register("radio_band", () -> new Band(DoggyItems.RADIO_COLLAR).setModelTexture(Resources.RADIO_BAND).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Clothing> CONAN_SUIT = register("conan_suit", () -> new Clothing(DoggyItems.CONAN_SUIT).setModelTexture(Resources.CONAN_SUIT).setHasHindLegDiffTex(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final Supplier<Clothing> BEASTARS_UNIFORM_MALE = register("beastars_uniform_male", () -> new BeastarsUniformMale(DoggyItems.BEASTARS_UNIFORM_MALE).setModelTexture(Resources.BEASTARS_UNIFORM_MALE).setHasHindLegDiffTex(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY_AND_MODEL).setRenderTranslucent(true));
    public static final Supplier<Clothing> BEASTARS_UNIFORM_FEMALE = register("beastars_uniform_female", () -> new BeastarsUniformFemale(DoggyItems.BEASTARS_UNIFORM_FEMALE).setModelTexture(Resources.BEASTARS_UNIFORM_FEMALE).setHasHindLegDiffTex(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY_AND_MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> CHI_ORB = register("locator_orb_chi", () -> new LocatorOrbAccessory(DoggyItems.CHI_ORB, 0xffd69f94).setModelTexture(Resources.CHI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> CHU_ORB = register("locator_orb_chu", () -> new LocatorOrbAccessory(DoggyItems.CHU_ORB, 0xff8161dd).setModelTexture(Resources.CHU_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> KO_ORB = register("locator_orb_ko", () -> new LocatorOrbAccessory(DoggyItems.KO_ORB, 0xffa58ee1).setModelTexture(Resources.KO_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> GI_ORB = register("locator_orb_gi", () -> new LocatorOrbAccessory(DoggyItems.GI_ORB, 0xff809abc).setModelTexture(Resources.GI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> TEI_ORB = register("locator_orb_tei", () -> new LocatorOrbAccessory(DoggyItems.TEI_ORB, 0xffedb689).setModelTexture(Resources.TEI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> REI_ORB = register("locator_orb_rei", () -> new LocatorOrbAccessory(DoggyItems.REI_ORB, 0xfff0e33f).setModelTexture(Resources.REI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> SHIN_ORB = register("locator_orb_shin", () -> new LocatorOrbAccessory(DoggyItems.SHIN_ORB, 0xffaec867).setModelTexture(Resources.SHIN_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<LocatorOrbAccessory> JIN_ORB = register("locator_orb_jin", () -> new LocatorOrbAccessory(DoggyItems.JIN_ORB, 0xffd1523a).setModelTexture(Resources.JIN_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<DoubleDyableAccessory> DYED_ORB = register("locator_orb_dyable", () -> new DyableLocatorOrb(DoggyItems.DYED_ORB).setModelTexture(Resources.DYABLE_ORB_BG).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<Clothing> HOT_DOG = register("hot_dog", () -> new HotDog(DoggyItems.HOT_DOG).setModelTexture(Resources.HOT_DOG).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<Clothing> GIANT_STICK = register("giant_stick", () -> new GiantStick(DoggyItems.GIANT_STICK).setModelTexture(Resources.GIANT_STICK).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final Supplier<Glasses> SNORKEL = register("snorkel", () -> new Snorkel(DoggyItems.SNORKEL).setModelTexture(Resources.SNORKEL).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<HeadBandAccessory> HEAD_BAND_BlANK = register("head_band_blank", () -> new HeadBandAccessory(DoggyItems.HEAD_BAND_BLANK).setModelTexture(Resources.HEAD_BAND_BLANK).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<HeadBandAccessory> HEAD_BAND_MYSTERY = register("head_band_mystery", () -> new HeadBandAccessory(DoggyItems.HEAD_BAND_MYSTERY).setModelTexture(Resources.HEAD_BAND_MYSTERY).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<HeadBandAccessory> HEAD_BAND_HIGHHH = register("head_band_highhh", () -> new HeadBandAccessory(DoggyItems.HEAD_BAND_HIGHHH).setModelTexture(Resources.HEAD_BAND_HIGHHH).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<BakerHat> BAKER_HAT = register("baker_hat", () -> new BakerHat(DoggyItems.BAKER_HAT).setModelTexture(Resources.BAKER_HAT).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<ChefHat> CHEF_HAT = register("chef_hat", () -> new ChefHat(DoggyItems.CHEF_HAT).setModelTexture(Resources.CHEF_HAT).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<LabCoat> LAB_COAT = register("lab_coat", () -> new LabCoat(DoggyItems.LAB_COAT).setModelTexture(Resources.LAB_COAT).setAccessoryRenderType(AccessoryRenderType.MODEL));

    public static final Supplier<Clothing> SUPERDOG_SUIT = register("superdog_suit", () -> new Clothing(DoggyItems.SUPERDOG_SUIT).setModelTexture(Resources.SUPERDOG_SUIT).setHasHindLegDiffTex(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY).setRenderTranslucent(true));
    public static final Supplier<DyeableAccessory> FLYING_CAPE = register("flying_cape", () -> new FlyingCape(DoggyItems.FLYING_CAPE).setModelTexture(Resources.FLYING_CAPE).setRenderTranslucent(true));
    public static final Supplier<Accessory> BAT_WINGS = register("bat_wings", () -> new BatWings(DoggyItems.BAT_WINGS).setModelTexture(Resources.BAT_WINGS).setRenderTranslucent(true));
    public static final Supplier<Accessory> CROW_WINGS = register("crow_wings", () -> new CrowWings(DoggyItems.CROW_WINGS).setModelTexture(Resources.CROW_WINGS).setRenderTranslucent(true));
    public static final Supplier<Accessory> DIVINE_RETRIBUTION = register("divine_retribution", () -> new FieryReflector(DoggyItems.DIVINE_RETRIBUTON).setModelTexture(Resources.DIVINE_RETRIBUTION).setRenderTranslucent(true));
    public static final Supplier<Accessory> SOUL_REFLECTOR = register("soul_reflector", () -> new FieryReflector(DoggyItems.SOUL_REFLECTOR, FieryReflector.Type.SOUL_REFLECTOR).setModelTexture(Resources.SOUL_REFLECTOR).setRenderTranslucent(true));
    public static final Supplier<AngelWings> ANGEL_WINGS = register("angel_wings", () -> new AngelWings(DoggyItems.ANGEL_WINGS).setAccessoryRenderType(AccessoryRenderType.MODEL));

    public static final Supplier<TenguMask> TENGU_MASK = register("tengu_mask", () -> new TenguMask(DoggyItems.TENGU_MASK).setModelTexture(Resources.TENGU_MASK).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<DemonHornsAccessory> DEMON_HORNS = register("demon_horns", () -> new DemonHornsAccessory(DoggyItems.DEMON_HORNS).setModelTexture(Resources.DEMON_HORNS).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<KitsuneMask> KITSUNE_MASK = register("kitsune_mask", () -> new KitsuneMask(DoggyItems.KITSUNE_MASK).setModelTexture(Resources.KITSUNE_MASK).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<WitchHatAccessory> WITCH_HAT = register("witch_hat", () -> new WitchHatAccessory(DoggyItems.WITCH_HAT).setModelTexture(Resources.WITCH_HAT).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<PlagueDoctorMaskAccessory> PLAGUE_DOC_MASK = register("plague_doctor_mask", () -> new PlagueDoctorMaskAccessory(DoggyItems.PLAGUE_DOC_MASK).setModelTexture(Resources.PLAGUE_DOC_MASK).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<BirthdayHatAccessory> BIRTHDAY_HAT = register("birthday_hat", () -> new BirthdayHatAccessory(DoggyItems.BIRTHDAY_HAT).setModelTexture(Resources.BIRTHDAY_HAT_BG).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<CeremonialGarb> CERE_GARB = register("ceramonial_garb", () -> new CeremonialGarb(DoggyItems.CERE_GARB).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<Accessory> PROPELLAR = register("propeller_hat", () -> new Propellar(DoggyItems.PROPELLER_HAT).setModelTexture(Resources.DOG_PROPELLAR).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<Accessory> FEDORA = register("fedora", () -> new Fedora(DoggyItems.FEDORA).setModelTexture(Resources.DOG_FEDORA).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final Supplier<DyeableAccessory> FLATCAP = register("flatcap", () -> new FlatCap(DoggyItems.FLATCAP).setModelTexture(Resources.DOG_FLATCAP).setAccessoryRenderType(AccessoryRenderType.MODEL));

    public static final Supplier<Contacts> DOGGY_CONTACTS = register("doggy_contacts", () -> new Contacts(DoggyItems.DOGGY_CONTACTS).setModelTexture(Resources.DOGGY_CONTACTS_BG).setAccessoryRenderType(AccessoryRenderType.MODEL));

    private static <T extends Accessory> Supplier<T> register(final String name, final Supplier<T> sup) {
        return ACCESSORIES.register(name, sup);
    }
}
