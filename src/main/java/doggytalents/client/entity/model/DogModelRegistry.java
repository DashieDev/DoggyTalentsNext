package doggytalents.client.entity.model;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.IwankoModel;
import doggytalents.client.entity.model.dog.LucarioModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DogModelRegistry {
    
    private static Map<String, DogModelHolder<? extends AbstractDog>> MODEL_MAP;

    public static <T extends AbstractDog> void register(String name, Function<EntityRendererProvider.Context, DogModel<T>>  getter) {
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
        register("iwanko", ctx -> new IwankoModel(ctx.bakeLayer(ClientSetup.DOG_IWANKO)));
        register("lucario", ctx -> new LucarioModel(ctx.bakeLayer(ClientSetup.DOG_LUCARIO)));
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
