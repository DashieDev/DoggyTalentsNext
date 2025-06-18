package doggytalents.common.data;

import java.util.concurrent.CompletableFuture;

import doggytalents.DoggyItems;
import doggytalents.common.event.PackHandler;
import doggytalents.common.lib.Constants;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DTNDatapackProvider {
 
    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();    
        var prov = event.getLookupProvider();

        var pack_gen = gen.getBuiltinDatapack(event.includeServer(), 
            Constants.MOD_ID, PackHandler.ALT_RECIPE_1);
        pack_gen.addProvider(pack_output -> {
            return PackMetadataGenerator.forFeaturePack(pack_output, 
                Component.literal("Make Conducting Bone Recipe less expensive."));
        });
        pack_gen.addProvider(pack_output -> PackRecipeProvider.of(pack_output, prov,
            (recipe_output, recipe_prov) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DoggyItems.CONDUCTING_BONE.get(), 1)
                    .pattern(" D ")
                    .pattern("SBS")
                    .pattern(" T ")
                    .define('T', DoggyItems.TRAINING_TREAT.get())
                    .define('S', Items.NETHERITE_SCRAP)
                    .define('B', Items.BONE)
                    .define('D', Items.DIAMOND)
                    .unlockedBy("has_netherite_scrap", recipe_prov.has(Items.NETHERITE_SCRAP))
                    .save(recipe_output);
            }
        ));
    }

    public static class PackRecipeProvider extends RecipeProvider {

        private final PackRecipeBuilder recipeBuilder;

        private PackRecipeProvider(PackOutput output, CompletableFuture<Provider> prov,
            PackRecipeBuilder recipeBuilder) {
            super(output, prov);
            this.recipeBuilder = recipeBuilder;
        }

        public static PackRecipeProvider of(PackOutput output, CompletableFuture<Provider> prov,
            PackRecipeBuilder recipeBuilder) {
            return new PackRecipeProvider(output, prov, recipeBuilder);
        }

        @Override
        protected void buildRecipes(RecipeOutput output) {
            this.recipeBuilder.buildRecipes(output, new RecipeProviderAccessor() {

                @Override
                public Criterion<TriggerInstance> has(ItemLike item) {
                    return RecipeProvider.has(item);
                }
                
            });
        }
        
        @FunctionalInterface
        public static interface PackRecipeBuilder {
        
            void buildRecipes(RecipeOutput output, RecipeProviderAccessor recipe_prov);
            
        }

        public static interface RecipeProviderAccessor {
            
            Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item);
            
        }
    }
}
