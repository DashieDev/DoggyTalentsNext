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
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DogMoveControl extends MoveControl {

    private static final float SNEAK_SPEED_1 = 0.35f;
    private static final float SNEAK_SPEED_2 = 0.25f;

    private Dog dog;
    private float forceSneak = -1;

    public DogMoveControl(Dog dog) {
        super(dog);
        this.dog = dog;
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            doDogMoveTo();
            forceSneak = -1;
            return;
        }
        if (this.operation == MoveControl.Operation.STRAFE) {
            this.operation = MoveControl.Operation.WAIT;
            doDogStrafe();
            return;
        }
        super.tick();
    }

    public void forceSneak(float val) {
        this.forceSneak = val;
    }

    private void doDogMoveTo() {
        double dx = this.wantedX - this.dog.getX();
        double dz = this.wantedZ - this.dog.getZ();
        double dy = this.wantedY - this.dog.getY();
        double l_sqr = dx * dx + dy * dy + dz * dz;
        if (l_sqr < (double)2.5000003E-7F) {
            this.dog.setZza(0.0F);
            return;
        }
        
        final double base_speed = this.dog.getAttributeValue(Attributes.MOVEMENT_SPEED);
        double speed = base_speed * this.speedModifier;
        double dy_abs = Math.abs(dy);
        if (dy_abs > 0.75) {
            final double speed_cap = dy_abs > 1.75 ? SNEAK_SPEED_2 : SNEAK_SPEED_1;
            speed = Math.min(speed, speed_cap);
        }
        if (this.dog.isDogCurious()) {
            speed = Math.min(speed, SNEAK_SPEED_2);
        }
        if (forceSneak > 0)
            speed = Math.min(speed, forceSneak);
        
        final float target_yrot = (float)( Mth.atan2(dz, dx) * Mth.RAD_TO_DEG - 90f );
        float apporaching_yrot = speed < 0.39f ? 
            this.rotlerp(this.dog.getYRot(), target_yrot, 90f)
            : target_yrot;
        
        this.dog.setYRot(apporaching_yrot);
        this.dog.setSpeed((float) speed);
        
        var b0 = this.dog.blockPosition();
        var b0_state = this.dog.level().getBlockState(b0);
        var b0_collision = b0_state.getCollisionShape(this.dog.level(), b0);
        boolean dyRequiresJump = 
            (dy > (double)this.dog.maxUpStep() 
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
            this.dog.setYRot(target_yrot);
            this.dog.getJumpControl().jump();
            this.operation = MoveControl.Operation.JUMPING;
        }
        return;
    }

    private void doDogStrafe() {
        float speed = (float) this.dog.getAttributeValue(Attributes.MOVEMENT_SPEED);
        speed *= (float) this.speedModifier;
        
        float check_formard = this.strafeForwards;
        float check_right = this.strafeRight;
        float check_length = Mth.sqrt(check_formard * check_formard + check_right * check_right);

        //normalize if greater than 1
        if (check_length > 1) {
            check_formard /= check_length;
            check_right /= check_length;
        }

        check_formard *= speed;
        check_right *= speed;
        
        float yrot_x = Mth.sin(this.dog.getYRot() * Mth.DEG_TO_RAD);
        float yrot_z = Mth.cos(this.dog.getYRot() * Mth.DEG_TO_RAD);
        
        //2d transform the check vector into the Dog view space.
        float check_x = check_right * yrot_z + check_formard * (-yrot_x);
        float check_z = check_right * yrot_x + check_formard * yrot_z;
        
        if (!this.isWalkableStrafe(check_x, check_z)) {
            this.dog.setSpeed(0);
            this.dog.setZza(0);
            this.dog.setXxa(0);
            return;
        }

        this.mob.setSpeed(speed);
        this.mob.setZza(this.strafeForwards);
        this.mob.setXxa(this.strafeRight);
    }

    private boolean isWalkableStrafe(float dx, float dz) {
        var nav = this.dog.getNavigation();
        if (nav == null)
            return false;
        var node_eval = nav.getNodeEvaluator();
        if (node_eval == null)
            return false;

        var check_pos = BlockPos.containing(
            this.dog.getX() + dx, 
            this.mob.getBlockY(), 
            this.mob.getZ() + dz
        ); 
        boolean is_walkable = node_eval.getPathType(this.dog, check_pos) == PathType.WALKABLE;
        return is_walkable;
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
