package doggytalents.common.talent.talentclass;


import java.util.function.BiFunction;

import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;

public class SingleLevelTalent extends Talent {
    
    public SingleLevelTalent(BiFunction<Talent, Integer, TalentInstance> sup) {
        super(sup);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getLevelCost(int toGoToLevel) {
        return 3;
    }

    @Override
    public int getCummulativeCost(int level) {
        return level > 0 ? 3 : 0;
    }

}
