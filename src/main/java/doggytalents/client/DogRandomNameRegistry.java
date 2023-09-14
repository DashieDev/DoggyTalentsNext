package doggytalents.client;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class DogRandomNameRegistry extends SimplePreparableReloadListener<DogRandomNameRegistry.Prep>  {
    private static ResourceLocation NAME_RES = Util.getResource("dogname/name.json");
    private static DogRandomNameRegistry INSTANCE = new DogRandomNameRegistry();
    private static final Gson GSON = new Gson();

    public static DogRandomNameRegistry getInstance() {
        return INSTANCE;
    }

    private final List<String> nameList = new ArrayList<>();

    public void populateNameList(List<String> name) {
        this.nameList.clear();
        this.nameList.addAll(name);
    }

    public String getRandomName(Dog dog) {
        if (nameList.isEmpty())
            return "";
        int r = new Random().nextInt(nameList.size());
        return nameList.get(r);
    }

    public static class Prep {
        public List<String> names;
        public Prep(List<String> x) {
            this.names = x;
        }
    }

    @Override
    protected Prep prepare(ResourceManager resMan, ProfilerFiller p_10797_) {
        InputStream istream = null;
        var prep = new Prep(new ArrayList<String>());
        try {
            var res = resMan.getResource(NAME_RES);
            istream = res.getInputStream();
            var jsonElement = GSON.fromJson(new InputStreamReader(istream, StandardCharsets.UTF_8), JsonElement.class);
            var jsonArray = jsonElement.getAsJsonArray();
            for (var e : jsonArray) {
                var name = e.getAsString();
                prep.names.add(name);
            }
        } catch (Exception e) {

        } finally {
            IOUtils.closeQuietly(istream);
        }
        return prep;
    }

    @Override
    protected void apply(Prep prep, ResourceManager p_10794_, ProfilerFiller p_10795_) {
        this.populateNameList(prep.names);
    }
        
}