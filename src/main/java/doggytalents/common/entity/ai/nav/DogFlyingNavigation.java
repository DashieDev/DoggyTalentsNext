package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;

public class DogFlyingNavigation extends FlyingPathNavigation implements IDogNavLock {

    private Dog dog;
    private boolean locked;
    
    public DogFlyingNavigation(Dog dog, Level level) {
        super(dog, level);
        this.dog = dog;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public boolean canDashToTarget(LivingEntity target) {
        return this.canMoveDirectly(dog.position(), target.position());
    }

    @Override
    protected boolean canUpdatePath() {
        return super.canUpdatePath() && !dog.isOnSwitchNavCooldown() 
            && !locked;
    }
    
    @Override
    public void lockDogNavigation() {
        this.locked = true;
    }

    @Override
    public void unlockDogNavigation() {
        this.locked = false;
    }

}
