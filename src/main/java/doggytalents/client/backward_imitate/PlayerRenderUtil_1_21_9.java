package doggytalents.client.backward_imitate;

import java.util.Optional;

import doggytalents.common.util.Util;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

public class PlayerRenderUtil_1_21_9 {
    
    private static final ContextKey<Player> PLAYER_ACCESS = new ContextKey<>(Util.getResource("player_access"));

    private static boolean isPlayerRender = false;

    public static Optional<Player> getPlayerFromState(EntityRenderState renderState, boolean flagCheck) {
        if (flagCheck && !isPlayerRender)
            return Optional.empty();
        return Optional.ofNullable(renderState.getRenderData(PLAYER_ACCESS));
    }

    public static void setPlayerToState(EntityRenderState renderState, Player player) {
        renderState.setRenderData(PLAYER_ACCESS, player);
    }

    public static void onPlayerRenderStart(RenderPlayerEvent.Pre<?> event) {
        isPlayerRender = true;
    }

    public static void onPlayerRenderEnd(RenderPlayerEvent.Post<?> event) {
        isPlayerRender = false;
    }

    public static void registerEvents(IEventBus forgeEventBus) {
        forgeEventBus.addListener(PlayerRenderUtil_1_21_9::onPlayerRenderStart);
        forgeEventBus.addListener(PlayerRenderUtil_1_21_9::onPlayerRenderEnd);
    }
    
}
