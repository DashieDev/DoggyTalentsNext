package doggytalents.common.inventory.recipe;

import doggytalents.DoggyRecipeSerializers;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.util.DogBedUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.IShapedRecipe;

public class DogBedRecipe extends CustomRecipe implements IShapedRecipe<CraftingInput> {

    public DogBedRecipe(CraftingBookCategory p_249010_) {
        super(p_249010_);
    }

    @Override
    public boolean matches(CraftingInput inv, Level worldIn) {
        //Validate for 1.21
        if (inv.width() != 3 || inv.height() != 3)
            return false;
        
        IBeddingMaterial beddingId = null;
        ICasingMaterial casingId = null;

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if (col == 1 && row < 2) {
                    IBeddingMaterial id = DogBedUtil.getBeddingFromStack(inv.getItem(col, row));

                    if (id == null) {
                        return false;
                    }

                    if (beddingId == null) {
                        beddingId = id;
                    } else if (beddingId != id) {
                        return false;
                    }
                }
                else {
                    ICasingMaterial id = DogBedUtil.getCasingFromStack(inv.getItem(col, row));

                    if (id == null) {
                        return false;
                    }

                    if (casingId == null) {
                        casingId = id;
                    } else if (casingId != id) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // @Override
    // public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
    //     NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getContainerSize(), ItemStack.EMPTY);

    //     for (int i = 0; i < nonnulllist.size(); ++i) {
    //         ItemStack itemstack = inv.getItem(i);
    //         nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getCraftingRemainingItem(itemstack)); //TODO?
    //     }

    //     return nonnulllist;
    // }

    //Is on a 3x3 grid or bigger
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DoggyRecipeSerializers.DOG_BED.get();
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider p_267165_) {
        IBeddingMaterial beddingId = DogBedUtil.getBeddingFromStack(inv.getItem(1));
        ICasingMaterial casingId = DogBedUtil.getCasingFromStack(inv.getItem(0));

        return DogBedUtil.createItemStack(casingId, beddingId);
    }
}
