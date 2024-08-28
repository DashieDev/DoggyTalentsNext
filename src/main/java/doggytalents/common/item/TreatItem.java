package doggytalents.common.item;

import org.apache.commons.lang3.tuple.Pair;

import doggytalents.api.feature.DogLevel;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.IDogItem;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import doggytalents.common.talent.OokamiKazeTalent;
import doggytalents.common.util.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

        if (handleKamiBypass(dog, worldIn, playerIn, handIn).shouldSwing())
            return InteractionResult.SUCCESS;

        return handleTreatTrain(dog, worldIn, playerIn, handIn);   
    }

    private InteractionResult handleKamiBypass(AbstractDog dog, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (this.type != DogLevel.Type.KAMI)
            return InteractionResult.PASS;
        if (!playerIn.isCreative())
            return InteractionResult.PASS;
        var dog_level = dog.getDogLevel();
        if (dog_level.canIncrease(DogLevel.Type.KAMI))
            return InteractionResult.PASS;
        
        if (!(dog instanceof Dog actual_dog))
            return InteractionResult.PASS;
        
        if (!dog.level().isClientSide) {
            actual_dog.setLevel(DogLevel.kamiReady());
            playerIn.getCooldowns().addCooldown(this, 40);
        }
            
        playKamiBypassEffect(actual_dog);

        return InteractionResult.SUCCESS;
    } 

    private void playKamiBypassEffect(Dog dog) {
        var level = dog.level();
        if (!level.isClientSide)
            return;
        var dog_pos = dog.position();
        level.playLocalSound(dog_pos.x, dog_pos.y, dog_pos.z, 
            SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, 
            (1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.2F) * 0.7F, false);
        level.addParticle(ParticleTypes.EXPLOSION, dog_pos.x, dog_pos.y, dog_pos.z, 1.0D, 0.0D, 0.0D);
        level.addParticle(ParticleTypes.EXPLOSION_EMITTER, dog_pos.x, dog_pos.y, dog_pos.z, 1.0D, 0.0D, 0.0D);
        
        for (int i = 0; i <= 30; ++i) {
            float f1 = RandomUtil.nextFloatRemapped(dog.getRandom()) * dog.getBbWidth() * 1;
            float f2 = RandomUtil.nextFloatRemapped(dog.getRandom()) * dog.getBbWidth() * 1;
        
            double dx = level.getRandom().nextGaussian() * (double)0.3;
            double dy = level.getRandom().nextGaussian() * (double)0.3;
            double dz = level.getRandom().nextGaussian() * (double)0.3;

            dog.level().addParticle(ParticleTypes.PORTAL,
                dog.getX() + f1,
                dog.getY(),
                dog.getZ() + f2,
                dx, dy, dz);
        }
        
        
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

        if (!playerIn.level.isClientSide) {
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
