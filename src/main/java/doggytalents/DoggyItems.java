package doggytalents;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.registry.Accessory;
import doggytalents.common.artifacts.FeatheredMantleArtifact;
import doggytalents.common.entity.accessory.DyeableAccessory;
import doggytalents.common.entity.accessory.LocatorOrbAccessory;
import doggytalents.common.entity.accessory.Wig;
import doggytalents.common.item.*;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<Item> THROW_BONE = registerThrowBone("throw_bone");
    public static final RegistryObject<Item> THROW_BONE_WET = registerThrowBoneWet("throw_bone_wet");
    public static final RegistryObject<Item> THROW_STICK = registerThrowStick("throw_stick");
    public static final RegistryObject<Item> THROW_STICK_WET = registerThrowStickWet("throw_stick_wet");
    public static final RegistryObject<Item> TRAINING_TREAT = registerTreat("training_treat", DogLevel.Type.NORMAL, 20);
    public static final RegistryObject<Item> SUPER_TREAT = registerTreat("super_treat", DogLevel.Type.NORMAL, 40);
    public static final RegistryObject<Item> MASTER_TREAT = registerTreat("master_treat", DogLevel.Type.NORMAL, 60);
    public static final RegistryObject<Item> DIRE_TREAT = registerTreat("dire_treat", DogLevel.Type.DIRE, 30);
    public static final RegistryObject<Item> BREEDING_BONE = register("breeding_bone");
    public static final RegistryObject<Item> DOGGY_CHARM = registerWith("doggy_charm", DoggyCharmItem::new, 1);
    public static final RegistryObject<AccessoryItem> RADIO_COLLAR = registerAccessory("radio_collar", DoggyAccessories.RADIO_BAND);
    public static final RegistryObject<DyeableAccessoryItem> WOOL_COLLAR = registerAccessoryDyed("wool_collar", DoggyAccessories.DYEABLE_COLLAR);
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
    public static final RegistryObject<Item> GENDER_BONE = registerTool("gender_bone", GenderBoneItem::new, 10);
    
    public static final RegistryObject<DoggyArtifactItem> FEATHERED_MANTLE = registerWith("feathered_mantle", 
        props -> new DoggyArtifactItem(
            () -> new FeatheredMantleArtifact(), props), 1);

    public static final RegistryObject<Item> MUSIC_DISC_BWV_1080_FUGUE_11_KIMIKO = register("disc_bwv_1080_fugue_11", 
        () -> new RecordItem(13, () -> DoggySounds.BWV_1080_FUGUE_11_KIMIKO.get() , 
        (new Item.Properties()).stacksTo(1).tab(DoggyItemGroups.GENERAL).rarity(Rarity.RARE), 292));
    public static final RegistryObject<Item> MUSIC_DISC_BWV_849_FUGUE_KIMIKO = register("disc_bwv_849_fugue", 
        () -> new RecordItem(13, () -> DoggySounds.BWV_849_FUGUE_KIMIKO.get() , 
        (new Item.Properties()).stacksTo(1).tab(DoggyItemGroups.GENERAL).rarity(Rarity.RARE), 160));
    public static final RegistryObject<Item> MUSIC_DISC_OKAMI_1 = register("disc_okami_ryoshima_coast_arr", 
        () -> new RecordItem(13, () -> DoggySounds.OKAMI_RYOSHIMA_COAST_ARR.get() , 
        (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 111));
    public static final RegistryObject<Item> MUSIC_DISC_CHOPIN_OP64_NO1 = register("disc_chopin_op64_no1", 
        () -> new ChopinRecordItem(13, () -> DoggySounds.CHOPIN_OP64_NO1.get() , 
        (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 117));
    
    //public static final RegistryObject<Item> PATROL = registerWith("patrol_item", PatrolItem::new, 1);

    private static Item.Properties createInitialProp() {
        return new Item.Properties().tab(DoggyItemGroups.GENERAL);
    }

    private static RegistryObject<Item> registerThrowBone(final String name) {
        return register(name, () -> new ThrowableItem(THROW_BONE_WET, () -> Items.BONE, createInitialProp().stacksTo(2)));
    }

    private static RegistryObject<Item> registerThrowStick(final String name) {
        return register(name, () -> new ThrowableItem(THROW_STICK_WET, THROW_STICK, createInitialProp().stacksTo(8)));
    }

    private static RegistryObject<Item> registerThrowBoneWet(final String name) {
        return register(name, () -> new DroolBoneItem(THROW_BONE, createInitialProp().stacksTo(1)));
    }

    private static RegistryObject<Item> registerThrowStickWet(final String name) {
        return register(name, () -> new DroolBoneItem(THROW_STICK, createInitialProp().stacksTo(1)));
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

    private static RegistryObject<AccessoryItem> registerLocatorOrb(final String name, Supplier<? extends LocatorOrbAccessory> type) {
        return register(name, () -> new LocatorOrbItem(type, createInitialProp()));
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
        ItemColors itemColors = event.getItemColors();
        Util.acceptOrElse(DoggyItems.WOOL_COLLAR, (item) -> {
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

        Util.acceptOrElse(DoggyBlocks.DOG_BATH, (item) -> {
            itemColors.register((stack, tintIndex) -> {
                return 4159204;
             }, item);
        }, DoggyBlocks::logError);
    }
}
