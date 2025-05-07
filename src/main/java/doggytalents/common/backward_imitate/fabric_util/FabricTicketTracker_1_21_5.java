package doggytalents.common.backward_imitate.fabric_util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import doggytalents.api.backward_imitate.CompoundTag_1_21_5;
import doggytalents.api.backward_imitate.LegacyNbtCodec_1_21_5;
import doggytalents.common.util.Util;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.TicketStorage;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class FabricTicketTracker_1_21_5 extends SavedData {
    
    private static final String SAVE_LOCATION = "doggytalents_fabricTicketTracker_1_21_5";
    private static final ResourceLocation TICKET_TYPE_ID = Util.getResource("entity_fabric");
    private static final TicketType TICKET_TYPE = new TicketType(0L, false, TicketType.TicketUse.LOADING_AND_SIMULATION);
    
    private final Long2ObjectMap<Set<UUID>> ticketsPerChunk = new Long2ObjectOpenHashMap<>();

    public static void init() {
        Registry.register(BuiltInRegistries.TICKET_TYPE, TICKET_TYPE_ID, TICKET_TYPE);
    }

    public static void addTicket(ServerLevel level, UUID owner, ChunkPos chunk) {
        addTicket(level, owner, chunk.toLong());
    }

    public static void removeTicket(ServerLevel level, UUID owner, ChunkPos chunk) {
        removeTicket(level, owner, chunk.toLong());
    }

    public static void addTicket(ServerLevel level, UUID owner, long chunk) {
        var storage = get(level);
        var owners = storage.ticketsPerChunk.computeIfAbsent(chunk, $ -> new HashSet<>());
        if (owners.isEmpty()) {
            getTicketStorage(level).addTicket(chunk, createTicket());  
        }
        owners.add(owner);
    }

    public static void removeTicket(ServerLevel level, UUID owner, long chunk) {
        var storage = get(level);
        var owners = storage.ticketsPerChunk.get(chunk);
        if (owners == null)
            return;
        if (!owners.remove(owner))
            return;
        if (owners.isEmpty()) {
            storage.ticketsPerChunk.remove(chunk);
            getTicketStorage(level).removeTicket(chunk, createTicket());
        }
    }

    private static TicketStorage getTicketStorage(ServerLevel level) {
        return level.getChunkSource().ticketStorage;
    }

    private static Ticket createTicket() {
        return new Ticket(TICKET_TYPE, ChunkMap.FORCED_TICKET_LEVEL);
    }

    public static FabricTicketTracker_1_21_5 get(ServerLevel level) {
        var storage = level.getDataStorage();
        return storage.computeIfAbsent(savedDataType(SAVE_LOCATION));
    }

    private static FabricTicketTracker_1_21_5 load(CompoundTag_1_21_5 compound) {
        return new FabricTicketTracker_1_21_5();
    }

    public CompoundTag_1_21_5 save(CompoundTag_1_21_5 compound) {
        return compound;
    }

    //1.21.5+
    public static SavedDataType<FabricTicketTracker_1_21_5> savedDataType(String name) {
        return LegacyNbtCodec_1_21_5.createSavedDataType(name, 
            FabricTicketTracker_1_21_5::new, FabricTicketTracker_1_21_5::save, FabricTicketTracker_1_21_5::load);
    }

}
