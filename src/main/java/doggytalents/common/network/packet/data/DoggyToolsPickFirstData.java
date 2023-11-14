package doggytalents.common.network.packet.data;

public class DoggyToolsPickFirstData extends DogData {
    
    public boolean val;

    public DoggyToolsPickFirstData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }
}