package doggytalents.common.network.packet.data;

public class DoggyTorchPlacingTorchData extends DogData {
    
    public boolean val;

    public DoggyTorchPlacingTorchData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }
}