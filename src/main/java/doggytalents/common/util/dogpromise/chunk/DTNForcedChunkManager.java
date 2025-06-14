package doggytalents.common.util.dogpromise.chunk;

import java.util.HashSet;
import java.util.Set;

import doggytalents.common.backward_imitate.TicketTypeUtil_1_21_5;
import doggytalents.common.util.Util;
import doggytalents.common.util.dogpromise.promise.AbstractPromise;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;

public class DTNForcedChunkManager {

    private static final TicketType CHUNK_TASK = 
        TicketTypeUtil_1_21_5.CHUNK_TASK;

    private static final Long2ObjectMap<Set<AbstractPromise>> accquiredChunkMap = 
        new Long2ObjectOpenHashMap<>();   

    public static final void accquireChunk(ServerLevel level, AbstractPromise chunkTask, ChunkPos pos) {
        var tickets = accquiredChunkMap.computeIfAbsent(pos.toLong(), k -> new HashSet<>());
        if (tickets.isEmpty())
            TicketTypeUtil_1_21_5.addRegionTicket(level, CHUNK_TASK, pos, 2, Unit.INSTANCE);
        tickets.add(chunkTask);
    }

    public static final void dropChunk(ServerLevel level, AbstractPromise chunkTask, ChunkPos pos) {
        var tickets = accquiredChunkMap.get(pos.toLong());
        if (tickets == null || tickets.isEmpty())
            return;
        tickets.remove(chunkTask);
        if (tickets.isEmpty())
            TicketTypeUtil_1_21_5.removeRegionTicket(level, CHUNK_TASK, pos, 2, Unit.INSTANCE);
    }

    public static final void onServerStop() {
        //Clear the map since our tickets won't persist anyways.
        accquiredChunkMap.clear();
    }

}
