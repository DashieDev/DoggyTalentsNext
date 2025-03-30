package doggytalents.common.talent;

import doggytalents.DoggyTalents;
import doggytalents.api.backward_imitate.DogInteractionResult;
import doggytalents.api.backward_imitate.InteractionResultHolder;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class PillowPawTalent extends TalentInstance {

    public PillowPawTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public DogInteractionResult canTrample(AbstractDog dogIn, BlockState state, BlockPos pos, float fallDistance) {
        return this.level() >= 5 ? DogInteractionResult.FAIL : DogInteractionResult.PASS;
    }

    @Override
    public DogInteractionResult onLivingFall(AbstractDog dogIn, float distance, float damageMultiplier) {
        return this.level() >= 5 ? DogInteractionResult.SUCCESS : DogInteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractDog dogIn, float distance) {
        if (this.level() > 0) {
            return InteractionResultHolder.success(distance - this.level() * 3);
        }

        return InteractionResultHolder.pass(0F);
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        if (this.level() >= 5)
            props.setFallImmune();
    }

    public static boolean isDogEligible(AbstractDog dog) {
        return dog.getDogLevel(DoggyTalents.FLYING_FURBALL) <= 0;
    } 
}
