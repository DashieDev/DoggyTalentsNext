package doggytalents;

import doggytalents.common.inventory.recipe.DogBedRecipe;
import doggytalents.common.inventory.recipe.DoubleDyableRecipe;
import doggytalents.common.lib.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DoggyRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Constants.MOD_ID);

    public static final Supplier<RecipeSerializer<DogBedRecipe>> DOG_BED = RECIPE_SERIALIZERS.register("dog_bed", () -> DogBedRecipe.SERIALIZER);
    public static final Supplier<RecipeSerializer<DoubleDyableRecipe>> DOUBLE_DYABLE = RECIPE_SERIALIZERS.register("double_dyable", () -> DoubleDyableRecipe.SERIALIZER);

    private static <T extends RecipeSerializer<?>> Supplier<T> register(final String name, final Supplier<T> sup) {
        return RECIPE_SERIALIZERS.register(name, sup);
    }
}

