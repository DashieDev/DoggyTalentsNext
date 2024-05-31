package doggytalents.common.inventory;

import javax.annotation.Nonnull;

import doggytalents.DoggyTags;
import doggytalents.api.feature.FoodHandler;
import doggytalents.api.forge_imitate.inventory.ItemStackHandler;
import doggytalents.common.item.TreatBagItem;
import doggytalents.common.util.ItemUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;

public class TreatBagItemHandler extends ItemStackHandler {

    private ItemStack bag;

    public TreatBagItemHandler(ItemStack bag) {
        super(5);
        this.bag = bag;
        
        var bagList = TreatBagItem.inventory(bag);
        for (int i = 0; i < bagList.size(); ++i) {
            this.stacks.set(i, bagList.get(i));
        }
        
    }

    @Override
    protected void onContentsChanged() {
        TreatBagItem.flushInveotory(bag, this.stacks);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.is(DoggyTags.TREATS) || FoodHandler.isFood(stack).isPresent() || stack.is(Items.GUNPOWDER);
    }
}