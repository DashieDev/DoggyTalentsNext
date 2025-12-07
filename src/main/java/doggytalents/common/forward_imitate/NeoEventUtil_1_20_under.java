package doggytalents.common.forward_imitate;

import java.util.stream.Stream;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.OnDatapackSyncEvent;

public class NeoEventUtil_1_20_under {
    
    public static Stream<ServerPlayer> getRelevantPlayers(OnDatapackSyncEvent event) {
        var target_player = event.getPlayer();
        return target_player == null ? 
            event.getPlayerList().getPlayers().stream() 
            : Stream.of(target_player);
    }

}
