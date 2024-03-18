package doggytalents;

import java.util.ArrayList;
import java.util.function.Supplier;

import doggytalents.forge_imitate.potion.BrewingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;

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

    public static void registerAll() {
        // for (var x : RECIPES) {
        //     var y = x.get();
        //     PotionBrewing.addContainerRecipe(
        //         y.inputBase.getItems()[0].getItem(), 
        //         y.inputTop.getItems()[0].getItem(), 
        //         y.output.getItem());
        // }
    }

}
