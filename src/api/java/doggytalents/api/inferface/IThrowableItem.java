package doggytalents.api.inferface;

import javax.annotation.Nullable;

import net.minecraft.world.item.ItemStack;

public interface IThrowableItem {

    /**
     * The stack the dog drops upon return
     * @param stack The stack the dog fetched
     * @param type The type fetched stack
     */
    public ItemStack getReturnStack(ItemStack stack);

    /**
     * The stack the dog renders in mouth
     * @param stack The stack the dog fetched
     */
    public @Nullable ItemStack getCustomRenderStack(ItemStack stack);
}
