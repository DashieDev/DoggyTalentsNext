package doggytalents.common.entity;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import doggytalents.DoggyEntityTypes;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.lib.Constants;
import doggytalents.common.storage.DogLocationData;
import doggytalents.common.storage.DogLocationStorage;
import doggytalents.common.storage.DogRespawnStorage;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class DogDuplicationDetection {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dupeDetect");
    public static final String DUPE_DETECT_TAG_ID = "DTN_DupeDetect";

    private final Dog dog;

    private DetectDuplicateContext detectDuplicateContext = DetectDuplicateContext.EMPTY;
    private UUID sessionUUID = null;

    public DogDuplicationDetection(Dog dog) {
        this.dog = dog;
    }

    public static boolean isEffective(Level level) {
        if (level.isClientSide())
            return false;
        return !ConfigHandler.SERVER.TRUST_THIRD_PARTY_STORAGE.get();
    }

    public static void beforeEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!isEffective(event.getLevel()))
            return;
        if (event.loadedFromDisk())
            return;
        var entity = event.getEntity();
        if (entity.getType() != DoggyEntityTypes.DOG.get())
            return;
        if (!(entity instanceof Dog dog))
            return;
        var detector = dog.getDogDuplicationDetection();
        if (!detector.isDuplicate())
            return;
        event.setCanceled(true);
    }

    public static void afterDogJoinLevel(Dog dog, DogLocationData data) {
        if (!isEffective(dog.level()))
            return;
        var detector = dog.getDogDuplicationDetection();
        detector.detectDuplicateContext = DetectDuplicateContext.EMPTY;
        detector.accquireSessionUUID(data);
    }

    public static void onSessionUUIDUpdate(Dog dog, UUID sessionUUID) {
        if (!isEffective(dog.level()))
            return;
        dog.getDogDuplicationDetection().sessionUUID = sessionUUID;
    }

    public static void onAboutToRespawn(Dog dog) {
        if (!isEffective(dog.level()))
            return;
        var detector = dog.getDogDuplicationDetection();
        detector.detectDuplicateContext = DetectDuplicateContext.EMPTY;
    }

    public void load(CompoundTag compound, Consumer<UUID> owner_setter) {
        if (!isEffective(dog.level()))
            return;
        if (!compound.contains(DUPE_DETECT_TAG_ID))
            return;
        var tag = compound.getCompoundOrEmpty(DUPE_DETECT_TAG_ID);
        this.detectDuplicateContext = DetectDuplicateContext.load(tag);
        this.checkAndRecorrectOwner(owner_setter);
    }

    private void checkAndRecorrectOwner(Consumer<UUID> owner_setter) {
        if (this.detectDuplicateContext.isEmpty())
            return;
        var ctx = this.detectDuplicateContext;
        var ownerUUID = ctx.ownerUUID();

        if (ObjectUtils.notEqual(ownerUUID, dog.getOwnerUUID()))
            owner_setter.accept(ownerUUID);
    }

    public void save(CompoundTag compound) {
        if (!isEffective(dog.level()))
            return;
        //In case Dog failed to accquire session_uuid (when addedToLevel is not called.)
        if (!this.detectDuplicateContext.isEmpty())
            return;
        var ctx = DetectDuplicateContext.of(dog.getUUID(), dog.getOwnerUUID(), this.sessionUUID);
        if (ctx.isEmpty())
            return;
        Optional<Tag> result = Optional.empty();
        try {
            result = ctx.save().result();
        } catch (Exception e) {

        }
        result.ifPresent(result_tag -> {
            compound.put(DUPE_DETECT_TAG_ID, result_tag);
        });
    }

    private boolean isDuplicate() {
        if (this.detectDuplicateContext.isEmpty())
            return false;
        
        if (ConfigHandler.SERVER.DISABLE_PRESERVE_UUID.get())
            return false;
        if (ConfigHandler.SERVER.TRUST_THIRD_PARTY_STORAGE.get())
            return false;

        var ctx = this.detectDuplicateContext;
        var uuid_self = ctx.selfUUID();
        var uuid_owner = ctx.ownerUUID();
        var uuid_session = ctx.sessionUUID().orElse(null);

        boolean isDuplicate = false;
        
        if (!isDuplicate && checkRespawnStorageForDuplicate(uuid_self, uuid_owner))
            isDuplicate = true;
        if (!isDuplicate && checkLocationStorageForDuplicate(uuid_self, uuid_owner, uuid_session))
            isDuplicate = true;
        if (!isDuplicate)
            return false;

        if (ConfigHandler.SERVER.THIRD_PARTY_STORE_WARN.get()) {
            LOGGER.error(
                "Dog [ uuid = " + uuid_self + " owner_uuid = " + uuid_owner + " ] "
                + "has been restored from third-party storage which may leads to duplications. Please restore this Dog via its Dog Bed, by using a Totem of Undying on an Unlinked Dog Bed or via [ /dog revive ] instead. To allow the Dog to be restored from third-party storage, set trust_third_party_storage=true. To disable this message, set third_party_store_warn=false"
            );
        }
        
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

    private void accquireSessionUUID(DogLocationData data) {
        if (data == null)
            return;
        var sessionUUID = data.getSessionUUID();
        this.sessionUUID = sessionUUID;
    }

    public static class DetectDuplicateContext {

        public static final DetectDuplicateContext EMPTY = 
            new DetectDuplicateContext(null, null, Optional.empty());
        public static DetectDuplicateContext of(UUID selfUUID, UUID ownerUUID, Optional<UUID> sessionUUID) {
            if (selfUUID == null || ownerUUID == null)
                return EMPTY;
            return new DetectDuplicateContext(selfUUID, ownerUUID, sessionUUID);
        }

        public static DetectDuplicateContext of(UUID selfUUID, UUID ownerUUID, UUID sessionUUID) {
            return of(selfUUID, ownerUUID, Optional.ofNullable(sessionUUID));
        }

        private final UUID selfUUID, ownerUUID;
        private final Optional<UUID> sessionUUID;

        private DetectDuplicateContext(UUID selfUUID, UUID ownerUUID, Optional<UUID> sessionUUID) {
            this.selfUUID = selfUUID;
            this.ownerUUID = ownerUUID;
            this.sessionUUID = sessionUUID;
        }

        public UUID selfUUID() { return this.selfUUID; }
        public UUID ownerUUID() { return this.ownerUUID; }
        public Optional<UUID> sessionUUID() { return this.sessionUUID; }
        public boolean isEmpty() { return this == EMPTY; }

        public DataResult<Tag> save() {
            if (this.isEmpty())
                return DataResult.error(() -> "Empty!");
            return CTX_CODEC.encodeStart(NbtOps.INSTANCE, this);
        }

        public static DetectDuplicateContext load(Tag tag) {
            var ret = EMPTY;
            try {
                ret = CTX_CODEC.decode(NbtOps.INSTANCE, tag).result()
                    .map(pair -> pair.getFirst()).orElse(EMPTY);
            } catch (Exception e) {

            }
            return ret;
        }
    }

    public static Codec<DetectDuplicateContext> CTX_CODEC = RecordCodecBuilder.create(
        builder -> builder.group(
            UUIDUtil.CODEC.fieldOf("uuid_self").forGetter(DetectDuplicateContext::selfUUID),
            UUIDUtil.CODEC.fieldOf("uuid_owner").forGetter(DetectDuplicateContext::ownerUUID),
            UUIDUtil.CODEC.optionalFieldOf("uuid_session").forGetter(DetectDuplicateContext::sessionUUID)
        ).apply(builder, DetectDuplicateContext::of)
    );
}
