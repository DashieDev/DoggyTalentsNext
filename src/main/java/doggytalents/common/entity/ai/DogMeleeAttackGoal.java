package doggytalents.common.entity.ai;

import java.util.EnumSet;


import doggytalents.client.block.model.DogBedItemOverride;
import doggytalents.common.entity.Dog;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;

public class DogMeleeAttackGoal extends Goal {
   protected final Dog dog;
   private final double speedModifier;
   private final boolean followingTargetEvenIfNotSeen;
   // private double pathedTargetX;
   // private double pathedTargetY;
   // private double pathedTargetZ;
   private int ticksUntilPathRecalc = 10;
   private int ticksUntilNextAttack;
   private final int attackInterval = 20;
   private long lastCanUseCheck;
   private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
   private int failedPathFindingPenalty = 0;

   public DogMeleeAttackGoal(Dog p_25552_, double p_25553_, boolean p_25554_) {
      this.dog = p_25552_;
      this.speedModifier = p_25553_;
      this.followingTargetEvenIfNotSeen = p_25554_;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean canUse() {
      // long i = this.dog.level.getGameTime();
      // if (i - this.lastCanUseCheck < 20L) {
      // return false;
      // } else {
      // this.lastCanUseCheck = i;
      LivingEntity livingentity = this.dog.getTarget();
      if (livingentity == null) {
         return false;
      } else if (!livingentity.isAlive()) {
         return false;
      } else {
         return true;
      }
      // else {

      // // this.path = this.dog.getNavigation().createPath(livingentity, 0);
      // // if (this.path != null) {
      // // return true;
      // // } else {
      // ChopinLogger.l("here!");
      // ChopinLogger.l("this.getAttackReachSqr(livingentity) : " +
      // this.getAttackReachSqr(livingentity));
      // ChopinLogger.l("this.dog.distanceToSqr(livingentity.getX(),
      // livingentity.getY(), livingentity.getZ() : " +
      // this.dog.distanceToSqr(livingentity.getX(), livingentity.getY(),
      // livingentity.getZ()));

      // return this.getAttackReachSqr(livingentity) >=
      // this.dog.distanceToSqr(livingentity.getX(), livingentity.getY(),
      // livingentity.getZ());
      // //}
      // }
   }
   // }

   public boolean canContinueToUse() {
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
   }

   public void stop() {
      LivingEntity livingentity = this.dog.getTarget();
      if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
         this.dog.setTarget((LivingEntity) null);
      }

      this.dog.setAggressive(false);
      this.dog.getNavigation().stop();
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   // TODO dog pause occasionally
   public void tick() {
      
      var e = this.dog.getTarget();
      var n = this.dog.getNavigation();
      var dog_bp = this.dog.blockPosition();
      var target_bp = e.blockPosition();

      
      if (e != null) {

         this.dog.getLookControl().setLookAt(e, 30.0F, 30.0F);
         double d0 = this.dog.distanceToSqr(e.getX(), e.getY(), e.getZ());
         if ((this.followingTargetEvenIfNotSeen
               || this.dog.getSensing().hasLineOfSight(e))
               && this.ticksUntilPathRecalc <= 0
         // && (
         // this.pathedTargetX == 0.0D
         // && this.pathedTargetY == 0.0D
         // && this.pathedTargetZ == 0.0D
         // || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY,
         // this.pathedTargetZ) >= 1.0D
         // || this.dog.getRandom().nextFloat() < 0.05F
         // )
         ) {
            this.ticksUntilPathRecalc = 10;

            // this.pathedTargetX = livingentity.getX();
            // this.pathedTargetY = livingentity.getY();
            // this.pathedTargetZ = livingentity.getZ();

            n.moveTo(e, this.speedModifier);

         }
         --this.ticksUntilPathRecalc;
         --this.ticksUntilNextAttack;
         // if dog arrived to destination and the entity is not there, recalc
         // immediately!
         // var target_block = this.dog.getNavigation().getTargetPos();
         // if (target_block != null
         //       && target_block.equals(dog.blockPosition())
         //       && !target_block.equals(livingentity.blockPosition())) {
         //    this.ticksUntilPathRecalc = 0;
         //    ChopinLogger.l("reset");
         // }

         if (n.isDone() && dog_bp.equals(target_bp) && !this.canReachTarget(e, d0)) {
            dog.getMoveControl().setWantedPosition(e.getX(), e.getY(), e.getZ(), this.speedModifier);
         }
         if(n.isDone() && !this.canReachTarget(e, d0)) {
            this.ticksUntilPathRecalc = 0;
         }
         this.checkAndPerformAttack(e, d0);
         //ChopinLogger.l("" + this.ticksUntilNextAttack);
      }
   }

   protected boolean checkAndPerformAttack(LivingEntity target, double distanceToTargetSqr) {
      if (this.canReachTarget(target, distanceToTargetSqr) && this.ticksUntilNextAttack <= 0) {
         this.resetAttackCooldown();

         this.dog.swing(InteractionHand.MAIN_HAND);
         this.dog.doHurtTarget(target);
         return true;
      }
      return false;
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
}
