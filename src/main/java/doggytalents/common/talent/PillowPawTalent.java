package doggytalents.common.talent;

import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class PillowPawTalent extends TalentInstance {

    public PillowPawTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResult canTrample(AbstractDog dogIn, BlockState state, BlockPos pos, float fallDistance) {
        return this.level() >= 5 ? InteractionResult.FAIL : InteractionResult.PASS;
    }

    @Override
    public InteractionResult onLivingFall(AbstractDog dogIn, float distance, float damageMultiplier) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractDog dogIn, float distance) {
        if (this.level() > 0) {
            return InteractionResultHolder.success(distance - this.level() * 3);
        }

        return InteractionResultHolder.pass(0F);
    }

    public static boolean isDogEligible(AbstractDog dog) {
        return dog.getDogLevel(DoggyTalents.FLYING_FURBALL) <= 0;
    } 
}
