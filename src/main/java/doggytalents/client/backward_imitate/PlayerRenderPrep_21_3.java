package doggytalents.client.backward_imitate;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

public class PlayerRenderPrep_21_3 {
    public static Player player = null;

    public static void afterPlayerRender(RenderPlayerEvent.Post event) {
        player = null;
    }
}
