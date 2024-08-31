package doggytalents.common.chunk;

import java.util.UUID;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.common.world.ForgeChunkManager.TicketHelper;

public class DoggyChunkController {

    // public static final TicketController TICKET_CONTROLLER
    //     = new TicketController(Util.getResource("ticket_controller"), DoggyChunkController::validateTickets);
 
    // public static void onChunkControllerRegistryEvent(RegisterTicketControllersEvent event) {
    //     event.register(TICKET_CONTROLLER);
    // }

    // public static void validateTickets(ServerLevel level, TicketHelper helper) {
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

    // public static TicketController get() {
    //     return TICKET_CONTROLLER;
    // }

    //forge
    public void forceChunk(ServerLevel level, UUID uuid, int chunk_x, int chunk_z, boolean add, boolean tick) {
        ForgeChunkManager.forceChunk(level, Constants.MOD_ID, uuid, chunk_x, chunk_z, add, tick);
    }
    private static final DoggyChunkController instance = new DoggyChunkController();
    public static DoggyChunkController get() { return instance; }
    public static void init() {
        //DoggyTalents should always start up with no chunks at all. For now.
        ForgeChunkManager
            .setForcedChunkLoadingCallback(Constants.MOD_ID, (x, y) -> get().validateTickets(x, y));
    }
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
    }

}
