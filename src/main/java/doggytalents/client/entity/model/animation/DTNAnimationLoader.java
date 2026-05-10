package doggytalents.client.entity.model.animation;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.MapMaker;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class DTNAnimationLoader extends SimplePreparableReloadListener<Map<Identifier, JsonElement>> {

    // In charge of loading the animation files at
    // assets/doggytalents/doggytalents/dog_animations
    // Overridable via resourcepacks.

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/animationLoader");

    private static final FileToIdConverter LISTER = FileToIdConverter.json(createRegistryPath());

    private final Map<Identifier, DogAnimationHolder> holderMap = new MapMaker()
        .concurrencyLevel(1).makeMap();

    private DTNAnimationLoader() {
    }

    public static String createRegistryPath() {
        var registry = Util.getResource("dog_animations");
        return registry.getNamespace() + "/" + registry.getPath();
    }

    @Override
    protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<Identifier, JsonElement> result = new HashMap<>();
        for (Map.Entry<Identifier, Resource> entry : LISTER.listMatchingResources(resourceManager).entrySet()) {
            Identifier location = entry.getKey();
            Identifier id = LISTER.fileToId(location);
            try (Reader reader = entry.getValue().openAsReader()) {
                result.put(id, JsonParser.parseReader(reader));
            } catch (IOException | JsonParseException e) {
                LOGGER.error("Couldn't parse data file '{}' from '{}'", id, location, e);
            }
        }
        return result;
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> contents, ResourceManager resourceManager,
            ProfilerFiller profiler) {

        holderMap.values().forEach(DogAnimationHolder::invalidate);

        var builtin_anims = new HashMap<Identifier, AnimationDefinition>();
        for (var entry : contents.entrySet()) {
            final var id = entry.getKey();
            final var anim_json = entry.getValue();

            //Don't load animations from different namespace for now.
            if (!id.getNamespace().equals(Constants.MOD_ID))
                continue;

            var dynamic_data = new Dynamic<>(JsonOps.INSTANCE, anim_json);
            AnimationDefinition anim = null;
            try {
                anim = DTNAnimationCodec.CODEC.parse(dynamic_data)
                    .getOrThrow(JsonParseException::new);
            } catch (Exception e) {
                LOGGER.error("Failed to load animation: {} ", id, e);
            }
            if (anim == null)
                continue;
            builtin_anims.put(id, anim);
        }

        //Ensure that every DogAnimation has a sequence bound
        var all_dog_anim = DogAnimation.values();
        var updated_map = new HashMap<DogAnimation, AnimationDefinition>();
        var missing_set = new HashSet<String>();
        for (var dog_anim : all_dog_anim) {
            if (dog_anim.isNone())
                continue;
            var id = dog_anim.name().toLowerCase(Locale.ROOT);
            var sequence = builtin_anims.remove(Util.getResource(id));
            if (sequence == null) {
                missing_set.add(id);
                continue;
            }
            updated_map.put(dog_anim, sequence);
        }
        if (!missing_set.isEmpty()) {
            final int max_missing_log = 5;
            int additional_missing = Math.max(0, missing_set.size() - max_missing_log);
            var missing_list_str =
                missing_set.stream().limit(max_missing_log)
                    .collect(Collectors.joining(", "))
                    + (missing_set.size() > max_missing_log ? ", +" + additional_missing : "");
            LOGGER.error(
                String.format(
                    "Failed to load some built-in dog animation: [ %s ]",
                    missing_list_str
                )
            );
        } else {
            LOGGER.info(
                String.format(
                    "All built-in animation is loaded successfully."
                )
            );
        }

        DogAnimationRegistry.update(updated_map);

        for (var entry : this.holderMap.entrySet()) {
            var id = entry.getKey();
            var holder = entry.getValue();
            var anim = builtin_anims.get(id);
            holder.update(anim);
        }
    }

    public DogAnimationHolder getAnim(String id) {
        return getAnim(Util.getResource(id));
    }

    public DogAnimationHolder getAnim(Identifier id) {
        return this.holderMap.computeIfAbsent(id,
            k -> new DogAnimationHolder(null));
    }

    public static class DogAnimationHolder implements Supplier<AnimationDefinition> {

        private AnimationDefinition value;

        private DogAnimationHolder(AnimationDefinition value) {
            this.value = value;
        }

        private void update(AnimationDefinition newValue) {
            this.value = newValue;
        }

        private void invalidate() {
            this.value = null;
        }

        @Override
        public AnimationDefinition get() {
            return this.value;
        }
    }

    public static final DTNAnimationLoader INSTANCE = new DTNAnimationLoader();

}
