package doggytalents.common.talent;

import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.inventory.DoggyToolsItemHandler;

public class DoggyToolsTalent extends TalentInstance  {

    private DoggyToolsItemHandler tools;

    public DoggyToolsTalent(Talent talentIn, int level) {
        super(talentIn, level);
        this.tools = new DoggyToolsItemHandler(getSize(level));
    }

    private int getSize(int level) {
        return level;
    }

    
    

}
