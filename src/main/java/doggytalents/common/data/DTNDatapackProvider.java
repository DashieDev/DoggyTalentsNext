package doggytalents.common.data;

import java.util.concurrent.CompletableFuture;

import doggytalents.DoggyItems;
import doggytalents.common.backward_imitate.DTNRecipeProvider_21_3;
import doggytalents.common.backward_imitate.RecipeProviderWrapper_1_21_5;
import doggytalents.common.event.PackHandler;
import doggytalents.common.lib.Constants;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DTNDatapackProvider {
 
    public static void start(GatherDataEvent event) {
        var gen = event.getGenerator();    
        var prov = event.getLookupProvider();

        var pack_gen = gen.getBuiltinDatapack(true, 
            Constants.MOD_ID, PackHandler.ALT_RECIPE_1);
        pack_gen.addProvider(pack_output -> {
            return PackMetadataGenerator.forFeaturePack(pack_output, 
                Component.literal("Make Conducting Bone Recipe less expensive."));
        });
        pack_gen.addProvider(pack_output -> PackRecipeProvider.of(pack_output, prov,
            (recipe_output, recipe_prov) -> {
                recipe_prov.shaped(RecipeCategory.TOOLS, DoggyItems.CONDUCTING_BONE.get(), 1)
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

    public static class PackRecipeProvider extends DTNRecipeProvider_21_3.BaseProv {

        private final PackRecipeBuilder recipeBuilder;

        private PackRecipeProvider(HolderLookup.Provider prov, RecipeOutput output,
            PackRecipeBuilder recipeBuilder) {
            super(prov, output);
            this.recipeBuilder = recipeBuilder;
        }

        public static RecipeProviderWrapper_1_21_5 of(PackOutput output, CompletableFuture<Provider> prov,
            PackRecipeBuilder recipeBuilder) {
            return RecipeProviderWrapper_1_21_5.wrap(output, prov, (prov1, output1) -> new PackRecipeProvider(prov1, output1, recipeBuilder), "DTN Pack Recipe Provider");
        }

        @Override
        protected void buildRecipes(RecipeOutput output) {
            this.recipeBuilder.buildRecipes(output, new RecipeProviderAccessor() {

                @Override
                public Criterion<TriggerInstance> has(ItemLike item) {
                    return PackRecipeProvider.this.has(item);
                }

                //1.21.5+
                @Override
                public ShapedRecipeBuilder shaped(RecipeCategory category, ItemLike item) { 
                    return PackRecipeProvider.this.shaped(category, item);
                }
                @Override
                public ShapedRecipeBuilder shaped(RecipeCategory category, ItemLike item, int count) { 
                    return PackRecipeProvider.this.shaped(category, item, count);
                }
                @Override
                public ShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike item) {
                    return PackRecipeProvider.this.shapeless(category, item);
                }
                @Override
                public ShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike item, int count) {
                    return PackRecipeProvider.this.shapeless(category, item, count);
                }
                
            });
        }
        
        @FunctionalInterface
        public static interface PackRecipeBuilder {
        
            void buildRecipes(RecipeOutput output, RecipeProviderAccessor recipe_prov);
            
        }

        public static interface RecipeProviderAccessor {
            
            Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike item);

            //1.21.5+
            ShapedRecipeBuilder shaped(RecipeCategory category, ItemLike item);
            ShapedRecipeBuilder shaped(RecipeCategory category, ItemLike item, int count);
            ShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike item);
            ShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike ite, int count);
            
        }
    }
}
