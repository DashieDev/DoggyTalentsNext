package doggytalents.common.network.packet.data;

public class RescueDogRenderData extends DogData {
    
    public boolean val;

    public RescueDogRenderData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }

}
