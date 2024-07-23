package doggytalents;

import doggytalents.common.entity.serializers.*;
import doggytalents.common.lib.Constants;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.network.syncher.EntityDataSerializer;

import java.util.function.Supplier;

public class DoggySerializers {

    //public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);

    public static final EntityDataSerializer DOG_VARIANT_SERIALIZER = register2("dog_variant", DogVariantSerializer::new);
    public static final EntityDataSerializer COLLAR_TYPE_SERIALIZER = register2("collar", CollarSerializer::new);
    public static final EntityDataSerializer GENDER_SERIALIZER = register2("gender", GenderSerializer::new);
    public static final EntityDataSerializer MODE_SERIALIZER = register2("mode", ModeSerializer::new);
    public static final EntityDataSerializer DOG_LEVEL_SERIALIZER = register2("dog_level", DogLevelSerializer::new);
    public static final EntityDataSerializer BED_LOC_SERIALIZER = register2("dog_bed_location", BedLocationsSerializer::new);
    public static final EntityDataSerializer INCAP_SYNC_SERIALIZER = register2("incap_sync", IncapacitatedSyncSerializer::new);
    public static final EntityDataSerializer ARTIFACTS_SERIALIZER = register2("doggy_artifacts", DoggyArtifactsSerializer::new);
    public static final EntityDataSerializer DOG_SIZE_SERIALIZER = register2("dog_size", DogSizeSerializer::new);
    public static final EntityDataSerializer DOG_SKIN_DATA_SERIALIZER = register2("dog_skin_data", DogSkinDataSerializer::new);
    public static final EntityDataSerializer DOG_PETTING_STATE = register2("dog_petting_state", PettingStateSerializer::new);

    private static <X extends EntityDataSerializer<?>> EntityDataSerializer register2(final String name, final Supplier<X> factory) {
        return register(name, () -> factory.get());
    }

    private static EntityDataSerializer register(final String name, final Supplier<EntityDataSerializer> sup) {
        return sup.get();
    }
}
