package doggytalents.common.entity;

import javax.annotation.Nullable;

import doggytalents.DoggyTags;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogFoodHandler;
import doggytalents.common.network.packet.ParticlePackets;
import doggytalents.common.util.ItemUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WhitelistFoodHandler implements IDogFoodHandler {

    @Override
    public boolean isFood(ItemStack stackIn) {
        if (!ItemUtil.isEddible(stackIn))
            return false;
        return isWhiteListFood(stackIn) && !isBlackListFood(stackIn);
    }

    @Override
    public boolean canConsume(AbstractDog dogIn, ItemStack stackIn, @Nullable Entity entityIn) {
        return isFood(stackIn);
    }

    @Override
    public InteractionResult consume(AbstractDog dog, ItemStack stack, @Nullable Entity entityIn) {
        if (dog.level().isClientSide)
            return InteractionResult.SUCCESS;

        if (dog.canStillEat()) {
            if (!dog.level().isClientSide) {
                var item = stack.getItem();

                var props = ItemUtil.food(stack);

                if (props == null) return InteractionResult.FAIL;
                
                int heal = Mth.floor(props.nutrition() * 2.5);

                dog.addHunger(heal);
                dog.consumeItemFromStack(entityIn, stack);

                if (dog.level() instanceof ServerLevel) {
                    ParticlePackets.DogEatingParticlePacket.sendDogEatingParticlePacketToNearby(
                        dog, new ItemStack(item));
                }
                dog.playSound(
                    SoundEvents.GENERIC_EAT, 
                    dog.getSoundVolume(), 
                    (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
                );
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;

    }
    
    private boolean isWhiteListFood(ItemStack stack) {
        return stack.is(DoggyTags.WHITELIST_FOOD);
    }

    private boolean isBlackListFood(ItemStack stack) {
        var item = stack.getItem();
        return

            //Dog: WTF are you trying to feed me ?? 🥴🥴🥴
            item == Items.ROTTEN_FLESH

        ;
    }

}
