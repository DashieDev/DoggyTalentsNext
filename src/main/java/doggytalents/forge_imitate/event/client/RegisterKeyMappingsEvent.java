package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

public class RegisterKeyMappingsEvent extends Event {
    
    public RegisterKeyMappingsEvent() {}

    public void register(KeyMapping key) {
        KeyBindingHelper.registerKeyBinding(key);
    }

}
