package doggytalents.forge_imitate.event;

import net.minecraft.server.MinecraftServer;

public class ServerStoppingEvent extends Event {

    private MinecraftServer server;

    public ServerStoppingEvent(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

}
