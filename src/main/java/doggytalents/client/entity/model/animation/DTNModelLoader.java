package doggytalents.client.entity.model.animation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;

import doggytalents.client.ClientSetup;
import doggytalents.client.entity.model.util.DTNModelCodec;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class DTNModelLoader extends SimpleJsonResourceReloadListener {
    
    // In charge of loading the animation files at
    // assets/doggytalents/doggytalents/dog_models
    // Overriable via resourcepacks.
    
    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dogModelLoader");

    private final Map<ResourceLocation, LayerDefinition> models = Maps.newHashMap();

    private DTNModelLoader() {
        super(new Gson(), createRegistryPath());
    }

    public static String createRegistryPath() {
        var registry = Util.getResource("dog_models");
        return registry.getNamespace() + "/" + registry.getPath();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> contents, ResourceManager resourceManager,
            ProfilerFiller profiler) {
        
        
        
        //resolveJson
    }

    // private void test1(Map<ResourceLocation, JsonElement> contents) {
        
    //     compareModelFromPath(contents, "test1_dtn", TestLayers.test1(), 1); //Golden
    //     compareModelFromPath(contents, "test2_dtn", TestLayers.test2(), 2); //Standard
    //     compareModelFromPath(contents, "test3_dtn", TestLayers.test3(), 3); //Arcanine
    //     compareModelFromPath(contents, "test4_dtn", TestLayers.test4(), 4); //Ammy
    //     compareModelFromPath(contents, "test5_dtn", TestLayers.test5(), 5); //Tei
    //     compareModelFromPath(contents, "test6_dtn", TestLayers.test6(), 6); //Hope

    //     LOGGER.info("All Test completed");
    // }

    // private void compareModelFromPath(Map<ResourceLocation, JsonElement> contents, String id,
    //     LayerDefinition expected, int test_id) {

    //     var test_layer_1_json = contents.get(Util.getResource(id));
    //     var dynamic_data = new Dynamic<>(JsonOps.INSTANCE, test_layer_1_json);
    //     var test_layer_1_parsed = DTNModelCodec.CODEC.parse(dynamic_data)
    //         .getOrThrow();
    //     var test_layer_1 = DTNModelCodec.layerDefinitionFromParsed(test_layer_1_parsed);
    //     var expected_layer_1 = expected;
        
    //     LOGGER.info("Test {} starting...", test_id);

    //     ModelComparator.verifyModelsMatch(expected_layer_1, test_layer_1);

    //     LOGGER.info("Test {} completed: 100% Matched!", test_id);
    // }

    // private void test2() {
    //     LOGGER.info("Test 2 Begin:");
        
    //     //This field has been populated with all of the layer defintion registered by the mod.
    //     final var all_layer_defs = ClientSetup.LAYER_DEFS;
        
    //     int model_count = all_layer_defs.size();
    //     for (var entry : all_layer_defs.entrySet()) {
    //         var model = entry.getValue().get();
    //         var encoded_model = DTNModelCodec.parsedFromLayerDefintion(model);
    //         var model_json = DTNModelCodec.CODEC.encodeStart(JsonOps.INSTANCE, encoded_model).getOrThrow();
    //         var decoded_result = DTNModelCodec.CODEC.decode(JsonOps.INSTANCE, model_json).getOrThrow().getFirst();
    //         var decoded_model = DTNModelCodec.layerDefinitionFromParsed(decoded_result);
    //         ModelComparator.verifyModelsMatch(model, decoded_model);
    //         LOGGER.info("model {} passed!", entry.getKey().toString());
    //     }
    //     LOGGER.info("All {} models passed Test 2!", model_count);
    // }

    public static final DTNModelLoader INSTANCE = new DTNModelLoader();

}
