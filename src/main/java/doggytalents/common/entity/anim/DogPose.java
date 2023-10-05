package doggytalents.common.entity.anim;

public enum DogPose {
    
    STAND(0, true, true, true, true), 
    SIT(1, true, true, true, true),
    FAINTED(2),
    REST(3),
    FAINTED_2(4),
    LYING_2(5),
    DROWN(6);

    private final int id;
    public final boolean canBeg;
    public final boolean canShake;
    public final boolean freeTail;
    public final boolean freeHead;
    private DogPose(int id) {
        this.id = id;
        this.canBeg = false;
        this.canShake = false;
        this.freeTail = false;
        this.freeHead = false;
    }

    private DogPose(int id, boolean beg, boolean shake, boolean tail, boolean head) {
        this.id = id;
        this.canBeg = beg;
        this.canShake = shake;
        this.freeTail = tail;
        this.freeHead = head;
    }

    public static DogPose byId(int i) {
        var values = DogPose.values();
        if (i < 0) return STAND;
        if (i >= values.length) return STAND;
        return values[i];
    };

    public int getId() { return this.id; }
}
