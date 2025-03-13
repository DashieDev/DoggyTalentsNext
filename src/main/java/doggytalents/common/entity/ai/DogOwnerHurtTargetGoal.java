package doggytalents.common.entity.ai;

import doggytalents.api.feature.DogMode;
import doggytalents.common.entity.Dog;

public class DogOwnerHurtTargetGoal extends net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal {

    private Dog dog;

    public DogOwnerHurtTargetGoal(Dog dogIn) {
        super(dogIn);
        this.dog = dogIn;
    }

    @Override
    public boolean canUse() {
         return this.dog.isMode(DogMode.AGGRESIVE, DogMode.BERSERKER, DogMode.TACTICAL) && super.canUse();
    }
}
