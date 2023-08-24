package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;

public class DogFloatGoal extends FloatGoal {

    private Dog dog;


    public DogFloatGoal(Dog dog) {
        super(dog);
        this.dog = dog;
    }
    
    @Override
    public boolean canUse() {
        if (this.dog.isInLava() && this.dog.isDefeated()) {
            return this.dog.getFluidHeight(FluidTags.LAVA) > this.dog.getFluidJumpThreshold() && !this.dog.isDogSwimming();
        }

        return super.canUse() && !this.dog.isDogSwimming();
    }

    
}
