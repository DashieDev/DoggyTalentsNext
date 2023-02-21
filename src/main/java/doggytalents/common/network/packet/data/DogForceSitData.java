package doggytalents.common.network.packet.data;

public class DogForceSitData extends DogData {

    public boolean forceSit;

    public DogForceSitData(int entityId, boolean forceSit) {
        super(entityId);
        this.forceSit = forceSit;
    }
    
}
