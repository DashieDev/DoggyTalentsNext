package doggytalents.common.inventory.recipe;

import java.util.ArrayList;

import doggytalents.DoggyRecipeSerializers;
import doggytalents.common.item.DoubleDyableAccessoryItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DoubleDyableRecipe extends CustomRecipe {

    public DoubleDyableRecipe(ResourceLocation p_252125_) {
        super(p_252125_);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack paperStack = null;
        ItemStack dyeStack = null;
        ItemStack targetStack = null;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            var stack = container.getItem(i);
            if (stack.isEmpty())
                continue;
            if (stack.is(Items.PAPER)) {
                if (paperStack != null)
                    return false;
                paperStack = stack;
                continue;
            }
            if (stack.getItem() instanceof DoubleDyableAccessoryItem) {
                if (targetStack != null)
                    return false;
                targetStack = stack;
                continue;
            }
            if (stack.getItem() instanceof DyeItem) {
                if (dyeStack == null)
                    dyeStack = stack;
                continue;
            }
            return false;
        }
        return dyeStack != null && targetStack != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        ItemStack paperStack = null;
        var dyeList = new ArrayList<DyeColor>();
        ItemStack targetStack = null;
        boolean fg_color = false;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            var stack = container.getItem(i);
            if (stack.isEmpty())
                continue;
            if (stack.is(Items.PAPER)) {
                if (paperStack != null)
                    return ItemStack.EMPTY;
                paperStack = stack;
                fg_color = true;
                continue;
            }
            if (stack.getItem() instanceof DoubleDyableAccessoryItem) {
                if (targetStack != null)
                    return ItemStack.EMPTY;
                targetStack = stack;
                continue;
            }
            if (stack.getItem() instanceof DyeItem dye) {
                dyeList.add(dye.getDyeColor());
                continue;
            }
        }
        if (targetStack == null || dyeList.isEmpty())
            return ItemStack.EMPTY;
        return DoubleDyableAccessoryItem.copyAndSetColorForStack(targetStack, dyeList, fg_color);
    }

    @Override
    public boolean canCraftInDimensions(int p_43759_, int p_43760_) {
        return p_43759_ * p_43760_ >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DoggyRecipeSerializers.DOUBLE_DYABLE.get();
    }
}
