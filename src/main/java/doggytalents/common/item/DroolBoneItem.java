package doggytalents.common.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

import doggytalents.common.util.ItemUtil;

public class DroolBoneItem extends Item {

    public Supplier<? extends Item> altBone;

    public DroolBoneItem(Supplier<? extends Item> altBone, Properties properties) {
        super(properties);
        this.altBone = altBone;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemStackIn = playerIn.getItemInHand(handIn);

        if (itemStackIn.getItem() == this) {

            ItemStack returnStack = new ItemStack(this.altBone.get());
            if (ItemUtil.hasTag(itemStackIn)) {
                ItemUtil.copyTag(itemStackIn, returnStack);
            }

            playerIn.swing(handIn);
            playerIn.playSound(SoundEvents.INK_SAC_USE, 1f , 1f);
            return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, returnStack);
        }

        return new InteractionResultHolder<ItemStack>(InteractionResult.FAIL, itemStackIn);
    }
}
