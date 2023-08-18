package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class DogRandomLookAroundGoal extends RandomLookAroundGoal {

    private Dog dog;

    public DogRandomLookAroundGoal(Dog dog) {
        super(dog);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !dog.animationManager.started();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !dog.animationManager.started();
    }
    
}
