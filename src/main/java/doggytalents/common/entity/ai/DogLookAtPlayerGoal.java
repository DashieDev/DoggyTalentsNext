package doggytalents.common.entity.ai;

import doggytalents.common.entity.Dog;
import net.minecraft.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.entity.player.Player;

public class DogLookAtPlayerGoal extends LookAtPlayerGoal {

    private Dog dog;

    public DogLookAtPlayerGoal(Dog dog) {
        super(dog, Player.class, 8.0F);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (dog.isDefeated())
            return false;
        return super.canUse();
    }
}
