package doggytalents.common.network.packet.data;

public class WhistleRequestModeData {
    public int id;
    public boolean dogOnDutyOnly;

    public WhistleRequestModeData(int id, boolean dogOnDutyOnly) {
        this.id = id;
        this.dogOnDutyOnly = dogOnDutyOnly;
    }
}
