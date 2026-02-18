package doggytalents.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.client.entity.skin.DogSkinHolder;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class DogTextureManager extends SimplePreparableReloadListener<DogTextureManager.DogSkinLoadResult> {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dogSkin");
    public static final DogTextureManager INSTANCE = new DogTextureManager();
    private static final Gson GSON = new Gson();

    private Map<String, DogSkinHolder> hashToSkinHolder = ImmutableMap.of();
    private Map<DogSkin, String> skinToHash = ImmutableMap.of();
    private List<DogSkinHolder> dogSkinHolders = List.of();
    private List<DogSkin> dogSkins = List.of(); 
    private DogSkinHolder missingHolder = DogSkinHolder.resolved(DogSkin.MISSING);

    public List<DogSkin> getAll() {
        return dogSkins;
    }

    public DogSkinHolder getDogSkin(String hash) {
        if (hash == null || hash.isEmpty())
            return DogSkinHolder.getNone();
        return this.hashToSkinHolder.getOrDefault(hash, missingHolder); 
    }

    public String getHash(DogSkin loc) {
        if (loc == null || !loc.isCustom())
            return "";
        return this.skinToHash.getOrDefault(loc, "");
    }

    public Identifier getTexture(Dog dog) {
        var skin = dog.getClientSkin();
        if (skin != null && skin.isCustom()) {
            return skin.getPath();
        }
        return dog.dogVariant().texture();
    }

    private static enum RegisterState { SUCCESS, DUPLICATE, FAIL }

    private RegisterState registerDogSkin(DogTextureManager.DogSkinLoadResult prep, Resource resource, DogSkin dogSkin) {        
        var state = RegisterState.FAIL;

        InputStream inputstream = null;
        try {
            inputstream = resource.open();
            String hash = computeHash(IOUtils.toByteArray(inputstream));

            if (prep.hashAlreadyRegistered(hash)) {
                state = RegisterState.DUPLICATE;
            } else {
                prep.addDogSkin(dogSkin, hash);
                state = RegisterState.SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputstream);
        }
        return state;
    }

    public String computeHash(byte[] targetArray) {
        return Hashing.sha1().hashBytes(targetArray).toString();
    }

    @Override
    protected DogTextureManager.DogSkinLoadResult prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        var prep = new DogTextureManager.DogSkinLoadResult();
        this.getSkinFromSkinJsonAllPack(resourceManager, prep);
        return prep;
    }

    public boolean getSkinFromSkinJsonAllPack(ResourceManager resMan, DogTextureManager.DogSkinLoadResult prep) {
        final var SKIN_JSON_RES = Util.getResource("textures/entity/dog/skin.json");
        var jsonSkinPacks = resMan.listPacks()
            .collect(Collectors.toList());
        for (var jsonSkinPack : jsonSkinPacks) {
            InputStream istream = null;
            try {
                var packRes = jsonSkinPack.getResource(PackType.CLIENT_RESOURCES, SKIN_JSON_RES);
                if (packRes == null) continue;
                istream = packRes.get();
                var jsonElement = GSON.fromJson(new InputStreamReader(istream, StandardCharsets.UTF_8), JsonElement.class);
                var jsonObject = jsonElement.getAsJsonObject();
                var result = getSkinFromSkinJson(resMan, prep, jsonObject);
                if (result.success > 0) {
                    DogTextureManager.LOGGER.info(
                        "Successfully registered "
                        + result.success + " entries from " + 
                        "pack ["
                        + jsonSkinPack.packId()
                        + "]"
                    );
                }
                if (result.duplicates > 0) {
                    DogTextureManager.LOGGER.warn(
                        "Pack ["
                        + jsonSkinPack.packId()
                        + "] contains "
                        + result.duplicates + " duplicated entries. Those will be omitted."
                    );
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(istream);
            }
        }
        
        getSkinJsonFromOtherMods(resMan, prep);
        return true;
    }

    public void getSkinJsonFromOtherMods(ResourceManager resMan, DogTextureManager.DogSkinLoadResult prep) {
        var paths = ClientSetup.OTHER_MOD_SKIN_JSONS;
        if (paths.isEmpty())
            return;

        for (var path : paths) {
            InputStream istream = null;
            try {
                var res = resMan.getResource(path);
                if (res.isEmpty()) continue;
                istream = res.get().open();
                var jsonElement = GSON.fromJson(new InputStreamReader(istream, StandardCharsets.UTF_8), JsonElement.class);
                var jsonObject = jsonElement.getAsJsonObject();
                var result = getSkinFromSkinJson(resMan, prep, jsonObject);
                if (result.success > 0) {
                    DogTextureManager.LOGGER.info(
                        "Successfully registered "
                        + result.success + " entries from path ["
                        + path
                        + "]"    
                    );
                }
                if (result.duplicates > 0) {
                    DogTextureManager.LOGGER.warn(
                        "Path ["
                        + path
                        + "] contains "
                        + result.duplicates + " duplicated entries. Those will be omitted."
                    );
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(istream);
            }
        }

    }

    private static record RegsiterResult(int success, int duplicates, int fail) {}

    public RegsiterResult getSkinFromSkinJson(ResourceManager resMan, DogTextureManager.DogSkinLoadResult prep, JsonObject jsonObject) {
        int success_cnt = 0;
        int duplicate_cnt = 0;
        int failed_cnt = 0;

        var skinEntries = jsonObject.get("dog_skins").getAsJsonArray();
        for (var skinEntry : skinEntries) {
            var skinObject = skinEntry.getAsJsonObject();
            var name = skinObject.get("skin_name").getAsString();
            var id = skinObject.get("skin_id").getAsString();
            var use_model = skinObject.get("use_model").getAsString();
            
            Identifier text_rl;
            if (id.indexOf(':') >= 0) {
                text_rl = Identifier.parse(id + ".png");
            } else {
                text_rl = Util.getResource("textures/entity/dog/custom/" + id + ".png");
            }

            DogSkin skin;
            final boolean use_default_model = 
                use_model == null || use_model.equals("default") || use_model.equals("");
            if (!use_default_model) {
                skin = new DogSkin(text_rl, true).setName(name);
                prep.dogSkinWithCustomModel.add(Pair.of(skin, use_model));
            } else {
                skin = new DogSkin(text_rl).setName(name);
            }

            final boolean is_legacy_variant_model =
                "variant".equals(use_model);
            if (is_legacy_variant_model){
                var tailOptional = skinObject.get("tail_id");
                if (tailOptional != null) {
                    skin.setTail(tailOptional.getAsByte());
                }
                var earOptional = skinObject.get("ear_id");
                if (earOptional != null) {
                    skin.setEar(earOptional.getAsByte());
                }
            }

            readSkinExtraInfo(skin, skinObject);

            var state = RegisterState.FAIL;
            var res = resMan.getResource(text_rl);
            if (res.isPresent())
            state = registerDogSkin(prep, res.get(), skin);
            switch (state) {
            case SUCCESS:
                ++success_cnt;
                break;
            case DUPLICATE:
                ++duplicate_cnt;
                break;
            default:
                ++failed_cnt;
                break;
            }
        }
        return new RegsiterResult(success_cnt, duplicate_cnt, failed_cnt);
    }

    private void readSkinExtraInfo(DogSkin skin, JsonObject skinJsonObject) {
        var author = skinJsonObject.get("author");
        if (author != null) skin.setAuthor(author.getAsString());
        var based_on = skinJsonObject.get("based_on");
        if (based_on != null) skin.setBasedOn(based_on.getAsString());
        var desc = skinJsonObject.get("description");
        if (desc != null) skin.setDesc(desc.getAsString());
        var tags = skinJsonObject.get("tags");
        if (tags != null) skin.setTags(tags.getAsString());
        var mystery = skinJsonObject.get("mystery");
        if (mystery != null) skin.setMystery(mystery.getAsBoolean());
        var glow_path = skinJsonObject.get("glowing_overlay");
        if (glow_path != null) {
            var raw_path = skinJsonObject.get("glowing_overlay").getAsString();
            Identifier glow_rl;
            if (raw_path.indexOf(':') >= 0) {
                glow_rl = Identifier.parse(raw_path + ".png");
            } else {
                glow_rl = Util.getResource("textures/entity/dog/custom/" + raw_path + ".png");
            }
            skin.setGlowingOverlay(glow_rl);
        }
    }

    @Override
    protected void apply(DogTextureManager.DogSkinLoadResult loadResult, ResourceManager resourceManager, ProfilerFiller profiler) {
        
        this.dogSkinHolders.forEach(DogSkinHolder::invalidate);
        this.dogSkinHolders = List.of();

        this.missingHolder.invalidate();
        this.missingHolder = DogSkinHolder.resolved(DogSkin.MISSING);

        this.dogSkins = List.of();
        this.skinToHash = ImmutableMap.of();
        this.hashToSkinHolder = ImmutableMap.of();
        int skipping_cnt = 0;

        var filter_skin_map = Maps.<DogSkin, String>newHashMap();
        var filter_skin_map_1 = Maps.<String, DogSkinHolder>newHashMap();
        var holder_list = new ArrayList<DogSkinHolder>();
        var skin_list = new ArrayList<DogSkin>();

        skin_list.add(DogSkin.CLASSICAL);

        //Model resolution
        for (var entry : loadResult.dogSkinWithCustomModel) {
            var skin = entry.getLeft();
            var model_id = entry.getRight();
            var model = DogModelRegistry.getDogModelHolder(model_id);
            if (model != null) {
                skin.setCustomModel(model);
            } else {
                DogTextureManager.LOGGER.warn(
                    "Skin [ {} ] refers to the missing model: [ {} ]",
                    skin.getPath(), model_id    
                );
            }
        }
        
        for (var entry : loadResult.dogSkinsAndHash) {
            var skin = entry.getLeft();
            var hash = entry.getRight();
            if (skin.useCustomModel() && skin.getCustomModel() == null) {
                continue;
            }
            if (isDogSkinBlacklisted(skin)) {
                ++skipping_cnt;
                continue;
            }
            var holder = DogSkinHolder.resolved(skin);
            filter_skin_map.put(skin, hash);
            filter_skin_map_1.put(hash, holder);
            holder_list.add(holder);
            skin_list.add(skin);
        }
        
        this.dogSkinHolders = List.copyOf(holder_list);
        this.dogSkins = List.copyOf(skin_list);
        this.skinToHash = ImmutableMap.copyOf(filter_skin_map);
        this.hashToSkinHolder = ImmutableMap.copyOf(filter_skin_map_1);
        
        if (skipping_cnt > 0) {
            DogTextureManager.LOGGER.info(
                "Excluded " + skipping_cnt + " blacklisted dog skins."
            );
        }
    }

    private static boolean isDogSkinBlacklisted(DogSkin dog_skin) {
        if (dog_skin == null)
            return false;
        if (!dog_skin.isCustom())
            return false;
        var strategy = ConfigHandler.DogCustomSkinClientConfig.getStrategy();
        if (strategy == ConfigHandler.DogCustomSkinClientConfig.DataStrategy.NONE)
            return false;
        var config = ConfigHandler.DogCustomSkinClientConfig.getInstance();
        if (config == null)
            return false;
        var skin_path = dog_skin.getPath().toString();
        if (
            strategy == ConfigHandler.DogCustomSkinClientConfig.DataStrategy.ALLOW_EXCEPT
            && config.isBlacklisted(skin_path)
        )
            return true;
        if (
            strategy == ConfigHandler.DogCustomSkinClientConfig.DataStrategy.DISALLOW_EXCEPT
            && !config.isWhitelisted(skin_path)
        )
            return true;
        return false;
    }

    @Override
    public String getName() {
        return "DogTextureManager";
    }

    protected static class DogSkinLoadResult {

        public final List<Pair<DogSkin, String>> dogSkinsAndHash;
        public final List<Pair<DogSkin, String>> dogSkinWithCustomModel;
        private final Set<String> registeredHash;

        public DogSkinLoadResult() {
            this.dogSkinsAndHash = new ArrayList<>();
            this.dogSkinWithCustomModel = new ArrayList<>();
            this.registeredHash = new HashSet<>();
        }

        public void addDogSkin(DogSkin skin, String hash) {
            if (skin == null || hash == null)
                return;
            this.dogSkinsAndHash.add(Pair.of(skin, hash));
            registeredHash.add(hash);
        }

        public boolean hashAlreadyRegistered(String hash) {
            return this.registeredHash.contains(hash);
        }
    }
}
