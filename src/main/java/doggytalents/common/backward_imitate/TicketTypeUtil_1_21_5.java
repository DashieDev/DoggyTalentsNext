package doggytalents.common.backward_imitate;

import doggytalents.common.lib.Constants;
import doggytalents.mixin.ServerChunkCacheAccessorMixin_1_21_5;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.TicketStorage;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TicketTypeUtil_1_21_5 {
    
    public static final DeferredRegister<TicketType> TICKET_TYPE = DeferredRegister.create(BuiltInRegistries.TICKET_TYPE, Constants.MOD_ID);
    public static final TicketType CHUNK_TASK = register("chunk_task", new TicketType(0L, 15));

    private static TicketType register(String id, TicketType type) {
        TICKET_TYPE.register(id, () -> type);
        return type;
    }

    public static void init(IEventBus mod_bus) {
        TICKET_TYPE.register(mod_bus);
    }

    public static void addRegionTicket(ServerLevel level, TicketType type, ChunkPos pos, 
        int ticket_level, Object ticket_val) {

        getTicketStorage(level).addTicket(pos.toLong(), createTicket(type));
    }

    public static void removeRegionTicket(ServerLevel level, TicketType type, ChunkPos pos, 
        int ticket_level, Object ticket_val) {
        
        getTicketStorage(level).removeTicket(pos.toLong(), createTicket(type));
    }

    private static TicketStorage getTicketStorage(ServerLevel level) {
        return ((ServerChunkCacheAccessorMixin_1_21_5)level.getChunkSource())
            .dtn__getTicketStorage();
    }

    private static Ticket createTicket(TicketType type) {
        return new Ticket(type, ChunkMap.FORCED_TICKET_LEVEL);
    }

}
