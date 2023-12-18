package doggytalents.api.enu;

public enum WetSource {

    NONE(false),
    WATER(true),
    BUBBLE_COLUMN(true),
    RAIN(false),
    LAVA(false);

    boolean isWater;

    WetSource(boolean isWaterIn) {
        this.isWater = isWaterIn;
    }

    public boolean isWaterBlock() {
        return this.isWater;
    }

    public boolean isNone() {
        return this == NONE;
    }

    public boolean flame() {
        return this == LAVA;
    }

    public boolean soaked() {
        return this != LAVA && this != NONE;
    }
}
