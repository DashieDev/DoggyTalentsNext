package doggytalents.common.util;

import doggytalents.api.feature.FoodHandler;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import org.apache.commons.lang3.tuple.Pair;
import org.joml.Math;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class InventoryUtil {

    public static InteractionResult feedDogFrom(AbstractDog dogIn, @Nullable Entity entity, IItemHandlerModifiable source) {

        for (int i = 0; i < source.getSlots(); i++) {

            ItemStack stack = source.getStackInSlot(i).copy();
            Optional<IDogFoodHandler> foodHandler = FoodHandler.getMatch(dogIn, stack, entity);

            if (foodHandler.isPresent()) {
                InteractionResult response = foodHandler.get().consume(dogIn, stack, entity);
                source.setStackInSlot(i, stack);
                return response;
            }
        }

        return InteractionResult.PASS;
    }

    public static Pair<ItemStack, Integer> findStack(IItemHandler source, Predicate<ItemStack> searchCriteria) {
        for (int i = 0; i < source.getSlots(); i++) {

            ItemStack stack = source.getStackInSlot(i);
            if (searchCriteria.test(stack)) {
                return Pair.of(stack.copy(), i);
            }
        }

        return null;
    }

    public static void transferStacks(IItemHandlerModifiable source, IItemHandler target) {
        for (int i = 0; i < source.getSlots(); i++) {
            ItemStack stack = source.getStackInSlot(i);
            source.setStackInSlot(i, addItem(target, stack));
        }
    }

    public static ItemStack addItem(IItemHandler target, ItemStack remaining) {
        if (remaining.isEmpty()) {
            return remaining;
        }
        // Try to merge the stack into existing stack with same item first
        for (int i = 0; i < target.getSlots(); i++) {
            var stack = target.getStackInSlot(i);
            if (!stack.is(remaining.getItem()))
                continue;
            if (target.isItemValid(i, remaining)) {
                remaining = target.insertItem(i, remaining, false);
            }

            if (remaining.isEmpty()) {
                break;
            }
        }

        if (remaining.isEmpty()) {
            return remaining;
        }

        // Try to insert item into all slots
        for (int i = 0; i < target.getSlots(); i++) {
            if (target.isItemValid(i, remaining)) {
                remaining = target.insertItem(i, remaining, false);
            }

            if (remaining.isEmpty()) {
                break;
            }
        }
        return remaining;
    }

    // Same as net.minecraft.inventory.container.Container.calcRedstoneFromInventory but for IItemHandler
    public static int calcRedstoneFromInventory(@Nullable IItemHandler inv) {
        if (inv == null) {
           return 0;
        } else {
           int i = 0;
           float f = 0.0F;

           for (int j = 0; j < inv.getSlots(); ++j) {
              ItemStack itemstack = inv.getStackInSlot(j);
              if (!itemstack.isEmpty()) {
                 f += itemstack.getCount() / (float)Math.min(inv.getSlotLimit(j), itemstack.getMaxStackSize());
                 ++i;
              }
           }

           f = f / inv.getSlots();
           return Mth.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
     }

    public static @Nullable ItemStack findStackWithItemFromHands(Player player, Item item) {
        if (item == null) return null;
        if (player == null) return null;
        var stack0 = player.getMainHandItem();
        var stack1 = player.getOffhandItem();
        if (stack0 != null && stack0.getItem() == item) 
            return stack0;
        if (stack1 != null && stack1.getItem() == item)
            return stack1;
        return null; 
    }

    public static int maxStackSizeWithContainer(Container container, int slot, ItemStack stack) {
        var stack_maxSize = stack.getMaxStackSize();
        var container_maxSize = container.getMaxStackSize(); 
        return Math.min(stack_maxSize, container_maxSize);
    }

    public static int maxStackSizeWithContainer(ItemStackHandler container, int slot, ItemStack stack) {
        var stack_maxSize = stack.getMaxStackSize();
        var container_maxSize = container.getSlotLimit(slot); 
        return Math.min(stack_maxSize, container_maxSize);
    }

}
