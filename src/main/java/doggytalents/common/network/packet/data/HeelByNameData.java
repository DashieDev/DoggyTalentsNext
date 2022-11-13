package doggytalents.common.network.packet.data;

public class HeelByNameData extends DogData {

    public boolean heelAndSit;

    public HeelByNameData(int entityId, boolean heelAndSit) {
        super(entityId);
        this.heelAndSit = heelAndSit;
    }
}
