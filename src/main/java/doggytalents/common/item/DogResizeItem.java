package doggytalents.common.item;

import doggytalents.api.feature.DogSize;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class DogResizeItem extends Item implements IDogItem {

    private final DogResizeItem.Type type;

    public static enum Type {
        TINY("tiny_dog"),
        BIG("big_dog");

        String n;

        Type(String n) {
            this.n = n;
        }

        public String getName() {
            return this.n;
        }
    }

    public DogResizeItem(DogResizeItem.Type typeIn, Properties properties) {
        super(properties);
        this.type = typeIn;
    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!dog.canInteract(playerIn))
            return InteractionResult.FAIL;
        
        if (dog.getAge() < 0) {

            if (!playerIn.level.isClientSide){
                playerIn.sendSystemMessage(Component.translatable("treat."+this.type.getName()+".too_young"));
            }

            return InteractionResult.FAIL;
        }
        else {
            var itemInHand = playerIn.getItemInHand(handIn);

            if (!playerIn.level.isClientSide) {
                DogSize size0 = dog.getDogSize();
                DogSize size1 = (this.type == Type.BIG ? size0.grow() : size0.shrink());
                dog.setDogSize(size1);
                if (!playerIn.getAbilities().instabuild && size0 != size1)
                itemInHand.hurtAndBreak(1, playerIn, (player_consume) -> {
                    player_consume.broadcastBreakEvent(handIn);
                });
            }
            return InteractionResult.SUCCESS;
        }
    }
}
