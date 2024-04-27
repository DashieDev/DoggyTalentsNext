package doggytalents.common.chunk;

import doggytalents.common.util.Util;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import net.neoforged.neoforge.common.world.chunk.TicketHelper;

public class DoggyChunkController {

    public static final TicketController TICKET_CONTROLLER
        = new TicketController(Util.getResource("ticket_controller"), DoggyChunkController::validateTickets);
 
    public static void onChunkControllerRegistryEvent(RegisterTicketControllersEvent event) {
        event.register(TICKET_CONTROLLER);
    }

    public static void validateTickets(ServerLevel level, TicketHelper helper) {
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

    public static TicketController get() {
        return TICKET_CONTROLLER;
    }

}
