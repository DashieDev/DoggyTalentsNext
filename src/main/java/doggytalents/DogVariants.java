package doggytalents;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import doggytalents.common.lib.Constants;
import doggytalents.common.util.Util;
import doggytalents.common.variant.CherryWolfVariant;
import doggytalents.common.variant.DogVariant;
import doggytalents.common.variant.MoltenWolfVariant;
import net.minecraftforge.registries.DeferredRegister;

public class DogVariants {
    
    public static final DeferredRegister<DogVariant> DOG_VARIANT = DeferredRegister.create(DoggyRegistries.Keys.DOG_VARIANT, Constants.MOD_ID);
    public static final DeferredRegister<DogVariant> DOG_VARIANT_VANILLA = DeferredRegister.create(DoggyRegistries.Keys.DOG_VARIANT, Constants.VANILLA_ID);
    
    //Minecraft's
    public static final Supplier<DogVariant> PALE = registerDefault();
    public static final Supplier<DogVariant> CHESTNUT = registerVanilla("chestnut", 0xff9a8483);
    public static final Supplier<DogVariant> STRIPED = registerVanilla("striped", 0xffc9af80);
    public static final Supplier<DogVariant> WOOD = registerVanilla("woods", 0xff76583c);
    public static final Supplier<DogVariant> RUSTY = registerVanilla("rusty", 0xffde8338);
    public static final Supplier<DogVariant> BLACK = registerVanilla("black", 0xff292629);
    public static final Supplier<DogVariant> SNOWY = registerVanilla("snowy", 0xffb8877e);
    public static final Supplier<DogVariant> ASHEN = registerVanilla("ashen", 0xff928991);
    public static final Supplier<DogVariant> SPOTTED = registerVanilla("spotted", 0xffc8bc30);

    public static final Supplier<DogVariant> CHERRY = register("cherry", CherryWolfVariant::new);
    public static final Supplier<DogVariant> LEMONY_LIME = register("lemony_lime", 0xffa8c882);
    public static final Supplier<DogVariant> HIMALAYAN_SALT = register("himalayan_salt", 0xffb55c63);
    public static final Supplier<DogVariant> BAMBOO = register("bamboo", 0xff629122);
    public static final Supplier<DogVariant> CRIMSON = register("crimson", 0xff822f33);
    public static final Supplier<DogVariant> WARPED = register("warped", 0xff235c5d);
    public static final Supplier<DogVariant> BIRCH = register("birch", 0xffcec192);
    public static final Supplier<DogVariant> PISTACHIO = register("pistachio", 0xffa3a25b);
    public static final Supplier<DogVariant> GUACAMOLE = register("guacamole", 0xff9ab123);
    public static final Supplier<DogVariant> VSCODE = register("vscode", 0xff0078d4);
    public static final Supplier<DogVariant> MOLTEN = register("molten", MoltenWolfVariant::new);
    public static final Supplier<DogVariant> YUZU = register("yuzu", 0xffe3b401);

    private static Supplier<DogVariant> register(String name, Function<String, DogVariant> variant_creator) {
        var captured_variant = variant_creator.apply(name);
        return DOG_VARIANT.register(name, () -> captured_variant);
    }

    private static Supplier<DogVariant> register(String name, int color) {
        return register(name, p -> {p.guiColor(color);});
    }

    private static Supplier<DogVariant> register(String name, Consumer<DogVariant.Props> props_consumer) {
        var props = DogVariant.props(name);
        props.icon(Util.getResource("textures/item/doggy_charm.png"));
        props_consumer.accept(props);
        final var captured_variant = new DogVariant(props);
        return DOG_VARIANT.register(name, () -> captured_variant);
    }

    private static Supplier<DogVariant> registerVanilla(String name, int color) {
        var props = DogVariant.propsVanilla(name);
        props.icon(Util.getResource("textures/entity/dog/classical_icon/" + name + ".png"));
        props.guiColor(color);
        final var captured_variant = new DogVariant(props);
        return DOG_VARIANT_VANILLA.register(name, () -> captured_variant);
    }

    private static Supplier<DogVariant> registerDefault() {
        return DOG_VARIANT_VANILLA.register("pale", () -> DogVariant.PALE);
    }

}
