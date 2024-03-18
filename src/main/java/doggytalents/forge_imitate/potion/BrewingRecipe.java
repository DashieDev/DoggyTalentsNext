package doggytalents.forge_imitate.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BrewingRecipe {
    
    public final Ingredient inputBase;
    public final Ingredient inputTop;
    public final ItemStack output;

    public BrewingRecipe(Ingredient in1, Ingredient in2, ItemStack out) {
        this.inputBase = in1;
        this.inputTop = in2;
        this.output = out;
    }

}
