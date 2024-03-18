package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.Event;

public class ClientTickEvent extends Event {
    
    public static enum Phase {
        END
    }

    public final Phase phase = Phase.END;

    public ClientTickEvent() {
        
    }

    public Phase getPhase() {
        return this.phase;
    }

}
