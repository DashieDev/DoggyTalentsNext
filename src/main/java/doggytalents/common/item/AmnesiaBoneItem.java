package doggytalents.common.item;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class AmnesiaBoneItem extends Item implements IDogItem  {

    public AmnesiaBoneItem(Properties p_41383_) {
        super(p_41383_);
        //TODO Auto-generated constructor stub
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn,
            InteractionHand handIn) {
        
        throw new UnsupportedOperationException("Unimplemented method 'processInteract'");
    }
    
}
