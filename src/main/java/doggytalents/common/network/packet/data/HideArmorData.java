package doggytalents.common.network.packet.data;

public class HideArmorData extends DogData {

    public boolean val;

    public HideArmorData(int entityId, boolean val) {
        super(entityId);
        this.val = val;
    }
    
}
