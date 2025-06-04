package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import doggytalents.forge_imitate.atrrib.ForgeMod;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class DogSwimMoveControl extends MoveControl {

    private Dog dog;

    public DogSwimMoveControl(Dog dog) {
        super(dog);
        this.dog = dog;
    }
    
    public void tick() {
        if (
            this.operation == MoveControl.Operation.MOVE_TO 
            && !this.dog.getNavigation().isDone()
            && this.dog.isInWater()
        ) {
            double dx = this.wantedX - this.dog.getX();
            double dy = this.wantedY - this.dog.getY();
            double dz = this.wantedZ - this.dog.getZ();
            double l_sqr = dx * dx + dy * dy + dz * dz;
            if (l_sqr < (double)2.5000003E-7F) {
                this.dog.setZza(0.0F);
                return;
            }
            double l_xz = Math.sqrt(dx * dx + dz * dz);

            float speed = (float)(this.speedModifier * this.dog.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.dog.setSpeed(speed);

            float dy_abs = Mth.abs((float)dy);
            if (dy_abs / l_xz >= 6) {
                this.dog.yya = Mth.sign(dy) * speed;
                this.dog.zza = 0;
                return;
            }

            float wantedYRot = (float)(Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
            this.dog.setYRot(this.rotlerp(this.dog.getYRot(), wantedYRot, (float)this.dog.getMaxHeadYRot()));
            this.dog.yBodyRot = this.dog.getYRot();
            this.dog.yHeadRot = this.dog.getYRot();
            
            if (Math.abs(dy) > (double)1.0E-5F || Math.abs(l_xz) > (double)1.0E-5F) {
                float wantedXRot = -((float)(Mth.atan2(dy, l_xz) * (double)(180F / (float)Math.PI)));
                float maxTurnX = dog.getMaxHeadXRot();
                wantedXRot = Mth.clamp(Mth.wrapDegrees(wantedXRot), -maxTurnX, maxTurnX);
                float approachingXRot = this.rotlerp(this.dog.getXRot(), wantedXRot, 5.0F);
                this.dog.setXRot(approachingXRot);
            }

            float f6 = Mth.cos(this.dog.getXRot() * ((float)Math.PI / 180F));
            float f4 = Mth.sin(this.dog.getXRot() * ((float)Math.PI / 180F));
            this.dog.zza = f6 * speed;
            this.dog.yya = -f4 * speed;

            mayCheckAndLeapUpOrDownObstacle(speed);
        } else {
            this.dog.setSpeed(0.0F);
            this.dog.setXxa(0.0F);
            this.dog.setYya(0.0F);
            this.dog.setZza(0.0F);
        }
     }

    private void mayCheckAndLeapUpOrDownObstacle(float speed) {
        if (!this.dog.isInWater())
            return;
        double dy = this.getWantedY() - dog.getY();
        if (Math.abs(dy) <= 0.1)
            return;
        double dx = this.getWantedX() - dog.getX();
        double dz = this.getWantedZ() - dog.getZ();
        double l_xz_sqr = dx * dx + dz * dz;
        double min_lxz = dog.getBbWidth()/2 + 0.5 - 0.1; 
        if (l_xz_sqr < min_lxz * min_lxz)
            return;

        final boolean is_up = dy > 0;
        var check_pos_offset = 
            new Vec3(dx, 0, dz)
            .normalize()
            .scale(dog.getBbWidth()/2 + 0.2)
            .add(0, is_up ? 0 : dog.getBbHeight(), 0);
        var check_pos = BlockPos.containing(
            this.dog.position().add(check_pos_offset)   
        );
        var state = dog.level().getBlockState(check_pos);
        if (!state.getCollisionShape(dog.level(), check_pos).isEmpty()) {
            speed = Mth.clamp(speed, 0, 0.39f);
            this.dog.yya = is_up ? speed : -speed;
        }
    }

}
