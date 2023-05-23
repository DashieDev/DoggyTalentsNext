package doggytalents.common.network.packet.data;

public class CrossOriginTpData extends DogData {

    public boolean crossOriginTp;

    public CrossOriginTpData(int entityId, boolean val) {
        super(entityId);
        this.crossOriginTp = val;
    }
    
}
