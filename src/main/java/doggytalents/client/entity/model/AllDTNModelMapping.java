package doggytalents.client.entity.model;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.dog.*;
import doggytalents.client.entity.model.dog.dogs.*;
import doggytalents.client.entity.model.dog.dogs.kusa.*;
import doggytalents.client.entity.model.dog.dogs.oina.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class AllDTNModelMapping {

    public static final Map<ModelLayerLocation, Supplier<LayerDefinition>> MAPPING = Maps.newHashMap();
    
    public static void init() {
        register(ClientSetup.DOG, DogModel::createBodyLayer);
        register(ClientSetup.DOG_LEGACY, VariantDogModel::createBodyLayer);
        register(ClientSetup.DOG_IWANKO, IwankoModel::createBodyLayer);
        register(ClientSetup.DOG_LUCARIO, LucarioModel::createBodyLayer);
        register(ClientSetup.DOG_DEATH, DeathModel::createBodyLayer);
        register(ClientSetup.DOG_LEGOSHI, LegoshiModel::createBodyLayer);
        register(ClientSetup.DOG_JACK, JackModel::createBodyLayer);
        register(ClientSetup.DOG_JUNO, JunoModel::createBodyLayer);
        register(ClientSetup.DOG_ST_BERNARD, StBernardModel::createBodyLayer);

        register(ClientSetup.OKAMI_AMATERASU, AmaterasuModel::createBodyLayer);
        register(ClientSetup.AMMY_CHI, AmmyChiModel::createBodyLayer);
        register(ClientSetup.AMMY_JIN, AmmyJinModel::createBodyLayer);
        register(ClientSetup.AMMY_REBIRTH, AmmyRebirthModel::createBodyLayer);
        register(ClientSetup.AMMY_REI, AmmyReiModel::createBodyLayer);
        register(ClientSetup.AMMY_SHIN, AmmyShinModel::createBodyLayer);
        register(ClientSetup.AMMY_SHIRANUI, AmmyShiranuiModel::createBodyLayer);
        register(ClientSetup.AMMY_TEI, AmmyTeiModel::createBodyLayer);

        register(ClientSetup.KUSA_HAYABUSA, HayabusaModel::createBodyLayer);
        register(ClientSetup.KUSA_CHI, ChiModel::createBodyLayer);
        register(ClientSetup.KUSA_KO, KoModel::createBodyLayer);
        register(ClientSetup.KUSA_REI, ReiModel::createBodyLayer);
        register(ClientSetup.KUSA_SHIN, ShinModel::createBodyLayer);
        register(ClientSetup.KUSA_TAKE, TakeModel::createBodyLayer);
        register(ClientSetup.KUSA_TEI, TeiModel::createBodyLayer);
        register(ClientSetup.KUSA_UME, UmeModel::createBodyLayer);

        register(ClientSetup.OINA_KAIPOKU, KaipokuModel::createBodyLayer);
        register(ClientSetup.OINA_KAWAUSO, KawausoModel::createBodyLayer);
        register(ClientSetup.OINA_KEMUSHIRI, KemushiriModel::createBodyLayer);
        register(ClientSetup.OINA_MERCHANT, OinaMerchant1Model::createBodyLayer);
        register(ClientSetup.OINA_MERCHANT2, OinaMerchant2Model::createBodyLayer);
        register(ClientSetup.OINA_OKIKURUMI, OkikurumiModel::createBodyLayer);
        register(ClientSetup.OINA_PIRIKO, PirikoModel::createBodyLayer);
        register(ClientSetup.OINA_RISU, RisuModel::createBodyLayer);
        register(ClientSetup.OINA_SHAMIKURU, ShamikuruModel::createBodyLayer);
        register(ClientSetup.OINA_TODO, TodoModel::createBodyLayer);
        register(ClientSetup.OINA_TUSUKURU, TusukuruModel::createBodyLayer);
        register(ClientSetup.OINA_WARI, WariModel::createBodyLayer);
        register(ClientSetup.DOG_SOL_HOPE, HopeModel::createBodyLayer);
        register(ClientSetup.DOG_WOLF_LINK, WolfLinkModel::createBodyLayer);

        register(ClientSetup.DOG_ARCANINE, ArcanineModel::createBodyLayer);
        register(ClientSetup.DOG_POCHITA, PochitaModel::createBodyLayer);
        register(ClientSetup.DOG_DACHSHUND, DachshundModel::createBodyLayer);
        register(ClientSetup.DOG_DOBERMAN, DobermanModel::createBodyLayer);
        register(ClientSetup.DOG_PUG, PugModel::createBodyLayer);
        register(ClientSetup.DOG_BORZOI, BorzoiModel::createBodyLayer);
        register(ClientSetup.DOG_BORZOI_LONG, BorzoiLongModel::createBodyLayer);
        register(ClientSetup.DOG_ENGLISH_BULLDOG, EnglishBulldogModel::createBodyLayer);
        register(ClientSetup.DOG_FRENCH_BULLDOG, FrenchBulldogModel::createBodyLayer);
        register(ClientSetup.DOG_POODLE, PoodleModel::createBodyLayer);
        register(ClientSetup.DOG_CHIHUAHUA, ChihuahuaModel::createBodyLayer);
        register(ClientSetup.DOG_BOXER_FLOPPY, BoxerFloppyModel::createBodyLayer);
        register(ClientSetup.DOG_BOXER_POINTY, BoxerPointyModel::createBodyLayer);
        register(ClientSetup.DOG_MINIATURE_PINSCHER, MiniaturePinscherModel::createBodyLayer);
        register(ClientSetup.DOG_HUNGARIAN_PULI, HungarianPuliModel::createBodyLayer);
        register(ClientSetup.DOG_BASSET_HOUND, BassetHoundModel::createBodyLayer);
        register(ClientSetup.DOG_COLLIE_SMOOTH, CollieSmoothModel::createBodyLayer);
        register(ClientSetup.DOG_COLLIE_ROUGH, CollieRoughModel::createBodyLayer);
        register(ClientSetup.DOG_COLLIE_BORDER, CollieBorderModel::createBodyLayer);
        register(ClientSetup.DOG_COLLIE_BORDER_SHORT, CollieBorderShortModel::createBodyLayer);
        register(ClientSetup.DOG_BICHON_MALTAIS, BichonMaltaisModel::createBodyLayer);
        register(ClientSetup.DOG_BELGIAN_MALINOIS, BelgianMalinoisModel::createBodyLayer);
        register(ClientSetup.DOG_GERMAN_SHEPHERD, GermanShepherdModel::createBodyLayer);
        register(ClientSetup.DOG_OTTER, OtterModel::createBodyLayer);
        register(ClientSetup.DOG_BULL_TERRIER, BullTerrierModel::createBodyLayer);
        register(ClientSetup.INU_AKITA, AkitaJapaneseModel::createBodyLayer);
        register(ClientSetup.DOG_AKITA, AkitaAmericanModel::createBodyLayer);
        register(ClientSetup.INU_SHIBA, ShibaModel::createBodyLayer);
        register(ClientSetup.INU_SHIKOKU, ShikokuModel::createBodyLayer);
        register(ClientSetup.DOG_HOUNDSTONE, HoundstoneModel::createBodyLayer);
        register(ClientSetup.DOG_ZERO, ZeroModel::createBodyLayer);
        register(ClientSetup.DOG_SCRAPS, ScrapsModel::createBodyLayer);
        register(ClientSetup.DOG_SPARKY, SparkyModel::createBodyLayer);
        register(ClientSetup.DOG_POINTER_SHORT, GermanPointerShorthaired::createBodyLayer);
        register(ClientSetup.DOG_POINTER_WIRE, GermanPointerWirehaired::createBodyLayer);
        register(ClientSetup.DOG_SAMOYED, SamoyedModel::createBodyLayer);
        register(ClientSetup.RANGA, RangaModel::createBodyLayer);
        register(ClientSetup.BOLT, BoltModel::createBodyLayer);
        register(ClientSetup.DOG_NORFOLK_TERRIER, NorfolkTerrierModel::createBodyLayer);
        register(ClientSetup.DOG_AUSTRALIAN_KELPIE, AustralianKelpieModel::createBodyLayer);
        register(ClientSetup.DOG_NEWFOUNDLAND, NewfoundlandModel::createBodyLayer);
        register(ClientSetup.NA, Na::na);
        register(ClientSetup.MOCHI, MochiModel::createBodyLayer);
        register(ClientSetup.DOG_CORGI, CorgiModel::createBodyLayer);

        register(ClientSetup.DOG_ARMOR, DogArmorModel::createBodyLayer);
        register(ClientSetup.DOG_ARMOR_LEGACY, DogArmorModel::createLegacyLayer);
        register(ClientSetup.DOG_FRONT_LEGS_SEPERATE, DogFrontLegsSeperate::createBodyLayer);
        register(ClientSetup.DOG_BACKPACK, DogBackpackModel::createChestLayer);
        register(ClientSetup.DOG_RESCUE_BOX, DogRescueModel::createRescueBoxLayer);
        register(ClientSetup.DOG_SYNCED_FUNCTION_WITH_HEAD, SyncedRenderFunctionWithHeadModel::createLayer);
        register(ClientSetup.DOG_TORCHIE, TorchDogModel::createLayer);
        register(ClientSetup.DOG_FISHER_HAT, FisherDogModel::createLayer);

        register(ClientSetup.DOG_NULL, NullDogModel::createBodyLayer);
    }

    private static void register(ModelLayerLocation location, Supplier<LayerDefinition> sup) {
        MAPPING.put(location, sup);
    }

}
