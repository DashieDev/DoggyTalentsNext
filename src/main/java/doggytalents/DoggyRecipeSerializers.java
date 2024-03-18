package doggytalents;

import doggytalents.common.inventory.recipe.DogBedRecipe;
import doggytalents.common.inventory.recipe.DoubleDyableRecipe;
import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.registry.DeferredRegister;
import doggytalents.forge_imitate.registry.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import java.util.function.Function;
import java.util.function.Supplier;

public class DoggyRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(() -> BuiltInRegistries.RECIPE_SERIALIZER, Constants.MOD_ID);

    public static final RegistryObject<RecipeSerializer<DogBedRecipe>> DOG_BED = register("dog_bed", DogBedRecipe::new);
    public static final RegistryObject<RecipeSerializer<DoubleDyableRecipe>> DOUBLE_DYABLE = register("double_dyable", DoubleDyableRecipe::new);
//    public static final RegistryObject<SpecialRecipeSerializer<DogCollarRecipe>> COLLAR_COLOURING = register("collar_colouring", DogCollarRecipe::new);
//    public static final RegistryObject<SpecialRecipeSerializer<DogCapeRecipe>> CAPE_COLOURING = register("cape_colouring", DogCapeRecipe::new);

    private static <R extends CraftingRecipe, T extends RecipeSerializer<R>> RegistryObject<RecipeSerializer<R>> register(final String name, SimpleCraftingRecipeSerializer.Factory<R> factory) {
        return register(name, () -> new SimpleCraftingRecipeSerializer<R>(factory));
    }

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return RECIPE_SERIALIZERS.register(name, sup);
    }
}

