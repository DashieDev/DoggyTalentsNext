package doggytalents.common.entity.ai;

import java.util.EnumSet;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class DogLeapAtTargetGoal extends Goal {
   private final Dog dog;
   private LivingEntity target;
   private final float yd;

   //Tuning : START_DIS must be as low as possible to increase the attacking accuracy, while still keeping it noticable. 
   private final float START_LEAPING_AT_DIS_SQR = 2;
   private final float DONT_LEAP_AT_DIS_SQR = 1;

   //TODO Dog cannot attack while this goal is run (because MOVE is set as a MutexFlag)
   public DogLeapAtTargetGoal(Dog p_25492_, float p_25493_) {
      this.dog = p_25492_;
      this.yd = p_25493_;
      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   public boolean canUse() {
      if (this.dog.isVehicle()) {
         return false;
      } else {
        this.target = this.dog.getTarget();
        if (this.target == null) return false;
        if (!this.target.onGround()) return false;
        double d0 = this.dog.distanceToSqr(this.target);
        if (d0 >= DONT_LEAP_AT_DIS_SQR && d0 <= START_LEAPING_AT_DIS_SQR) {
            if (!this.dog.onGround()) {
                return false;
            } else {
               if (this.dog.getRandom().nextInt(reducedTickDelay(5)) != 0) return false;
               var tpos = target.blockPosition();
               if (WalkNodeEvaluator.getBlockPathTypeStatic(this.dog.level(), tpos.mutable()) !=BlockPathTypes.WALKABLE) {
                  return false;
               }
               
               var v0 = new Vec3(this.target.getX() - this.dog.getX(), 0.0D, this.target.getZ() - this.dog.getZ()).normalize();
               var bp1 = dog.blockPosition();
   
               var v1 = new Vec3(bp1.getX(), bp1.getY(), bp1.getZ());
               for (int i = 1; i <=2; ++i) {
                  v1 = v1.add(v0);
                  if (WalkNodeEvaluator.getBlockPathTypeStatic(this.dog.level(), new BlockPos(Mth.floor(v1.x), Mth.floor(v1.y), Mth.floor(v1.z)).mutable()) !=BlockPathTypes.WALKABLE)  {
                     return false;
                  }
               }
               
               return true;
            }
        } else {
            return false;
        }
      }
   }

   public boolean canContinueToUse() {
      return !this.dog.onGround();  
   }

   public void start() {
      Vec3 vec3 = this.dog.getDeltaMovement();
      Vec3 vec31 = new Vec3(this.target.getX() - this.dog.getX(), 0.0D, this.target.getZ() - this.dog.getZ());
      if (vec31.lengthSqr() > 1.0E-7D) {
         vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(0.2D));
      }

      this.dog.setDeltaMovement(vec31.x, (double)this.yd, vec31.z);
   }

}
