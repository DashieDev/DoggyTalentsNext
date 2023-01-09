package doggytalents.common.chunk;

import doggytalents.ChopinLogger;
import doggytalents.common.lib.Constants;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.common.world.ForgeChunkManager.LoadingValidationCallback;
import net.minecraftforge.common.world.ForgeChunkManager.TicketHelper;

public class GarbageChunkCollector implements LoadingValidationCallback {
    public static void init() {
        //DoggyTalents should always start up with no chunks at all. For now.
        ForgeChunkManager
            .setForcedChunkLoadingCallback(Constants.MOD_ID, new GarbageChunkCollector());
    }

    @Override
    public void validateTickets(ServerLevel level, TicketHelper helper) {
        var blocks = helper.getBlockTickets();
        var uuids = helper.getEntityTickets();
        int count = 0;
        for (var c : blocks.entrySet()) {
            helper.removeAllTickets(c.getKey());
            ++count;
        }
        for (var c : uuids.entrySet()) {
            helper.removeAllTickets(c.getKey());
            ++count;
        }
        ChopinLogger.l("Garbage Collected : " + count + " chunks upon startup.");
    }
}
