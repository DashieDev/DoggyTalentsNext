package doggytalents.client.entity.model.animation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec.MapCodecCodec;

import net.minecraft.client.animation.AnimationDefinition;
import net.neoforged.neoforge.client.entity.animation.json.AnimationParser;

public class DogNeoForgeAnimationTest {
 
    public static final String ANIM_JSON = "";

    public static AnimationDefinition getAnimationFromFile() {
        var gson = new Gson();
        JsonElement json = null;
        try (var reader = new JsonReader(Files.newBufferedReader(Paths.get("chopin.txt")))) {
            json = gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null)
            return null;
        var dynamic_data = new Dynamic<>(JsonOps.INSTANCE, json);
        return AnimationParser.CODEC.parse(dynamic_data).getOrThrow();
    }

}
