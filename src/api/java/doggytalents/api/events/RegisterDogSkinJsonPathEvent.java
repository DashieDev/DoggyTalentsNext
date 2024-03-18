package doggytalents.api.events;

import java.util.List;

import net.minecraft.resources.ResourceLocation;

public class RegisterDogSkinJsonPathEvent {
    
    private List<ResourceLocation> paths;

    public RegisterDogSkinJsonPathEvent(List<ResourceLocation> paths) {
        this.paths = paths;
    }

    public void register(ResourceLocation path) {
        this.paths.add(path);
    }

    //@Override
    public boolean isCancelable() {
        return false;
    }

}
