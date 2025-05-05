package doggytalents.client.backward_imitate;

import doggytalents.client.backward_imitate.fabric_util.RenderPlayerEvent_21_3;
import net.minecraft.world.entity.player.Player;

public class PlayerRenderPrep_21_3 {
    public static Player player = null;

    public static void afterPlayerRender(RenderPlayerEvent_21_3.Post event) {
        player = null;
    }
}
