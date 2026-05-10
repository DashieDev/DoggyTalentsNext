package doggytalents.client.entity.model.animation;

import java.io.BufferedReader;
import java.util.HashMap;
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
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class DTNModelLoader extends SimplePreparableReloadListener<Map<Identifier, JsonElement>> {

    // In charge of loading the json models files in DTN Format at
    // assets/<namespace>/doggytalents/dog_models

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dogModelLoader");
    private static final Gson GSON = new Gson();

    private DTNModelLoader() {}

    public static String createRegistryPath() {
        var registry = Util.getResource("dog_models");
        return registry.getNamespace() + "/" + registry.getPath();
    }

    @Override
    protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        var result = new HashMap<Identifier, JsonElement>();
        var converter = FileToIdConverter.json(createRegistryPath());
        for (var entry : converter.listMatchingResources(resourceManager).entrySet()) {
            var id = converter.fileToId(entry.getKey());
            try (BufferedReader reader = entry.getValue().openAsReader()) {
                result.put(id, GSON.fromJson(reader, JsonElement.class));
            } catch (Exception e) {
                LOGGER.error("Failed to load model resource: {}", entry.getKey(), e);
            }
        }
        return result;
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> contents, ResourceManager resourceManager, ProfilerFiller profiler) {
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
