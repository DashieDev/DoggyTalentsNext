package doggytalents.client.entity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
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
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoader;

public class DogModelRegistry {
    
    public static Map<ResourceLocation, DogModelHolder> MODEL_MAP;

    public static <T extends AbstractDog> void register(ResourceLocation id, Function<BakeContext, DogModel>  getter) {
        MODEL_MAP.putIfAbsent(id, new LegacyDogModelHolder(getter));
    }

    public static void register(String name, Function<BakeContext, DogModel>  getter) {
        register(Util.getResource(name), getter);
    }

    public static boolean registerParsed(ResourceLocation id, ParsedModelResult result,
        DTNModelCodec.DogModelProps props) {

        var model = wrapModelCreation(id, () -> ParsedDogModel.create(result, props));
        if (MODEL_MAP.get(id) != null)
            return false;
        MODEL_MAP.put(id, new ResolvedDogModelHolder(model));
        return true;
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

    public static void resolve(BakeContext ctx) {
        for (var holder : MODEL_MAP.entrySet()) {
            if (!(holder.getValue() instanceof LegacyDogModelHolder legacy_holder))
                continue;
            ctx.capturedId = holder.getKey();
            wrapModelCreation(holder.getKey(), () -> legacy_holder.resolve(ctx));
            ctx.capturedId = null;
        }
    }

    private static DogModel wrapModelCreation(ResourceLocation id, Supplier<DogModel> creator) {
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
        private Function<BakeContext, DogModel> getter;

        public LegacyDogModelHolder (Function<BakeContext, DogModel>  getter) {
            this.getter = getter;
        }

        @Override
        public DogModel getValue() {
            return this.value;
        }

        public DogModel resolve(BakeContext ctx) {
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

    public static class BakeContext {

        private final Optional<EntityRendererProvider.Context> wrapped;
        private final Map<ModelLayerLocation, Supplier<LayerDefinition>> modelMap;
        private final Map<ModelLayerLocation, ResourceLocation> idMap = Maps.newHashMap();

        private ResourceLocation capturedId = null;

        public BakeContext(Optional<EntityRendererProvider.Context> wrapped, Map<ModelLayerLocation, Supplier<LayerDefinition>> modelMap) {
            this.wrapped = wrapped;
            this.modelMap = modelMap;
        }

        public ModelPart bakeLayer(ModelLayerLocation location) {
            if (wrapped.isPresent()) {
                return wrapped.get().bakeLayer(location);
            }

            if (capturedId == null) 
                throw new IllegalStateException("no captured Id: " + location);
            var layer = modelMap.get(location);
            if (layer == null)
                throw new IllegalArgumentException("Cannot find model: " + location);
            if (idMap.get(location) != null)
                throw new IllegalStateException("Multiple model id shares the same layer definition!!!!");
            this.idMap.put(location, capturedId);
            return layer.get().bakeRoot();
        }

        public Map<ModelLayerLocation, ResourceLocation> getIdMap() {
            return idMap;
        }

    }

}
