package doggytalents.common.network.packet.data;

public class HeelByNameData extends DogData {

    public boolean heelAndSit;
    public boolean softHeel;

    public HeelByNameData(int entityId, boolean heelAndSit, boolean softHeel) {
        super(entityId);
        this.heelAndSit = heelAndSit;
        this.softHeel = softHeel;
    }
}
