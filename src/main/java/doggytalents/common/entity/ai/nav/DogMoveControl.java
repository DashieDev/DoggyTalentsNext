package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
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
        double dy = 0;
        if (this.hasWanted()) {
            dy = Math.abs(this.getWantedY() - this.dog.getY());
        }
        if (
            this.operation == MoveControl.Operation.MOVE_TO
            && dy > 0.75
        ) {
            final float SNEAK_SPEED_1 = 0.35f;
            final float SNEAK_SPEED_2 = 0.25f;

            this.operation = MoveControl.Operation.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedZ - this.mob.getZ();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;
            if (d3 < (double)2.5000003E-7F) {
                this.mob.setZza(0.0F);
                return;
            }

            float f9 = (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), f9, 90.0F));
            float speed_cap = dy > 1.75 ? SNEAK_SPEED_2 : SNEAK_SPEED_1;
            float speed = Math.min(speed_cap, 
                (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            this.mob.setSpeed(speed);
            BlockPos blockpos = this.mob.blockPosition();
            BlockState blockstate = this.mob.level().getBlockState(blockpos);
            VoxelShape voxelshape = blockstate.getCollisionShape(this.mob.level(), blockpos);
            if (d2 > (double)this.mob.getStepHeight() && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.mob.getBbWidth()) || !voxelshape.isEmpty() && this.mob.getY() < voxelshape.max(Direction.Axis.Y) + (double)blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES)) {
                this.mob.getJumpControl().jump();
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
