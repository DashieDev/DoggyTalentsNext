package doggytalents.common.backward_imitate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;

import doggytalents.DoggyTalentsNext;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public abstract class SimpleJsonResourceReloadListener_1_21_9 extends SimplePreparableReloadListener<Map<Identifier, JsonElement>> {
   private final Gson gson;
   private final String directory;

   public SimpleJsonResourceReloadListener_1_21_9(Gson gson, String dir) {
      this.gson = gson;
      this.directory = dir;
   }

   @Override
   protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
      var jsons = new HashMap<Identifier, JsonElement>();
      scanDirectory(resourceManager, this.directory, this.gson, jsons);
      return jsons;
   }

   public static void scanDirectory(ResourceManager resourceManager, String directory, Gson gson, Map<Identifier, JsonElement> outputJsons) {
      var file_to_id = FileToIdConverter.json(directory);

      for(var entry : file_to_id.listMatchingResources(resourceManager).entrySet()) {
         var entry_path = entry.getKey();
         var entry_id = file_to_id.fileToId(entry_path);

         try (var reader = entry.getValue().openAsReader()) {
            var entry_json = GsonHelper.fromJson(gson, reader, JsonElement.class);
            var entry_json0 = outputJsons.put(entry_id, entry_json);
            if (entry_json0 != null) {
               throw new IllegalStateException("Duplicate data file ignored with ID " + entry_id);
            }
         } catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
            DoggyTalentsNext.LOGGER.error("Couldn't parse data file {} from {}", entry_id, entry_path, jsonparseexception);
         }
      }

   }
}