package doggytalents.forge_imitate.event.client;


import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;

public class RenderPlayerEvent extends Event {

    public RenderPlayerEvent() {
    }

    public static class Pre extends RenderPlayerEvent {
        private final Player player;

        public Pre(Player player) {
            super();
            this.player = player;
        }

        public Player getEntity() {
            return this.player;
        }
    }

}
