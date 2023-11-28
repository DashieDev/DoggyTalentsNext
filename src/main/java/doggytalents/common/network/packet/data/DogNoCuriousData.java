package doggytalents.common.network.packet.data;

public class DogNoCuriousData extends DogData {
    
    public boolean val;

    public DogNoCuriousData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }

}
