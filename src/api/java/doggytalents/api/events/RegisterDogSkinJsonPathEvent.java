package doggytalents.api.events;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

public class RegisterDogSkinJsonPathEvent extends Event implements IModBusEvent {
    
    private List<ResourceLocation> paths;

    public RegisterDogSkinJsonPathEvent(List<ResourceLocation> paths) {
        this.paths = paths;
    }

    public void register(ResourceLocation path) {
        this.paths.add(path);
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

}
