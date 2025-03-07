package doggytalents.common.network.packet.data;

public class DogOnDutyData extends DogData {

    public boolean val;

    public DogOnDutyData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }
    
}
