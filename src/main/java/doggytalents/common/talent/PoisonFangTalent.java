package doggytalents.common.talent;

import doggytalents.api.backward_imitate.DogInteractionResult;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class PoisonFangTalent extends TalentInstance {

    public PoisonFangTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public DogInteractionResult processInteract(AbstractDog dogIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if (dogIn.isTame()) {
            if (this.level() < 5) {
                return DogInteractionResult.PASS;
            }

            ItemStack stack = playerIn.getItemInHand(handIn);

            if (stack.getItem() == Items.SPIDER_EYE) {

                if (playerIn.getEffect(MobEffects.POISON) == null || dogIn.getDogHunger() < 30) {
                    return DogInteractionResult.FAIL;
                }

                if (!worldIn.isClientSide) {
                    playerIn.removeAllEffects();
                    dogIn.setDogHunger(dogIn.getDogHunger() - 30);
                    dogIn.consumeItemFromStack(playerIn, stack);
                }

                return DogInteractionResult.SUCCESS;
            }
        }

        return DogInteractionResult.PASS;
    }

    @Override
    public DogInteractionResult isPotionApplicable(AbstractDog dogIn, MobEffectInstance effectIn) {
        if (this.level() >= 3) {
            if (effectIn.getEffect() == MobEffects.POISON) {
                return DogInteractionResult.FAIL;
            }
        }

        return DogInteractionResult.PASS;
    }

    @Override
    public void doInitialAttackEffects(AbstractDog dog, Entity entity) {
        if (entity instanceof LivingEntity) {
            if (this.level() > 0) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, this.level() * 20, 0));
            }
        }
    }
}
