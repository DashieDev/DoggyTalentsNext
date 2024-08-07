package doggytalents.api.feature;

import net.minecraft.util.Mth;

public class DogLevel {

    private int level;
    private int kamiLevel;

    public static enum Type {
        NORMAL("normal_treat"),
        KAMI("kami_treat");

        String n;

        Type(String n) {
            this.n = n;
        }

        public String getName() {
            return this.n;
        }
    }

    public DogLevel(int level, int kami) {
        this.level = level;
        this.kamiLevel = kami;
    }

    public int getLevel(Type type) {
        return type == Type.KAMI ? this.kamiLevel : this.level;
    }

    public boolean canIncrease(Type type) {
        return type == Type.KAMI ? this.level >= 60 : true;
    }

    @Deprecated
    public void setLevel(Type type, int level) {
        if (type == Type.KAMI) {
            this.kamiLevel = level;
        } else {
            this.level = level;
        }
    }

    @Deprecated
    public void incrementLevel(Type type) {
        this.setLevel(type, this.getLevel(type) + 1);
    }

    public float getMaxHealth() {
        int hearts = 10 + (int) Math.ceil((1f/6f) * this.level + (1f/3f) * this.kamiLevel);
        
        //Imitate Vanilla Wolves change
        hearts *= 2;
        
        return 2*hearts;
    }

    public DogLevel copy() {
        return new DogLevel(this.level, this.kamiLevel);
    }

    /**
     * Combines parents levels together
     */
    public DogLevel combine(DogLevel levelIn) {
        int combinedLevel = this.getLevel(Type.NORMAL) + levelIn.getLevel(Type.NORMAL);
        combinedLevel /= 2;
        combinedLevel = Math.min(combinedLevel, 20);
        return new DogLevel(combinedLevel, 0);
    }

    public final boolean isFullKami() {
        return this.kamiLevel >= 30;
    }

    public static DogLevel kamiReady() {
        return new DogLevel(60, 0);
    }

}
