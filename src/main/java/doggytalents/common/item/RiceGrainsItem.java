package doggytalents.common.item;

import doggytalents.DoggyItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RiceGrainsItem extends BlockItem{

    public RiceGrainsItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);
        var stack = context.getItemInHand();
        var hand = context.getHand();
        var player = context.getPlayer();

        if (!state.is(Blocks.GRINDSTONE))
            return InteractionResult.FAIL;

        level.playSound(player, pos, SoundEvents.GRINDSTONE_USE, 
            SoundSource.BLOCKS, 1.0F, 1.0F);

        if (level.isClientSide)
            return InteractionResult.SUCCESS;
        
        var resultStack = new ItemStack(
            DoggyItems.UNCOOKED_RICE.get(), 
            stack.getCount()
        );

        var resultEntity = new ItemEntity(
            level, 
            pos.getX() + 0.5, 
            pos.getY() + 1.0, 
            pos.getZ() + 0.5, resultStack);
        level.addFreshEntity(resultEntity);

        if (player != null)
            player.setItemInHand(hand, ItemStack.EMPTY);

        return InteractionResult.SUCCESS;
    }
}
