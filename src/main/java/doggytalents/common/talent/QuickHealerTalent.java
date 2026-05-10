package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.api.inferface.DTNInteractionResultHolder;

public class QuickHealerTalent extends TalentInstance {

    public QuickHealerTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public DTNInteractionResultHolder<Integer> healingTick(AbstractDog dogIn, int healingTick_add) {
        if (this.level() <= 0)
            return DTNInteractionResultHolder.pass(healingTick_add);

        boolean super_fast_healing = dogIn.isInSittingPose() && this.level() >= 5;

        healingTick_add *= super_fast_healing ? 10 : this.level();

        return DTNInteractionResultHolder.success(healingTick_add);
    }
}
