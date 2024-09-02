package doggytalents;

import java.util.ArrayList;
import java.util.function.Supplier;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;

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

    public static void onRegisterEvent(BrewingRecipeRegisterEvent event) {
        for (var x : RECIPES) {
            event.addRecipe(x.get());
        }
    }

}
