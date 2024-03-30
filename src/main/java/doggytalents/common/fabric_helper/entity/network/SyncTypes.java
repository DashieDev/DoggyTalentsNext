package doggytalents.common.fabric_helper.entity.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.google.common.collect.Maps;

import doggytalents.DoggySerializers;
import doggytalents.api.feature.DogLevel;
import doggytalents.api.feature.DogSize;
import doggytalents.api.feature.EnumGender;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.ClassicalVar;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.fabric_helper.entity.DogFabricHelper;
import doggytalents.common.item.DoggyArtifactItem;
import net.minecraft.network.syncher.EntityDataSerializer;

public class SyncTypes {

    public static int COUNT = 7;
    private static Map<Integer, SyncType<?>> ID_TO_TYPE = Maps.newHashMap();
    private static List<SyncType<?>> ALL = new ArrayList<>(COUNT);
    public static SyncType<DogLevel> DOG_LEVEL = register(new SyncType<DogLevel>(0, DoggySerializers.DOG_LEVEL_SERIALIZER, DogFabricHelper::setDogLevel, DogFabricHelper::getDogLevel));
    public static SyncType<EnumGender> DOG_GENDER = register(new SyncType<EnumGender>(1, DoggySerializers.GENDER_SERIALIZER, DogFabricHelper::setDogGender, DogFabricHelper::getDogGender));
    public static SyncType<EnumMode> DOG_MODE = register(new SyncType<EnumMode>(2, DoggySerializers.MODE_SERIALIZER, DogFabricHelper::setDogMode, DogFabricHelper::getDogMode));
    public static SyncType<IncapacitatedSyncState> INCAP_SYNC_STATE = register(new SyncType<IncapacitatedSyncState>(3, DoggySerializers.INCAP_SYNC_SERIALIZER, DogFabricHelper::setIncapSyncState, DogFabricHelper::getIncapSyncState));
    public static SyncType<List<DoggyArtifactItem>> ARTIFACTS = register(new SyncType<List<DoggyArtifactItem>>(4, DoggySerializers.ARTIFACTS_SERIALIZER, DogFabricHelper::setArtifacts, DogFabricHelper::getArtifacts));
    public static SyncType<DogSize> DOG_SIZE = register(new SyncType<DogSize>(5, DoggySerializers.DOG_SIZE_SERIALIZER, DogFabricHelper::setDogSize, DogFabricHelper::getDogSize));
    public static SyncType<DogSkinData> DOG_SKIN = register(new SyncType<DogSkinData>(6, DoggySerializers.DOG_SKIN_DATA_SERIALIZER, DogFabricHelper::setDogSkin, DogFabricHelper::getDogSkin));
    public static SyncType<ClassicalVar> CLASSICAL_VAR = register(new SyncType<ClassicalVar>(7, DoggySerializers.CLASSICAL_VAR, DogFabricHelper::setClassicalVar, DogFabricHelper::getClassicalVar));

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
