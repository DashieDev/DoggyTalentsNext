package doggytalents.forge_imitate.event;

import net.minecraft.server.MinecraftServer;

public class ServerTickEvent extends Event {
    
    public static enum Phase {
        END
    }

    private final MinecraftServer server; 
    public final Phase phase;

    public ServerTickEvent(MinecraftServer server) {
        this.server = server;
        this.phase = Phase.END;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

}
