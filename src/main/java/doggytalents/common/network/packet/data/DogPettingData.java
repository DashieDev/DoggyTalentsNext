package doggytalents.common.network.packet.data;

import doggytalents.common.entity.DogPettingManager.DogPettingType;

public class DogPettingData extends DogData {

    public final boolean val;
    public final DogPettingType type;

    public DogPettingData(int entityId, boolean val, DogPettingType type) {
        super(entityId);
        this.val = val;
        this.type = type;
    }
    
    

}
