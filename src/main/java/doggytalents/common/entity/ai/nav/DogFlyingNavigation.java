package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;

public class DogFlyingNavigation extends FlyingPathNavigation {

    private Dog dog;
    
    public DogFlyingNavigation(Dog dog, Level level) {
        super(dog, level);
        this.dog = dog;
    }

    @Override
    protected boolean canUpdatePath() {
        return super.canUpdatePath() && !dog.isOnSwitchNavCooldown();
    }
    
}
