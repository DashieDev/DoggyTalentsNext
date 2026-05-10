package doggytalents.common.variant.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import doggytalents.DogVariants;
import doggytalents.DoggyRegistries;
import doggytalents.common.variant.DogVariant;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.wolf.WolfVariant;

public class DogVariantUtil {
    
    public static DogVariant getDefault() {
        return DogVariant.PALE;
    }

    public static DogVariant getMissing() {
        return DogVariant.MISSING;
    }

    public static DogVariant fromSaveString(String string) {
        return fromSaveString(string, id -> {});
    }

    public static DogVariant fromSaveString(String string, Consumer<Identifier> missingSetter) {
        Identifier id = null;
        try {
            id = Identifier.parse(string);
        } catch (Exception e) {
            
        }
        if (id == null)
            return getDefault();
        
        var variant = DoggyRegistries.DOG_VARIANT.get().get(id)
            .map(net.minecraft.core.Holder.Reference::value).orElse(null);
        if (variant == null)
            return getDefault();

        boolean handle_missing =
            variant == getMissing() && !id.equals(getMissing().id());
        if (handle_missing) {
            missingSetter.accept(id);
            return getMissing();
        }

        if (variant == getMissing())
            variant = getDefault();
        return variant;
    }

    public static String toSaveString(DogVariant variant) {
        return toSaveString(variant, () -> Optional.empty());
    }

    public static String toSaveString(DogVariant variant, Supplier<Optional<Identifier>> missingGetter) {
        if (variant == null)
            variant = getDefault();
        if (variant == getMissing()) {
            var missing = missingGetter.get();
            if (missing.isPresent()) {
                return missing.get().toString();
            } else {
                return getDefault().id().toString();
            }
        }
        return variant.id().toString();
    }

    public static List<DogVariant> getAll() {
        var variant_reg = DoggyRegistries.DOG_VARIANT.get();
        var entries = variant_reg.stream()
            .filter(x -> x != getMissing())
            .collect(Collectors.toList());
        return entries;
    }

    public static List<DogVariant> getAllWithMissing() {
        var variant_reg = DoggyRegistries.DOG_VARIANT.get();
        var entries = variant_reg.stream()
            .collect(Collectors.toList());
        return entries;
    }

    public static DogVariant getRandom(RandomSource random) {
        var entries = getAll();
        if (entries.isEmpty())
            return getDefault();
        var r = random.nextInt(entries.size());
        return entries.get(r);
    }

    public static DogVariant cycle(DogVariant current) {
        var entries = getAll();
        if (entries.isEmpty())
            return getDefault();
        var current_indx = entries.indexOf(current); 
        var next_indx = (current_indx + 1) % entries.size();
        var next_variant = entries.get(next_indx);
        return next_variant;
    }

    public static DogVariant fromVanila(ResourceKey<WolfVariant> variant) {
        return VanillaToClassicalMapping.fromVanilla(variant).get();
    }

}
