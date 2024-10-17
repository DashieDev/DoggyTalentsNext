package doggytalents;

import doggytalents.common.inventory.recipe.DogBedRecipe;
import doggytalents.common.inventory.recipe.DoubleDyeableRecipe;
import doggytalents.common.lib.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Constants.MOD_ID);

    public static final Supplier<RecipeSerializer<DogBedRecipe>> DOG_BED = register("dog_bed", DogBedRecipe::new);
    public static final Supplier<RecipeSerializer<DoubleDyeableRecipe>> DOUBLE_DYEABLE = register("double_dyeable", DoubleDyeableRecipe::new);
//    public static final Supplier<SpecialRecipeSerializer<DogCollarRecipe>> COLLAR_COLOURING = register("collar_colouring", DogCollarRecipe::new);
//    public static final Supplier<SpecialRecipeSerializer<DogCapeRecipe>> CAPE_COLOURING = register("cape_colouring", DogCapeRecipe::new);

    private static <R extends CraftingRecipe, T extends RecipeSerializer<R>> Supplier<RecipeSerializer<R>> register(final String name, SimpleCraftingRecipeSerializer.Factory<R> factory) {
        return register(name, () -> new SimpleCraftingRecipeSerializer<R>(factory));
    }

    private static <T extends RecipeSerializer<?>> Supplier<T> register(final String name, final Supplier<T> sup) {
        return RECIPE_SERIALIZERS.register(name, sup);
    }
}

