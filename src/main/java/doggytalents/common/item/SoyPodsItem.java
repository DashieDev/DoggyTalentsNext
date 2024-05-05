package doggytalents.common.item;

import java.util.List;

import javax.annotation.Nullable;

import doggytalents.DoggyItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SoyPodsItem extends Item{

    
    public SoyPodsItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components,
            TooltipFlag flags) {
        var desc_id = this.getDescriptionId(stack) + ".description";
        components.add(Component.translatable(desc_id).withStyle(
            Style.EMPTY.withItalic(true)
        ));
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (level.isClientSide)
            return InteractionResultHolder.success(stack);
        
        float r = player.getRandom().nextFloat();
        int amount = r < 0.4f ? 3 : 2;
        var retStack = new ItemStack(DoggyItems.SOY_BEANS.get(), amount);

        var inv = player.getInventory();
        int freeSlot = inv.getFreeSlot();
        if (freeSlot >= 0) {
            inv.add(retStack);
        } else {
            player.spawnAtLocation(retStack);
        }

        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.success(stack);
    }
}
