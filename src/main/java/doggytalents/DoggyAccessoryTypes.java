package doggytalents;

import doggytalents.api.registry.AccessoryType;
import doggytalents.common.lib.Constants;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DoggyAccessoryTypes {

    public static final DeferredRegister<AccessoryType> ACCESSORY_TYPES = DeferredRegister.create(DoggyRegistries.Keys.ACCESSORY_TYPE_REGISTRY, Constants.MOD_ID);

    public static final Supplier<AccessoryType> COLLAR = register("collar");
    public static final Supplier<AccessoryType> BOWTIE = register("bowtie");
    public static final Supplier<AccessoryType> CLOTHING = register("clothing");
    public static final Supplier<AccessoryType> GLASSES = register("glasses");
    public static final Supplier<AccessoryType> CONTACTS = register("contacts");
    public static final Supplier<AccessoryType> BAND = register("band");
    public static final Supplier<AccessoryType> HEAD = register("head");
    public static final Supplier<AccessoryType> FEET = register("feet");
    public static final Supplier<AccessoryType> TAIL = register("tail");
    public static final Supplier<AccessoryType> WINGS = register("wings");

    private static Supplier<AccessoryType> register(final String name) {
        return register(name, () -> new AccessoryType());
    }

    private static <T extends AccessoryType> Supplier<T> register(final String name, final Supplier<T> sup) {
        return ACCESSORY_TYPES.register(name, sup);
    }
}
