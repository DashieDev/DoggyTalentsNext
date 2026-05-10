package doggytalents.common.inventory.recipe;

import doggytalents.DoggyRecipeSerializers;
import doggytalents.api.DoggyTalentsAPI;
import doggytalents.api.registry.IBeddingMaterial;
import doggytalents.api.registry.ICasingMaterial;
import doggytalents.common.block.DogBedMaterialManager;
import doggytalents.common.util.DogBedUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DogBedRecipe extends CustomRecipe /*implements IShapedRecipe<CraftingInput>*/ {

    public DogBedRecipe() {
        
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
                    var material_optional = DogBedUtil.getBeddingFromStack(inv.getItem(col, row));
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
                    var material_optional = DogBedUtil.getCasingFromStack(inv.getItem(col, row));
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

    // @Override
    // public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
    //     NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getContainerSize(), ItemStack.EMPTY);

    //     for (int i = 0; i < nonnulllist.size(); ++i) {
    //         ItemStack itemstack = inv.getItem(i);
    //         // NeoForge equivalent: nonnulllist.set(i, ForgeHooks.getCraftingRemainingItem(itemstack));
    //     }

    //     return nonnulllist;
    // }

    public static final DogBedRecipe INSTANCE = new DogBedRecipe();
    public static final MapCodec<DogBedRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DogBedRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DogBedRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public RecipeSerializer<DogBedRecipe> getSerializer() {
        return SERIALIZER;
    }

    // @Override
    // public int getWidth() {
    //     return 3;
    // }

    // @Override
    // public int getHeight() {
    //     return 3;
    // }

    @Override
    public ItemStack assemble(CraftingInput inv) {
        var beddingId = DogBedUtil.getBeddingFromStack(inv.getItem(1))
            .orElse(DogBedMaterialManager.NaniBedding.NULL);
        var casingId = DogBedUtil.getCasingFromStack(inv.getItem(0))
            .orElse(DogBedMaterialManager.NaniCasing.NULL);

        return DogBedUtil.createItemStack(casingId, beddingId);
    }
}
