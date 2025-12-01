package doggytalents.common.network.packet.data;

public class OpenDogScreenData {

    public enum ScreenType {
        INVENTORY(0),
        TOOL(1),
        ARMOR(2),
        INVENTORY_SINGLE(3);

        private int id;

        private ScreenType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static ScreenType byId(int id) {
            return ScreenType.values()[id];
        }

        public boolean mayRequireClosingContiner() {
            return this == INVENTORY;
        }
    }

    public ScreenType type = ScreenType.INVENTORY; 
    public int dogId = -1;
    public boolean closeContainer = false;
    
    public OpenDogScreenData(ScreenType type, int id) {
        this.type = type;
        this.dogId = id;
    }

    public OpenDogScreenData(ScreenType type, int id, boolean doCloseContainer) {
        this.type = type;
        this.dogId = id;
        this.closeContainer = doCloseContainer;
    }

    public static OpenDogScreenData dogInventory(boolean doCloseContainer) {
        return new OpenDogScreenData(ScreenType.INVENTORY, -1, doCloseContainer);
    }
}
