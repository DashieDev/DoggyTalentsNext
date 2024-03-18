package doggytalents.forge_imitate.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerLoggedInEvent extends Event {
    
    private ServerPlayer player;

    public PlayerLoggedInEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getEntity() {
        return this.player;
    }

}
