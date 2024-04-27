package doggytalents.api.events;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

public class RegisterDogSkinJsonPathEvent extends Event implements IModBusEvent {
    
    private List<ResourceLocation> paths;

    public RegisterDogSkinJsonPathEvent(List<ResourceLocation> paths) {
        this.paths = paths;
    }

    public void register(ResourceLocation path) {
        this.paths.add(path);
    }
}
