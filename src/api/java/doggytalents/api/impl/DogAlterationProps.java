package doggytalents.api.impl;

public class DogAlterationProps {
    
    private boolean fireImmune = false;
    private boolean canSwimUnderwater = false;
    private boolean resistWaterPush = false;
    private boolean canBreatheUnderwater = false;
    private boolean canFly = false;
    private boolean canWearArmor = false;
    private boolean canUseTools = false;
    private boolean fallImmune = false;
    private float bonusSwimSpeed = 0f;

    public DogAlterationProps() {}

    public boolean fireImmune() { return this.fireImmune; }
    public boolean canSwimUnderwater() { return this.canSwimUnderwater; }
    public boolean canFly() { return this.canFly;}
    public boolean canBreatheUnderwater() { return canBreatheUnderwater; }
    public boolean canWearArmor() { return canWearArmor; }
    public boolean canUseTools() { return canUseTools; }
    public boolean fallImmune() { return fallImmune; }
    public float bonusSwimSpeed() { return this.bonusSwimSpeed; }
    public boolean resistWaterPush() { return this.resistWaterPush; }

    public DogAlterationProps setFireImmune() {
        this.fireImmune = true;
        return this;
    }

    public DogAlterationProps setCanSwimUnderwater() {
        this.canSwimUnderwater = true;
        return this;
    }

    public DogAlterationProps setCanFly() {
        this.canFly = true;
        return this;
    }

    public DogAlterationProps setCanBreatheUnderwater() {
        this.canBreatheUnderwater = true;
        return this;
    }

    public DogAlterationProps setCanWearArmor() {
        this.canWearArmor = true;
        return this;
    }

    public DogAlterationProps setCanUseTools() {
        this.canUseTools = true;
        return this;
    }

    public DogAlterationProps setFallImmune() {
        this.fallImmune = true;
        return this;
    }

    public DogAlterationProps addBonusSwimSpeed(float val) {
        this.bonusSwimSpeed += val;
        return this;
    }

    public DogAlterationProps setResistWaterPush() {
        this.resistWaterPush = true;
        return this;
    }
}
