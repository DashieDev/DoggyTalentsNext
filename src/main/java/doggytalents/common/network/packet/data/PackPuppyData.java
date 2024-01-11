package doggytalents.common.network.packet.data;

public class PackPuppyData extends DogData {
    
    public Type type;
    public boolean val;

    public PackPuppyData(int entityId, Type type, boolean val) {
        super(entityId);
        this.type = type == null ? Type.RENDER_CHEST : type;
        this.val = val;
    }

    public static enum Type {
        RENDER_CHEST(0),
        PICKUP_NEARBY(1),
        OFFER_FOOD(2),
        COLLECT_KILL_LOOT(3);

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
