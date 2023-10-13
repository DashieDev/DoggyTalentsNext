package doggytalents.common.network.packet.data;

public class PatrolTargetLockData extends DogData {

    public boolean val = false;

    public PatrolTargetLockData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }
    
}
