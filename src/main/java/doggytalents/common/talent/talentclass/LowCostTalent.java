package doggytalents.common.talent.talentclass;

import java.util.function.BiFunction;

import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;

public class LowCostTalent extends Talent {

    public LowCostTalent(BiFunction<Talent, Integer, TalentInstance> sup) {
        super(sup);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    
}
