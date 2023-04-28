package doggytalents.common.network.packet.data;

import doggytalents.api.registry.Talent;

public class DogDeTrainData extends DogData {

    public Talent talent;

    public DogDeTrainData(int entityId, Talent talent) {
        super(entityId);
        this.talent = talent;
    }
    
}
