package doggytalents;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.feature.DogSize;
import doggytalents.api.feature.DogGender;
import doggytalents.api.feature.DogMode;
import doggytalents.common.entity.DogIncapacitatedMananger.IncapacitatedSyncState;
import doggytalents.common.entity.DogPettingManager.DogPettingState;
import doggytalents.common.entity.anim.DogAnimationManager.DogAnimDebugState;
import doggytalents.common.entity.DogSleepOnManager.DogSleepOnState;
import doggytalents.common.entity.serializers.*;
import doggytalents.common.entity.texture.DogSkinData;
import doggytalents.common.item.DoggyArtifactItem;
import doggytalents.common.lib.Constants;
import doggytalents.common.variant.DogVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataSerializer;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DoggySerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);

    public static final EntityDataSerializer<DogVariant> DOG_VARIANT_SERIALIZER = register("dog_variant", DogVariantSerializer::new);
    public static final EntityDataSerializer<DogGender> GENDER_SERIALIZER = register("gender", GenderSerializer::new);
    public static final EntityDataSerializer<DogMode> MODE_SERIALIZER = register("mode", ModeSerializer::new);
    public static final EntityDataSerializer<DogLevel> DOG_LEVEL_SERIALIZER = register("dog_level", DogLevelSerializer::new);
    public static final EntityDataSerializer<Dimension2BlockPosMap> DIM2BLOCKPOS_SERIALIZER = register("dim2blockpos", Dim2BlockPosSerializer::new);
    public static final EntityDataSerializer<IncapacitatedSyncState> INCAP_SYNC_SERIALIZER = register("incap_sync", IncapacitatedSyncSerializer::new);
    public static final EntityDataSerializer<List<DoggyArtifactItem>> ARTIFACTS_SERIALIZER = register("doggy_artifacts", DoggyArtifactsSerializer::new);
    public static final EntityDataSerializer<DogSize> DOG_SIZE_SERIALIZER = register("dog_size", DogSizeSerializer::new);
    public static final EntityDataSerializer<DogSkinData> DOG_SKIN_DATA_SERIALIZER = register("dog_skin_data", DogSkinDataSerializer::new);
    public static final EntityDataSerializer<DogPettingState> DOG_PETTING_STATE = register("dog_petting_state", PettingStateSerializer::new);
    public static final EntityDataSerializer<DogAnimDebugState> DOG_ANIM_DEBUG_STATE = register("dog_anim_debug", AnimDebugStateSerializer::new);
    public static final EntityDataSerializer<DogSleepOnState> DOG_SLEEP_ON_STATE = register("dog_sleep_on_state", SleepOnStateSerializer::new);

    private static <T> EntityDataSerializer<T> register(final String name, final Supplier<EntityDataSerializer<T>> sup) {
        final var captured_value = sup.get();
        SERIALIZERS.register(name, () -> captured_value);
        return captured_value;
    }
}
