package doggytalents.api.fabric_helper.entry;

import java.util.ArrayList;
import java.util.List;

import doggytalents.api.events.RegisterCustomDogModelsEvent.DogModelProps;
import doggytalents.api.fabric_helper.entry.DogModelConfiguationRegistry.Context;
import net.minecraft.resources.ResourceLocation;

public interface DogModelConfigurationEntry {
    public void onGatherDogModel(Context ctx);
}
