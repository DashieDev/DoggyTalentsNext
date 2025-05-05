package doggytalents.client.backward_imitate.fabric_util;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.world.entity.player.Player;

public class RenderPlayerEvent_21_3 extends Event {

    public RenderPlayerEvent_21_3() {
    }

    public static class Post extends RenderPlayerEvent_21_3 {
        private final Player player;

        public Post(Player player) {
            super();
            this.player = player;
        }

        public Player getEntity() {
            return this.player;
        }
    }

}