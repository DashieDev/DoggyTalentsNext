package doggytalents.forge_imitate.event;

import net.minecraft.server.MinecraftServer;

public class ServerStoppedEvent extends Event {

    private MinecraftServer server;

    public ServerStoppedEvent(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return this.server;
    }

}
