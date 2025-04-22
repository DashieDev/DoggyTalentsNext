package doggytalents.common.fabric_helper.entity.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.common.collect.Maps;

import doggytalents.DoggySerializers;
import doggytalents.api.feature.DogGender;
import doggytalents.api.feature.DogLevel;
import doggytalents.api.feature.DogMode;
import doggytalents.api.feature.DogSize;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.DogPettingManager.DogPettingState;
import doggytalents.common.entity.DogSleepOnManager.DogSleepOnState;
import doggytalents.common.entity.anim.DogAnimationManager.DogAnimDebugState;
import doggytalents.common.entity.serializers.Dimension2BlockPosMap;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.fabric_helper.entity.DogFabricHelper;
import doggytalents.common.fabric_helper.entity.network.SyncTypes.SyncType;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.variant.DogVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializer;

public class SyncTypes {

    public static int COUNT = 12;
    private static Map<Integer, SyncType<?>> ID_TO_TYPE = Maps.newHashMap();
    private static List<SyncType<?>> ALL = new ArrayList<>(COUNT);
    public static SyncType<DogLevel> DOG_LEVEL = register(new SyncType<DogLevel>(0, DoggySerializers.DOG_LEVEL_SERIALIZER, DogFabricHelper::setDogLevel, DogFabricHelper::getDogLevel));
    public static SyncType<DogGender> DOG_GENDER = register(new SyncType<DogGender>(1, DoggySerializers.GENDER_SERIALIZER, DogFabricHelper::setDogGender, DogFabricHelper::getDogGender));
    public static SyncType<DogMode> DOG_MODE = register(new SyncType<DogMode>(2, DoggySerializers.MODE_SERIALIZER, DogFabricHelper::setDogMode, DogFabricHelper::getDogMode));
    public static SyncType<IncapacitatedSyncState> INCAP_SYNC_STATE = register(new SyncType<IncapacitatedSyncState>(3, DoggySerializers.INCAP_SYNC_SERIALIZER, DogFabricHelper::setIncapSyncState, DogFabricHelper::getIncapSyncState));
    public static SyncType<List<DoggyArtifactItem>> ARTIFACTS = register(new SyncType<List<DoggyArtifactItem>>(4, DoggySerializers.ARTIFACTS_SERIALIZER, DogFabricHelper::setArtifacts, DogFabricHelper::getArtifacts));
    public static SyncType<DogSize> DOG_SIZE = register(new SyncType<DogSize>(5, DoggySerializers.DOG_SIZE_SERIALIZER, DogFabricHelper::setDogSize, DogFabricHelper::getDogSize));
    public static SyncType<DogSkinData> DOG_SKIN = register(new SyncType<DogSkinData>(6, DoggySerializers.DOG_SKIN_DATA_SERIALIZER, DogFabricHelper::setDogSkin, DogFabricHelper::getDogSkin));
    public static SyncType<DogVariant> DOG_VARIANT = register(new SyncType<DogVariant>(7, DoggySerializers.DOG_VARIANT_SERIALIZER, DogFabricHelper::setDogVariant, DogFabricHelper::getDogVariant));
    public static SyncType<DogPettingState> DOG_PETTING_STATE = register(new SyncType<DogPettingState>(8, DoggySerializers.DOG_PETTING_STATE, DogFabricHelper::setDogPettingState, DogFabricHelper::getDogPettingState));
    public static SyncType<DogAnimDebugState> DOG_ANIM_DEBUG_STATE = register(new SyncType<DogAnimDebugState>(9, DoggySerializers.DOG_ANIM_DEBUG_STATE, DogFabricHelper::setDogAnimDebugState, DogFabricHelper::getDogAnimDebugState));
    public static SyncType<DogSleepOnState> DOG_SLEEP_ON_STATE = register(new SyncType<DogSleepOnState>(10, DoggySerializers.DOG_SLEEP_ON_STATE, DogFabricHelper::setDogSleepOnState, DogFabricHelper::getDogSleepOnState));
    public static SyncType<Dimension2BlockPosMap> BOWL_POS = register(new SyncType<Dimension2BlockPosMap>(11, DoggySerializers.DIM2BLOCKPOS_SERIALIZER, DogFabricHelper::setBowlPos, DogFabricHelper::getBowlPos));

    public static void init() {}

    private static <T> SyncType<T> register(SyncType<T> type) {
        ID_TO_TYPE.put(type.getId(), type);
        ALL.add(type);
        return type;
    }

    public static SyncType<?> fromId(int id) {
        return ID_TO_TYPE.get(id);
    }

    public static List<SyncType<?>> getAll() {
        return ALL;
    }

    public static class SyncType<T> {

        private final int id;
        private final EntityDataSerializer<T> serializer;
        private final BiConsumer<DogFabricHelper, T> clientSetter;
        private final Function<DogFabricHelper, T> getter;

        private SyncType(int id, EntityDataSerializer<T> serializer,
            BiConsumer<DogFabricHelper, T> setter,
            Function<DogFabricHelper, T> getter) {
            this.id = id;
            this.serializer = serializer;
            this.clientSetter = setter;
            this.getter = getter;
        }

        public int getId() {
            return this.id; 
        }

        public EntityDataSerializer<T> getSerializer() {
            return this.serializer;
        }

        public BiConsumer<DogFabricHelper, T> getClientSetter() {
            return this.clientSetter;
        }

        public Function<DogFabricHelper, T> getGetter() {
            return this.getter;
        }

    }

}
