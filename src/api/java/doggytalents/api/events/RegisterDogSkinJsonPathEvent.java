package doggytalents.api.events;

import java.util.List;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

public class RegisterDogSkinJsonPathEvent extends Event implements IModBusEvent {
    
    private List<Identifier> paths;

    public RegisterDogSkinJsonPathEvent(List<Identifier> paths) {
        this.paths = paths;
    }

    public void register(Identifier path) {
        this.paths.add(path);
    }
}
