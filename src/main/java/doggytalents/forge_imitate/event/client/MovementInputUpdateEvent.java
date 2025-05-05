package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;

public class MovementInputUpdateEvent extends Event {
    
    private ClientInput input;
    private LocalPlayer player;

    public MovementInputUpdateEvent(ClientInput input, LocalPlayer player) {
        this.input = input;
        this.player = player;
    }

    public ClientInput getInput() {
        return this.input;
    }

    public LocalPlayer getEntity() {
        return this.player;
    }

}
