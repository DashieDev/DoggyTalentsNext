package doggytalents.common.util.dogpromise.chunk;

import java.util.HashSet;
import java.util.Set;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.ServerWorldUtil;
import doggytalents.common.util.Util;
import doggytalents.common.util.dogpromise.promise.AbstractPromise;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Unit;
import net.minecraft.world.level.ChunkPos;

public class DTNForcedChunkManager {

    private static final String TRANSIENT_ID = Constants.DTN_FORCED_CHUNK_TRACKER;
    private static final TicketType<Unit> CHUNK_TASK = 
        TicketType.create(Util.getResource("chunk_task").toString(), (a, b) -> 0);

    private static class AccquiredChunkPerLevel {
        public final Long2ObjectMap<Set<AbstractPromise>> accquiredChunkMap = 
            new Long2ObjectOpenHashMap<>();   
    }
    
    public static final void accquireChunk(ServerLevel level, AbstractPromise chunkTask, ChunkPos pos) {
        final var accquired_chunks = accquiredChunkFor(level);

        var tickets = accquired_chunks.computeIfAbsent(pos.toLong(), k -> new HashSet<>());
        if (tickets.isEmpty())
            level.getChunkSource().addRegionTicket(CHUNK_TASK, pos, 2, Unit.INSTANCE);
        tickets.add(chunkTask);
    }

    public static final void dropChunk(ServerLevel level, AbstractPromise chunkTask, ChunkPos pos) {
        final var accquired_chunks = accquiredChunkFor(level);

        var tickets = accquired_chunks.get(pos.toLong());
        if (tickets == null || tickets.isEmpty())
            return;
        tickets.remove(chunkTask);
        if (tickets.isEmpty())
            level.getChunkSource().removeRegionTicket(CHUNK_TASK, pos, 2, Unit.INSTANCE);
    }

    public static final void onServerStop(MinecraftServer server) {
        //Clear the map since our tickets won't persist anyways.
        server.getAllLevels().forEach(level -> {
            accquiredChunkFor(level).clear();
        });
    }

    private static Long2ObjectMap<Set<AbstractPromise>> accquiredChunkFor(ServerLevel level) {
        return ServerWorldUtil.getOrCreateTransientLevelOwnedObject(
            level, 
            AccquiredChunkPerLevel::new, 
            TRANSIENT_ID
        ).accquiredChunkMap;
    }

}
