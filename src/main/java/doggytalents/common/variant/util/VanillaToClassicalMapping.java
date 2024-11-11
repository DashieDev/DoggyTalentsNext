package doggytalents.common.variant.util;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

import doggytalents.DogVariants;
import doggytalents.common.variant.DogVariant;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.WolfVariants;

public class VanillaToClassicalMapping {
   
    private static final Map<ResourceKey<WolfVariant>, Supplier<DogVariant>> vanillToClassical =
        new ImmutableMap.Builder<ResourceKey<WolfVariant>, Supplier<DogVariant>>()
        .put(WolfVariants.PALE, DogVariants.PALE)
        .put(WolfVariants.CHESTNUT, DogVariants.CHESTNUT)
        .put(WolfVariants.STRIPED, DogVariants.STRIPED)
        .put(WolfVariants.WOODS, DogVariants.WOOD)
        .put(WolfVariants.RUSTY, DogVariants.RUSTY)
        .put(WolfVariants.BLACK, DogVariants.BLACK)
        .put(WolfVariants.SNOWY, DogVariants.SNOWY)
        .put(WolfVariants.ASHEN, DogVariants.ASHEN)
        .put(WolfVariants.SPOTTED, DogVariants.SPOTTED)
        .build();

    public static Supplier<DogVariant> fromVanilla(ResourceKey<WolfVariant> variant) {
        return vanillToClassical.getOrDefault(variant, DogVariantUtil::getDefault);
    }

}
