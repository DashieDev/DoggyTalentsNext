package doggytalents.api.inferface;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import doggytalents.api.backward_imitate.DogInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public interface IDogItem {

    public DogInteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn, InteractionHand handIn);

    public static DogInteractionResult getMatch(AbstractDog dog, ItemStack stack, Player player, InteractionHand hand) {
        var item = stack.getItem();
        if (!(item instanceof IDogItem dog_item))
            return DogInteractionResult.PASS;
        var result = dog_item.processInteract(dog, dog.level(), player, hand);
        return result;
    }

}
