package doggytalents.client.backward_imitate;

import java.util.Optional;

import doggytalents.client.backward_imitate.fabric_util.FabricClientAttachmentHolder_1_21_10;
import doggytalents.client.backward_imitate.fabric_util.RenderPlayerEvent_21_3;
import doggytalents.common.backward_imitate.fabric_util.FabricEventRegisterer_1_21_9;
import doggytalents.common.util.Util;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;
import doggytalents.forge_imitate.event.client.RenderPlayerEvent;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.player.Player;

public class PlayerRenderUtil_1_21_9 {
    
    private static final ContextKey<Player> PLAYER_ACCESS = new ContextKey<>(Util.getResource("player_access"));

    private static boolean isPlayerRender = false;

    public static Optional<Player> getPlayerFromState(EntityRenderState renderState, boolean flagCheck) {
        if (flagCheck && !isPlayerRender)
            return Optional.empty();
        return Optional.ofNullable(FabricClientAttachmentHolder_1_21_10.getAttachment(renderState, PLAYER_ACCESS));
    }

    public static void setPlayerToState(EntityRenderState renderState, Player player) {
        FabricClientAttachmentHolder_1_21_10.putAttachement(renderState, PLAYER_ACCESS, player);
    }

    public static void onPlayerRenderStart(RenderPlayerEvent.Pre event) {
        isPlayerRender = true;
    }

    public static void onPlayerRenderEnd(RenderPlayerEvent_21_3.Post event) {
        isPlayerRender = false;
    }

    public static void registerEvents() {
        FabricEventRegisterer_1_21_9.registerSingleEvent(RenderPlayerEvent.Pre.class, PlayerRenderUtil_1_21_9::onPlayerRenderStart);
        FabricEventRegisterer_1_21_9.registerSingleEvent(RenderPlayerEvent_21_3.Post.class, PlayerRenderUtil_1_21_9::onPlayerRenderEnd);
    }
    
}
