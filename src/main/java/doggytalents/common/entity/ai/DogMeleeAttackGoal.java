package doggytalents.common.entity.ai;

import java.util.EnumSet;

import org.jetbrains.annotations.NotNull;

import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.Dog.LowHealthStrategy;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

/**
 * @author DashieDev
 */
public class DogMeleeAttackGoal extends Goal {
   protected final Dog dog;
   private final double speedModifier;
   private final boolean followingTargetEvenIfNotSeen;
   private int ticksUntilPathRecalc = 10;
   private int ticksUntilNextAttack;
   private int awayFromOwnerDistance;

   private int timeOutTick;
   private int waitingTick;
   private BlockPos.MutableBlockPos dogPos0;

   private int detectReachPenalty = 0;

   //Tuning : START_DIS must be as low as possible to increase the attacking accuracy, while still keeping it noticable. 
   private final float START_LEAPING_AT_DIS_SQR = 2;
   private final float DONT_LEAP_AT_DIS_SQR = 1;
   private final float LEAP_YD = 0.4F;
   

   public DogMeleeAttackGoal(Dog dog, double speedModifier, boolean followingTargetIfNotSeen, int awayFromOwnerDistance, int timeOutTick) {
      this.dog = dog;
      this.speedModifier = speedModifier;
      this.followingTargetEvenIfNotSeen = followingTargetIfNotSeen;
      this.awayFromOwnerDistance = awayFromOwnerDistance;
      this.timeOutTick = timeOutTick;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean canUse() {

      if (!this.dog.getMode().shouldAttack()) return false;

      if (
         this.dog.isDogLowHealth() 
         && this.dog.getLowHealthStrategy() == LowHealthStrategy.RUN_AWAY
      ) {
         return false;
      }

      if (this.dog.fallDistance > 7) return false;
      
      var owner = this.dog.getOwner();

      if (owner != null) {
         if (this.dog.distanceToSqr(owner) > this.getMaxDistanceAwayFromOwner()) 
            return false;
      }

      LivingEntity target = this.dog.getTarget();
      if (target == null) {
         return false;
      } else if (!target.isAlive()) {
         this.dog.setTarget(null); // Disacrd dead target no matter what
         return false;
      }

      if (--this.detectReachPenalty > 0) return false;
      
      this.detectReachPenalty = 5;
      
      /*
       * Only begin attacking if the dog can ACTUALLY reach the entity
       * 
       * Although in vanilla TargetGoal there is already an option to require that
       * the entity must be in reach, which COINCIDENTALLY determined using 
       * the exact same procedure. But in order to enable that, the 
       * Goal (ex. OwnerAttack or OwnerBeingAttack) may have to be REIMPLEMENTED, DECOUPLED,
       * and any parent class code (still the child of TargetGoal)
       * must be physically copied over cause almost every of them have the super 
       * constructor to TargetGoal mustSee parameter fixed to false, or 
       * to a parent class which the preceding condition applies. That produce a bunch of
       * BOILERPLATES which we don't want. And also the vanilla code also for some reason, 
       * cache the result of the entity after the code executed so every other entities
       * checked before the tickUntilCodeReExecution runs out would also give the same result.
       * Which means that the dog may consider a non-reachable nearest target to be reached
       * right after he check the other actually reachable target.
       * 
       * The code below, which is the procedure to determine if the dog can reach 
       * (which is relatively expensive)
       * is only executed when the dog actually have a target to attack, instead of all the time
       * when finding targets, hence its potiental efficency gain. Also the dog should only 
       * attack entities that is in reach regardless of any conditions. Otherwise, the result is kinda
       * stoopid. Also when target is not qualified, it will set it to null so that the target goal can 
       * refind targets.
       *  
       */
      var p = dog.getNavigation().createPath(target, 1);
      double d0 = dog.distanceToSqr(target);
      
      if (p == null) return false;

      this.detectReachPenalty += (d0 > 256 ? 10 : 5);

      if (!DogUtil.canPathReachTargetBlock(dog, p, target.blockPosition(), 1, dog.getMaxFallDistance())) { 
         this.dog.setTarget(null);
         return false;
      } 
      
      return true;
   }

   public boolean canContinueToUse() {

      if (!this.dog.getMode().shouldAttack()) return false;

      if (this.dog.fallDistance > 7) return false;

      var owner = this.dog.getOwner();

      if (owner != null) {
         if (this.dog.distanceToSqr(owner) > this.getMaxDistanceAwayFromOwner()) 
            return false;
      }

      if (this.waitingTick > this.timeOutTick) return false;
      
      LivingEntity livingentity = this.dog.getTarget();

      if (livingentity == null) {
         return false;
      } else if (!livingentity.isAlive()) {
         return false;
      } else if (!this.followingTargetEvenIfNotSeen) {
         return !this.dog.getNavigation().isDone();
      } else if (!this.dog.isWithinRestriction(livingentity.blockPosition())) {
         return false;
      } else {
         return !(livingentity instanceof Player)
               || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
      }
   }

   public void start() {
      this.dog.getNavigation().moveTo(this.dog.getTarget(), this.speedModifier);
      this.dog.setAggressive(true);
      this.ticksUntilPathRecalc = 0;
      this.ticksUntilNextAttack = 0;
      this.waitingTick = 0;
      this.dogPos0 = this.dog.blockPosition().mutable();
   }

   public void stop() {
      LivingEntity livingentity = this.dog.getTarget();
      
      //The goal now runs continuously from when the dog start attacking to when 
      //the dog stop attacking for practical reasons, (used to be impractically
      //interupted by LeapAtTargetGoal, but now that goal is integrated in)
      //That's why dog now always discard the target when stop attacking.
      this.dog.setTarget((LivingEntity) null); 
      
      this.dog.setAggressive(false);
      this.dog.getNavigation().stop();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   //TODO : Global problem : 
   //due to the dog move control inaccuracy, the dog may be failed to land on a safe platform because
   //of the "safe" area is only an 1-block edge which stick out a cliff or something. This is a part of
   //a bigger problem, the inaccuracy of the moveControl.
   public void tick() {
      
      var e = this.dog.getTarget();
      if (e == null) return;
      var n = this.dog.getNavigation();
      var dog_bp = this.dog.blockPosition();
      var target_bp = e.blockPosition();

      if (dog_bp.equals(this.dogPos0)) {
         ++this.waitingTick;
      } else {
         this.waitingTick = 0;
         this.dogPos0.set(dog_bp.getX(), dog_bp.getY(), dog_bp.getZ());
      }

      this.dog.getLookControl().setLookAt(e, 30.0F, 30.0F);
      double d0 = this.dog.distanceToSqr(e.getX(), e.getY(), e.getZ());
      if ((this.followingTargetEvenIfNotSeen
            || this.dog.getSensing().hasLineOfSight(e))
            && this.ticksUntilPathRecalc <= 0
      ) {
         this.ticksUntilPathRecalc = 10;
         n.moveTo(e, this.speedModifier);
      }

      --this.ticksUntilPathRecalc;
      --this.ticksUntilNextAttack;
      
      if (  
         n.isDone() 
         && dog_bp.distSqr(target_bp) <= 2.25 
         && !this.canReachTarget(e, d0) 
         //Make sure target is still in safe area 
         && this.isTargetInSafeArea(dog, e)
      ) {
         dog.getMoveControl().setWantedPosition(e.getX(), e.getY(), e.getZ(), this.speedModifier);
      }
      if(n.isDone() && dog.tickCount % 2 != 0 && !this.canReachTarget(e, d0)) {
         this.ticksUntilPathRecalc = 0;
      }
      if (this.checkAndPerformAttack(e, d0)) this.waitingTick = 0;

      this.checkAndLeapAtTarget(e);

   }

   /*
    * Integrated DogLeapAtTargetGoal to here to allow dog to attack mid air
    * One of the reasons is to avoid weird scenario as where the dog repeatedly jump
    * at an entity while causing no harm for a long period despite even touching it.
    */
   protected void checkAndLeapAtTarget(LivingEntity target) {
      if (!this.canLeapAtTarget(target)) return;

      Vec3 vec3 = this.dog.getDeltaMovement();
      Vec3 vec31 = new Vec3(target.getX() - this.dog.getX(), 0.0D, target.getZ() - this.dog.getZ());
      if (vec31.lengthSqr() > 1.0E-7D) {
         vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(0.2D));
      }

      this.dog.setDeltaMovement(vec31.x, (double)this.LEAP_YD, vec31.z);
   }

   protected boolean canLeapAtTarget(@NotNull LivingEntity target) {

      if (this.dog.isVehicle()) return false;

      if (!this.dog.onGround()) return false;

      if (!target.onGround()) return false;

      double d0 = this.dog.distanceToSqr(target);

      if (!(d0 >= DONT_LEAP_AT_DIS_SQR && d0 <= START_LEAPING_AT_DIS_SQR)) return false;
               
      if (this.dog.getRandom().nextInt(reducedTickDelay(5)) != 0) return false;
               
      var tpos = target.blockPosition();
               
      if (WalkNodeEvaluator.getBlockPathTypeStatic(this.dog.level(), tpos.mutable()) !=BlockPathTypes.WALKABLE) {
         return false;
      }
               
      var v_offset = new Vec3(target.getX() - this.dog.getX(), 0.0D, target.getZ() - this.dog.getZ()).normalize();
      var v_dog = dog.position();
      for (int i = 1; i <=3; ++i) {
         v_dog = v_dog.add(v_offset);
         if (WalkNodeEvaluator.getBlockPathTypeStatic(this.dog.level(), BlockPos.containing(v_dog).mutable()) !=BlockPathTypes.WALKABLE)  {
            return false;
         }
      }
      
      return true;   
      
   }

   //TODO make dog be able to attack in the air
   protected boolean checkAndPerformAttack(LivingEntity target, double distanceToTargetSqr) {
      if (this.canReachTarget(target, distanceToTargetSqr) && this.ticksUntilNextAttack <= 0) {
         this.resetAttackCooldown();

         this.dog.swing(InteractionHand.MAIN_HAND);
         this.dog.doHurtTarget(target);
         return true;
      }
      return false;
   }

   protected boolean isTargetInSafeArea(Dog dog, LivingEntity target) {
      var type = WalkNodeEvaluator.getBlockPathTypeStatic(dog.level(), target.blockPosition().mutable());
      for (var x : dog.getAlterations()) {
         if (x.isBlockTypeWalkable(dog, type).shouldSwing()) {
            type = BlockPathTypes.WALKABLE;
            break;
         }
      }
      return type == BlockPathTypes.WALKABLE;
   }

   protected boolean canReachTarget (LivingEntity target,  double distanceToTargetSqr) {
      return this.getAttackReachSqr(target) >= distanceToTargetSqr;
   }

   protected void resetAttackCooldown() {
      this.ticksUntilNextAttack = this.adjustedTickDelay(20);
   }

   protected int getTicksUntilNextAttack() {
      return this.ticksUntilNextAttack;
   }

   protected int getAttackInterval() {
      return this.adjustedTickDelay(20);
   }

   protected double getAttackReachSqr(LivingEntity target) {
      return (double) (this.dog.getBbWidth() * 2.0F * this.dog.getBbWidth() * 2.0F + target.getBbWidth());
   }

   protected double getMaxDistanceAwayFromOwner() {
      return this.awayFromOwnerDistance*this.awayFromOwnerDistance;
   }
}
