package doggytalents.common.data.fabric_data;

import java.util.concurrent.CompletableFuture;

import doggytalents.DoggyItems;
import doggytalents.common.backward_imitate.DTNRecipeProvider_21_3;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.core.HolderLookup.Provider;

public class FabricDTRecipeProvider extends DTNRecipeProvider_21_3.BaseProv {

    public FabricDTRecipeProvider(Provider prov, RecipeOutput output) {
        super(prov, output);
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        //Fabric
        this.shapeless(RecipeCategory.FOOD, DoggyItems.SAKE.get(), 1)
            .requires(Items.POTION, 1)
            .requires(DoggyItems.KOJI.get())
            .unlockedBy("has_koji", has(DoggyItems.KOJI.get()))
            .save(consumer);
    }
}
