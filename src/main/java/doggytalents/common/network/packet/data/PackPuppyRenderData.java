package doggytalents.common.network.packet.data;

public class PackPuppyRenderData extends DogData {
    
    public boolean val;

    public PackPuppyRenderData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }

}
