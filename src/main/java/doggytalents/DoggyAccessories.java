package doggytalents;

import doggytalents.api.registry.Accessory;
import doggytalents.api.registry.Accessory.AccessoryRenderType;
import doggytalents.common.entity.accessory.*;
import doggytalents.common.lib.Constants;
import doggytalents.common.lib.Resources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DoggyAccessories {

    public static final DeferredRegister<Accessory> ACCESSORIES = DeferredRegister.create(DoggyRegistries.Keys.ACCESSORIES_REGISTRY, Constants.MOD_ID);

    public static final RegistryObject<DyeableAccessory> DYEABLE_COLLAR = register("dyeable_collar", () -> new DyeableAccessory(DoggyAccessoryTypes.COLLAR, DoggyItems.WOOL_COLLAR).setModelTexture(Resources.COLLAR_DEFAULT).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Collar> GOLDEN_COLLAR = register("golden_collar", () -> new Collar(DoggyItems.CREATIVE_COLLAR).setModelTexture(Resources.COLLAR_GOLDEN).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Collar> SPOTTED_COLLAR = register("spotted_collar", () -> new Collar(DoggyItems.SPOTTED_COLLAR).setModelTexture(Resources.COLLAR_SPOTTED).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Collar> MULTICOLORED_COLLAR = register("multicolored_collar", () -> new Collar(DoggyItems.MULTICOLOURED_COLLAR).setModelTexture(Resources.COLLAR_MULTICOLORED).setAccessoryRenderType(AccessoryRenderType.OVERLAY));

    public static final RegistryObject<Clothing> GUARD_SUIT = register("guard_suit", () -> new Clothing(DoggyItems.GUARD_SUIT).setModelTexture(Resources.GUARD_SUIT).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Clothing> LEATHER_JACKET_CLOTHING = register("leather_jacket_clothing", () -> new Clothing(DoggyItems.LEATHER_JACKET).setModelTexture(Resources.CLOTHING_LEATHER_JACKET).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Glasses> SUNGLASSES = register("sunglasses", () -> new Glasses(DoggyItems.SUNGLASSES).setModelTexture(Resources.GLASSES_SUNGLASSES).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Clothing> TANTAN_CAPE = register("tantan_cape", () -> new Clothing(DoggyItems.TANTAN_CAPE).setModelTexture(Resources.TANTAN_CAPE).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<DyeableAccessory> DYEABLE_CAPE = register("dyeable_cape", () -> new DyeableAccessory(DoggyAccessoryTypes.CLOTHING, DoggyItems.CAPE_COLOURED).setModelTexture(Resources.DYEABLE_CAPE).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Clothing> PIANIST_SUIT = register("pianist_suit", () -> new Clothing(DoggyItems.PIANIST_SUIT).setModelTexture(Resources.PIANIST_SUIT).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Clothing> DEATH_HOOD = register("death_hood", () -> new Clothing(DoggyItems.DEATH_HOOD).setModelTexture(Resources.DEATH_HOOD).setRenderTranslucent(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<BowTie> BOWTIE = register("bowtie", () -> new BowTie(DoggyItems.BOWTIE).setModelTexture(Resources.BOW_TIE).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final RegistryObject<SmartyGlasses> SMARTY_GLASSES = register("smarty_glasses", () -> new SmartyGlasses(DoggyItems.SMARTY_GLASSES).setModelTexture(Resources.SMARTY_GLASSES).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final RegistryObject<Wig> WIG = register("wig", () -> new Wig(DoggyItems.WIG).setModelTexture(Resources.WIG).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final RegistryObject<BachWig> BACH_WIG = register("bach_wig", () -> new BachWig(DoggyItems.BACH_WIG).setModelTexture(Resources.BACH_WIG).setAccessoryRenderType(AccessoryRenderType.MODEL));
    public static final RegistryObject<Band> RADIO_BAND = register("radio_band", () -> new Band(DoggyItems.RADIO_COLLAR).setModelTexture(Resources.RADIO_BAND).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Clothing> CONAN_SUIT = register("conan_suit", () -> new Clothing(DoggyItems.CONAN_SUIT).setModelTexture(Resources.CONAN_SUIT).setHasHindLegDiffTex(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY));
    public static final RegistryObject<Clothing> BEASTARS_UNIFORM_MALE = register("beastars_uniform_male", () -> new BeastarsUniformMale(DoggyItems.BEASTARS_UNIFORM_MALE).setModelTexture(Resources.BEASTARS_UNIFORM_MALE).setHasHindLegDiffTex(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY_AND_MODEL).setRenderTranslucent(true));
    public static final RegistryObject<Clothing> BEASTARS_UNIFORM_FEMALE = register("beastars_uniform_female", () -> new BeastarsUniformFemale(DoggyItems.BEASTARS_UNIFORM_FEMALE).setModelTexture(Resources.BEASTARS_UNIFORM_FEMALE).setHasHindLegDiffTex(true).setAccessoryRenderType(AccessoryRenderType.OVERLAY_AND_MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> CHI_ORB = register("locator_orb_chi", () -> new LocatorOrbAccessory(DoggyItems.CHI_ORB, 0xffd69f94).setModelTexture(Resources.CHI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> CHU_ORB = register("locator_orb_chu", () -> new LocatorOrbAccessory(DoggyItems.CHU_ORB, 0xff8161dd).setModelTexture(Resources.CHU_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> KO_ORB = register("locator_orb_ko", () -> new LocatorOrbAccessory(DoggyItems.KO_ORB, 0xffa58ee1).setModelTexture(Resources.KO_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> GI_ORB = register("locator_orb_gi", () -> new LocatorOrbAccessory(DoggyItems.GI_ORB, 0xff809abc).setModelTexture(Resources.GI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> TEI_ORB = register("locator_orb_tei", () -> new LocatorOrbAccessory(DoggyItems.TEI_ORB, 0xffedb689).setModelTexture(Resources.TEI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> REI_ORB = register("locator_orb_rei", () -> new LocatorOrbAccessory(DoggyItems.REI_ORB, 0xfff0e33f).setModelTexture(Resources.REI_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> SHIN_ORB = register("locator_orb_shin", () -> new LocatorOrbAccessory(DoggyItems.SHIN_ORB, 0xffaec867).setModelTexture(Resources.SHIN_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<LocatorOrbAccessory> JIN_ORB = register("locator_orb_jin", () -> new LocatorOrbAccessory(DoggyItems.JIN_ORB, 0xffd1523a).setModelTexture(Resources.JIN_ORB).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    public static final RegistryObject<Clothing> HOT_DOG = register("hot_dog", () -> new HotDog(DoggyItems.HOT_DOG).setModelTexture(Resources.HOT_DOG).setAccessoryRenderType(AccessoryRenderType.MODEL).setRenderTranslucent(true));
    
    public static final RegistryObject<Helmet> IRON_HELMET = registerHelmet("iron_helmet", () -> Items.IRON_HELMET, Resources.IRON_HELMET);
    public static final RegistryObject<Helmet> DIAMOND_HELMET = registerHelmet("diamond_helmet", () -> Items.DIAMOND_HELMET, Resources.DIAMOND_HELMET);
    public static final RegistryObject<Helmet> GOLDEN_HELMET = registerHelmet("golden_helmet", () -> Items.GOLDEN_HELMET, Resources.GOLDEN_HELMET);
    public static final RegistryObject<Helmet> CHAINMAIL_HELMET = registerHelmet("chainmail_helmet", () -> Items.CHAINMAIL_HELMET, Resources.CHAINMAIL_HELMET);
    public static final RegistryObject<Helmet> TURTLE_HELMET = registerHelmet("turtle_helmet", () -> Items.TURTLE_HELMET, Resources.TURTLE_HELMET);
    public static final RegistryObject<Helmet> NETHERITE_HELMET = registerHelmet("netherite_helmet", () -> Items.NETHERITE_HELMET, Resources.NETHERITE_HELMET);

    public static final RegistryObject<ArmourAccessory> IRON_BODY_PIECE = registerBodyPiece("iron_body_piece", () -> Items.IRON_CHESTPLATE, Resources.IRON_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> DIAMOND_BODY_PIECE = registerBodyPiece("diamond_body_piece", () -> Items.DIAMOND_CHESTPLATE, Resources.DIAMOND_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> GOLDEN_BODY_PIECE = registerBodyPiece("golden_body_piece", () -> Items.GOLDEN_CHESTPLATE, Resources.GOLDEN_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> CHAINMAIL_BODY_PIECE = registerBodyPiece("chainmail_body_piece", () -> Items.CHAINMAIL_CHESTPLATE, Resources.CHAINMAIL_BODY_PIECE);
    public static final RegistryObject<ArmourAccessory> NETHERITE_BODY_PIECE = registerBodyPiece("netherite_body_piece", () -> Items.NETHERITE_CHESTPLATE, Resources.NETHERITE_BODY_PIECE);

    public static final RegistryObject<ArmourAccessory> IRON_BOOTS = registerBoots("iron_boots", () -> Items.IRON_BOOTS, Resources.IRON_BOOTS);
    public static final RegistryObject<ArmourAccessory> DIAMOND_BOOTS = registerBoots("diamond_boots", () -> Items.DIAMOND_BOOTS, Resources.DIAMOND_BOOTS);
    public static final RegistryObject<ArmourAccessory> GOLDEN_BOOTS = registerBoots("golden_boots", () -> Items.GOLDEN_BOOTS, Resources.GOLDEN_BOOTS);
    public static final RegistryObject<ArmourAccessory> CHAINMAIL_BOOTS = registerBoots("chainmail_boots", () -> Items.CHAINMAIL_BOOTS, Resources.CHAINMAIL_BOOTS);
    public static final RegistryObject<ArmourAccessory> NETHERITE_BOOTS = registerBoots("netherite_boots", () -> Items.NETHERITE_BOOTS, Resources.NETHERITE_BOOTS);

    public static final RegistryObject<LeatherArmourAccessory> LEATHER_HELMET = register("leather_helmet", () -> new LeatherArmourAccessory(DoggyAccessoryTypes.HEAD, () -> Items.LEATHER_HELMET).setModelTexture(Resources.LEATHER_HELMET));
    public static final RegistryObject<LeatherArmourAccessory> LEATHER_BODY_PIECE = register("leather_body_piece", () -> new LeatherArmourAccessory(DoggyAccessoryTypes.CLOTHING, () -> Items.LEATHER_CHESTPLATE).setModelTexture(Resources.LEATHER_BODY_PIECE));
    public static final RegistryObject<LeatherArmourAccessory> LEATHER_BOOTS = register("leather_boots", () -> new LeatherArmourAccessory(DoggyAccessoryTypes.FEET, () -> Items.LEATHER_BOOTS).setModelTexture(Resources.LEATHER_BOOTS));

    private static RegistryObject<Helmet> registerHelmet(final String name, final Supplier<? extends ItemLike> itemIn, ResourceLocation modelLocation) {
        return ACCESSORIES.register(name, () -> new Helmet(itemIn).setModelTexture(modelLocation));
    }

    private static RegistryObject<ArmourAccessory> registerBoots(final String name, final Supplier<? extends ItemLike> itemIn, ResourceLocation modelLocation) {
        return ACCESSORIES.register(name, () -> new ArmourAccessory(DoggyAccessoryTypes.FEET, itemIn).setModelTexture(modelLocation));
    }

    private static RegistryObject<ArmourAccessory> registerBodyPiece(final String name, final Supplier<? extends ItemLike> itemIn, ResourceLocation modelLocation) {
        return ACCESSORIES.register(name, () -> new ArmourAccessory(DoggyAccessoryTypes.CLOTHING, itemIn).setModelTexture(modelLocation));
    }

    private static <T extends Accessory> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ACCESSORIES.register(name, sup);
    }
}
