package doggytalents.client.data.addon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import doggytalents.api.events.RegisterCustomDogModelsEvent;
import doggytalents.client.entity.model.dog.CustomDogModel;
import doggytalents.client.entity.model.dog.DogModel;
import doggytalents.client.entity.model.util.DTNModelCodec;
import doggytalents.client.entity.model.util.ParsedDogModel;
import doggytalents.client.entity.model.util.DTNModelCodec.DogModelProps;
import doggytalents.client.entity.model.util.DTNModelCodec.ParsedModelResult;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

public class AllAddonModel {
    
    public static final String ADDON_ID = "";

    public static List<ModelLayerLocation> ALL_LOCATION;
    public static Map<ModelLayerLocation, Supplier<LayerDefinition>> LAYERS;
    public static Map<ModelLayerLocation, RegisterCustomDogModelsEvent.DogModelProps> PROPS;

    public static void init() {
        ALL_LOCATION = new ArrayList<>();
        LAYERS = Maps.newHashMap();
        PROPS = Maps.newHashMap();

        LayerRegisterer event_layer = (location, layer_gettter) -> {
            if (LAYERS.get(location) != null)
                throw new IllegalArgumentException("layer: " + location + " is already exist.");
            LAYERS.put(location, layer_gettter);
        };
        registerAllLayers(event_layer);
        
        final var id_set = new HashSet<ResourceLocation>();
        PropsRegisterer event_props = props -> {
            if (!id_set.add(props.id))
                throw new IllegalArgumentException("props: " + props.id + " is already exist.");
            PROPS.put(props.layer, props);
        };
        registerAllProps(event_props);

        ALL_LOCATION = List.copyOf(LAYERS.keySet());
    }

    public static void registerAllLayers(LayerRegisterer event) {

    }

    public static void registerAllProps(PropsRegisterer event) {

    }

    public static ResourceLocation getRes(String id) {
        return ResourceLocation.fromNamespaceAndPath(ADDON_ID, id);
    }

    public static record ModelResult(ResourceLocation id, ParsedModelResult layer, DogModelProps props) {

    }
    public static Map<ResourceLocation, ModelResult> getAllModels() {
        return ALL_LOCATION.stream()
            .map(AllAddonModel::modelFrom)
            .collect(
                Collectors.toMap(ModelResult::id, Function.identity()));
    }

    public static ModelResult modelFrom(ModelLayerLocation location) {
        var layer_creator = LAYERS.get(location);
        if (layer_creator == null)
            throw new IllegalArgumentException(location + " doesn't have any layers supplier bound");
        var layer = layer_creator.get();
        
        var props = PROPS.get(location);
        if (props == null)
            throw new IllegalArgumentException(location + " doesn't have any props bound");
        
        final var id = props.id;
        final var model = new CustomDogModel(layer.bakeRoot(), props);
        return new ModelResult(id, 
            DTNModelCodec.parsedFromLayerDefintion(layer), 
            ParsedDogModel.propsFrom(model));
    }

    @FunctionalInterface
    private static interface PropsRegisterer {

        void register(RegisterCustomDogModelsEvent.DogModelProps props);

    }

    @FunctionalInterface
    private static interface LayerRegisterer {

        void register(ModelLayerLocation location, Supplier<LayerDefinition> layerCreator);

    }

}
