package doggytalents.common.item;

import doggytalents.DoggyItems;
import doggytalents.api.feature.EnumGender;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.common.entity.Dog;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class GenderBoneItem extends Item implements IDogItem{

    public GenderBoneItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn,
            InteractionHand handIn) {
        if (!(dogIn instanceof Dog dog))
            return InteractionResult.CONSUME;
        if (!dog.canInteract(playerIn))
            return InteractionResult.CONSUME;
        if (playerIn.getCooldowns().isOnCooldown(DoggyItems.GENDER_BONE.get()))
            return InteractionResult.CONSUME;
            
        if (dog.level().isClientSide)
            return InteractionResult.SUCCESS;
        
        dog.setGender(dog.getGender() == EnumGender.MALE ?
            EnumGender.FEMALE
            : EnumGender.MALE);
        playerIn.getCooldowns().addCooldown(DoggyItems.GENDER_BONE.get(), 40);
        dog.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
        if (dog.level() instanceof ServerLevel sL) {
            var item = dog.getGender() == EnumGender.MALE ? 
                Items.LIGHT_BLUE_WOOL : Items.PINK_WOOL;
            sL.sendParticles(
                new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(item)), 
                dog.getX(), dog.getY(), dog.getZ(), 
                24, 
                dog.getBbWidth(), 0.8f, dog.getBbWidth(), 
                0.1
            );
        }
        var stack = playerIn.getItemInHand(handIn);
        stack.hurtAndBreak(1, playerIn, (player_consume) -> {
            player_consume.broadcastBreakEvent(handIn);
        });
        return InteractionResult.SUCCESS;
    }
    
}
