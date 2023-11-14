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
    public void tick() {
        super.tick();
        dashToTargetIfNeeded();
    }

    private void dashToTargetIfNeeded() {
        var target = dog.getTarget();
        if (target == null)
            return;
        var d_sqr = dog.distanceToSqr(target);
        if (d_sqr > 16)
            return;
        if (dog.tickCount % 5 != 0)
            return;
        if (!canMoveDirectly(dog.position(), target.position()))
            return;
        this.stop();
        dog.getMoveControl().setWantedPosition(target.position().x, 
            target.position().y, target.position().z, 2f);
    }

    @Override
    protected boolean canUpdatePath() {
        return super.canUpdatePath() && !dog.isOnSwitchNavCooldown();
    }
    
}
