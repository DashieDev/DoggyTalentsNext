package doggytalents.common.item;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import org.apache.commons.lang3.tuple.Pair;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.common.lib.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class TreatItem extends Item implements IDogItem {

    private final int maxLevel;
    private final DogLevel.Type type;

    public TreatItem(int maxLevel, DogLevel.Type typeIn, Properties properties) {
        super(properties);
        this.maxLevel = maxLevel;
        this.type = typeIn;
    }

    @Override
    public InteractionResult processInteract(AbstractDog dog, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!dog.isTame() || !dog.canInteract(playerIn)) {
            return InteractionResult.FAIL;
        }

        return handleTreatTrain(dog, worldIn, playerIn, handIn);   
    }

    private InteractionResult handleTreatTrain(AbstractDog dog, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (dog.getAge() < 0) {
            treatFailPrompt(dog, worldIn, playerIn, 
                Component.translatable("treat."+this.type.getName()+".too_young"));

            return InteractionResult.CONSUME;
        }
        
        var dogLevel = dog.getDogLevel();
        if (!dogLevel.canIncrease(this.type)) {
            treatFailPrompt(dog, worldIn, playerIn, 
                Component.translatable("treat."+this.type.getName()+".low_level"));

            return InteractionResult.CONSUME;
        }

        if (dogLevel.getLevel(this.type) >= this.maxLevel) {
            treatFailPrompt(dog, worldIn, playerIn, 
                Component.translatable("treat."+this.type.getName()+".max_level"));

            return InteractionResult.CONSUME;
        }

        if (!playerIn.level().isClientSide) {
            if (!playerIn.getAbilities().instabuild) {
                playerIn.getItemInHand(handIn).shrink(1);
            }

            dog.increaseLevel(this.type);
            dog.setOrderedToSit(true);
        }

        treatSuccessPrompt(dog, worldIn, playerIn);

        return InteractionResult.SUCCESS;
    }

    private void treatFailPrompt(AbstractDog dog, Level worldIn, Player playerIn, Component msg) {
        if (!worldIn.isClientSide) {
            worldIn.broadcastEntityEvent(dog, Constants.EntityState.WOLF_SMOKE);
            playerIn.sendSystemMessage(msg);
        }
    }

    private void treatSuccessPrompt(AbstractDog dog, Level worldIn, Player playerIn) {
        if (!worldIn.isClientSide) {
            worldIn.broadcastEntityEvent(dog, Constants.EntityState.WOLF_HEARTS);
            playerIn.sendSystemMessage(Component.translatable("treat."+this.type.getName()+".level_up"));
        }
    }
}
