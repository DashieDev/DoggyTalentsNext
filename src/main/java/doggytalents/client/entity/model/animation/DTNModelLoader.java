package doggytalents.client.entity.model.animation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.util.DTNModelCodec;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class DTNModelLoader extends SimpleJsonResourceReloadListener {
    
    // In charge of loading the json models files in DTN Format at
    // assets/<namespace>/doggytalents/dog_models
    
    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dogModelLoader");

    private DTNModelLoader() {
        super(new Gson(), createRegistryPath());
    }

    public static String createRegistryPath() {
        var registry = Util.getResource("dog_models");
        return registry.getNamespace() + "/" + registry.getPath();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> contents, ResourceManager resourceManager,
            ProfilerFiller profiler) {
        
        int load_count = 0;
        for (var entry : contents.entrySet()) {
            final var id = entry.getKey();
            final var model_json = entry.getValue();
            
            var dynamic_data = new Dynamic<>(JsonOps.INSTANCE, model_json);
            try {
                var result_pair = DTNModelCodec.DOG_MODEL_CODEC.parse(dynamic_data)
                    .getOrThrow(JsonParseException::new);
                var result = result_pair.getLeft();
                var props = result_pair.getRight();
                
                boolean load_result = false;
                if (result != null && props != null) {
                    load_result = DogModelRegistry.registerParsed(id, result, props);   
                }
                if (load_result)
                    ++load_count;
            } catch (Exception e) {
                LOGGER.error("Failed to load model: {} ", id, e);
            }
        }
        LOGGER.info("Successfully loaded {} models.", load_count);
    }

    public static final DTNModelLoader INSTANCE = new DTNModelLoader();

}
