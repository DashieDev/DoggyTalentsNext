package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
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
        if (this.dog.shouldDogBlockFloat())
            return false;
        if (this.dog.isInLava() && this.dog.isDefeated()) {
            return this.dog.getFluidHeight(FluidTags.LAVA) > this.dog.getFluidJumpThreshold() && !this.dog.isDogSwimming();
        }

        boolean has_next_node_below = this.dog.getDefaultNavigationIfActive()
            .flatMap(DogPathNavigation::getDogNextNode)
            .filter(node -> node.y <= this.dog.blockPosition().getY())
            .isPresent();
        if (has_next_node_below)
            return false;

        return super.canUse() && !this.dog.isDogSwimming();
    }

    
}
