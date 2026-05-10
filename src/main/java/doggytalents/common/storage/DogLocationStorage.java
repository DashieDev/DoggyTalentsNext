package doggytalents.common.storage;

import com.google.common.collect.Maps;
import doggytalents.DoggyTalentsNext;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogDuplicationDetection;
import doggytalents.common.lib.Constants;
import doggytalents.common.util.NBTUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class DogLocationStorage extends SavedData {

    private Map<UUID, DogLocationData> locationDataMap = Maps.newHashMap();

    private final OnlineDogLocationManager onlineDogManager = new OnlineDogLocationManager(this);

    public final Map<UUID, Integer> grettingDogLimitMap = Maps.newHashMap();
    public final Map<UUID, Integer> bridgingDogLimitMap = Maps.newHashMap();

    public DogLocationStorage() {}

    public static DogLocationStorage get(Level world) {
        if (!(world instanceof ServerLevel)) {
            throw new IllegalStateException("DogLocationStorage is being accessed from the Client Side. Please report to the DTN Team.");
        }

        ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);

        SavedDataStorage storage = overworld.getDataStorage();
        return storage.computeIfAbsent(TYPE);
    }

    public static DogLocationStorage get(MinecraftServer server) {
        ServerLevel overworld = server.getLevel(Level.OVERWORLD);

        SavedDataStorage storage = overworld.getDataStorage();
        return storage.computeIfAbsent(TYPE);
    }

    public Stream<DogLocationData> getDogs(LivingEntity owner) {
        UUID ownerId = owner.getUUID();

        return this.locationDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()));
    }

    public Stream<DogLocationData> getDogs(LivingEntity owner, ResourceKey<Level> key) {
        UUID ownerId = owner.getUUID();

        return this.locationDataMap.values().stream()
                .filter(data -> ownerId.equals(data.getOwnerId()))
                .filter(data -> key.equals(data.getDimension()));
    }

    @Nullable
    public DogLocationData getData(Dog dogIn) {
        return getData(dogIn.getUUID());
    }

    @Nullable
    public DogLocationData getData(UUID uuid) {
        if (this.locationDataMap.containsKey(uuid)) {
            return this.locationDataMap.get(uuid);
        }

        return null;
    }

    @Nullable
    public DogLocationData remove(Dog dogIn) {
        return remove(dogIn.getUUID());
    }

    @Nullable
    public DogLocationData getOrCreateData(Dog dogIn) {
        UUID uuid = dogIn.getUUID();

        return this.locationDataMap.computeIfAbsent(uuid, k -> {
            this.setDirty();
            return DogLocationData.from(this, dogIn);
        });
    }

    @Nullable
    public DogLocationData remove(UUID uuid) {
        if (this.locationDataMap.containsKey(uuid)) {
            DogLocationData storage = this.locationDataMap.remove(uuid);

            // Mark dirty so changes are saved
            this.setDirty();
            return storage;
        }

        return null;
    }

    @Nullable
    public DogLocationData putData(Dog dogIn) {
        UUID uuid = dogIn.getUUID();

        DogLocationData storage = new DogLocationData(this, uuid);

        this.locationDataMap.put(uuid, storage);
        // Mark dirty so changes are saved
        this.setDirty();
        return storage;
    }

    public Set<UUID> getAllUUID() {
        return Collections.unmodifiableSet(this.locationDataMap.keySet());
    }

    public Collection<DogLocationData> getAll() {
        return Collections.unmodifiableCollection(this.locationDataMap.values());
    }

    public static DogLocationStorage load(CompoundTag nbt, @Nullable HolderLookup.Provider prov) {
        DogLocationStorage store = new DogLocationStorage();
        store.locationDataMap.clear();

        ListTag list = nbt.getListOrEmpty("locationData");

        // Old style
        if (list.isEmpty()) {
            list = nbt.getListOrEmpty("dog_locations");
        }

        for (int i = 0; i < list.size(); ++i) {
            CompoundTag locationCompound = list.getCompoundOrEmpty(i);

            UUID uuid = NBTUtil.getUniqueId(locationCompound, "uuid");

            // Old style
            if (uuid == null) {
                uuid = NBTUtil.getUniqueId(locationCompound, "entityId");
            }

            DogLocationData locationData = new DogLocationData(store, uuid);
            locationData.read(locationCompound);

            if (uuid == null) {
                DoggyTalentsNext.LOGGER.info("Failed to load dog location data. Please report to mod author...");
                DoggyTalentsNext.LOGGER.info(locationData);
                continue;
            }

            store.locationDataMap.put(uuid, locationData);
        }

        return store;
    }

    public CompoundTag saveData(CompoundTag compound) {
        ListTag list = new ListTag();

        for (Entry<UUID, DogLocationData> entry : this.locationDataMap.entrySet()) {
            CompoundTag locationCompound = new CompoundTag();

            DogLocationData locationData = entry.getValue();
            NBTUtil.putUniqueId(locationCompound, "uuid", entry.getKey());
            locationData.write(locationCompound);

            list.add(locationCompound);
        }

        compound.put("locationData", list);

        return compound;
    }

    @Override
    public boolean isDirty() {
        //Always save with the latest Dog info if Dog is online. 
        return true;
    }

    public static void setSessionUUIDFor(Dog dog, UUID sessionUUID) {
        var storage = DogLocationStorage.get(dog.level());
        if (storage == null)
            return;
        var data = storage.getData(dog);
        if (data == null)
            return;
        data.setSessionUUID(sessionUUID);
        DogDuplicationDetection.onSessionUUIDUpdate(dog, sessionUUID);
    }

    public OnlineDogLocationManager getOnlineDogsManager() {
        return this.onlineDogManager;
    }

    public void onServerStop(ServerStoppingEvent event) {
        this.onlineDogManager.onServerStop();
        grettingDogLimitMap.clear();
    }

    public void onServerStopped(ServerStoppedEvent event) {
        this.onlineDogManager.onServerStopped();
    }

    public static final Codec<DogLocationStorage> CODEC = net.minecraft.nbt.CompoundTag.CODEC.xmap(
        tag -> DogLocationStorage.load(tag, null),
        storage -> { CompoundTag tag = new CompoundTag(); storage.saveData(tag); return tag; }
    );
    public static final SavedDataType<DogLocationStorage> TYPE = new SavedDataType<>(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, Constants.STORAGE_DOG_LOCATION),
        DogLocationStorage::new,
        CODEC,
        DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES
    );
}
