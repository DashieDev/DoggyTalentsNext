package doggytalents.common.variant.util;

import java.util.List;
import java.util.stream.Collectors;

import doggytalents.DogVariants;
import doggytalents.DoggyRegistries;
import doggytalents.common.util.Util;
import doggytalents.common.variant.DogVariant;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class DogVariantUtil {
    
    public static DogVariant getDefault() {
        return DogVariant.PALE;
    }

    public static DogVariant fromSaveString(String string) {
        ResourceLocation id = null;
        try {
            id = Util.parseResource(string);
        } catch (Exception e) {
            
        }
        if (id == null)
            return getDefault();
        var variant = DoggyRegistries.DOG_VARIANT.get().get(id);
        if (variant == null)
            return getDefault();
        return variant;
    }

    public static String toSaveString(DogVariant variant) {
        return variant.id().toString();
    }

    public static List<DogVariant> getAll() {
        var variant_reg = DoggyRegistries.DOG_VARIANT.get();
        var entries = variant_reg.entrySet().stream()
            .map(x -> x.getValue())
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

    // public static DogVariant fromVanila(ResourceKey<WolfVariant> variant) {
    //     return VanillaToClassicalMapping.fromVanilla(variant).get();
    // }

}
