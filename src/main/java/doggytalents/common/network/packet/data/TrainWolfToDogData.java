package doggytalents.common.network.packet.data;

public class TrainWolfToDogData {
    
    public final int wolfId;
    public final float wolfYBodyRot;
    public final float wolfYHeadRot;

    public TrainWolfToDogData(int wolfId, float wolfYBody, float wolfYHead) {
        this.wolfId = wolfId;
        this.wolfYBodyRot = wolfYBody;
        this.wolfYHeadRot = wolfYHead;
    }

}
