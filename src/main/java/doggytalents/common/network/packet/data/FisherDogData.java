package doggytalents.common.network.packet.data;

public class FisherDogData extends DogData {
    
    public boolean val;

    public FisherDogData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }
}
