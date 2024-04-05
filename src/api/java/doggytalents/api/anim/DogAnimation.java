package doggytalents.api.anim;

import net.minecraft.util.Mth;

public enum DogAnimation {
    
    NONE(0, 0), //emgniypocpots_redrehtac
    STRETCH(1, 70),
    FAINT(2, 80),
    SIT_DOWN(3, 25, 1.75f),
    STAND_UP(4, 40, 1.25f),
    FAINT_2(5, 80),
    LYING_DOWN(6, 80),
    STAND_QUICK(7, 15, 1.25f),
    DROWN(8, 145),
    HURT_1(9, 15, 1.25f),
    HURT_2(10, 10, 1.25f),
    FAINT_STAND_1(11, 80),
    FAINT_STAND_2(12, 80),
    BACKFLIP(13, 20),
    PROTEST(14, 120),
    STAND_IDLE_2(15, 80, 1f, true, false, HeadHandling.FREE_HEAD),
    DIG(16, 120),
    SIT_IDLE(17, 80, 1f, true, false, HeadHandling.FREE_HEAD),
    SCRATCHIE(18, 70),
    CHOPIN_TAIL(19, 200),
    BELLY_RUB(20, 11*20, 1f, false),
    SIT_LOOK_AROUND(21, 100),
    HOWL(22, 165),
    LIE_DOWN_IDLE(23, 20, 0.5f, true, true),
    SIT_TO_REST(24, 40, 0.5f),
    REST_IDLE(25, 20, 0.75f, true, true),
    REST_TO_SIT(26, 65),
    FLY_JUMP_START(27, 10, 1f, false, false, HeadHandling.FREE_HEAD),
    FLY_AIR_BOURNE(28, 40, 1f, false, true, HeadHandling.FREE_HEAD),
    FLY_LANDING(29, 10, 1f, false, false, HeadHandling.FREE_HEAD),
    TOUCH_RETREAT(30, 11*20, 1f, true),
    SNIFF_HOT(31, 80, 1f, true),
    SNIFF_NEUTRAL(32, 80, 1f, true),
    SNIFF_SNEEZE(33, 50, 1f, true),
    STOP_DROP_ROLL(34, 170, 1f, false),
    TOUCHY_TOUCH(35, 100, 1f, true),
    DOWN_THE_HOLE(36, 70, 1f, true),
    REST_BELLY_START(37, 70, 1f, true),
    REST_BELLY_LOOP(38, 140, 1f, true, true),
    REST_BELLY_END(39, 70, 1f, true),
    NAKEY(40, 140, 1f, true),
    DRUNK_LOOP(41, 140, 0.5f, true, true), // Slower version of REST_BELLY_LOOP
    DRUNK_START(42, 36, 1f, true),
    SNIFFER_DOG_POINT_STRAIGHT(43, 160, 1f, true),
    SNIFFER_DOG_POINT_DOWNARD(44, 180, 1f, true),
    SNIFFER_DOG_POINT_UPWARD(45, 80, 1f, true),
    PLAY_WITH_MEH(46, 110, 1f, true, false, HeadHandling.FREE_X_AND_REAL_Z);

    private final int id;
    private final int lengthTicks;
    private final float speedModifier;
    private final boolean freeTail;
    private final HeadHandling headHandling;
    private boolean looping = false;
    
    private DogAnimation(int id, int lengthTicks) {
        this.id = id;
        this.lengthTicks = lengthTicks;
        this.speedModifier = 1;
        freeTail = true;
        headHandling = HeadHandling.LOCKED;
    }

    private DogAnimation(int id, int lengthTicks, float speed) {
        this.id = id;
        this.lengthTicks = Mth.ceil(((float)lengthTicks)/speed);
        this.speedModifier = speed;
        freeTail = true;
        headHandling = HeadHandling.LOCKED;
    }

    private DogAnimation(int id, int lengthTicks, float speed, boolean freeTail) {
        this.id = id;
        this.lengthTicks = Mth.ceil(((float)lengthTicks)/speed);
        this.speedModifier = speed;
        this.freeTail = freeTail;
        headHandling = HeadHandling.LOCKED;
    }

    private DogAnimation(int id, int lengthTicks, float speed, boolean freeTail, boolean looping) {
        this.id = id;
        this.lengthTicks = Mth.ceil(((float)lengthTicks)/speed);
        this.speedModifier = speed;
        this.freeTail = freeTail;
        this.looping = looping;
        headHandling = HeadHandling.LOCKED;
    }

    private DogAnimation(int id, int lengthTicks, float speed, boolean freeTail, boolean looping, HeadHandling headHandling) {
        this.id = id;
        this.lengthTicks = Mth.ceil(((float)lengthTicks)/speed);
        this.speedModifier = speed;
        this.freeTail = freeTail;
        this.looping = looping;
        this.headHandling = headHandling;
    }

    public static DogAnimation byId(int i) {
        var values = DogAnimation.values();
        if (i < 0) return NONE;
        if (i >= values.length) return NONE;
        return values[i];
    };

    public int getId() { return this.id; }
    public int getLengthTicks() { return this.lengthTicks; }
    public float getSpeedModifier() { return this.speedModifier; }
    public boolean freeTail() { return this.freeTail; }
    public boolean freeHead() { return this.headHandling == HeadHandling.FREE_HEAD; }
    public boolean freeHeadXRotOnly() { return this.headHandling == HeadHandling.FREE_X_AND_REAL_Z; }
    public boolean convertHeadZRot() { return this.headHandling == HeadHandling.FREE_X_AND_REAL_Z; }
    public boolean looping() { return this.looping; }

    private enum HeadHandling {
        LOCKED, FREE_HEAD, FREE_X_AND_REAL_Z
    }

}
