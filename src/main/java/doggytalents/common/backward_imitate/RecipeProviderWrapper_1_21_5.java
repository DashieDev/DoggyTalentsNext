package doggytalents.common.backward_imitate;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public class RecipeProviderWrapper_1_21_5 extends RecipeProvider.Runner {

    private final RecipeProviderCreator creator;
    private final String name;

    public RecipeProviderWrapper_1_21_5(PackOutput output, CompletableFuture<Provider> prov,
        RecipeProviderCreator creator, String name) {
        super(output, prov);
        this.creator = creator;
        this.name = name;
    }

    public static RecipeProviderWrapper_1_21_5 wrap(
        PackOutput output, CompletableFuture<Provider> prov,
        RecipeProviderCreator creator, String name) {
        return new RecipeProviderWrapper_1_21_5(output, prov, creator, name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    protected RecipeProvider createRecipeProvider(Provider prov, RecipeOutput output) {
        return this.creator.createProvider(prov, output);
    }

    @FunctionalInterface
    public static interface RecipeProviderCreator {
    
        RecipeProvider createProvider(HolderLookup.Provider prov, RecipeOutput output);

    }
}
