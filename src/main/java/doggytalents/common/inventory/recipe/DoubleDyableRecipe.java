package doggytalents.common.inventory.recipe;

import java.util.ArrayList;

import doggytalents.common.item.DoubleDyableAccessoryItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DoubleDyableRecipe extends CustomRecipe {

    public DoubleDyableRecipe() {
    }

    @Override
    public boolean matches(CraftingInput container, Level level) {
        ItemStack paperStack = null;
        ItemStack dyeStack = null;
        ItemStack targetStack = null;
        for (int i = 0; i < container.size(); ++i) {
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
    public ItemStack assemble(CraftingInput container) {
        ItemStack paperStack = null;
        var dyeList = new ArrayList<DyeColor>();
        ItemStack targetStack = null;
        boolean fg_color = false;
        for (int i = 0; i < container.size(); ++i) {
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
            if (stack.getItem() instanceof DyeItem) {
                var color = DyeColor.getColor(stack);
                if (color != null) dyeList.add(color);
                continue;
            }
        }
        if (targetStack == null || dyeList.isEmpty())
            return ItemStack.EMPTY;
        return DoubleDyableAccessoryItem.copyAndSetColorForStack(targetStack, dyeList, fg_color);
    }

    public static final DoubleDyableRecipe INSTANCE = new DoubleDyableRecipe();
    public static final MapCodec<DoubleDyableRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, DoubleDyableRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<DoubleDyableRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public RecipeSerializer<DoubleDyableRecipe> getSerializer() {
        return SERIALIZER;
    }
}
