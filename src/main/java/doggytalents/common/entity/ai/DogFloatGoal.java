package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import net.minecraft.tags.FluidTags;
import net.minecraft.entity.Mob;
import net.minecraft.entity.ai.goal.FloatGoal;

public class DogFloatGoal extends FloatGoal {

    private Dog dog;


    public DogFloatGoal(Dog dog) {
        super(dog);
        this.dog = dog;
    }
    
    @Override
    public boolean canUse() {
        if (this.dog.shouldDogBlockFloat())
            return false;
        if (this.dog.isInLava() && this.dog.isDefeated()) {
            return this.dog.getFluidHeight(FluidTags.LAVA) > this.dog.getFluidJumpThreshold() && !this.dog.isDogSwimming();
        }

        return super.canUse() && !this.dog.isDogSwimming();
    }

    
}
