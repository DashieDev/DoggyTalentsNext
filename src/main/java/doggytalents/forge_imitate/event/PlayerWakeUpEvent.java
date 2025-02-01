package doggytalents.forge_imitate.event;

import net.minecraft.world.entity.player.Player;

public class PlayerWakeUpEvent extends Event {
    private Player player;
    public PlayerWakeUpEvent(Player player) {
        this.player = player;
    }

    public Player getEntity() {
        return this.player;
    }
}
