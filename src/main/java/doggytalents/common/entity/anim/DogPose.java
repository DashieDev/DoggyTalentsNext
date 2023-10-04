package doggytalents.common.entity.anim;

public enum DogPose {
    
    STAND(0, false, true, true, true, true), 
    SIT(1, false, true, true, true, true),
    FAINTED(2, true),
    REST(3, true),
    FAINTED_2(4, true),
    LYING_2(5, true),
    DROWN(6, true);

    private final int id;
    public final boolean needRenderRefresh;
    public final boolean canBeg;
    public final boolean canShake;
    public final boolean freeTail;
    public final boolean freeHead;
    private DogPose(int id, boolean refresh) {
        this.id = id;
        this.needRenderRefresh = refresh;
        this.canBeg = false;
        this.canShake = false;
        this.freeTail = false;
        this.freeHead = false;
    }

    private DogPose(int id, boolean refresh, boolean beg, boolean shake, boolean tail, boolean head) {
        this.id = id;
        this.needRenderRefresh = refresh;
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
