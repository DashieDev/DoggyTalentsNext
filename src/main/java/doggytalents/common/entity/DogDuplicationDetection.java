package doggytalents.common.entity;

import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.lang3.ObjectUtils;

import doggytalents.DoggyTalentsNext;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.storage.DogRespawnStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class DogDuplicationDetection {

    private final Dog dog;

    public boolean detectedDuplicateVertified = false;
    private UUID cachedSessionUUID = null;

    public DogDuplicationDetection(Dog dog) {
        this.dog = dog;
    }

    public boolean detectDuplicate(CompoundTag tag) {
        if (detectedDuplicateVertified)
            return false; 
        if (ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get())
            return false;
        if (ConfigHandler.SERVER.TRUST_THIRD_PARTY_STORAGE.get())
            return false;
        if (!tag.contains("DTN_DupeDetect_UUID", Tag.TAG_COMPOUND))
            return false;
        if (tag.contains("DTN_DupeDetect_marked"))
            return tag.getBoolean("DTN_DupeDetect_marked");
        var backupUUIDTag = tag.getCompound("DTN_DupeDetect_UUID");
        var uuid = backupUUIDTag.getUUID("dtn_uuid_self");
        var ownerUUID = backupUUIDTag.getUUID("dtn_uuid_owner");
        UUID sessionUUID = null;
        if (backupUUIDTag.hasUUID("session_uuid")) {
            sessionUUID = backupUUIDTag.getUUID("session_uuid");
        }
        if (uuid == null || ownerUUID == null)
            return false;

        // Only detect if dog is not added to world while having the same uuid
        // for now. This is a pretty unlikely case though.
        if (dog.isAddedToWorld() && uuid.equals(dog.getUUID())) {
            return false;
        }
        
        boolean isDuplicate = false;
        
        if (!isDuplicate && checkRespawnStorageForDuplicate(uuid, ownerUUID))
            isDuplicate = true;
        if (!isDuplicate && checkLocationStorageForDuplicate(uuid, ownerUUID, sessionUUID))
            isDuplicate = true;
        if (!isDuplicate)
            return false;
        
        DoggyTalentsNext.LOGGER.warn(
            "Duplicated Dog Detected! dog_uuid=[" 
            + uuid.toString()
            + "] owner_uuid=["
            + ownerUUID.toString()
            + "]"
        );
        return true;
    }

    private boolean checkRespawnStorageForDuplicate(UUID uuid, UUID ownerUUID) {
        var storage = DogRespawnStorage.get(dog.level());
        if (storage == null)
            return false;
        var data = storage.getData(uuid);
        if (data == null)
            return false;
        var ownerUUID0 = data.getOwnerId();
        if (ownerUUID0 == null)
            return false;

        if (ObjectUtils.notEqual(ownerUUID0, ownerUUID))        
            return false;
        
        return true;
    }

    private boolean checkLocationStorageForDuplicate(UUID uuid, UUID ownerUUID, UUID sessionUUID) {
        var storage = DogLocationStorage.get(dog.level());
        if (storage == null) 
            return false;
        var data = storage.getData(uuid);
        if (data == null)
            return false;
        var ownerUUID0 = data.getOwnerId();
        if (ownerUUID0 == null) 
            return false;
        
        if (ObjectUtils.notEqual(ownerUUID0, ownerUUID))        
            return false;
        
        var correctSessionUUID = data.getSessionUUID();
        if (correctSessionUUID == null)
            return false;
        return ObjectUtils.notEqual(correctSessionUUID, sessionUUID);
    }

    public void writeSessionUUIDToCompound(UUID uuid, CompoundTag tag) {
        if (cachedSessionUUID != null) {
            tag.putUUID("session_uuid", cachedSessionUUID);
            cachedSessionUUID = null;
            return;
        }
        var level = dog.level();
        if (level == null)
            return;
        var storage = DogLocationStorage.get(level);
        if (storage == null) 
            return;
        var data = storage.getData(uuid);
        if (data == null)
            return;
        var sessionUUID = data.getSessionUUID();
        if (sessionUUID == null)
            return;
        tag.putUUID("session_uuid", sessionUUID);
    }

    public void cacheSessionUUID() {
        var uuid = dog.getUUID();
        var level = dog.level();
        if (level == null)
            return;
        var storage = DogLocationStorage.get(level);
        if (storage == null) 
            return;
        var data = storage.getData(uuid);
        if (data == null)
            return;
        var sessionUUID = data.getSessionUUID();
        if (sessionUUID == null)
            return;
        this.cachedSessionUUID = sessionUUID;
    }

    public void checkAndRecorrectOwner(CompoundTag tag, Consumer<UUID> owner_changer) {
        if (!tag.contains("DTN_DupeDetect_UUID", Tag.TAG_COMPOUND))
            return;
        var backupUUIDTag = tag.getCompound("DTN_DupeDetect_UUID");
        var ownerUUID = backupUUIDTag.getUUID("dtn_uuid_owner");
        if (!ObjectUtils.notEqual(ownerUUID, dog.getOwnerUUID()))
            return;
        owner_changer.accept(ownerUUID);
    }
}
