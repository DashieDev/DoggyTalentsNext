package doggytalents.common.network.packet.data;

public class ChangeAccessoriesData extends DogData {
    
    public boolean add;
    public int slotId;
    public boolean wolfArmorSlot;

    public ChangeAccessoriesData(int entityId, boolean add, 
        int slotId, boolean wolfArmorSlot) {
        super(entityId);
        this.add = add;
        this.slotId = slotId;
        this.wolfArmorSlot = wolfArmorSlot;
    }

    public ChangeAccessoriesData(int entityId, boolean add, int slotId) {
        this(entityId, add, slotId, false);
    }

}
