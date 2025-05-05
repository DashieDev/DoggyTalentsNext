package doggytalents.common.backward_imitate.fabric_util;

import java.util.concurrent.CompletableFuture;

import doggytalents.common.data.fabric_data.FabricDTRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public class FabricDTNRecipeProvider_1_21_3 extends FabricRecipeProvider {

    public FabricDTNRecipeProvider_1_21_3(FabricDataOutput output, CompletableFuture<Provider> registriesFuture) {
        super(output, registriesFuture);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return "DTN Fabric Recipes";
    }

    @Override
    protected RecipeProvider createRecipeProvider(Provider registryLookup, RecipeOutput exporter) {
        return new FabricDTRecipeProvider(registryLookup, exporter);
    }
    
}
