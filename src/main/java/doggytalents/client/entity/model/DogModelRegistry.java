package doggytalents.client.entity.model;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.dog.AmaterasuModel;
import doggytalents.client.entity.model.dog.ArcanineModel;
import doggytalents.client.entity.model.dog.BorzoiLongModel;
import doggytalents.client.entity.model.dog.BorzoiModel;
import doggytalents.client.entity.model.dog.BoxerFloppyModel;
import doggytalents.client.entity.model.dog.BoxerPointyModel;
import doggytalents.client.entity.model.dog.ChihuahuaModel;
import doggytalents.client.entity.model.dog.DachshundModel;
import doggytalents.client.entity.model.dog.DeathModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.EnglishBulldogModel;
import doggytalents.client.entity.model.dog.FrenchBulldogModel;
import doggytalents.client.entity.model.dog.IwankoModel;
import doggytalents.client.entity.model.dog.JackModel;
import doggytalents.client.entity.model.dog.JunoModel;
import doggytalents.client.entity.model.dog.LegoshiModel;
import doggytalents.client.entity.model.dog.LucarioModel;
import doggytalents.client.entity.model.dog.MiniaturePinscherModel;
import doggytalents.client.entity.model.dog.PochitaModel;
import doggytalents.client.entity.model.dog.PoodleModel;
import doggytalents.client.entity.model.dog.PugModel;
import doggytalents.client.entity.model.dog.StBernardModel;
import doggytalents.client.entity.model.dog.VariantDogModel;
import doggytalents.client.entity.model.dog.kusa.ChiModel;
import doggytalents.client.entity.model.dog.kusa.HayabusaModel;
import doggytalents.client.entity.model.dog.kusa.KoModel;
import doggytalents.client.entity.model.dog.kusa.ReiModel;
import doggytalents.client.entity.model.dog.kusa.ShinModel;
import doggytalents.client.entity.model.dog.kusa.TakeModel;
import doggytalents.client.entity.model.dog.kusa.TeiModel;
import doggytalents.client.entity.model.dog.kusa.UmeModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DogModelRegistry {
    
    private static Map<String, DogModelHolder> MODEL_MAP;

    public static <T extends AbstractDog> void register(String name, Function<EntityRendererProvider.Context, DogModel>  getter) {
        MODEL_MAP.putIfAbsent(name, new DogModelHolder(getter));
    }

    public static DogModelHolder getDogModelHolder(String name) {
        return MODEL_MAP.get(name);
    }

    public static void resolve(EntityRendererProvider.Context ctx) {
        for (var holder : MODEL_MAP.entrySet()) holder.getValue().resolve(ctx);
    }

    public static void init() {
        MODEL_MAP = Maps.newConcurrentMap();
        register("default", ctx -> new DogModel(ctx.bakeLayer(ClientSetup.DOG)));
        register("variant", ctx -> new VariantDogModel(ctx.bakeLayer(ClientSetup.DOG_LEGACY)));
        register("iwanko", ctx -> new IwankoModel(ctx.bakeLayer(ClientSetup.DOG_IWANKO)));
        register("lucario", ctx -> new LucarioModel(ctx.bakeLayer(ClientSetup.DOG_LUCARIO)));
        register("death", ctx -> new DeathModel(ctx.bakeLayer(ClientSetup.DOG_DEATH)));
        register("legoshi", ctx -> new LegoshiModel(ctx.bakeLayer(ClientSetup.DOG_LEGOSHI)));
        register("jack", ctx -> new JackModel(ctx.bakeLayer(ClientSetup.DOG_JACK)));
        register("juno", ctx -> new JunoModel(ctx.bakeLayer(ClientSetup.DOG_JUNO)));
        register("st_bernard", ctx -> new StBernardModel(ctx.bakeLayer(ClientSetup.DOG_ST_BERNARD)));
        register("okami_amaterasu", ctx ->  new AmaterasuModel(ctx.bakeLayer(ClientSetup.OKAMI_AMATERASU)));
        register("kusa_hayabusa", ctx ->  new HayabusaModel(ctx.bakeLayer(ClientSetup.KUSA_HAYABUSA)));
        register("kusa_chi", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_CHI)));
        register("kusa_ko", ctx ->  new KoModel(ctx.bakeLayer(ClientSetup.KUSA_KO)));
        register("kusa_rei", ctx ->  new ReiModel(ctx.bakeLayer(ClientSetup.KUSA_REI)));
        register("kusa_shin", ctx ->  new ShinModel(ctx.bakeLayer(ClientSetup.KUSA_SHIN)));
        register("kusa_take", ctx ->  new TakeModel(ctx.bakeLayer(ClientSetup.KUSA_TAKE)));
        register("kusa_tei", ctx ->  new TeiModel(ctx.bakeLayer(ClientSetup.KUSA_TEI)));
        register("kusa_ume", ctx ->  new UmeModel(ctx.bakeLayer(ClientSetup.KUSA_UME)));
        register("arcanine", ctx ->  new ArcanineModel(ctx.bakeLayer(ClientSetup.DOG_ARCANINE)));

        register("pochita", ctx ->  new PochitaModel(ctx.bakeLayer(ClientSetup.DOG_POCHITA)));
        register("dachshund", ctx ->  new DachshundModel(ctx.bakeLayer(ClientSetup.DOG_DACHSHUND)));
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
