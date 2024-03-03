package doggytalents.common.network.packet.data;

public class CreeperSweeperData extends DogData {
    
    public boolean val;

    public CreeperSweeperData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }

}
