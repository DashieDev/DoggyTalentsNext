package doggytalents.common.network.packet.data;

public class DogMountData {
    public int dogId;
    public boolean mount;

    public DogMountData(int id, boolean mount) {
        this.dogId = id;
        this.mount = mount;
    }
}
