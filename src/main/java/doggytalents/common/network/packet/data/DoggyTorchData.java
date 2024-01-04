package doggytalents.common.network.packet.data;

public class DoggyTorchData extends DogData {
    
    public Type type = Type.ALLOW_PLACING;
    public boolean val;

    public DoggyTorchData(int entityId, boolean val) {
        super(entityId);
        this.type = Type.ALLOW_PLACING;
        this.val = val;
    }

    
    public DoggyTorchData(int entityId, boolean val, Type type) {
        super(entityId);
        this.type = type == null ? Type.ALLOW_PLACING : type;
        this.val = val;
    }

    public static enum Type {
        ALLOW_PLACING(0),
        RENDER_TORCH(1);

        private int id;

        private Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Type fromId(int id) {
            var values = Type.values();
            return values[id];
        }
    }
}