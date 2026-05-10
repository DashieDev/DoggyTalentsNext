package doggytalents.common.util.dogpromise.chunk;

import java.util.HashSet;
import java.util.Set;

import doggytalents.common.util.dogpromise.promise.AbstractPromise;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;

public class DTNForcedChunkManager {

    // TicketType is no longer generic in 26.1; create with timeout=0, flags=0
    private static final TicketType CHUNK_TASK = new TicketType(0L, 0);

    private static final Long2ObjectMap<Set<AbstractPromise>> accquiredChunkMap =
        new Long2ObjectOpenHashMap<>();

    public static final void accquireChunk(ServerLevel level, AbstractPromise chunkTask, ChunkPos pos) {
        var tickets = accquiredChunkMap.computeIfAbsent(pos.pack(), k -> new HashSet<>());
        if (tickets.isEmpty())
            level.getChunkSource().addTicketWithRadius(CHUNK_TASK, pos, 2);
        tickets.add(chunkTask);
    }

    public static final void dropChunk(ServerLevel level, AbstractPromise chunkTask, ChunkPos pos) {
        var tickets = accquiredChunkMap.get(pos.pack());
        if (tickets == null || tickets.isEmpty())
            return;
        tickets.remove(chunkTask);
        if (tickets.isEmpty())
            level.getChunkSource().removeTicketWithRadius(CHUNK_TASK, pos, 2);
    }

    public static final void onServerStop() {
        //Clear the map since our tickets won't persist anyways.
        accquiredChunkMap.clear();
    }
}
