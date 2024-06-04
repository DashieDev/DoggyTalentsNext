package doggytalents.client;

import java.util.ArrayList;
import java.util.List;

import doggytalents.DoggyContainerTypes;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyTileEntityTypes;
import doggytalents.api.events.RegisterDogSkinJsonPathEvent;
import doggytalents.client.block.model.RiceMillModel;
import doggytalents.client.block.render.RiceMillRenderer;
import doggytalents.client.entity.model.DogArmorModel;
import doggytalents.client.entity.model.DogBackpackModel;
import doggytalents.client.entity.model.DogFrontLegsSeperate;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.DogRescueModel;
import doggytalents.client.entity.model.FisherDogModel;
import doggytalents.client.entity.model.SyncedRenderFunctionWithHeadModel;
import doggytalents.client.entity.model.TorchDogModel;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.client.entity.model.dog.AkitaAmericanModel;
import doggytalents.client.entity.model.dog.AkitaJapaneseModel;
import doggytalents.client.entity.model.dog.AmaterasuModel;
import doggytalents.client.entity.model.dog.AmmyChiModel;
import doggytalents.client.entity.model.dog.AmmyJinModel;
import doggytalents.client.entity.model.dog.AmmyRebirthModel;
import doggytalents.client.entity.model.dog.AmmyReiModel;
import doggytalents.client.entity.model.dog.AmmyShinModel;
import doggytalents.client.entity.model.dog.AmmyShiranuiModel;
import doggytalents.client.entity.model.dog.AmmyTeiModel;
import doggytalents.client.entity.model.dog.ArcanineModel;
import doggytalents.client.entity.model.dog.AustralianKelpieModel;
import doggytalents.client.entity.model.dog.BassetHoundModel;
import doggytalents.client.entity.model.dog.BelgianMalinoisModel;
import doggytalents.client.entity.model.dog.BichonMaltaisModel;
import doggytalents.client.entity.model.dog.BoltModel;
import doggytalents.client.entity.model.dog.BorzoiLongModel;
import doggytalents.client.entity.model.dog.BorzoiModel;
import doggytalents.client.entity.model.dog.BoxerFloppyModel;
import doggytalents.client.entity.model.dog.BoxerPointyModel;
import doggytalents.client.entity.model.dog.BullTerrierModel;
import doggytalents.client.entity.model.dog.ChihuahuaModel;
import doggytalents.client.entity.model.dog.CollieBorderModel;
import doggytalents.client.entity.model.dog.CollieRoughModel;
import doggytalents.client.entity.model.dog.CollieSmoothModel;
import doggytalents.client.entity.model.dog.DachshundModel;
import doggytalents.client.entity.model.dog.DeathModel;
import doggytalents.client.entity.model.dog.DobermanModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.EnglishBulldogModel;
import doggytalents.client.entity.model.dog.FrenchBulldogModel;
import doggytalents.client.entity.model.dog.GermanPointerShorthaired;
import doggytalents.client.entity.model.dog.GermanPointerWirehaired;
import doggytalents.client.entity.model.dog.GermanShepherdModel;
import doggytalents.client.entity.model.dog.HoundstoneModel;
import doggytalents.client.entity.model.dog.HungarianPuliModel;
import doggytalents.client.entity.model.dog.IwankoModel;
import doggytalents.client.entity.model.dog.JackModel;
import doggytalents.client.entity.model.dog.JunoModel;
import doggytalents.client.entity.model.dog.LegoshiModel;
import doggytalents.client.entity.model.dog.LucarioModel;
import doggytalents.client.entity.model.dog.MiniaturePinscherModel;
import doggytalents.client.entity.model.dog.Na;
import doggytalents.client.entity.model.dog.NorfolkTerrierModel;
import doggytalents.client.entity.model.dog.NullDogModel;
import doggytalents.client.entity.model.dog.OtterModel;
import doggytalents.client.entity.model.dog.PochitaModel;
import doggytalents.client.entity.model.dog.PoodleModel;
import doggytalents.client.entity.model.dog.PugModel;
import doggytalents.client.entity.model.dog.RangaModel;
import doggytalents.client.entity.model.dog.SamoyedModel;
import doggytalents.client.entity.model.dog.ScrapsModel;
import doggytalents.client.entity.model.dog.ShibaModel;
import doggytalents.client.entity.model.dog.ShikokuModel;
import doggytalents.client.entity.model.dog.SparkyModel;
import doggytalents.client.entity.model.dog.StBernardModel;
import doggytalents.client.entity.model.dog.VariantDogModel;
import doggytalents.client.entity.model.dog.ZeroModel;
import doggytalents.client.entity.model.dog.kusa.ChiModel;
import doggytalents.client.entity.model.dog.kusa.HayabusaModel;
import doggytalents.client.entity.model.dog.kusa.KoModel;
import doggytalents.client.entity.model.dog.kusa.ReiModel;
import doggytalents.client.entity.model.dog.kusa.ShinModel;
import doggytalents.client.entity.model.dog.kusa.TakeModel;
import doggytalents.client.entity.model.dog.kusa.TeiModel;
import doggytalents.client.entity.model.dog.kusa.UmeModel;
import doggytalents.client.entity.model.misc.DogPlushieModel;
import doggytalents.client.entity.model.misc.GrandPianoModel;
import doggytalents.client.entity.model.misc.UprightPianoModel;
import doggytalents.client.entity.render.AccessoryModelManager;
import doggytalents.client.entity.render.CollarRenderManager;
import doggytalents.client.entity.render.DogFoodProjectileRenderer;
import doggytalents.client.entity.render.DogGunpowderProjectileRenderer;
import doggytalents.client.entity.render.DogScreenOverlays;
import doggytalents.client.entity.render.DogRenderer;
import doggytalents.client.entity.render.DoggyBeamRenderer;
import doggytalents.client.entity.render.layer.DogMouthItemRenderer;
import doggytalents.client.entity.render.layer.DogWolfArmorRenderer;
import doggytalents.client.entity.render.layer.FisherDogRenderer;
import doggytalents.client.entity.render.layer.PackPuppyRenderer;
import doggytalents.client.entity.render.layer.RescueDogRenderer;
import doggytalents.client.entity.render.layer.TorchDogRenderer;
import doggytalents.client.entity.render.layer.accessory.AccessoryModelRenderer;
import doggytalents.client.entity.render.layer.accessory.DoggyArmorRenderer;
import doggytalents.client.entity.render.layer.accessory.DefaultAccessoryRenderer;
import doggytalents.client.entity.render.layer.accessory.IncapacitatedRenderer;
import doggytalents.client.entity.render.layer.accessory.modelrenderentry.AccessoryModelRenderEntries;
import doggytalents.client.entity.render.misc.DogPlushieRenderer;
import doggytalents.client.entity.render.misc.DogThrownTridentRenderer;
import doggytalents.client.entity.render.misc.PianoRenderer;
import doggytalents.client.screen.DogArmorScreen;
import doggytalents.client.screen.DogInventoriesScreen;
import doggytalents.client.screen.DoggyToolsScreen;
import doggytalents.client.screen.FoodBowlScreen;
import doggytalents.client.screen.PackPuppyScreen;
import doggytalents.client.screen.RiceMillScreen;
import doggytalents.client.screen.TreatBagScreen;
import doggytalents.client.tileentity.renderer.DogBedRenderer;
import doggytalents.common.lib.Constants;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static final ModelLayerLocation DOG = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog"), "main");
    public static final ModelLayerLocation DOG_LEGACY = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_legacy"), "main");
    public static final ModelLayerLocation DOG_IWANKO = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_iwanko"), "main");
    public static final ModelLayerLocation DOG_LUCARIO = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_lucario"), "main");
    public static final ModelLayerLocation DOG_DEATH = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_death"), "main");    
    public static final ModelLayerLocation DOG_LEGOSHI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_legoshi"), "main");    
    public static final ModelLayerLocation DOG_JACK = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_jack"), "main");   
    public static final ModelLayerLocation DOG_JUNO = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_juno"), "main");   
    public static final ModelLayerLocation DOG_ST_BERNARD = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_st_bernard"), "main");   

    public static final ModelLayerLocation OKAMI_AMATERASU = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "okami_amaterasu"), "main");
    public static final ModelLayerLocation AMMY_CHI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ammy_divine_chi"), "main");
    public static final ModelLayerLocation AMMY_JIN = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ammy_divine_jin"), "main");
    public static final ModelLayerLocation AMMY_REBIRTH = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ammy_divine_rebirth"), "main");
    public static final ModelLayerLocation AMMY_REI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ammy_divine_rei"), "main");
    public static final ModelLayerLocation AMMY_SHIN = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ammy_divine_shin"), "main");
    public static final ModelLayerLocation AMMY_SHIRANUI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ammy_divine_shiranui"), "main");
    public static final ModelLayerLocation AMMY_TEI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ammy_divine_tei"), "main");

    public static final ModelLayerLocation KUSA_HAYABUSA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_hayabusa"), "main");   
    public static final ModelLayerLocation KUSA_CHI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_chi"), "main");   
    public static final ModelLayerLocation KUSA_KO = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_ko"), "main");   
    public static final ModelLayerLocation KUSA_REI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_rei"), "main");   
    public static final ModelLayerLocation KUSA_SHIN = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_shin"), "main");   
    public static final ModelLayerLocation KUSA_TAKE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_take"), "main");   
    public static final ModelLayerLocation KUSA_TEI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_tei"), "main");   
    public static final ModelLayerLocation KUSA_UME = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "kusa_ume"), "main");   
    public static final ModelLayerLocation DOG_ARCANINE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_arcanine"), "main");
    
    public static final ModelLayerLocation DOG_POCHITA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "pochita"), "main");   
    public static final ModelLayerLocation DOG_DACHSHUND = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dachshund"), "main");
    public static final ModelLayerLocation DOG_DOBERMAN = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "doberman"), "main");
    public static final ModelLayerLocation DOG_PUG = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "pug"), "main");
    public static final ModelLayerLocation DOG_BORZOI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "borzoi"), "main");
    public static final ModelLayerLocation DOG_BORZOI_LONG = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "borzoi_long"), "main");
    public static final ModelLayerLocation DOG_ENGLISH_BULLDOG = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "english_bulldog"), "main");
    public static final ModelLayerLocation DOG_FRENCH_BULLDOG = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "french_bulldog"), "main");
    public static final ModelLayerLocation DOG_POODLE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "poodle"), "main");
    public static final ModelLayerLocation DOG_CHIHUAHUA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "chihuahua"), "main");
    public static final ModelLayerLocation DOG_BOXER_FLOPPY = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "boxer_floppy"), "main");
    public static final ModelLayerLocation DOG_BOXER_POINTY = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "boxer_pointy"), "main");
    public static final ModelLayerLocation DOG_MINIATURE_PINSCHER = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "miniature_pinscher"), "main");
    public static final ModelLayerLocation DOG_HUNGARIAN_PULI = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "hungarian_puli"), "main");
    public static final ModelLayerLocation DOG_BASSET_HOUND = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "basset_hound"), "main");
    public static final ModelLayerLocation DOG_COLLIE_SMOOTH = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "collie_smooth"), "main");
    public static final ModelLayerLocation DOG_COLLIE_ROUGH = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "collie_rough"), "main");
    public static final ModelLayerLocation DOG_COLLIE_BORDER = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "collie_border"), "main");
    public static final ModelLayerLocation DOG_BICHON_MALTAIS = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "bichon_maltais"), "main");
    public static final ModelLayerLocation DOG_BELGIAN_MALINOIS = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "belgian_malinois"), "main");
    public static final ModelLayerLocation DOG_GERMAN_SHEPHERD = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "german_shepherd"), "main");
    public static final ModelLayerLocation DOG_OTTER = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "otter"), "main");
    public static final ModelLayerLocation DOG_BULL_TERRIER = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "bull_terrier"), "main");
    public static final ModelLayerLocation INU_AKITA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "akita_inu"), "main");
    public static final ModelLayerLocation DOG_AKITA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "akita_dog"), "main");
    public static final ModelLayerLocation INU_SHIBA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "shiba_inu"), "main");
    public static final ModelLayerLocation INU_SHIKOKU = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "shikoku_inu"), "main");
    public static final ModelLayerLocation DOG_HOUNDSTONE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "houndstone"), "main");
    public static final ModelLayerLocation DOG_ZERO = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "zero"), "main");
    public static final ModelLayerLocation DOG_SCRAPS = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "scraps"), "main");
    public static final ModelLayerLocation DOG_SPARKY = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "sparky"), "main");
    public static final ModelLayerLocation DOG_POINTER_SHORT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "german_pointer_shorthaired"), "main");
    public static final ModelLayerLocation DOG_POINTER_WIRE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "german_pointer_wirehaired"), "main");
    public static final ModelLayerLocation DOG_SAMOYED = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "samoyed"), "main");
    public static final ModelLayerLocation RANGA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "ranga"), "main");
    public static final ModelLayerLocation BOLT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "bolt"), "main");
    public static final ModelLayerLocation DOG_NORFOLK_TERRIER = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "norfolk_terrier"), "main");
    public static final ModelLayerLocation DOG_AUSTRALIAN_KELPIE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "australian_kelpie"), "main");
    public static final ModelLayerLocation NA = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "na"), "main");

    public static final ModelLayerLocation DOG_ARMOR = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog"), "armor");
    public static final ModelLayerLocation DOG_ARMOR_LEGACY = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog"), "armor_legacy");
    public static final ModelLayerLocation DOG_FRONT_LEGS_SEPERATE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_hind_leg_diff_tex"), "main");
    public static final ModelLayerLocation DOG_BACKPACK = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_backpack"), "main");
    public static final ModelLayerLocation DOG_RESCUE_BOX = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_rescue_box"), "main");
    public static final ModelLayerLocation DOG_SYNCED_FUNCTION_WITH_HEAD = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_mouth_item"), "main");
    public static final ModelLayerLocation DOG_TORCHIE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_torchie"), "main");
    public static final ModelLayerLocation DOG_FISHER_HAT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_fisher_hat"), "main");

    public static final ModelLayerLocation DOG_NULL = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_null"), "main");

    public static final ModelLayerLocation PIANO = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "piano"), "main");
    public static final ModelLayerLocation PIANO_UPRIGHT = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "piano_upright"), "main");
    public static final ModelLayerLocation DOG_PLUSHIE = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "dog_plushie_toy"), "main");
    public static final ModelLayerLocation RICE_MILL = new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, "rice_mill"), "main");

    public static final List<ResourceLocation> OTHER_MOD_SKIN_JSONS = new ArrayList<ResourceLocation>();

    public static void setupScreenManagers(final FMLClientSetupEvent event) {
        MenuScreens.register(DoggyContainerTypes.FOOD_BOWL.get(), FoodBowlScreen::new);
        MenuScreens.register(DoggyContainerTypes.PACK_PUPPY.get(), PackPuppyScreen::new);
        MenuScreens.register(DoggyContainerTypes.TREAT_BAG.get(), TreatBagScreen::new);
        MenuScreens.register(DoggyContainerTypes.DOG_INVENTORIES.get(), DogInventoriesScreen::new);
        MenuScreens.register(DoggyContainerTypes.DOG_ARMOR.get(), DogArmorScreen::new);
        MenuScreens.register(DoggyContainerTypes.DOG_TOOLS.get(), DoggyToolsScreen::new);
        MenuScreens.register(DoggyContainerTypes.RICE_MILL.get(), RiceMillScreen::new);
    }

    public static void setupEntityRenderers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DOG, DogModel::createBodyLayer);
        event.registerLayerDefinition(DOG_LEGACY, VariantDogModel::createBodyLayer);
        event.registerLayerDefinition(DOG_IWANKO, IwankoModel::createBodyLayer);
        event.registerLayerDefinition(DOG_LUCARIO, LucarioModel::createBodyLayer);
        event.registerLayerDefinition(DOG_DEATH, DeathModel::createBodyLayer);
        event.registerLayerDefinition(DOG_LEGOSHI, LegoshiModel::createBodyLayer);
        event.registerLayerDefinition(DOG_JACK, JackModel::createBodyLayer);
        event.registerLayerDefinition(DOG_JUNO, JunoModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ST_BERNARD, StBernardModel::createBodyLayer);

        event.registerLayerDefinition(OKAMI_AMATERASU, AmaterasuModel::createBodyLayer);
        event.registerLayerDefinition(AMMY_CHI, AmmyChiModel::createBodyLayer);
        event.registerLayerDefinition(AMMY_JIN, AmmyJinModel::createBodyLayer);
        event.registerLayerDefinition(AMMY_REBIRTH, AmmyRebirthModel::createBodyLayer);
        event.registerLayerDefinition(AMMY_REI, AmmyReiModel::createBodyLayer);
        event.registerLayerDefinition(AMMY_SHIN, AmmyShinModel::createBodyLayer);
        event.registerLayerDefinition(AMMY_SHIRANUI, AmmyShiranuiModel::createBodyLayer);
        event.registerLayerDefinition(AMMY_TEI, AmmyTeiModel::createBodyLayer);

        event.registerLayerDefinition(KUSA_HAYABUSA, HayabusaModel::createBodyLayer);
        event.registerLayerDefinition(KUSA_CHI, ChiModel::createBodyLayer);
        event.registerLayerDefinition(KUSA_KO, KoModel::createBodyLayer);
        event.registerLayerDefinition(KUSA_REI, ReiModel::createBodyLayer);
        event.registerLayerDefinition(KUSA_SHIN, ShinModel::createBodyLayer);
        event.registerLayerDefinition(KUSA_TAKE, TakeModel::createBodyLayer);
        event.registerLayerDefinition(KUSA_TEI, TeiModel::createBodyLayer);
        event.registerLayerDefinition(KUSA_UME, UmeModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ARCANINE, ArcanineModel::createBodyLayer);

        event.registerLayerDefinition(DOG_POCHITA, PochitaModel::createBodyLayer);
        event.registerLayerDefinition(DOG_DACHSHUND, DachshundModel::createBodyLayer);
        event.registerLayerDefinition(DOG_DOBERMAN, DobermanModel::createBodyLayer);
        event.registerLayerDefinition(DOG_PUG, PugModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BORZOI, BorzoiModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BORZOI_LONG, BorzoiLongModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ENGLISH_BULLDOG, EnglishBulldogModel::createBodyLayer);
        event.registerLayerDefinition(DOG_FRENCH_BULLDOG, FrenchBulldogModel::createBodyLayer);
        event.registerLayerDefinition(DOG_POODLE, PoodleModel::createBodyLayer);
        event.registerLayerDefinition(DOG_CHIHUAHUA, ChihuahuaModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BOXER_FLOPPY, BoxerFloppyModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BOXER_POINTY, BoxerPointyModel::createBodyLayer);
        event.registerLayerDefinition(DOG_MINIATURE_PINSCHER, MiniaturePinscherModel::createBodyLayer);
        event.registerLayerDefinition(DOG_HUNGARIAN_PULI, HungarianPuliModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BASSET_HOUND, BassetHoundModel::createBodyLayer);
        event.registerLayerDefinition(DOG_COLLIE_SMOOTH, CollieSmoothModel::createBodyLayer);
        event.registerLayerDefinition(DOG_COLLIE_ROUGH, CollieRoughModel::createBodyLayer);
        event.registerLayerDefinition(DOG_COLLIE_BORDER, CollieBorderModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BICHON_MALTAIS, BichonMaltaisModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BELGIAN_MALINOIS, BelgianMalinoisModel::createBodyLayer);
        event.registerLayerDefinition(DOG_GERMAN_SHEPHERD, GermanShepherdModel::createBodyLayer);
        event.registerLayerDefinition(DOG_OTTER, OtterModel::createBodyLayer);
        event.registerLayerDefinition(DOG_BULL_TERRIER, BullTerrierModel::createBodyLayer);
        event.registerLayerDefinition(INU_AKITA, AkitaJapaneseModel::createBodyLayer);
        event.registerLayerDefinition(DOG_AKITA, AkitaAmericanModel::createBodyLayer);
        event.registerLayerDefinition(INU_SHIBA, ShibaModel::createBodyLayer);
        event.registerLayerDefinition(INU_SHIKOKU, ShikokuModel::createBodyLayer);
        event.registerLayerDefinition(DOG_HOUNDSTONE, HoundstoneModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ZERO, ZeroModel::createBodyLayer);
        event.registerLayerDefinition(DOG_SCRAPS, ScrapsModel::createBodyLayer);
        event.registerLayerDefinition(DOG_SPARKY, SparkyModel::createBodyLayer);
        event.registerLayerDefinition(DOG_POINTER_SHORT, GermanPointerShorthaired::createBodyLayer);
        event.registerLayerDefinition(DOG_POINTER_WIRE, GermanPointerWirehaired::createBodyLayer);
        event.registerLayerDefinition(DOG_SAMOYED, SamoyedModel::createBodyLayer);
        event.registerLayerDefinition(RANGA, RangaModel::createBodyLayer);
        event.registerLayerDefinition(BOLT, BoltModel::createBodyLayer);
        event.registerLayerDefinition(DOG_NORFOLK_TERRIER, NorfolkTerrierModel::createBodyLayer);
        event.registerLayerDefinition(DOG_AUSTRALIAN_KELPIE, AustralianKelpieModel::createBodyLayer);
        event.registerLayerDefinition(NA, Na::na);

        event.registerLayerDefinition(DOG_ARMOR, DogArmorModel::createBodyLayer);
        event.registerLayerDefinition(DOG_ARMOR_LEGACY, DogArmorModel::createLegacyLayer);
        event.registerLayerDefinition(DOG_FRONT_LEGS_SEPERATE, DogFrontLegsSeperate::createBodyLayer);
        event.registerLayerDefinition(DOG_BACKPACK, DogBackpackModel::createChestLayer);
        event.registerLayerDefinition(DOG_RESCUE_BOX, DogRescueModel::createRescueBoxLayer);
        event.registerLayerDefinition(DOG_SYNCED_FUNCTION_WITH_HEAD, SyncedRenderFunctionWithHeadModel::createLayer);
        event.registerLayerDefinition(DOG_TORCHIE, TorchDogModel::createLayer);
        event.registerLayerDefinition(DOG_FISHER_HAT, FisherDogModel::createLayer);

        event.registerLayerDefinition(DOG_NULL, NullDogModel::createBodyLayer);

        event.registerLayerDefinition(PIANO, GrandPianoModel::creatPianoLayer);
        event.registerLayerDefinition(PIANO_UPRIGHT, UprightPianoModel::createPianoLayer);
        event.registerLayerDefinition(DOG_PLUSHIE, DogPlushieModel::createBodyLayer);
        event.registerLayerDefinition(RICE_MILL, RiceMillModel::createLayer);

        AccessoryModelRenderEntries.registerEntries();
        AccessoryModelManager.registerLayerDef(event);

        DogAnimationRegistry.init();
        DogModelRegistry.init();

        gatherSkinJsonFromOtherMods();
        // TODO: RenderingRegistry.registerEntityRenderingHandler(DoggyEntityTypes.DOG_BEAM.get(), manager -> new DoggyBeamRenderer<>(manager, event.getMinecraftSupplier().get().getItemRenderer()));
    }

    private static void gatherSkinJsonFromOtherMods() {
        OTHER_MOD_SKIN_JSONS.clear();
        var paths = new ArrayList<ResourceLocation>();
        ModLoader.get().postEvent(new RegisterDogSkinJsonPathEvent(paths));
        if (paths.isEmpty())
            return;
        OTHER_MOD_SKIN_JSONS.addAll(paths);
    }

    public static void setupTileEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DoggyEntityTypes.DOG.get(), DogRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_FOOD_PROJ.get(), DogFoodProjectileRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_GUNPOWDER_PROJ.get(), DogGunpowderProjectileRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_BEAM.get(), DoggyBeamRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_TRIDENT_PROJ.get(), DogThrownTridentRenderer::new);
        event.registerBlockEntityRenderer(DoggyTileEntityTypes.DOG_BED.get(), DogBedRenderer::new);
        event.registerBlockEntityRenderer(DoggyTileEntityTypes.RICE_MILL.get(), RiceMillRenderer::new);

        event.registerEntityRenderer(DoggyEntityTypes.GRAND_PIANO_BLACK.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.GRAND_PIANO_WHITE.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.UPRIGHT_PIANO_BLACK.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.UPRIGHT_PIANO_BROWN.get(), PianoRenderer::new);
        event.registerEntityRenderer(DoggyEntityTypes.DOG_PLUSHIE_TOY.get(), DogPlushieRenderer::new);
    }

    public static void setupCollarRenderers(final FMLClientSetupEvent event) {
        
        CollarRenderManager.registerLayer(DefaultAccessoryRenderer::new);
        //CollarRenderManager.registerLayer(DogWolfArmorRenderer::new);
        CollarRenderManager.registerLayer(IncapacitatedRenderer::new);
        CollarRenderManager.registerLayer(DoggyArmorRenderer::new);
        CollarRenderManager.registerLayer(PackPuppyRenderer::new);
        CollarRenderManager.registerLayer(RescueDogRenderer::new);
        CollarRenderManager.registerLayer(AccessoryModelRenderer::new);
        CollarRenderManager.registerLayer(DogMouthItemRenderer::new);
        CollarRenderManager.registerLayer(TorchDogRenderer::new);
        CollarRenderManager.registerLayer(FisherDogRenderer::new);
    }

    public static void registerOverlay(FMLClientSetupEvent e) {
        OverlayRegistry.registerOverlayTop("Dog Food Level", DogScreenOverlays.FOOD_LEVEL_ELEMENT);
        OverlayRegistry.registerOverlayTop("Dog Air Level", DogScreenOverlays.AIR_LEVEL_ELEMENT);
    }

    public static void addClientReloadListeners(final RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(DogTextureManager.INSTANCE);
        event.registerReloadListener(DogRandomNameRegistry.getInstance());
    }
}
