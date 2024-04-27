package doggytalents;

import java.util.ArrayList;
import java.util.function.Supplier;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class DoggyBrewingRecipes {
    
    private static ArrayList<Supplier<BrewingRecipe>> RECIPES = new ArrayList<>();
    public static Supplier<BrewingRecipe> SAKE_BREW = register(
        () -> new BrewingRecipe(
            Ingredient.of(Items.POTION), 
            Ingredient.of(DoggyItems.KOJI.get()), 
            new ItemStack(DoggyItems.SAKE.get()))
    );
        
    private static Supplier<BrewingRecipe> register(Supplier<BrewingRecipe> recipe) {
        RECIPES.add(recipe);
        return recipe;
    }

    public static void onRegisterEvent(RegisterBrewingRecipesEvent event) {
        for (var x : RECIPES) {
            event.getBuilder().addRecipe(x.get());
        }
    }

}
