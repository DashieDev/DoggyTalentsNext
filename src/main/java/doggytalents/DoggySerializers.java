package doggytalents;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.feature.DogSize;
import doggytalents.api.feature.EnumGender;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.DogPettingManager.DogPettingState;
import doggytalents.common.entity.serializers.*;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.lib.Constants;
import doggytalents.common.variant.DogVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializer;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DoggySerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);

    public static final EntityDataSerializer<DogVariant> DOG_VARIANT_SERIALIZER = register("dog_variant", DogVariantSerializer::new);
    public static final EntityDataSerializer<EnumGender> GENDER_SERIALIZER = register("gender", GenderSerializer::new);
    public static final EntityDataSerializer<EnumMode> MODE_SERIALIZER = register("mode", ModeSerializer::new);
    public static final EntityDataSerializer<DogLevel> DOG_LEVEL_SERIALIZER = register("dog_level", DogLevelSerializer::new);
    public static final EntityDataSerializer<DimensionDependantArg<Optional<BlockPos>>> BED_LOC_SERIALIZER = register("dog_bed_location", BedLocationsSerializer::new);
    public static final EntityDataSerializer<IncapacitatedSyncState> INCAP_SYNC_SERIALIZER = register("incap_sync", IncapacitatedSyncSerializer::new);
    public static final EntityDataSerializer<List<DoggyArtifactItem>> ARTIFACTS_SERIALIZER = register("doggy_artifacts", DoggyArtifactsSerializer::new);
    public static final EntityDataSerializer<DogSize> DOG_SIZE_SERIALIZER = register("dog_size", DogSizeSerializer::new);
    public static final EntityDataSerializer<DogSkinData> DOG_SKIN_DATA_SERIALIZER = register("dog_skin_data", DogSkinDataSerializer::new);
    public static final EntityDataSerializer<DogPettingState> DOG_PETTING_STATE = register("dog_petting_state", PettingStateSerializer::new);

    private static <T> EntityDataSerializer<T> register(final String name, final Supplier<EntityDataSerializer<T>> sup) {
        final var captured_value = sup.get();
        SERIALIZERS.register(name, () -> captured_value);
        return captured_value;
    }
}
