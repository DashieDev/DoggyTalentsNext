package doggytalents.common.entity.texture;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.common.lib.Constants;
import doggytalents.common.network.DTNNetworkHandler.NetworkEvent.Context;
import doggytalents.common.network.IPacket;
import doggytalents.common.network.PacketDistributor;
import doggytalents.common.network.PacketHandler;
import doggytalents.common.util.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

public class DogAllowedSkinManager extends SimpleJsonResourceReloadListener {

    // In charge of loading the allowed skin config at
    // data/doggytalents/doggytalents/allowed_skin/allowed_skin.json
    // Overriable via datapacks.

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dogAllowedSkin");
    private static final ResourceLocation ALLOWED_SKIN_PATH = Util.getResource("allowed_skin");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private AllowedSkinEntry entries = AllowedSkinEntry.EMPTY;
    private Set<String> entrySet = Set.of();

    private DogAllowedSkinManager() {
        super(GSON, createRegistryPath());
    }

    private static String createRegistryPath() {
        var registry = Util.getResource("allowed_skin");
        return registry.getNamespace() + "/" + registry.getPath();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> contents, ResourceManager resourceManager,
            ProfilerFiller profiler) {
        
        this.entries = AllowedSkinEntry.EMPTY;
        this.entrySet = Set.of();

        var entry_raw = contents.get(ALLOWED_SKIN_PATH);
        if (entry_raw == null)
            return;

        var dynamic_data = new Dynamic<>(JsonOps.INSTANCE, entry_raw);
        var entry_optional = ENTRY_CODEC.decode(dynamic_data).result();
        if (!entry_optional.isPresent())
            return;
        
        var entry = entry_optional.get().getFirst();

        if (!entry.validateEntry()) {
            LOGGER.error("Invalid allowed skins entries");
            return;
        }

        if (!entry.strategy.isNone())
            LOGGER.info("Loaded Dog Skin %s with %s entries.".formatted(
                entry.strategy() == Strategy.ALLOW_EXCEPT ?
                    "Blacklist" : "Whitelist", 
                Integer.toString(entry.entries().size())
            ));

        this.update(entry);
    }

    public boolean isNone() {
        return this.entries.strategy.isNone();
    }

    public boolean isSkinValid(String hash) {
        if (hash == null || hash.isEmpty())
            return true;

        var strategy = this.entries.strategy();
        return switch (strategy) {
            case NONE -> true;
            case ALLOW_EXCEPT -> !this.entrySet.contains(hash);
            case DISALLOW_EXCEPT -> this.entrySet.contains(hash);
        };
    }

    protected void update(AllowedSkinEntry entry) {
        this.entries = entry;
        this.entrySet = new HashSet<>(entry.entries());
    }

    protected void broadcastToAll(Stream<ServerPlayer> players) {
        var compressed_entries = this.entries.compress();
        players.forEach(player -> {
            PacketHandler.send(PacketDistributor.PLAYER.with(() -> player),
                compressed_entries
            );
        });
    }

    public static void onRegisterReloadListener(AddReloadListenerEvent event) {
        event.addListener(getServer());
    }

    public static void onDataPackSyncServer(OnDatapackSyncEvent event) {
        var to_sync = event.getRelevantPlayers();
        getServer().broadcastToAll(to_sync);
    }

    public static void onClientRecievedSync(AllowedSkinEntry entry) {
        getClient().update(entry);
    }

    public static enum Strategy implements StringRepresentable { 
        NONE, ALLOW_EXCEPT, DISALLOW_EXCEPT;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        } 

        public boolean isNone() {
            return this == NONE;
        }
        
    }

    private static final int SHA1_BYTE_LENGTH = 20;

    public static record AllowedSkinEntry(Strategy strategy, List<String> entries) {
        public static final AllowedSkinEntry EMPTY = new AllowedSkinEntry(Strategy.NONE, List.of());

        public static AllowedSkinEntry of(Strategy strategy, List<String> entries) {
            if (strategy == Strategy.NONE || entries == null)
                return EMPTY;

            return new AllowedSkinEntry(strategy, List.copyOf(entries));
        }

        public boolean validateEntry() {
            return getEntriesRawBytes().isPresent();
        }

        private Optional<List<byte[]>> getEntriesRawBytes() {
            var entries_raw = new ArrayList<byte[]>(this.entries.size());
            for (var entry : entries) {
                byte[] result = null; 
                try {
                    result = HexFormat.of().parseHex(entry);
                } catch (IllegalArgumentException e) {
                    result = null;
                }
                if (result == null || result.length != SHA1_BYTE_LENGTH) {
                    return Optional.empty();
                }
                entries_raw.add(result);
            }
            return Optional.of(entries_raw);
        }

        public AllowedSkinEntryCompressed compress() {
            if (this == EMPTY) return AllowedSkinEntryCompressed.EMPTY;
            return new AllowedSkinEntryCompressed(this.strategy, this.getEntriesRawBytes().get());
        }
    }

    public static record AllowedSkinEntryCompressed(Strategy strategy, List<byte[]> entriesRaw) {
        public static final AllowedSkinEntryCompressed EMPTY = new AllowedSkinEntryCompressed(Strategy.NONE, List.of());

        public void writeToStream(FriendlyByteBuf buf) {
            if (this.strategy.isNone()) {
                buf.writeByte((byte) -1);
                return;
            }
            buf.writeByte((byte) this.strategy.ordinal());

            var entries_raw = entriesRaw;
            buf.writeInt(entries_raw.size());
            for (var entry_raw : entries_raw) {
                buf.writeBytes(entry_raw);
            }
        }

        public static AllowedSkinEntryCompressed readFromStream(FriendlyByteBuf buf) {
            byte strategy_raw = buf.readByte();
            if (strategy_raw < 0)
                return EMPTY;
            var strategy = Strategy.values()[strategy_raw];

            int entries_raw_size = buf.readInt();
            var entries_raw = new ArrayList<byte[]>(entries_raw_size);
            for (int i = 0; i < entries_raw_size; ++i) {
                var temp_buff = new byte[SHA1_BYTE_LENGTH];
                buf.readBytes(temp_buff);
                entries_raw.add(temp_buff);
            }

            return new AllowedSkinEntryCompressed(strategy, entries_raw);
        }

        public AllowedSkinEntry decompress() {
            if (this == EMPTY) return AllowedSkinEntry.EMPTY;
            return new AllowedSkinEntry(this.strategy, 
                this.entriesRaw().stream().map(x -> HexFormat.of().formatHex(x)).toList());
        }
    }

    public static final Codec<AllowedSkinEntry> ENTRY_CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            StringRepresentable.fromEnum(Strategy::values)
                .optionalFieldOf("strategy", Strategy.NONE)
                .forGetter(AllowedSkinEntry::strategy),
            Codec.STRING.listOf()
                .fieldOf("skins_hashes")
                .forGetter(AllowedSkinEntry::entries)
        )
        .apply(builder, AllowedSkinEntry::of)
    );

    public static final DogAllowedSkinManager SERVER_INSTANCE = new DogAllowedSkinManager();
    public static final DogAllowedSkinManager CLIENT_INSTANCE = new DogAllowedSkinManager();

    public static DogAllowedSkinManager getClient() {
        return CLIENT_INSTANCE;
    }

    public static DogAllowedSkinManager getServer() {
        return SERVER_INSTANCE;
    }

    public static record Packet() implements IPacket<AllowedSkinEntryCompressed> {

        @Override
        public void encode(AllowedSkinEntryCompressed data, FriendlyByteBuf buf) {
            data.writeToStream(buf);
        }

        @Override
        public AllowedSkinEntryCompressed decode(FriendlyByteBuf buf) {
            return AllowedSkinEntryCompressed.readFromStream(buf);   
        }

        @Override
        public void handle(AllowedSkinEntryCompressed data, Supplier<Context> ctx) {
            ctx.get().enqueueWork(() -> {
                onClientRecievedSync(data.decompress());
            });
        }
        
    } 
}
