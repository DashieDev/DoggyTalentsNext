package doggytalents.common.entity.ai;

import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;

public class DogOwnerHurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal {

    private Dog dog;

    public DogOwnerHurtByTargetGoal(Dog dogIn) {
        super(dogIn);
        this.dog = dogIn;
    }

    @Override
    public boolean canUse() {
         return this.dog.isMode(EnumMode.AGGRESIVE, EnumMode.BERSERKER) && super.canUse();
    }
}
