package doggytalents.common.entity;

import doggytalents.api.feature.DogMode;
import net.minecraft.world.entity.LivingEntity;

public class DogAttackManager {
 
    private final Dog dog;

    public boolean attacking = false;
    
    private int tacticalTimeout = 10;
    private boolean hasTaticalTarget = false;
    private boolean isDogFarChasingTarget = false;

    public DogAttackManager(Dog dog) {
        this.dog = dog;
    }

    public void tickServer() {
        invalidateTacticalTarget();
    }

    private void invalidateTacticalTarget() {
        if (!this.hasTaticalTarget)
            return;
        var target = dog.getTarget();
        if (target == null) {
            this.hasTaticalTarget = false;
            return;
        }
        if (!target.isAlive()) {
            this.dog.setTarget(null);
            this.hasTaticalTarget = false;
            return;
        }
        if (!this.attacking) {
            if (this.tacticalTimeout <= 0) {
                this.dog.setTarget(null);
                this.hasTaticalTarget = false;
                return;
            }
            --this.tacticalTimeout;
        }
    }

    public boolean hasTaticalTarget() {
        return hasTaticalTarget;
    }

    public boolean setDogTaticalTarget(LivingEntity target) {
        if (target == null)
            return false;
        if (dog.getMode() != DogMode.TACTICAL)
            return false;
        // if (dog.getTarget() != null)
        //     return false;
        dog.setTarget(target);
        this.tacticalTimeout = 10;
        this.hasTaticalTarget = true;
        return false;
    }

    public boolean isDogFarChasingTarget() {
        return isDogFarChasingTarget;
    }

    public void setDogFarChasingTarget(boolean val) {
        this.isDogFarChasingTarget = val;
    }

    public void onTargetChange() {
        this.hasTaticalTarget = false;
    }

    public int getStandardFollowRange() {
        return 16;
    }

    public int getFarFollowRange() {
        return 32;
    }

}
