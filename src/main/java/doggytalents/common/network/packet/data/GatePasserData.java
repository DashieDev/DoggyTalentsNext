package doggytalents.common.network.packet.data;

public class GatePasserData extends DogData {

    public boolean allowPassingGate = false;

    public GatePasserData(int entityId, boolean allowPassingGate) {
        super(entityId);
        this.allowPassingGate = allowPassingGate;
    }
    
}
