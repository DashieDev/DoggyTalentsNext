package doggytalents.common.entity.ai.nav;

import doggytalents.ChopinLogger;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.anim.DogAnimation;
import doggytalents.common.talent.FlyingFurballTalent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;

public class DogFlyingMoveControl extends FlyingMoveControl {
    
    private Dog dog;
    private FlyingFurballTalent talentInst;

    public DogFlyingMoveControl(Dog dog, FlyingFurballTalent talentInst) {
        super(dog, 10, false);
        this.dog = dog;
        this.talentInst = talentInst;
    }

    public void tick() {

        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;

            double dx = this.wantedX - this.dog.getX();
            double dy = this.wantedY - this.dog.getY();
            double dz = this.wantedZ - this.dog.getZ();
            double l_sqr = dx * dx + dy * dy + dz * dz;
            if (l_sqr < (double)2.5000003E-7F) {
               this.dog.setYya(0.0F);
               this.dog.setZza(0.0F);
               this.dog.setNoGravity(false);
               return;
            }
            
            if (
                !dog.getNavigation().isDone()
                && dog.onGround()
                && dy > 0
            ) {
                dog.setAnim(DogAnimation.FLY_JUMP_START);
            }

            if (!dog.getNavigation().isDone() && !dog.isDogFlying()) {
                dog.setDogFlying(true);
                talentInst.startGliding(dog);
                this.dog.setNoGravity(true);
            }
   
            float wantedYRot = (float)(Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
            float approachingYRot = this.rotlerp(this.dog.getYRot(), wantedYRot, 90.0F);
            this.dog.setYRot(approachingYRot);
            float speed;
            if (this.dog.onGround()) {
               speed = (float)(this.speedModifier * this.dog.getAttributeValue(Attributes.MOVEMENT_SPEED));
            } else {
               speed = (float)(this.speedModifier * this.dog.getAttributeValue(Attributes.FLYING_SPEED));
            }
   
            this.dog.setSpeed(speed);
            double l_xz = Math.sqrt(dx * dx + dz * dz);
            if (Math.abs(dy) > (double)1.0E-5F || Math.abs(l_xz) > (double)1.0E-5F) {
               float wantedXRot = (float)(-(Mth.atan2(dy, l_xz) * (double)(180F / (float)Math.PI)));
               float approachingXRot = this.rotlerp(this.dog.getXRot(), wantedXRot, this.dog.getMaxHeadXRot());
               this.dog.setXRot(approachingXRot);
               
               float zAngleMod = Mth.cos(this.dog.getXRot() * Mth.DEG_TO_RAD);
               float yAngleMod = Mth.sin(this.dog.getXRot() * Mth.DEG_TO_RAD);
               this.dog.zza = zAngleMod * speed;
               this.dog.yya = -yAngleMod * speed;
            }
        } else {
            boolean
            hoversInPlace = false;
            if (!hoversInPlace) {
                this.dog.setNoGravity(false);
            }

            this.dog.setSpeed(0.0F);
            this.dog.setXxa(0.0F);
            this.dog.setYya(0.0F);
            this.dog.setZza(0.0F);
        }
        

    }

}
