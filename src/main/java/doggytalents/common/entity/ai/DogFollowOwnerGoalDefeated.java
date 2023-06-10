package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogFollowOwnerGoalDefeated extends Goal {

    private Dog dog;
    private int tickTillPathRecalc;
    private int tickTillWhine = 20;
    private LivingEntity owner;

    public DogFollowOwnerGoalDefeated(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.dog.isDefeated())
            return false;
        if (dog.isOrderedToSit())
            return false;

        if (tickTillWhine > 0) --tickTillWhine;
        
        var owner = this.dog.getOwner();
        if (owner == null)
            return false;
        if (owner.isSpectator())
            return false;
        var distance_sqr = owner.distanceToSqr(dog);
        if (distance_sqr < 100 || distance_sqr > 256)
            return false;
        
        this.owner = owner;
        return true;
    }

    public boolean canContinueToUse() {
        if (!this.dog.isDefeated())
            return false;
        if (dog.isOrderedToSit())
            return false;
        if (dog.getNavigation().isDone())
            return false;

        if (tickTillWhine > 0) --tickTillWhine;
        
        var owner = this.dog.getOwner();
        if (owner == null)
            return false;
        if (owner.isSpectator())
            return false;
        var distance_sqr = owner.distanceToSqr(dog);
        if (distance_sqr < 4 || distance_sqr > 256)
            return false;
        
        return true;
    }

    @Override
    public void tick() {
        if (this.owner == null) return;
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (--this.tickTillPathRecalc > 0 ) 
            return;
        this.tickTillPathRecalc = 15;
        this.dog.getNavigation().moveTo(owner, 1.0f);
    }

    @Override
    public void stop() {
        if (this.dog.distanceToSqr(owner) > 256) {
            //TODO whine : wait up !
            this.tickTillWhine = 20;
        }
        this.owner = null;
    }
    
}
