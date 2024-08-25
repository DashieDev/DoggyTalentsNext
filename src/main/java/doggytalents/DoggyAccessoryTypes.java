package doggytalents;

import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.AccessoryType;
import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;

import java.util.function.Supplier;

public class DoggyAccessoryTypes {

    public static final DeferredRegister<AccessoryType> ACCESSORY_TYPES = DeferredRegister.create(DoggyTalentsAPI.ACCESSORY_TYPE, Constants.MOD_ID);

    public static final Supplier<AccessoryType> COLLAR = register("collar");
    public static final Supplier<AccessoryType> BOWTIE = register("bowtie");
    public static final Supplier<AccessoryType> CLOTHING = register("clothing");
    public static final Supplier<AccessoryType> GLASSES = register("glasses");
    public static final Supplier<AccessoryType> CONTACTS = register("contacts");
    public static final Supplier<AccessoryType> RADIO_COLLAR_LEGACY = register("radio_collar_legacy");
    public static final Supplier<AccessoryType> SCARF = register("scarf");
    public static final Supplier<AccessoryType> HEAD = register("head");
    public static final Supplier<AccessoryType> FEET = register("feet");
    public static final Supplier<AccessoryType> TAIL = register("tail");
    public static final Supplier<AccessoryType> WINGS = register("wings");
    public static final Supplier<AccessoryType> BODY = register("body");
    public static final Supplier<AccessoryType> UPPER_BODY = register("upper_body");

    private static RegistryObject<AccessoryType> register(final String name) {
        return register(name, () -> new AccessoryType());
    }

    private static <T extends AccessoryType> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ACCESSORY_TYPES.register(name, sup);
    }
}
