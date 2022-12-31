package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;

public class DoggyCarrier extends TalentInstance {

    

    public DoggyCarrier(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }
    
    @Override
    public void tick(AbstractDog dog) {
        
    }

}
