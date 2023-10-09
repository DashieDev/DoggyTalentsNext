package doggytalents.common.entity.ai.nav;

import doggytalents.common.entity.Dog;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class DogBodyRotationControl extends BodyRotationControl {

    private static final int HEAD_STABLE_ANGLE = 15;
    private static final int DELAY_UNTIL_STARTING_TO_FACE_FORWARD = 10;
    private static final int HOW_LONG_IT_TAKES_TO_FACE_FORWARD = 10;
    private int headStableTime;
    private float lastStableYHeadRot;

    private Dog dog;

    public DogBodyRotationControl(Dog dog) {
        super(dog);
        this.dog = dog;
    }
    
    public void clientTick() {
        if (this.isMoving()) {
            this.dog.yBodyRot = this.dog.getYRot();
            this.rotateHeadIfNecessary();
            this.lastStableYHeadRot = this.dog.yHeadRot;
            this.headStableTime = 0;
        } else {
            if (Math.abs(this.dog.yHeadRot - this.lastStableYHeadRot) > 15.0F) {
                this.headStableTime = 0;
                this.lastStableYHeadRot = this.dog.yHeadRot;
                this.rotateBodyIfNecessary();
            } else {
                ++this.headStableTime;
                if (this.headStableTime > 10) {
                    this.rotateHeadTowardsFront();
                }
            }
        }
    }

    private void rotateBodyIfNecessary() {
        this.dog.yBodyRot = Mth.rotateIfNecessary(this.dog.yBodyRot, this.dog.yHeadRot, (float)this.dog.getMaxHeadYRot());
    }

    private void rotateHeadIfNecessary() {
        this.dog.yHeadRot = Mth.rotateIfNecessary(this.dog.yHeadRot, this.dog.yBodyRot, (float)this.dog.getMaxHeadYRot());
    }

    private void rotateHeadTowardsFront() {
        int i = this.headStableTime - 10;
        float f = Mth.clamp((float)i / 10.0F, 0.0F, 1.0F);
        float f1 = (float)this.dog.getMaxHeadYRot() * (1.0F - f);
        this.dog.yBodyRot = Mth.rotateIfNecessary(this.dog.yBodyRot, this.dog.yHeadRot, f1);
    }

    private boolean isMoving() {
        double d0 = this.dog.getX() - this.dog.xo;
        double d1 = this.dog.getZ() - this.dog.zo;
        return d0 * d0 + d1 * d1 > (double)2.5000003E-7F;
    }
}
