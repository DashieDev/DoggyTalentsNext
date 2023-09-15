package doggytalents.common.item;

import doggytalents.DoggyItems;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class GenderBoneItem extends Item implements IDogItem{

    public GenderBoneItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn,
            InteractionHand handIn) {
        throw new UnsupportedOperationException("Unimplemented method 'processInteract'");
    }
    
}
