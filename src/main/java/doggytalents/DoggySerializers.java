package doggytalents;

import doggytalents.common.entity.serializers.*;
import doggytalents.common.lib.Constants;
import net.minecraft.network.syncher.EntityDataSerializer;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DoggySerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);

    public static final Supplier<EntityDataSerializer> CLASSICAL_VAR = register2("classical_var", ClassicalVarSerializer::new);
    public static final Supplier<EntityDataSerializer> COLLAR_TYPE_SERIALIZER = register2("collar", CollarSerializer::new);
    public static final Supplier<EntityDataSerializer> GENDER_SERIALIZER = register2("gender", GenderSerializer::new);
    public static final Supplier<EntityDataSerializer> MODE_SERIALIZER = register2("mode", ModeSerializer::new);
    public static final Supplier<EntityDataSerializer> DOG_LEVEL_SERIALIZER = register2("dog_level", DogLevelSerializer::new);
    public static final Supplier<EntityDataSerializer> BED_LOC_SERIALIZER = register2("dog_bed_location", BedLocationsSerializer::new);
    public static final Supplier<EntityDataSerializer> INCAP_SYNC_SERIALIZER = register2("incap_sync", IncapacitatedSyncSerializer::new);
    public static final Supplier<EntityDataSerializer> ARTIFACTS_SERIALIZER = register2("doggy_artifacts", DoggyArtifactsSerializer::new);
    public static final Supplier<EntityDataSerializer> DOG_SIZE_SERIALIZER = register2("dog_size", DogSizeSerializer::new);
    public static final Supplier<EntityDataSerializer> DOG_SKIN_DATA_SERIALIZER = register2("dog_skin_data", DogSkinDataSerializer::new);

    private static <X extends EntityDataSerializer<?>> Supplier<EntityDataSerializer> register2(final String name, final Supplier<X> factory) {
        return register(name, () -> factory.get());
    }

    private static Supplier<EntityDataSerializer> register(final String name, final Supplier<EntityDataSerializer> sup) {
        return SERIALIZERS.register(name, sup);
    }
}
