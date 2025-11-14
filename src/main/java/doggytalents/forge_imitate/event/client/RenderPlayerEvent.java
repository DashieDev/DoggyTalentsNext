package doggytalents.forge_imitate.event.client;


import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.forge_imitate.event.Event;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.player.Player;

public class RenderPlayerEvent extends Event {

    public RenderPlayerEvent() {
    }

    public static class Pre extends RenderPlayerEvent {
        private final Player player;
        private final LivingEntityRenderState renderState_1_21_10;

        public Pre(Player player, LivingEntityRenderState renderState_1_21_10) {
            super();
            this.player = player;
            this.renderState_1_21_10 = renderState_1_21_10;
        }

        public Player getEntity() {
            return this.player;
        }

        public LivingEntityRenderState getRenderState_1_21_10() {
            return this.renderState_1_21_10;
        }
    }

}
