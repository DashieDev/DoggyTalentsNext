package doggytalents.client.entity.model;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.dog.AmaterasuModel;
import doggytalents.client.entity.model.dog.BrownHeelerMixModel;
import doggytalents.client.entity.model.dog.ChuckleModel;
import doggytalents.client.entity.model.dog.DeathModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.IwankoModel;
import doggytalents.client.entity.model.dog.JackModel;
import doggytalents.client.entity.model.dog.JunoModel;
import doggytalents.client.entity.model.dog.LegoshiModel;
import doggytalents.client.entity.model.dog.LucarioModel;
import doggytalents.client.entity.model.dog.StBernardModel;
import doggytalents.client.entity.model.dog.kusa.ChiModel;
import doggytalents.client.entity.model.dog.kusa.HayabusaModel;
import doggytalents.common.entity.Dog;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DogModelRegistry {
    
    private static Map<String, DogModelHolder<? extends AbstractDog>> MODEL_MAP;

    public static <T extends AbstractDog> void register(String name, Function<EntityRendererProvider.Context, DogModel<T>>  getter) {
        MODEL_MAP.putIfAbsent(name, new DogModelHolder<T>(getter));
    }

    public static DogModelHolder getDogModelHolder(String name) {
        return MODEL_MAP.get(name);
    }

    public static void resolve(EntityRendererProvider.Context ctx) {
        for (var holder : MODEL_MAP.entrySet()) holder.getValue().resolve(ctx);
    }

    public static void init() {
        MODEL_MAP = Maps.newConcurrentMap();
        register("default", ctx -> new DogModel<Dog>(ctx.bakeLayer(ClientSetup.DOG)));
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
        register("kusa_ko", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_KO)));
        register("kusa_rei", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_REI)));
        register("kusa_shin", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_SHIN)));
        register("kusa_take", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_TAKE)));
        register("kusa_tei", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_TEI)));
        register("kusa_ume", ctx ->  new ChiModel(ctx.bakeLayer(ClientSetup.KUSA_UME)));

        register("brown_heeler_mix", ctx ->  new BrownHeelerMixModel(ctx.bakeLayer(ClientSetup.DOG_BROWN_HEELER_MIX)));
        register("chuckle", ctx ->  new ChuckleModel(ctx.bakeLayer(ClientSetup.DOG_CHUCKLE)));

    }

    public static class DogModelHolder<T extends AbstractDog> {
        private DogModel<T> value;
        private Function<EntityRendererProvider.Context, DogModel<T>> getter;

        public DogModelHolder (Function<EntityRendererProvider.Context, DogModel<T>>  getter) {
            this.getter = getter;
        }

        public DogModel<T> getValue() {
            return this.value;
        }

        public void resolve(EntityRendererProvider.Context ctx) {
            this.value = getter.apply(ctx);
        }

    }

}
