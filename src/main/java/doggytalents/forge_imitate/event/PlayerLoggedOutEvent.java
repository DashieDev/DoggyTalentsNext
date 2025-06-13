package doggytalents.forge_imitate.event;

import net.minecraft.server.level.ServerPlayer;

public class PlayerLoggedOutEvent extends Event {
    
    private ServerPlayer player;

    public PlayerLoggedOutEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getEntity() {
        return this.player;
    }

}
