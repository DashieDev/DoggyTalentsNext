package doggytalents.common.inventory.recipe;

import doggytalents.DoggyItems;
import doggytalents.DoggyRecipeSerializers;
import doggytalents.common.item.DyableBirthdayHatItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class UncookedRiceRecipe extends CustomRecipe {
    
    public UncookedRiceRecipe(ResourceLocation p_252125_, CraftingBookCategory p_249010_) {
        super(p_252125_, p_249010_);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack uncookedRiceStack = null;
        ItemStack waterBucketStack = null;
        ItemStack bowlStack = null;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            var stack = container.getItem(i);
            if (stack.isEmpty())
                continue;
            if (stack.is(DoggyItems.UNCOOKED_RICE.get())) {
                if (uncookedRiceStack != null)
                    return false;
                uncookedRiceStack = stack;
                continue;
            }
            if (stack.is(Items.WATER_BUCKET)) {
                if (waterBucketStack != null)
                    return false;
                waterBucketStack = stack;
                continue;
            }
            if (stack.is(Items.BOWL)) {
                if (bowlStack != null)
                    return false;
                bowlStack = stack;
                continue;
            }
            return false;
        }
        return uncookedRiceStack != null && waterBucketStack != null && bowlStack != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        return new ItemStack(DoggyItems.UNCOOKED_RICE_BOWL.get());
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        var remains = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < remains.size(); ++i) {
            ItemStack item = container.getItem(i);
            if (item.getItem() == Items.WATER_BUCKET) {
                remains.set(i, item.copy());
            }
        }

      return remains;
    }

    @Override
    public boolean canCraftInDimensions(int p_43759_, int p_43760_) {
        return p_43759_ * p_43760_ >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DoggyRecipeSerializers.UNCOOKED_RICE_BOWL.get();
    }

}
