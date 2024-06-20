package doggytalents.client;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import doggytalents.DoggyTalentsNext;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class DogTextureManager extends SimplePreparableReloadListener<DogTextureManager.Preparations> {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dogSkin");
    public static final DogTextureManager INSTANCE = new DogTextureManager();
    private static final Gson GSON = new Gson();

    protected final Map<String, DogSkin> skinHashToLoc = Maps.newHashMap();
    protected final Map<DogSkin, String> locToSkinHash = Maps.newHashMap();
    protected final List<DogSkin> customSkinLoc = new ArrayList<>(20);

    public List<DogSkin> getAll() {
        return Collections.unmodifiableList(this.customSkinLoc);
    }

    public DogSkin getDogSkin(String hash) {
        if (hash == null || hash.isEmpty())
            return DogSkin.CLASSICAL;
        return this.skinHashToLoc.getOrDefault(hash, DogSkin.MISSING); 
    }

    public String getHash(DogSkin loc) {
        if (loc == null || !loc.isCustom())
            return "";
        return this.locToSkinHash.getOrDefault(loc, "");
    }

    public ResourceLocation getTexture(Dog dog) {
        var skin = dog.getClientSkin();
        if (skin != null && skin.isCustom()) {
            return skin.getPath();
        }
        return dog.dogVariant().texture();
    }

    private static enum RegisterState { SUCCESS, DUPLICATE, FAIL }

    private synchronized RegisterState registerDogSkin(DogTextureManager.Preparations prep, Resource resource, DogSkin dogSkin) {        
        var state = RegisterState.FAIL;

        InputStream inputstream = null;
        try {
            inputstream = resource.open();
            String hash = computeHash(IOUtils.toByteArray(inputstream));

            if (prep.skinHashToLoc.containsKey(hash)) {
                state = RegisterState.DUPLICATE;
            } else {
                prep.skinHashToLoc.put(hash, dogSkin);
                prep.locToSkinHash.put(dogSkin, hash);
                prep.customSkinLoc.add(dogSkin);    
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
    protected DogTextureManager.Preparations prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        var prep = new DogTextureManager.Preparations();
        profiler.startTick();
        this.getSkinFromSkinJsonAllPack(resourceManager, prep);
        profiler.pop();
        profiler.endTick();
        return prep;
    }

    public synchronized boolean getSkinFromSkinJsonAllPack(ResourceManager resMan, DogTextureManager.Preparations prep) {
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

        prep.customSkinLoc.add(0, DogSkin.CLASSICAL);
        return true;
    }

    public void getSkinJsonFromOtherMods(ResourceManager resMan, DogTextureManager.Preparations prep) {
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

    public RegsiterResult getSkinFromSkinJson(ResourceManager resMan, DogTextureManager.Preparations prep, JsonObject jsonObject) {
        int success_cnt = 0;
        int duplicate_cnt = 0;
        int failed_cnt = 0;

        var skinEntries = jsonObject.get("dog_skins").getAsJsonArray();
        for (var skinEntry : skinEntries) {
            var skinObject = skinEntry.getAsJsonObject();
            var name = skinObject.get("skin_name").getAsString();
            var id = skinObject.get("skin_id").getAsString();
            var use_model = skinObject.get("use_model").getAsString();
            
            ResourceLocation text_rl;
            if (id.indexOf(':') >= 0) {
                text_rl = ResourceLocation.parse(id + ".png");
            } else {
                text_rl = Util.getResource("textures/entity/dog/custom/" + id + ".png");
            }
            DogSkin skin;
            if (use_model == null || use_model.equals("default") || use_model.equals("")) {
                skin = new DogSkin(text_rl).setName(name);
            } else if (use_model.equals("variant")) {
                var dogModel = DogModelRegistry.getDogModelHolder(use_model);
                skin = (dogModel == null) ?
                    new DogSkin(text_rl).setName(name)
                    : new DogSkin(text_rl, dogModel).setName(name);
                var tailOptional = skinObject.get("tail_id");
                if (tailOptional != null) {
                    skin.setTail(tailOptional.getAsByte());
                }
                var earOptional = skinObject.get("ear_id");
                if (earOptional != null) {
                    skin.setEar(earOptional.getAsByte());
                }
            } else {
                var dogModel = DogModelRegistry.getDogModelHolder(use_model);
                if (dogModel == null)  skin = new DogSkin(text_rl).setName(name);
                else skin = new DogSkin(text_rl, dogModel).setName(name);
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
    }

    @Override
    protected void apply(DogTextureManager.Preparations prep, ResourceManager resourceManager, ProfilerFiller profiler) {
        prep.apply(this);
    }

    @Override
    public String getName() {
        return "DogTextureManager";
    }

    protected static class Preparations {

        private final Map<String, DogSkin> skinHashToLoc = new HashMap<>();
        private final Map<DogSkin, String> locToSkinHash = new HashMap<>();
        private final List<DogSkin> customSkinLoc = new ArrayList<>();

        public void apply(DogTextureManager dogTextureManager) {
            dogTextureManager.skinHashToLoc.clear();
            dogTextureManager.customSkinLoc.clear();

            dogTextureManager.skinHashToLoc.putAll(this.skinHashToLoc);
            dogTextureManager.locToSkinHash.putAll(this.locToSkinHash);
            dogTextureManager.customSkinLoc.addAll(this.customSkinLoc);
        }
    }
}
