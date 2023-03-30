package doggytalents.client;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import doggytalents.ChopinLogger;
import doggytalents.DoggyTalentsNext;
import doggytalents.client.entity.model.DogModelRegistry;
import doggytalents.client.entity.skin.DogSkin;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.texture.DogTextureServer;
import doggytalents.common.lib.Resources;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.network.packet.data.RequestSkinData;
import doggytalents.common.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DogTextureManager extends SimplePreparableReloadListener<DogTextureManager.Preparations> {

    public static final DogTextureManager INSTANCE = new DogTextureManager();
    private static final Gson GSON = new Gson();
    private static final ResourceLocation OVERRIDE_RESOURCE_LOCATION = Util.getResource("textures/entity/dog/custom/overrides.json");

    private final Map<String, SkinRequest> hashToSkinRequest = Maps.newConcurrentMap();

    protected final Map<String, DogSkin> skinHashToLoc = Maps.newHashMap();
    protected final Map<DogSkin, String> locToSkinHash = Maps.newHashMap();
    protected final List<DogSkin> customSkinLoc = new ArrayList<>(20);

    public SkinRequest getRequestStatus(String hash) {
        return this.hashToSkinRequest.getOrDefault(hash, SkinRequest.UNREQUESTED);
    }

    public void setRequestHandled(String hash) {
        this.hashToSkinRequest.put(hash, SkinRequest.RECEIVED);
    }

    public void setRequestFailed(String hash) {
        this.hashToSkinRequest.put(hash, SkinRequest.FAILED);
    }

    public void setRequested(String hash) {
        this.hashToSkinRequest.put(hash, SkinRequest.REQUESTED);
    }

    public List<DogSkin> getAll() {
        return Collections.unmodifiableList(this.customSkinLoc);
    }

    public DogSkin getLocFromHashOrGet(String hash, Function<? super String, DogSkin> mappingFunction) {
        return this.skinHashToLoc.computeIfAbsent(hash, mappingFunction);
    }

    public String getTextureHash(DogSkin loc) {
        return this.locToSkinHash.getOrDefault(loc, "MISSING_MAPPING");
    }

    public DogSkin getSkinFromHash(String loc) {
        return this.skinHashToLoc.getOrDefault(loc, DogSkin.MISSING); // TODO return missing not null
    }


    public File getClientFolder() {
        Minecraft mc = Minecraft.getInstance();
        SkinManager skinManager = mc.getSkinManager();
        return new File(skinManager.skinsDirectory.getParentFile(), "skins_dog");
    }

    @Nullable
    public byte[] getResourceBytes(ResourceLocation loc) throws IOException {
        InputStream stream = null;

        try {
            stream = this.getResourceStream(loc);
            return IOUtils.toByteArray(stream);
        } finally  {
            IOUtils.closeQuietly(stream);
        }
    }

    @Nullable
    public InputStream getResourceStream(ResourceLocation loc) throws IOException {
        Minecraft mc = Minecraft.getInstance();

        ResourceManager resourceManager = mc.getResourceManager();
        Resource resource = resourceManager.getResource(loc).get();
        return resource.open();
    } 

    public ResourceLocation getTexture(Dog dog) {
        var skin = dog.getClientSkin();
        return skin.getPath();
    }


    public AbstractTexture getOrLoadTexture(File baseFolder, String hash) {
        Minecraft mc = Minecraft.getInstance();
        TextureManager textureManager = mc.getTextureManager();

        File cacheFile = DogTextureServer.INSTANCE.getCacheFile(baseFolder, hash);
        ResourceLocation loc = DogTextureServer.INSTANCE.getResourceLocation(hash);

        AbstractTexture texture = textureManager.getTexture(loc);
        if (texture == null && cacheFile.isFile() && cacheFile.exists()) {
            texture = new CachedFileTexture(loc, cacheFile);
            textureManager.register(loc, texture);
        }

        return texture;
    }

    /**
     * @param baseFolder The top level folder
     * @param data The data
     */
    public String saveTextureAndLoad(File baseFolder, byte[] data) throws IOException {
        Minecraft mc = Minecraft.getInstance();
        TextureManager textureManager = mc.getTextureManager();

        String hash = DogTextureServer.INSTANCE.getHash(data);

        File cacheFile = DogTextureServer.INSTANCE.getCacheFile(baseFolder, hash);
        ResourceLocation loc = DogTextureServer.INSTANCE.getResourceLocation(hash);

        AbstractTexture texture = textureManager.getTexture(loc);
        if (texture == null) {
            DoggyTalentsNext.LOGGER.debug("Saved dog texture to local cache ({})", cacheFile);
            FileUtils.writeByteArrayToFile(cacheFile, data);
            DoggyTalentsNext.LOGGER.debug("Texture not current loaded trying to load");
            textureManager.register(loc, new CachedFileTexture(loc, cacheFile));
        }

        return hash;
    }

    public DogSkin getCached(String hash) {
        if (!ConfigHandler.DISPLAY_OTHER_DOG_SKINS) {
            return DogSkin.CLASSICAL;
        }

        ResourceLocation loc = DogTextureServer.INSTANCE.getResourceLocation(hash);

        AbstractTexture texture = getOrLoadTexture(getClientFolder(), hash);
        if (texture != null) {
            return new DogSkin(loc);
        }

        if (!this.getRequestStatus(hash).requested()) {
            DoggyTalentsNext.LOGGER.debug("Marked {} dog skin to be requested from the server", hash);
            this.setRequested(hash);
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new RequestSkinData(hash));
        }

        return DogSkin.CLASSICAL;
    }

    private synchronized void loadDogSkinResource(DogTextureManager.Preparations prep, Resource resource, ResourceLocation rl) {
        InputStream inputstream = null;
        try {
            inputstream = resource.open();
            String hash = DogTextureServer.INSTANCE.getHash(IOUtils.toByteArray(inputstream));

            if (prep.skinHashToLoc.containsKey(hash)) {
                DoggyTalentsNext.LOGGER.warn("The loaded resource packs contained a duplicate custom dog skin ({} & {})", rl, this.skinHashToLoc.get(hash));
            } else {
                DoggyTalentsNext.LOGGER.info("Found custom dog skin at {} with hash {}", rl, hash);
                var dogSkin =new DogSkin(rl);
                prep.skinHashToLoc.put(hash, dogSkin);
                prep.locToSkinHash.put(dogSkin, hash);
                prep.customSkinLoc.add(dogSkin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputstream);
        }
    }

    private synchronized void loadDogSkinResourceWithMetadata(DogTextureManager.Preparations prep, Resource resource, DogSkin dogSkin) {
        InputStream inputstream = null;
        try {
            inputstream = resource.open();
            String hash = DogTextureServer.INSTANCE.getHash(IOUtils.toByteArray(inputstream));

            if (prep.skinHashToLoc.containsKey(hash)) {
                DoggyTalentsNext.LOGGER.warn("The loaded resource packs contained a duplicate custom dog skin ({} & {})", dogSkin, this.skinHashToLoc.get(hash));
            } else {
                DoggyTalentsNext.LOGGER.info("Found custom dog skin at {} with hash {}", dogSkin, hash);
                prep.skinHashToLoc.put(hash, dogSkin);
                prep.locToSkinHash.put(dogSkin, hash);
                prep.customSkinLoc.add(dogSkin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputstream);
        }
    }

    private void loadOverrideData(DogTextureManager.Preparations prep, List<Resource> resourcesList) {
        for (Resource iresource : resourcesList) {
            try {
                InputStream inputstream = iresource.open();
                DoggyTalentsNext.LOGGER.debug("Loading {}", iresource);
                try {
                    this.loadLocaleData(prep, inputstream);
                } finally {
                    IOUtils.closeQuietly(inputstream);
                }
            } catch (Exception e) {
                //d
            }
        }
    }

    private synchronized void loadLocaleData(DogTextureManager.Preparations prep, InputStream inputStreamIn) {
        JsonElement jsonelement = GSON.fromJson(new InputStreamReader(inputStreamIn, StandardCharsets.UTF_8), JsonElement.class);
        JsonObject jsonobject = GsonHelper.convertToJsonObject(jsonelement, "strings");

        for (Entry<String, JsonElement> entry : jsonobject.entrySet()) {
            String hash = entry.getKey();
            //TODO DOGSKIN
            ResourceLocation texture = new ResourceLocation(GsonHelper.convertToString(entry.getValue(), hash));
            var previous = prep.skinHashToLoc.put(hash, new DogSkin(texture));

            if (previous != null) {

            } else {

            }
            //prep.customSkinLoc.remove(texture);z`

            DoggyTalentsNext.LOGGER.info("Loaded override for {} -> {}", hash, texture);
        }
    }

    @Override
    protected DogTextureManager.Preparations prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        DogTextureManager.Preparations prep = new DogTextureManager.Preparations();

        profiler.startTick();

        DogModelRegistry.init();

        //TODO parse and load model files from resource here.

        if (this.getSkinFromSkinJsonAllPack(resourceManager, prep)) {
            profiler.pop();
            profiler.endTick();
            return prep;
        }
        DoggyTalentsNext.LOGGER.warn("Could not parse or get resource from skin.json. Will be searching at textures/entity/dog/custom instead.");
        prep.skinHashToLoc.clear();
        prep.locToSkinHash.clear();
        prep.customSkinLoc.clear();
        
        var resources = resourceManager.listResources("textures/entity/dog/custom", (fileName) -> {
            return true;
            //return fileName.getPath().endsWith(".png");
        });
        for (var i : resources.entrySet()) {
            try {
                Resource resource = resourceManager.getResource(i.getKey()).get();

                if (resource == null) {
                    DoggyTalentsNext.LOGGER.warn("Could not get resource");
                    continue;
                }

                this.loadDogSkinResource(prep,  i.getValue(), i.getKey());
            } catch (Exception exception) {
                DoggyTalentsNext.LOGGER.warn("Skipped custom dog skin file: {} ({})", i.getKey(), exception);
            }
        }

        try {
            List<Resource> override = new ArrayList<>(resourceManager.listResources(OVERRIDE_RESOURCE_LOCATION.getPath(), x -> true).values());
            this.loadOverrideData(prep, override);
        } catch (RuntimeException runtimeexception) {
            DoggyTalentsNext.LOGGER.warn("Unable to parse dog skin override data: {}", runtimeexception);
        }

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
                getSkinFromSkinJson(resMan, prep, jsonObject);
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(istream);
            }
        }
        prep.customSkinLoc.add(0, DogSkin.CLASSICAL);
        return true;
    }

    public void getSkinFromSkinJson(ResourceManager resMan, DogTextureManager.Preparations prep, JsonObject jsonObject) {
        var skinEntries = jsonObject.get("dog_skins").getAsJsonArray();
        for (var skinEntry : skinEntries) {
            var skinObject = skinEntry.getAsJsonObject();
            var name = skinObject.get("skin_name").getAsString();
            var id = skinObject.get("skin_id").getAsString();
            var use_model = skinObject.get("use_model").getAsString();
            ChopinLogger.l("Found : " + name + " " + id + " " + use_model);
            var text_rl = Util.getResource("textures/entity/dog/custom/" + id + ".png");
            DogSkin skin;
            if (use_model == null || use_model.equals("default") || use_model.equals("")) {
                skin = new DogSkin(text_rl).setName(name);
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

            var res = resMan.getResource(text_rl);
            if (res.isPresent())
            loadDogSkinResourceWithMetadata(prep, res.get(), skin);
        }
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
        private final List<DogSkin> customSkinLoc = new ArrayList<>(20);

        public void apply(DogTextureManager dogTextureManager) {
            dogTextureManager.skinHashToLoc.clear();
            dogTextureManager.customSkinLoc.clear();

            dogTextureManager.skinHashToLoc.putAll(this.skinHashToLoc);
            dogTextureManager.locToSkinHash.putAll(this.locToSkinHash);
            dogTextureManager.customSkinLoc.addAll(this.customSkinLoc);
        }
    }
}
