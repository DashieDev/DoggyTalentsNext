package doggytalents.common.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.util.GsonHelper;

public class ResourceUtil {
    
    private static final Gson GSON = new Gson(); 

    public static Optional<Path> getBundledResource(String path) {
        return ForgeUtil.getBundledModResource(path);
    }

    public static Optional<JsonElement> getBundledJson(String path) {
        final var resource_path_optional = getBundledResource(path);
        if (!resource_path_optional.isPresent())
            return Optional.empty();    
        final var resource_path = resource_path_optional.get();

        JsonElement json = null;
        try (var reader = Files.newBufferedReader(resource_path)) {
            json = GsonHelper.fromJson(GSON, reader, JsonElement.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Optional.ofNullable(json);
    }
}
