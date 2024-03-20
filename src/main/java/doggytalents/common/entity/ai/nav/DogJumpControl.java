package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.phys.Vec3;

public class DogJumpControl extends JumpControl {

    private final Dog dog;

    public DogJumpControl(Dog dog) {
        super(dog);
        this.dog = dog;
    }

    @Override
    public void jump() {
        if (dog.canDogFly()) {
            this.jump = false;
            var current_move = dog.getDeltaMovement();
            var jump_move = current_move.add(new Vec3(0, 0.25f, 0));
            dog.setDeltaMovement(jump_move);
            return;
        }
        super.jump();
    }
    
}
