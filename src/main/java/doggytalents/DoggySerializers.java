package doggytalents;

import doggytalents.common.entity.serializers.*;
import doggytalents.common.lib.Constants;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DoggySerializers {

    public static final DeferredRegister<DataSerializerEntry> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.DATA_SERIALIZERS, Constants.MOD_ID);

    public static final RegistryObject<EntityDataSerializer> CLASSICAL_VAR = register2("classical_var", ClassicalVarSerializer::new);
    public static final RegistryObject<DataSerializerEntry> COLLAR_TYPE_SERIALIZER = register2("collar", CollarSerializer::new);
    public static final RegistryObject<DataSerializerEntry> GENDER_SERIALIZER = register2("gender", GenderSerializer::new);
    public static final RegistryObject<DataSerializerEntry> MODE_SERIALIZER = register2("mode", ModeSerializer::new);
    public static final RegistryObject<DataSerializerEntry> DOG_LEVEL_SERIALIZER = register2("dog_level", DogLevelSerializer::new);
    public static final RegistryObject<DataSerializerEntry> BED_LOC_SERIALIZER = register2("dog_bed_location", BedLocationsSerializer::new);
    public static final RegistryObject<DataSerializerEntry> INCAP_SYNC_SERIALIZER = register2("incap_sync", IncapacitatedSyncSerializer::new);
    public static final RegistryObject<DataSerializerEntry> ARTIFACTS_SERIALIZER = register2("doggy_artifacts", DoggyArtifactsSerializer::new);
    public static final RegistryObject<DataSerializerEntry> DOG_SIZE_SERIALIZER = register2("dog_size", DogSizeSerializer::new);
    public static final RegistryObject<DataSerializerEntry> DOG_SKIN_DATA_SERIALIZER = register2("dog_skin_data", DogSkinDataSerializer::new);

    private static <X extends EntityDataSerializer<?>> RegistryObject<DataSerializerEntry> register2(final String name, final Supplier<X> factory) {
        return register(name, () -> new DataSerializerEntry(factory.get()));
    }

    private static RegistryObject<DataSerializerEntry> register(final String name, final Supplier<DataSerializerEntry> sup) {
        return SERIALIZERS.register(name, sup);
    }
}
