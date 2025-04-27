package doggytalents.common.inventory.recipe;

import doggytalents.DoggyRecipeSerializers;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.util.DogBedUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class DogBedRecipe extends CustomRecipe implements IShapedRecipe<CraftingContainer> {

    public DogBedRecipe(ResourceLocation resource, CraftingBookCategory p_249010_) {
        super(resource, p_249010_);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        IBeddingMaterial beddingId = null;
        ICasingMaterial casingId = null;

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if (col == 1 && row < 2) {
                    var material_optional = DogBedUtil.getBeddingFromStack(inv.getItem(row * inv.getWidth() + col));
                    if (!material_optional.isPresent())
                        return false;
                    var id = material_optional.get();

                    if (beddingId == null) {
                        beddingId = id;
                    } else if (beddingId != id) {
                        return false;
                    }
                }
                else {
                    var material_optional = DogBedUtil.getCasingFromStack(inv.getItem(row * inv.getWidth() + col));
                    if (!material_optional.isPresent())
                        return false;
                    var id = material_optional.get();
                    
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

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getCraftingRemainingItem(itemstack)); //TODO?
        }

        return nonnulllist;
    }

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
    public int getRecipeWidth() {
        return 3;
    }

    @Override
    public int getRecipeHeight() {
        return 3;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess p_267165_) {
        var beddingId = DogBedUtil.getBeddingFromStack(inv.getItem(1))
            .orElse(DogBedMaterialManager.NaniBedding.NULL);
        var casingId = DogBedUtil.getCasingFromStack(inv.getItem(0))
            .orElse(DogBedMaterialManager.NaniCasing.NULL);

        return DogBedUtil.createItemStack(casingId, beddingId);
    }
}
