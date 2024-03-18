package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;

public class MovementInputUpdateEvent extends Event {
    
    private Input input;
    private LocalPlayer player;

    public MovementInputUpdateEvent(Input input, LocalPlayer player) {
        this.input = input;
        this.player = player;
    }

    public Input getInput() {
        return this.input;
    }

    public LocalPlayer getEntity() {
        return this.player;
    }

}
