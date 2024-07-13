package doggytalents.api.anim;

import java.util.function.Function;

import net.minecraft.util.Mth;

public enum DogAnimation {
    
    NONE(0, 0), //emgniypocpots_redrehtac
    STRETCH(1, 70),
    FAINT(2, 80),
    SIT_DOWN(3, 25, p -> p.speedMod(1.75f)),
    STAND_UP(4, 40, p -> p.speedMod(1.25f)),
    FAINT_2(5, 80),
    LYING_DOWN(6, 80),
    STAND_QUICK(7, 15, p -> p.speedMod(1.25f)),
    DROWN(8, 145),
    HURT_1(9, 15, p -> p.speedMod(1.25f)),
    HURT_2(10, 10, p -> p.speedMod(1.25f)),
    FAINT_STAND_1(11, 80),
    FAINT_STAND_2(12, 80),
    BACKFLIP(13, 20),
    PROTEST(14, 120),
    STAND_IDLE_2(15, 80, p -> p.freeHead()),
    DIG(16, 120),
    SIT_IDLE(17, 80, p -> p.freeHead()),
    SCRATCHIE(18, 70),
    CHOPIN_TAIL(19, 200),
    BELLY_RUB(20, 11*20, p -> p.lockTail()),
    SIT_LOOK_AROUND(21, 100),
    HOWL(22, 165),
    LIE_DOWN_IDLE(23, 20, p -> p.speedMod(0.5f).looping()),
    SIT_TO_REST(24, 40, p -> p.speedMod(0.5f)),
    REST_IDLE(25, 20, p -> p.speedMod(0.75f).looping()),
    REST_TO_SIT(26, 65),
    FLY_JUMP_START(27, 10, p -> p.lockTail().freeHead()),
    FLY_AIR_BOURNE(28, 40, p -> p.lockTail().freeHead().looping()),
    FLY_LANDING(29, 10, p -> p.lockTail().freeHead()),
    TOUCH_RETREAT(30, 11*20),
    SNIFF_HOT(31, 80),
    SNIFF_NEUTRAL(32, 80),
    SNIFF_SNEEZE(33, 50),
    STOP_DROP_ROLL(34, 170, p -> p.lockTail()),
    TOUCHY_TOUCH(35, 100),
    DOWN_THE_HOLE(36, 70),
    REST_BELLY_START(37, 70),
    REST_BELLY_LOOP(38, 140, p -> p.looping()),
    REST_BELLY_END(39, 70),
    NAKEY(40, 140),
    DRUNK_LOOP(41, 140, p -> p.speedMod(0.5f).looping()), // Slower version of REST_BELLY_LOOP
    DRUNK_START(42, 36),
    SNIFFER_DOG_POINT_STRAIGHT(43, 160),
    SNIFFER_DOG_POINT_DOWNARD(44, 180),
    SNIFFER_DOG_POINT_UPWARD(45, 80),
    PLAY_WITH_MEH(46, 110, p -> p.headHandling(HeadHandling.FREE_X_AND_REAL_Z)),
    GREET(47, 40, p -> p.speedMod(1.5f).headHandling(HeadHandling.FREE_X_AND_REAL_Z)),
    SNIFF_AWW_HAPPY(48, 200);

    private final int id;
    private final int lengthTicks;
    private final float speedModifier;
    private final boolean freeTail;
    private final HeadHandling headHandling;
    private final TimelineMode timelineMode;

    private DogAnimation(int id, int lengthTicks, Function<Props, Props> props_consumer) {
        this.id = id;
        this.lengthTicks = lengthTicks;
        var props = new Props();
        props = props_consumer.apply(props);
        this.speedModifier = props.speedModifier;
        this.freeTail = props.freeTail;
        this.headHandling = props.headHandling;
        this.timelineMode = props.timelineMode;
    }
    
    private DogAnimation(int id, int lengthTicks) {
        this(id, lengthTicks, p -> p);
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
    public boolean looping() { return this.timelineMode == TimelineMode.LOOP; }
    public boolean holdOnLastTick() { return this.timelineMode == TimelineMode.HOLD_ON_LAST_TICK; }

    private enum HeadHandling {
        LOCKED, FREE_HEAD, FREE_X_AND_REAL_Z
    }

    private enum TimelineMode {
        STOP_ON_LAST_TICK, LOOP, HOLD_ON_LAST_TICK
    }

    private static class Props {

        private float speedModifier = 1f;
        private boolean freeTail = true;
        private HeadHandling headHandling = HeadHandling.LOCKED;
        private TimelineMode timelineMode = TimelineMode.STOP_ON_LAST_TICK;

        public Props speedMod(float val) {
            this.speedModifier = val;
            return this;
        }

        public Props lockTail() {
            this.freeTail = false;
            return this;
        }

        public Props headHandling(HeadHandling val) {
            this.headHandling = val;
            return this;
        }

        public Props freeHead() {
            this.headHandling = HeadHandling.FREE_HEAD;
            return this;
        }

        public Props looping() {
            this.timelineMode = TimelineMode.LOOP;
            return this;
        }

        public Props holdOnLastTick() {
            this.timelineMode = TimelineMode.HOLD_ON_LAST_TICK;
            return this;
        }

    }

}
