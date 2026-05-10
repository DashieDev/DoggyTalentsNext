package doggytalents.common.storage;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;

import doggytalents.DoggyTalentsNext;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class DogRespawnStorage extends SavedData {

    private Map<UUID, DogRespawnData> respawnDataMap = Maps.newHashMap();

    public DogRespawnStorage() {}

    public static final Codec<DogRespawnStorage> CODEC = CompoundTag.CODEC.xmap(
        DogRespawnStorage::loadFromTag,
        DogRespawnStorage::saveToTag
    );

    public static final SavedDataType<DogRespawnStorage> TYPE = new SavedDataType<>(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "dead_dogs"),
        DogRespawnStorage::new,
        CODEC,
        DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES
    );

    public static DogRespawnStorage get(Level world) {
        if (!(world instanceof ServerLevel)) {
            throw new IllegalStateException("DogRespawnStorage is being accessed from the Client Side. Please report to the DTN Team.");
        }
        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);
        return overworld.getDataStorage().computeIfAbsent(TYPE);
    }

    public static DogRespawnStorage get(MinecraftServer server) {
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);
        return overworld.getDataStorage().computeIfAbsent(TYPE);
    }

    public Stream<DogRespawnData> getDogs(@Nonnull UUID ownerId) {
        return this.respawnDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()));
    }

    @Nullable
    public DogRespawnData getData(UUID uuid) {
        if (this.respawnDataMap.containsKey(uuid)) {
            return this.respawnDataMap.get(uuid);
        }
        return null;
    }

    @Nullable
    public DogRespawnData remove(UUID uuid) {
        if (this.respawnDataMap.containsKey(uuid)) {
            DogRespawnData storage = this.respawnDataMap.remove(uuid);
            this.setDirty();
            return storage;
        }
        return null;
    }

    @Nullable
    public DogRespawnData putData(Dog dogIn) {
        UUID uuid = dogIn.getUUID();
        DogRespawnData storage = new DogRespawnData(this, uuid);
        storage.populate(dogIn);
        this.respawnDataMap.put(uuid, storage);
        this.setDirty();
        return storage;
    }

    public Set<UUID> getAllUUID() {
        return Collections.unmodifiableSet(this.respawnDataMap.keySet());
    }

    public Collection<DogRespawnData> getAll() {
        return Collections.unmodifiableCollection(this.respawnDataMap.values());
    }

    private static DogRespawnStorage loadFromTag(CompoundTag nbt) {
        DogRespawnStorage store = new DogRespawnStorage();
        store.respawnDataMap.clear();

        ListTag list = nbt.getListOrEmpty("respawnData");
        for (int i = 0; i < list.size(); ++i) {
            CompoundTag respawnCompound = list.getCompoundOrEmpty(i);
            UUID uuid = NBTUtil.getUniqueId(respawnCompound, "uuid");
            DogRespawnData respawnData = new DogRespawnData(store, uuid);
            respawnData.read(respawnCompound);

            if (uuid == null) {
                DoggyTalentsNext.LOGGER.info("Failed to load dog respawn data. Please report to mod author...");
                DoggyTalentsNext.LOGGER.info(respawnData);
                continue;
            }
            store.respawnDataMap.put(uuid, respawnData);
        }
        return store;
    }

    private CompoundTag saveToTag() {
        CompoundTag compound = new CompoundTag();
        ListTag list = new ListTag();
        for (Map.Entry<UUID, DogRespawnData> entry : this.respawnDataMap.entrySet()) {
            CompoundTag respawnCompound = new CompoundTag();
            DogRespawnData respawnData = entry.getValue();
            NBTUtil.putUniqueId(respawnCompound, "uuid", entry.getKey());
            respawnData.write(respawnCompound);
            list.add(respawnCompound);
        }
        compound.put("respawnData", list);
        return compound;
    }
}
