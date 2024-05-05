package doggytalents.common.item;

import doggytalents.DoggyItems;
import doggytalents.common.util.ItemUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DroolScentTreatItem extends Item {

    public DroolScentTreatItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        var stack = playerIn.getItemInHand(handIn);

        if (!stack.is(this)) 
            return new InteractionResultHolder<ItemStack>(InteractionResult.FAIL, stack);

        var returnStack = new ItemStack(DoggyItems.SCENT_TREAT.get());

        if (ItemUtil.hasTag(stack)) {
            ItemUtil.copyTag(stack, returnStack);
        }

        playerIn.swing(handIn);
        playerIn.playSound(SoundEvents.INK_SAC_USE, 1f , 1f);
        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, returnStack);
    }
}
