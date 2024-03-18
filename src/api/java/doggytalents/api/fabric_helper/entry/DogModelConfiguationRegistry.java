package doggytalents.api.fabric_helper.entry;

import java.util.ArrayList;
import java.util.List;

import doggytalents.api.events.RegisterCustomDogModelsEvent;
import doggytalents.api.events.RegisterDogSkinJsonPathEvent;
import doggytalents.api.events.RegisterCustomDogModelsEvent.DogModelProps;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.resources.ResourceLocation;

public class DogModelConfiguationRegistry {

    public static String ENTRY_ID = "doggytalents.dog_model_configuration";

    private static ArrayList<DogModelProps> entries = new ArrayList<>();
    private static ArrayList<ResourceLocation> skinJsonPaths = new ArrayList<>();

    public static List<DogModelProps> getAllProps() {
        return entries;
    }

    public static List<ResourceLocation> getJsonPaths() {
        return skinJsonPaths;
    }

    public static void doGatherFromOtherMods() {
        entries.clear();
        skinJsonPaths.clear();

        var skinJsonEvent = new RegisterDogSkinJsonPathEvent(skinJsonPaths);
        var propsEvent = new RegisterCustomDogModelsEvent(entries);
        var ctx = new Context(propsEvent, skinJsonEvent);
        var containers = FabricLoaderImpl.INSTANCE
            .getEntrypointContainers(ENTRY_ID, DogModelConfigurationEntry.class);
        if (containers.isEmpty())
            return;
        for (var container : containers) {
            container.getEntrypoint().onGatherDogModel(ctx);
        }
    }
    
    public static class Context {

        private RegisterDogSkinJsonPathEvent skinJsonEvent;
        private RegisterCustomDogModelsEvent propsEvent;

        private Context(RegisterCustomDogModelsEvent entries, RegisterDogSkinJsonPathEvent skinJson) {
            this.skinJsonEvent = skinJson;
            this.propsEvent = entries;
        }

        public RegisterDogSkinJsonPathEvent skinJsonEvent() {
            return this.skinJsonEvent;
        }

        public RegisterCustomDogModelsEvent propsEvent() {
            return this.propsEvent;
        }
    }


}
