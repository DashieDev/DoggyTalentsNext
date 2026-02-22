package doggytalents.client.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;

import doggytalents.client.ClientSetup;
import doggytalents.client.data.addon.AllAddonModel;
import doggytalents.client.entity.model.AllDTNModelMapping;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.model.DogModelRegistry.BakeContext;
import doggytalents.client.entity.model.animation.DTNModelLoader;
import doggytalents.client.entity.model.util.DTNModelCodec;
import doggytalents.client.entity.model.util.ParsedDogModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;

public class DogModelJsonProvider implements DataProvider {
    
    private static final Gson GSON = new GsonBuilder()
                        .setPrettyPrinting()
                        .disableHtmlEscaping() // Common in MC to keep characters readable
                        .create();
    private final PackOutput output;

    public DogModelJsonProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        var tasks = new ArrayList<CompletableFuture<?>>();
        var path_prov = this.output.createPathProvider(Target.RESOURCE_PACK, 
            DTNModelLoader.createRegistryPath());
        
        AllAddonModel.init();

        for (var entry : AllAddonModel.getAllModels().entrySet()) {
            final var model_id = entry.getKey();
            final var result = entry.getValue();
            final var layer = result.layer();
            final var props = result.props();
            var model_json = DTNModelCodec.DOG_MODEL_CODEC
                .encodeStart(JsonOps.INSTANCE, Pair.of(layer, props))
                .getOrThrow();
            var save_path = path_prov.json(model_id);
            var save_future = 
                saveStableNoIndent(output, model_json, save_path);
            tasks.add(save_future);
        }
        return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
    }

    private static CompletableFuture<?> saveStableNoIndent(CachedOutput output, JsonElement json, Path path) {
        return CompletableFuture.runAsync(() -> {
            try {
                var content_bytes = new ByteArrayOutputStream();
                var hashing_output = new HashingOutputStream(Hashing.sha1(), content_bytes);

                try (var writer = new JsonWriter(
                    new OutputStreamWriter(hashing_output, StandardCharsets.UTF_8))) {
                    
                    writer.setSerializeNulls(false);
                    GsonHelper.writeValue(writer, json, KEY_COMPARATOR);
                }

                output.writeIfNeeded(path, content_bytes.toByteArray(), hashing_output.hash());
            } catch (IOException ioexception) {
                LOGGER.error("Failed to save file to {}", path, ioexception);
            }
        }, net.minecraft.Util.backgroundExecutor());
    }

    private static CompletableFuture<?> saveStableNoArrayIndent(CachedOutput output, JsonElement json, Path path) {
        return CompletableFuture.runAsync(() -> {
            try {
                
                var pretty_json = GSON.toJson(json);

                final var array_inline_pattern = Pattern.compile("\\[\\s+([\\d\\.\\-\\s,]+)\\s+\\]");
                var matcher = array_inline_pattern.matcher(pretty_json);
                
                var builder = new StringBuilder();
                int last_end = 0;
                while (matcher.find()) {
                    builder.append(pretty_json, last_end, matcher.start());

                    var inlined_array = matcher.group(1).replaceAll("\\s+", "").replace(",", ", ");
                    builder.append("[").append(inlined_array).append("]");
                    
                    last_end = matcher.end();
                }
                builder.append(pretty_json.substring(last_end));
                


                var content_bytes = new ByteArrayOutputStream();
                var hashing_output = new HashingOutputStream(Hashing.sha1(), content_bytes);

                hashing_output.write(builder.toString().getBytes(StandardCharsets.UTF_8));

                output.writeIfNeeded(path, content_bytes.toByteArray(), hashing_output.hash());
                
            } catch (IOException ioexception) {
                LOGGER.error("Failed to save file to {}", path, ioexception);
            }
        }, net.minecraft.Util.backgroundExecutor());
    }

    @Override
    public String getName() {
        return "DTN Dog Model Provider";
    }

}
