package doggytalents.common.network.packet.data;

public class DogAutoMountData extends DogData {

    public boolean val;

    public DogAutoMountData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }
    
}
