package doggytalents.client.entity.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import doggytalents.api.events.RegisterCustomDogModelsEvent;
import doggytalents.api.events.RegisterCustomDogModelsEvent.DogModelProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.dog.CustomDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.dog.VariantDogModel;
import doggytalents.client.entity.model.dog.DogModel.AccessoryState;
import doggytalents.client.entity.model.util.DTNModelCodec;
import doggytalents.client.entity.model.util.ParsedDogModel;
import doggytalents.client.entity.model.util.DTNModelCodec.ParsedModelResult;
import doggytalents.common.util.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModLoader;

public class DogModelRegistry {
    
    private static Map<Identifier, DogModelHolder> MODEL_MAP;

    public static <T extends AbstractDog> void register(Identifier id, Function<EntityRendererProvider.Context, DogModel>  getter) {
        MODEL_MAP.putIfAbsent(id, new LegacyDogModelHolder(getter));
    }

    public static void register(String name, Function<EntityRendererProvider.Context, DogModel>  getter) {
        register(Util.getResource(name), getter);
    }

    public static boolean registerParsed(Identifier id, ParsedModelResult result,
        DTNModelCodec.DogModelProps props) {

        var model = wrapModelCreation(id, () -> ParsedDogModel.create(result, props));
        if (MODEL_MAP.get(id) != null)
            return false;
        MODEL_MAP.put(id, new ResolvedDogModelHolder(model));
        return true;
    }

    public static DogModelHolder getDogModelHolder(Identifier id) {
        return MODEL_MAP.get(id);
    }

    public static DogModelHolder getDogModelHolder(String name) {
        Identifier loc;
        if (name.indexOf(':') >= 0) {
            loc = Identifier.parse(name);
        } else {
            loc = Util.getResource(name);
        }
        return getDogModelHolder(loc);
    }

    public static void resolve(EntityRendererProvider.Context ctx) {
        for (var holder : MODEL_MAP.entrySet()) {
            if (!(holder.getValue() instanceof LegacyDogModelHolder legacy_holder))
                continue;
            wrapModelCreation(holder.getKey(), () -> legacy_holder.resolve(ctx));
        }
    }

    private static DogModel wrapModelCreation(Identifier id, Supplier<DogModel> creator) {
        DogModel ret;
        try {
            ret = creator.get();
        } catch (NoSuchElementException e) {
            var msg = "Dog Model [" + id + "] is missing crucial parts! [" + e.getMessage() + "]";
            throw new NoSuchElementException(msg);
        }
        return ret;
    }

    public static void init() {
        MODEL_MAP = Maps.newConcurrentMap();
        register("default", ctx -> new DogModel(ctx.bakeLayer(ClientSetup.DOG)).setAccessoryState(AccessoryState.RECOMMENDED));
        register("variant", ctx -> new VariantDogModel(ctx.bakeLayer(ClientSetup.DOG_LEGACY)).setAccessoryState(AccessoryState.RECOMMENDED));
        //81

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

    public static sealed interface DogModelHolder
        permits LegacyDogModelHolder, ResolvedDogModelHolder {

        DogModel getValue();

    }

    private static final class LegacyDogModelHolder implements DogModelHolder  {
        private DogModel value;
        private Function<EntityRendererProvider.Context, DogModel> getter;

        public LegacyDogModelHolder(Function<EntityRendererProvider.Context, DogModel>  getter) {
            this.getter = getter;
        }

        @Override
        public DogModel getValue() {
            return this.value;
        }

        public DogModel resolve(EntityRendererProvider.Context ctx) {
            this.value = getter.apply(ctx);
            return this.value;
        }
    }

    private static final record ResolvedDogModelHolder(DogModel value) implements DogModelHolder {

        @Override
        public DogModel getValue() {
            return this.value();
        }

    }

}
