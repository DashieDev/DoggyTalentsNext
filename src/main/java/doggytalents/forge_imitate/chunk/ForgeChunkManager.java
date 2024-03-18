package doggytalents.forge_imitate.chunk;

import java.util.Comparator;
import java.util.UUID;

import doggytalents.common.lib.Constants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;

public class ForgeChunkManager {
    
    private static TicketType<UUID> ENTITY = TicketType.create(Constants.MOD_ID + ":entity", Comparator.comparing(x -> x));

    public static void forceChunk(ServerLevel level, String modId, UUID dog, int chunkX, int chunkZ, boolean add, boolean ticking) {
        var pos = new ChunkPos(chunkX, chunkZ);
        if (add)
            level.getChunkSource().addRegionTicket(ENTITY, pos, 2, dog);
        else
            level.getChunkSource().removeRegionTicket(ENTITY, pos, 2, dog);
    }

}
