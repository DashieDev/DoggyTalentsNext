package doggytalents.common.data.fabric_data;

import java.util.concurrent.CompletableFuture;

import doggytalents.DoggyItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

public class FabricDTRecipeProvider extends FabricRecipeProvider {

    public FabricDTRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        //Fabric
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, DoggyItems.SAKE.get(), 1)
            .requires(Items.POTION, 1)
            .requires(DoggyItems.KOJI.get())
            .unlockedBy("has_koji", has(DoggyItems.KOJI.get()))
            .save(consumer);
    }
}
