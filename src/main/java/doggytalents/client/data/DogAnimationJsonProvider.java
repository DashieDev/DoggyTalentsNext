package doggytalents.client.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;

import doggytalents.api.anim.DogAnimation;
import doggytalents.client.entity.model.animation.DTNAnimationCodec;
import doggytalents.client.entity.model.animation.DTNAnimationLoader;
import doggytalents.client.entity.model.animation.DogAnimationRegistry;
import doggytalents.common.util.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.util.GsonHelper;

public class DogAnimationJsonProvider implements DataProvider {
    
    private final PackOutput output;

    public DogAnimationJsonProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        var tasks = new ArrayList<CompletableFuture<?>>();
        DogAnimationRegistry.init();
        var path_prov = this.output.createPathProvider(Target.RESOURCE_PACK, 
            DTNAnimationLoader.createRegistryPath());
        for (var dog_anim : DogAnimation.values()) {
            var sequence = DogAnimationRegistry.getSequence(dog_anim);
            if (sequence == null)
                continue;
            var anim_json_optional = DTNAnimationCodec.CODEC.encodeStart(JsonOps.INSTANCE, sequence).result();
            if (!anim_json_optional.isPresent())
                continue;
            var anim_json = anim_json_optional.get();
            var file_name = dog_anim.name().toLowerCase(Locale.ROOT);
            var save_path = path_prov.json(Util.getResource(file_name));
            var save_future = 
                saveStableNoIndent(output, anim_json, save_path);
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

    @Override
    public String getName() {
        return "DTN Animation Json Generator";
    }

}
