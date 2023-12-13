package doggytalents.common.inventory.recipe;

import java.util.ArrayList;

import doggytalents.DoggyItems;
import doggytalents.DoggyRecipeSerializers;
import doggytalents.common.item.DyableBirthdayHatItem;
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

public class BirthdayHatRecipe extends CustomRecipe {

    public BirthdayHatRecipe(ResourceLocation p_252125_) {
        super(p_252125_);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack paperStack = null;
        ItemStack dyeStack = null;
        ItemStack bdStack = null;
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
            if (stack.is(DoggyItems.BIRTHDAY_HAT.get())) {
                if (bdStack != null)
                    return false;
                bdStack = stack;
                continue;
            }
            if (stack.getItem() instanceof DyeItem) {
                if (dyeStack == null)
                    dyeStack = stack;
                continue;
            }
            return false;
        }
        return paperStack != null && dyeStack != null && bdStack != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        ItemStack paperStack = null;
        var dyeList = new ArrayList<DyeColor>();
        ItemStack bdStack = null;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            var stack = container.getItem(i);
            if (stack.isEmpty())
                continue;
            if (stack.is(Items.PAPER)) {
                if (paperStack != null)
                    return ItemStack.EMPTY;
                paperStack = stack;
                continue;
            }
            if (stack.is(DoggyItems.BIRTHDAY_HAT.get())) {
                if (bdStack != null)
                    return ItemStack.EMPTY;
                bdStack = stack;
                continue;
            }
            if (stack.getItem() instanceof DyeItem dye) {
                dyeList.add(dye.getDyeColor());
                continue;
            }
        }
        if (paperStack == null || bdStack == null || dyeList.isEmpty())
            return ItemStack.EMPTY;
        return DyableBirthdayHatItem.dyeForegroundColorStack(bdStack, dyeList);
    }

    @Override
    public boolean canCraftInDimensions(int p_43759_, int p_43760_) {
        return p_43759_ * p_43760_ >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DoggyRecipeSerializers.BIRTHDAY_HAT.get();
    }
    
}
