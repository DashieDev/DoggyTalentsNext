package doggytalents.common.item;

import doggytalents.client.screen.ConductingBoneScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @Author DashieDev
 * @Author Artist MashXP
 */
public class ConductingBoneItem extends Item {

    public ConductingBoneItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ConductingBoneScreen.open();
        }
        return InteractionResult.SUCCESS; // stack: player.getItemInHand(hand));
    }
    
}
