package doggytalents.client.entity.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

import org.checkerframework.checker.units.qual.cd;

import com.google.common.collect.Maps;

import doggytalents.api.events.RegisterCustomDogModelsEvent;
import doggytalents.api.events.RegisterCustomDogModelsEvent.DogModelProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.dog.AkitaAmericanModel;
import doggytalents.client.entity.model.dog.AkitaJapaneseModel;
import doggytalents.client.entity.model.dog.AmaterasuModel;
import doggytalents.client.entity.model.dog.AmmyChiModel;
import doggytalents.client.entity.model.dog.AmmyJinModel;
import doggytalents.client.entity.model.dog.AmmyRebirthModel;
import doggytalents.client.entity.model.dog.AmmyReiModel;
import doggytalents.client.entity.model.dog.AmmyShinModel;
import doggytalents.client.entity.model.dog.AmmyShiranuiModel;
import doggytalents.client.entity.model.dog.AmmyRebirthModel;
import doggytalents.client.entity.model.dog.AmmyTeiModel;
import doggytalents.client.entity.model.dog.ArcanineModel;
import doggytalents.client.entity.model.dog.AustralianKelpieModel;
import doggytalents.client.entity.model.dog.CustomDogModel;
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
import doggytalents.client.entity.model.dog.CollieBorderShortModel;
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
import doggytalents.client.entity.model.dog.DogModel.AccessoryState;
import doggytalents.client.entity.model.dog.kusa.ChiModel;
import doggytalents.client.entity.model.dog.kusa.HayabusaModel;
import doggytalents.client.entity.model.dog.kusa.KoModel;
import doggytalents.client.entity.model.dog.kusa.ReiModel;
import doggytalents.client.entity.model.dog.kusa.ShinModel;
import doggytalents.client.entity.model.dog.kusa.TakeModel;
import doggytalents.client.entity.model.dog.kusa.TeiModel;
import doggytalents.client.entity.model.dog.kusa.UmeModel;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoader;

public class DogModelRegistry {
    
    private static Map<ResourceLocation, DogModelHolder> MODEL_MAP;

    public static <T extends AbstractDog> void register(ResourceLocation id, Function<EntityRendererProvider.Context, DogModel>  getter) {
        MODEL_MAP.putIfAbsent(id, new DogModelHolder(getter));
    }

    public static void register(String name, Function<EntityRendererProvider.Context, DogModel>  getter) {
        register(Util.getResource(name), getter);
    }

    public static DogModelHolder getDogModelHolder(ResourceLocation id) {
        return MODEL_MAP.get(id);
    }

    public static DogModelHolder getDogModelHolder(String name) {
        ResourceLocation loc;
        if (name.indexOf(':') >= 0) {
            loc = ResourceLocation.parse(name);
        } else {
            loc = Util.getResource(name);
        }
        return getDogModelHolder(loc);
    }

    public static void resolve(EntityRendererProvider.Context ctx) {
        for (var holder : MODEL_MAP.entrySet()) {
            try {
                holder.getValue().resolve(ctx);
            } catch (NoSuchElementException e) {
                var msg = "Dog Model [" + holder.getKey() + "] is missing crucial parts! [" + e.getMessage() + "]";
                throw new NoSuchElementException(msg);
            }   
        }
    }

    public static void init() {
        MODEL_MAP = Maps.newConcurrentMap();
        register("default", ctx -> new DogModel(ctx.bakeLayer(ClientSetup.DOG)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("variant", ctx -> new VariantDogModel(ctx.bakeLayer(ClientSetup.DOG_LEGACY)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("iwanko", ctx -> new IwankoModel(ctx.bakeLayer(ClientSetup.DOG_IWANKO)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("lucario", ctx -> new LucarioModel(ctx.bakeLayer(ClientSetup.DOG_LUCARIO)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("death", ctx -> new DeathModel(ctx.bakeLayer(ClientSetup.DOG_DEATH)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("legoshi", ctx -> new LegoshiModel(ctx.bakeLayer(ClientSetup.DOG_LEGOSHI)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("jack", ctx -> new JackModel(ctx.bakeLayer(ClientSetup.DOG_JACK)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("juno", ctx -> new JunoModel(ctx.bakeLayer(ClientSetup.DOG_JUNO)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("st_bernard", ctx -> new StBernardModel(ctx.bakeLayer(ClientSetup.DOG_ST_BERNARD)).setAccessoryState(AccessoryState.RECOMMENDED));

        register("okami_amaterasu", ctx ->  new AmaterasuModel(ctx.bakeLayer(ClientSetup.OKAMI_AMATERASU)).setAccessoryState(AccessoryState.SOME_WILL_FIT));
        register("ammy_divine_chi", ctx ->  new AmmyChiModel(ctx.bakeLayer(ClientSetup.AMMY_CHI)).setAccessoryState(AccessoryState.SOME_WILL_FIT));
        register("ammy_divine_jin", ctx ->  new AmmyJinModel(ctx.bakeLayer(ClientSetup.AMMY_JIN)).setAccessoryState(AccessoryState.SOME_WILL_FIT));
        register("ammy_divine_rei", ctx ->  new AmmyReiModel(ctx.bakeLayer(ClientSetup.AMMY_REI)).setAccessoryState(AccessoryState.SOME_WILL_FIT));
        register("ammy_divine_shin", ctx ->  new AmmyShinModel(ctx.bakeLayer(ClientSetup.AMMY_SHIN)).setAccessoryState(AccessoryState.SOME_WILL_FIT));
        register("ammy_divine_shiranui", ctx ->  new AmmyShiranuiModel(ctx.bakeLayer(ClientSetup.AMMY_SHIRANUI)).setAccessoryState(AccessoryState.SOME_WILL_FIT));
        register("ammy_divine_rebirth", ctx ->  new AmmyRebirthModel(ctx.bakeLayer(ClientSetup.AMMY_REBIRTH)));        
        register("ammy_divine_tei", ctx ->  new AmmyTeiModel(ctx.bakeLayer(ClientSetup.AMMY_TEI)).setAccessoryState(AccessoryState.SOME_WILL_FIT));

        register("kusa_hayabusa", ctx ->  new HayabusaModel(ctx.bakeLayer(ClientSetup.KUSA_HAYABUSA)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("kusa_chi", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_CHI)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("kusa_ko", ctx ->  new KoModel(ctx.bakeLayer(ClientSetup.KUSA_KO)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("kusa_rei", ctx ->  new ReiModel(ctx.bakeLayer(ClientSetup.KUSA_REI)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("kusa_shin", ctx ->  new ShinModel(ctx.bakeLayer(ClientSetup.KUSA_SHIN)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("kusa_take", ctx ->  new TakeModel(ctx.bakeLayer(ClientSetup.KUSA_TAKE)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("kusa_tei", ctx ->  new TeiModel(ctx.bakeLayer(ClientSetup.KUSA_TEI)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("kusa_ume", ctx ->  new UmeModel(ctx.bakeLayer(ClientSetup.KUSA_UME)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("arcanine", ctx ->  new ArcanineModel(ctx.bakeLayer(ClientSetup.DOG_ARCANINE)).setAccessoryState(AccessoryState.SOME_WILL_FIT));

        register("pochita", ctx ->  new PochitaModel(ctx.bakeLayer(ClientSetup.DOG_POCHITA)));
        register("dachshund", ctx ->  new DachshundModel(ctx.bakeLayer(ClientSetup.DOG_DACHSHUND)));
        register("doberman", ctx ->  new DobermanModel(ctx.bakeLayer(ClientSetup.DOG_DOBERMAN)));
        register("pug", ctx ->  new PugModel(ctx.bakeLayer(ClientSetup.DOG_PUG)));
        register("borzoi", ctx ->  new BorzoiModel(ctx.bakeLayer(ClientSetup.DOG_BORZOI)));
        register("borzoi_long", ctx ->  new BorzoiLongModel(ctx.bakeLayer(ClientSetup.DOG_BORZOI_LONG)));
        register("english_bulldog", ctx ->  new EnglishBulldogModel(ctx.bakeLayer(ClientSetup.DOG_ENGLISH_BULLDOG)));
        register("french_bulldog", ctx ->  new FrenchBulldogModel(ctx.bakeLayer(ClientSetup.DOG_FRENCH_BULLDOG)));
        register("poodle", ctx ->  new PoodleModel(ctx.bakeLayer(ClientSetup.DOG_POODLE)));
        register("chihuahua", ctx ->  new ChihuahuaModel(ctx.bakeLayer(ClientSetup.DOG_CHIHUAHUA)));
        register("boxer_floppy", ctx ->  new BoxerFloppyModel(ctx.bakeLayer(ClientSetup.DOG_BOXER_FLOPPY)));
        register("boxer_pointy", ctx ->  new BoxerPointyModel(ctx.bakeLayer(ClientSetup.DOG_BOXER_POINTY)));
        register("miniature_pinscher", ctx ->  new MiniaturePinscherModel(ctx.bakeLayer(ClientSetup.DOG_MINIATURE_PINSCHER)));
        register("hungarian_puli", ctx ->  new HungarianPuliModel(ctx.bakeLayer(ClientSetup.DOG_HUNGARIAN_PULI)));
        register("basset_hound", ctx ->  new BassetHoundModel(ctx.bakeLayer(ClientSetup.DOG_BASSET_HOUND)));
        register("collie_smooth", ctx ->  new CollieSmoothModel(ctx.bakeLayer(ClientSetup.DOG_COLLIE_SMOOTH)));
        register("collie_rough", ctx ->  new CollieRoughModel(ctx.bakeLayer(ClientSetup.DOG_COLLIE_ROUGH)));
        register("collie_border", ctx ->  new CollieBorderModel(ctx.bakeLayer(ClientSetup.DOG_COLLIE_BORDER)));
        register("collie_border_short", ctx ->  new CollieBorderShortModel(ctx.bakeLayer(ClientSetup.DOG_COLLIE_BORDER_SHORT)));
        register("bichon_maltais", ctx ->  new BichonMaltaisModel(ctx.bakeLayer(ClientSetup.DOG_BICHON_MALTAIS)));
        register("belgian_malinois", ctx ->  new BelgianMalinoisModel(ctx.bakeLayer(ClientSetup.DOG_BELGIAN_MALINOIS)));
        register("german_shepherd", ctx ->  new GermanShepherdModel(ctx.bakeLayer(ClientSetup.DOG_GERMAN_SHEPHERD)));
        register("otter", ctx ->  new OtterModel(ctx.bakeLayer(ClientSetup.DOG_OTTER)));
        register("bull_terrier", ctx ->  new BullTerrierModel(ctx.bakeLayer(ClientSetup.DOG_BULL_TERRIER)));
        register("akita_inu", ctx ->  new AkitaJapaneseModel(ctx.bakeLayer(ClientSetup.INU_AKITA)));
        register("akita_dog", ctx ->  new AkitaAmericanModel(ctx.bakeLayer(ClientSetup.DOG_AKITA)));
        register("shiba_inu", ctx ->  new ShibaModel(ctx.bakeLayer(ClientSetup.INU_SHIBA)));
        register("shikoku_inu", ctx ->  new ShikokuModel(ctx.bakeLayer(ClientSetup.INU_SHIKOKU)));
        register("houndstone", ctx ->  new HoundstoneModel(ctx.bakeLayer(ClientSetup.DOG_HOUNDSTONE)));
        register("zero", ctx ->  new ZeroModel(ctx.bakeLayer(ClientSetup.DOG_ZERO)));
        register("scraps", ctx ->  new ScrapsModel(ctx.bakeLayer(ClientSetup.DOG_SCRAPS)));
        register("sparky", ctx ->  new SparkyModel(ctx.bakeLayer(ClientSetup.DOG_SPARKY)));
        register("german_pointer_shorthaired", ctx ->  new GermanPointerShorthaired(ctx.bakeLayer(ClientSetup.DOG_POINTER_SHORT)));
        register("german_pointer_wirehaired", ctx ->  new GermanPointerWirehaired(ctx.bakeLayer(ClientSetup.DOG_POINTER_WIRE)));
        register("samoyed", ctx ->  new SamoyedModel(ctx.bakeLayer(ClientSetup.DOG_SAMOYED)));
        register("ranga", ctx ->  new RangaModel(ctx.bakeLayer(ClientSetup.RANGA)));
        register("bolt", ctx ->  new BoltModel(ctx.bakeLayer(ClientSetup.BOLT)));
        register("norfolk_terrier", ctx ->  new NorfolkTerrierModel(ctx.bakeLayer(ClientSetup.DOG_NORFOLK_TERRIER)));
        register("australian_kelpie", ctx ->  new AustralianKelpieModel(ctx.bakeLayer(ClientSetup.DOG_AUSTRALIAN_KELPIE)));
        register("na", ctx ->  new Na(ctx.bakeLayer(ClientSetup.NA)));

        registerFromEvent();
    }

    private static void registerFromEvent() {
        var entries = new ArrayList<DogModelProps>(); 
        ModLoader.postEvent(new RegisterCustomDogModelsEvent(entries));
        if (entries.isEmpty())
            return;
        for (var entry : entries) {
            if (entry.id == null)
                continue;
            if (entry.layer == null)
                continue;
            if (MODEL_MAP.containsKey(entry.id)) 
                continue;
            register(entry.id, ctx -> new CustomDogModel(ctx.bakeLayer(entry.layer), entry));
        }
    }

    public static class DogModelHolder {
        private DogModel value;
        private Function<EntityRendererProvider.Context, DogModel> getter;

        public DogModelHolder (Function<EntityRendererProvider.Context, DogModel>  getter) {
            this.getter = getter;
        }

        public DogModel getValue() {
            return this.value;
        }

        public void resolve(EntityRendererProvider.Context ctx) {
            this.value = getter.apply(ctx);
        }

    }

}
