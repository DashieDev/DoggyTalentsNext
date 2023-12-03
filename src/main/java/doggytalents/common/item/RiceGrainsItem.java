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
        Level world = context.getLevel();

        if (world.getBlockState(context.getClickedPos()).is(Blocks.GRINDSTONE)) {
            // Check if right-clicked on a grindstone
            ItemStack riceGrainsStack = context.getItemInHand();
            ItemStack uncookedRiceStack = new ItemStack(DoggyItems.UNCOOKED_RICE.get(), riceGrainsStack.getCount());

            if (!context.getPlayer().isCreative()) {
                // Consume the entire stack of RiceGrainsItem when not in creative mode
                context.getItemInHand().shrink(riceGrainsStack.getCount());
            }

            // Play the grindstone noise
            world.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!world.isClientSide) {
                // Spawn the UNCOOKED_RICE as an item entity at the grindstone's position
                ItemEntity itemEntity = new ItemEntity(world, context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1.0, context.getClickedPos().getZ() + 0.5, uncookedRiceStack);
                world.addFreshEntity(itemEntity);
            }

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
