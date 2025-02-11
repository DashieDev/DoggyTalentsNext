package doggytalents.common.backward_imitate;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import doggytalents.common.data.DTRecipeProvider;
import doggytalents.common.util.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class DTNRecipeProvider_21_3 extends RecipeProvider.Runner {
    
    public DTNRecipeProvider_21_3(PackOutput generatorIn, CompletableFuture<HolderLookup.Provider> prov) {
        super(generatorIn, prov);
    }

    @Override
    protected RecipeProvider createRecipeProvider(Provider prov, RecipeOutput output) {
        return new DTRecipeProvider(prov, output);
    }

    @Override
    public String getName() {
        return "DoggyTalentsNext Recipe Provider";
    }

    public static abstract class BaseProv extends RecipeProvider {
        public BaseProv(Provider prov, RecipeOutput output) {
            super(prov, output);
        }
        @Override
        protected void buildRecipes() {
            this.buildRecipes(this.output);
        }

        protected abstract void buildRecipes(RecipeOutput output);
    }

    public static ResourceKey<Recipe<?>> recipeKey(ResourceLocation loc) {
        return ResourceKey.create(Registries.RECIPE, loc);
    }

    public static ResourceKey<Recipe<?>> recipeKey(String dtn_path) {
        return ResourceKey.create(Registries.RECIPE, Util.getResource(dtn_path));
    }
}
