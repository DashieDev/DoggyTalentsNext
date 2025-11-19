package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;
import doggytalents.forge_imitate.event.util.ModBusEvent;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class RegisterKeyMappingsEvent extends Event implements ModBusEvent {
    
    public RegisterKeyMappingsEvent() {}

    public void register(KeyMapping key) {
        KeyBindingHelper.registerKeyBinding(key);
    }

}
