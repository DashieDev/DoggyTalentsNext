package doggytalents.common.chunk;

import doggytalents.common.lib.Constants;
import net.minecraft.server.level.ServerLevel;

public class GarbageChunkCollector/* implements LoadingValidationCallback*/ {
    // public static void init() {
    //     //DoggyTalents should always start up with no chunks at all. For now.
    //     ForgeChunkManager
    //         .setForcedChunkLoadingCallback(Constants.MOD_ID, new GarbageChunkCollector());
    // }

    // @Override
    // public void validateTickets(ServerLevel level, TicketHelper helper) {
    //     var blocks = helper.getBlockTickets();
    //     var uuids = helper.getEntityTickets();
    //     int count = 0;
    //     for (var c : blocks.entrySet()) {
    //         helper.removeAllTickets(c.getKey());
    //         ++count;
    //     }
    //     for (var c : uuids.entrySet()) {
    //         helper.removeAllTickets(c.getKey());
    //         ++count;
    //     }
    // }
}
