package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;

public class DogWaterBoundNavigation extends WaterBoundPathNavigation {

    private Dog dog;

    public DogWaterBoundNavigation(Dog dog, Level level) {
        super(dog, level);
        this.dog = dog;
    }

    @Override
    protected boolean canUpdatePath() {
        return this.isInLiquid() && !dog.isOnSwitchNavCooldown();
    }
    
}
