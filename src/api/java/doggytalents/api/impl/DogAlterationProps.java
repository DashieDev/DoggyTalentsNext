package doggytalents.api.impl;

public class DogAlterationProps {
    
    private boolean fireImmune = false;
    private boolean canSwimUnderwater = false;
    private boolean canBreatheUnderwater = false;
    private boolean canFly = false;

    public DogAlterationProps() {}

    public boolean fireImmune() { return this.fireImmune; }
    public boolean canSwimUnderwater() { return this.canSwimUnderwater; }
    public boolean canFly() { return this.canFly;}
    public boolean canBreatheUnderwater() { return canBreatheUnderwater; }

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

}
