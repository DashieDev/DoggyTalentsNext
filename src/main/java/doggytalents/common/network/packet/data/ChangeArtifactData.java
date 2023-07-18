package doggytalents.common.network.packet.data;

public class ChangeArtifactData extends DogData {
    
    public boolean add;
    public int slotId;

    public ChangeArtifactData(int entityId, boolean add, 
        int slotId) {
        super(entityId);
        this.add = add;
        this.slotId = slotId;
    }

}
