package doggytalents.common.entity.anim;

import net.minecraft.world.entity.ai.goal.Goal;

public enum DogAnimation {
    
    NONE(0, 0), 
    STRETCH(1, 70),
    FAINT(2, 80),
    SIT_DOWN(3, 25, 1.25f),
    STAND_UP(4, 40, 1.25f);

    private final int id;
    private final int lengthTicks;
    private final float speedModifier;
    
    private DogAnimation(int id, int lengthTicks) {
        this.id = id;
        this.lengthTicks = lengthTicks;
        this.speedModifier = 1;
    }

    private DogAnimation(int id, int lengthTicks, float speed) {
        this.id = id;
        this.lengthTicks = lengthTicks;
        this.speedModifier = speed;
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

}
