package doggytalents.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.pathfinder.Path;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.common.lib.Constants;


public class DebugUtil {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/debug");

    @SuppressWarnings("unchecked")
    public static List<Pair<ChunkPos, Ticket>> getAllTicketsOfType(ServerLevel level, TicketType type) {
        var chunk_source = (ServerChunkCache) level.getChunkSource();
        var distance_manager = ReflectionUtil.getPrivateField(ServerChunkCache.class, chunk_source,
            "distanceManager", DistanceManager.class).get();
        var tickets = (Long2ObjectOpenHashMap<Set<Ticket>>)
            ReflectionUtil.getPrivateField(DistanceManager.class, distance_manager,
            "tickets", Long2ObjectOpenHashMap.class).get();
        var tickets_selected = new ArrayList<Pair<ChunkPos, Ticket>>();
        var entry_set = tickets.long2ObjectEntrySet();
        for (var entry : entry_set) {
            var chunk = ChunkPos.unpack(entry.getLongKey());
            entry.getValue().stream()
                .filter(filter_ticket -> filter_ticket.getType() == type)
                .forEach(x -> tickets_selected.add(Pair.of(chunk, x)));
        }
        return tickets_selected;
    }

    public static String dumpPath(Path path) {
        var builder = new StringBuilder("\n");
        for (int i = 0; i < path.getNodeCount(); ++i) {
            var node = path.getNode(i);
            builder.append(String.format("%s %s %s %s\n",
                Integer.toString(node.x), Integer.toString(node.y), Integer.toString(node.z), node.type));
        }
        return builder.toString();
    }
}
