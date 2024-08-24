package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DogMoveControl extends MoveControl {

    private Dog dog;

    public DogMoveControl(Dog dog) {
        super(dog);
        this.dog = dog;
    }

    @Override
    public void tick() {
        final float SNEAK_SPEED_1 = 0.35f;
        final float SNEAK_SPEED_2 = 0.25f;
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            double dx = this.wantedX - this.dog.getX();
            double dz = this.wantedZ - this.dog.getZ();
            double dy = this.wantedY - this.dog.getY();
            double l_sqr = dx * dx + dy * dy + dz * dz;
            if (l_sqr < (double)2.5000003E-7F) {
                this.dog.setZza(0.0F);
                return;
            }
            float speed0 = (float) this.dog.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float speed = speed0;
            
            float target_yrot = (float)( Mth.atan2(dz, dx) * Mth.RAD_TO_DEG - 90f );
            if (speed < 0.39f) {
                float apporaching_yrot = this.rotlerp(this.dog.getYRot(), target_yrot, 90f);
                target_yrot = apporaching_yrot;
            }
            this.dog.setYRot(target_yrot);

            double dy_abs = Math.abs(dy);
            if (dy_abs > 0.75 || dog.isDogCurious()) {
                float speed_cap = dy_abs > 1.75 ? SNEAK_SPEED_2 : SNEAK_SPEED_1;
                speed = Math.min(speed_cap, 
                    (float) (this.speedModifier * speed0));
                if (this.dog.isDogCurious()) {
                    speed = (float) speedModifier * SNEAK_SPEED_2;
                }
            }

            this.dog.setSpeed(speed);
            var b0 = this.dog.blockPosition();
            var b0_state = this.dog.level().getBlockState(b0);
            var b0_collision = b0_state.getCollisionShape(this.dog.level(), b0);
            boolean dyRequiresJump = 
                (dy > (double)this.dog.getStepHeight() 
                && dx * dx + dz * dz < (double)Math.max(1.0F, this.dog.getBbWidth()));
            boolean collisionRequireJump =
                !b0_collision.isEmpty() 
                && this.dog.getY() < b0_collision.max(Direction.Axis.Y) + (double)b0.getY() 
                && !b0_state.is(BlockTags.DOORS) 
                && !b0_state.is(BlockTags.FENCES)
                && !(b0_state.getBlock() instanceof FenceGateBlock);
            boolean shouldJump =
                dyRequiresJump
                || collisionRequireJump;
            if (shouldJump) {
                this.dog.getJumpControl().jump();
                this.operation = MoveControl.Operation.JUMPING;
            }
            return;
        }
        super.tick();
    }

    // private boolean isMovingDiagonallyDownward() {
    //     var dog_b0 = this.dog.blockPosition();
    //     var target_b0 = new BlockPos(this.getWantedX(), this.getWantedY(), this.getWantedZ());
    //     if (Math.abs(dog_b0.getY() - target_b0.getY()) < 1) return false;
    //     var dx = Math.abs(dog_b0.getX() - target_b0.getX());
    //     var dz = Math.abs(dog_b0.getZ() - target_b0.getZ());
    //     if (dx != 1) return false;
    //     if (dz != 1) return false;
    //     return true;
    // }

    // private boolean riskyDiagonallyDownward() {
    //     double v_dog_x = dog.position().x;
    //     double v_dog_z = dog.position().z;

    //     double v_target_x = this.getWantedX();
    //     double v_target_z = this.getWantedZ();

    //     double v_dog_target_x = v_target_x - v_dog_x;
    //     double v_dog_target_z = v_target_z - v_dog_z;
    //     double coeff_z_x_v_dog_target = v_dog_target_z / v_dog_target_x;

    //     //intersect the first x = n line (n are integers)
    //     double p_intersect_x = 
    //         Mth.floor(v_dog_x) + (v_dog_target_x > 0 ? 1 : 0);
    //     double p_intersect_z = 
    //         v_dog_z + ((p_intersect_x - v_dog_x) * coeff_z_x_v_dog_target);
        
    //     //intersect point of the target blockPos and dog's pos
    //     double p_node_intersect_z = 
    //         Math.max(Mth.floor(v_dog_z),Mth.floor(v_target_z));

    //     //sides of the triangle that result from the intersect of v_dog_target
    //     //to the nearest x = n and y = n lines (n are integers)
    //     double l_open_z = Math.abs(p_node_intersect_z - p_intersect_z);
    //     double l_open_x = l_open_z / Math.abs(coeff_z_x_v_dog_target);

    //     if (
    //         l_open_z > dog.getBbWidth()/2
    //         && l_open_x > dog.getBbWidth()/2
    //     ) {
    //         ChopinLogger.lwn(dog, "Dangerous : Went from "
    //             + "[ " + v_dog_x  + " " + v_dog_z  + " ]" 
    //             + "to"
    //             + "[ " + v_target_x  + " " + v_target_z  + " ]"
    //             + "open gap "
    //             + "[ " + l_open_x  + " " + l_open_z  + " ]" 
    //             );
    //         return true;
    //     }

    //     return false;

    // }


    
}
