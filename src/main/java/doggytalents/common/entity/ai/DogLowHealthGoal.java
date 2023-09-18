package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.List;

import doggytalents.ChopinLogger;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.IThrowableItem;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.Dog.LowHealthStrategy;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

/**
 * @author DashieDev
 */
public class DogLowHealthGoal {

    public static class StickToOwner extends Goal {
        private final Dog dog;
        private final float stopDist;

        private LivingEntity owner;
        private int timeToRecalcPath;
        private int tickTillSearchForTp = 0;
        private int tickTillInitTeleport = 0;
        private int tickTillInitWhine = 0;
        private float oldWaterCost;

        private boolean whine = false;

        public StickToOwner(Dog dogIn) {
            this.dog = dogIn;
            this.stopDist = 1.5f;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.tickTillInitTeleport > 0) {
                --this.tickTillInitTeleport;
            }

            if (this.tickTillInitWhine > 0) {
                --this.tickTillInitWhine;
            }

            LivingEntity owner = this.dog.getOwner();
            if (owner == null) return false;
            if (!this.dog.isDoingFine()) return false;
            if (!this.dog.isDogLowHealth()) return false;
            if (this.dog.getLowHealthStrategy() != LowHealthStrategy.STICK_TO_OWNER)
                return false;
            if (owner.isSpectator()) return false;
            if (this.dog.isInSittingPose()) return false;
            this.owner = owner;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return this.dog.isDogLowHealth();
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.dog.getPathfindingMalus(BlockPathTypes.WATER);
            this.dog.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
            this.whine = true;
            if (this.tickTillInitTeleport <= 0) {
                this.tickTillInitTeleport = 10;
                //Ensure this function never get called too many times.
                DogUtil.dynamicSearchAndTeleportToOwnwer(dog, owner, 4);
            }
            ChopinLogger.l("Low Health started!");
        }

        @Override
        public void stop() {
            if (this.dog.hasBone()) {
                double distanceToOwner = this.owner.distanceToSqr(this.dog);
                if (distanceToOwner <= this.stopDist * this.stopDist) {
                    IThrowableItem throwableItem = this.dog.getThrowableItem();
                    ItemStack fetchItem = throwableItem != null ? throwableItem.getReturnStack(this.dog.getBoneVariant()) : this.dog.getBoneVariant();

                    this.dog.spawnAtLocation(fetchItem, 0.0F);
                    this.dog.setBoneVariant(ItemStack.EMPTY);
                }
            }

            this.owner = null;
            this.dog.getNavigation().stop();
            this.dog.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
            this.dog.setBegging(false);
        }

        //TODO : Group the msg when dog msg owner about him being hurt based on how the dog was previously hurt, 
        //and make the dog choose accordingly
        @Override
        public void tick() {

            if (this.tickTillInitWhine > 0) {
                --this.tickTillInitWhine;
            }

            if (this.dog.distanceToSqr(this.owner) > stopDist*stopDist) {
                this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = 5;
                    DogUtil.moveToOwnerOrTeleportIfFarAway(
                        dog, owner, this.dog.getUrgentSpeedModifier(),
                        25, false, 
                        --this.tickTillSearchForTp <= 0,
                        400, 
                        dog.getMaxFallDistance());
                    if (this.tickTillSearchForTp <= 0) this.tickTillSearchForTp = 10;
                }
            }  else {
                if (this.whine && this.tickTillInitWhine <= 0) {
                    this.whine = false;
                    this.owner.sendSystemMessage(Component.translatable("dog.msg.low_health." + this.dog.getRandom().nextInt(4), this.dog.getName()));
                    this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
                    this.tickTillInitWhine = 40;
                }
                this.dog.getLookControl().setLookAt(this.owner);
            }
        }


    }

    public static class RunAway extends Goal {

        public static final int CAUTIOUS_RADIUS = 6;

        private Dog dog;
        private float oldWaterCost;
        private int tickTillMoveAwayRecalc = 0;
        private int tickTillCheckTeleport = 0;
        private LivingEntity owner;
        private Vec3 moveAwayPos;
        private List<Mob> enemies = List.of();

        private int tickTillUpdateEnemyRecalc = 0;

        public RunAway(Dog dog) {
            this.dog = dog;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {

            if (this.tickTillUpdateEnemyRecalc > 0)
                --this.tickTillUpdateEnemyRecalc;

            if (!this.dog.isDoingFine()) return false;
            if (this.dog.isInSittingPose()) return false;
            if (!this.dog.isDogLowHealth()) return false;
            if (this.dog.getLowHealthStrategy() != LowHealthStrategy.RUN_AWAY) {
                return false;
            }

            if (this.dog.isMode(EnumMode.GUARD, EnumMode.GUARD_FLAT, EnumMode.GUARD_MINOR))
                return false;

            var owner = this.dog.getOwner();
            if (owner == null) return false;

            this.owner = owner;

            this.enemies = List.of();

            if (this.tickTillUpdateEnemyRecalc <= 0) {
                this.tickTillUpdateEnemyRecalc = 3;
                this.updateEnemies(CAUTIOUS_RADIUS);
            }

            if (this.enemies.isEmpty()) return false; 

            return true;
        }

        @Override
        public boolean canContinueToUse() {
            if (this.enemies.isEmpty()) return false;

            return this.dog.isDogLowHealth();
        }

        @Override
        public void start() {
            this.oldWaterCost = this.dog.getPathfindingMalus(BlockPathTypes.WATER);
            this.dog.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
            this.dog.getNavigation().stop();
            this.moveAwayPos = this.findMoveAwayPos(owner);
            if (this.moveAwayPos != null) {
                this.tickTillMoveAwayRecalc = 5;
                dog.getNavigation().moveTo(
                    moveAwayPos.x, moveAwayPos.y, moveAwayPos.z, 
                    this.dog.getUrgentSpeedModifier()
                );
            }
        }

        @Override
        public void tick() {

            var dogNav = dog.getNavigation();
            if (--this.tickTillMoveAwayRecalc <= 0) {
                tickTillMoveAwayRecalc = 3 + dog.getRandom().nextInt(3);
                updateMoveAway(owner);
                if (this.moveAwayPos != null) {
                    ChopinLogger.sendToOwner(dog,
                        "Going to : " + moveAwayPos
                        + " distance away : " + (moveAwayPos.distanceTo(dog.position()))
                    );
                    dogNav.moveTo(
                        moveAwayPos.x, moveAwayPos.y, moveAwayPos.z, 
                        this.dog.getUrgentSpeedModifier()
                    );
                } else {
                    ChopinLogger.sendToOwner(dog, "what?");
                }
            }

            if (--this.tickTillCheckTeleport <= 0) {
                this.tickTillCheckTeleport = 5;
                if (this.dog.distanceToSqr(owner) > 400) {
                    DogUtil.dynamicSearchAndTeleportToOwnwer(dog, owner, 4);
                }
            }
        }

        private void updateEnemies(int search_radius) {
            this.enemies = dog.level().getEntitiesOfClass(
                Mob.class, 
                dog.getBoundingBox().inflate(search_radius, 3, search_radius),
                mob -> mob.isAlive() && mob.getTarget() == dog
            );
        }

        private void updateMoveAway(LivingEntity owner) {
            this.moveAwayPos = null;
            this.updateEnemies(CAUTIOUS_RADIUS);
            this.moveAwayPos = findMoveAwayPos(owner);
        }

        private Vec3 findMoveAwayPos(LivingEntity owner) {
            var centerPos = findAverageCenterOfAllTargetingEnemies();
            if (centerPos == null) return null;
            var ownerPos = owner.position();
            var dogPos = dog.position();
            var avoid_offset = dogPos
                .subtract(centerPos)
                .normalize()
                .scale(8);
            var v_dogPos_ownerPos = ownerPos.subtract(dogPos);
            var alpha = (v_dogPos_ownerPos.dot(avoid_offset))
                /(v_dogPos_ownerPos.length()*avoid_offset.length());
            Vec3 moveAwayPos;
            if (alpha < -0.8) {
                moveAwayPos = dog.tickCount % 2 == 0 ?
                    dogPos.add(avoid_offset.z, 0, -avoid_offset.x)
                    : dogPos.add(-avoid_offset.z, 0, avoid_offset.x);
            } else {
                var avoidPos = dogPos.add(avoid_offset);
                moveAwayPos = new Vec3(
                    (avoidPos.x + ownerPos.x)/2,
                    (avoidPos.y + ownerPos.y)/2,
                    (avoidPos.z + ownerPos.z)/2
                );
            }
            
            return moveAwayPos;
        }

        /*
         * var moveAwayPos = LandRandomPos
                .getPosAway(dog, 16, 7, centerPos);
         */

        public Vec3 findAverageCenterOfAllTargetingEnemies() {
            if (this.enemies.isEmpty()) return null;
            double size = this.enemies.size();
            double avg_x = 0, avg_y = 0, avg_z = 0;
            for (var enemy : enemies) {
                avg_x += enemy.getX();
                avg_y += enemy.getY();
                avg_z += enemy.getZ();
            }
            avg_x /= size;
            avg_y /= size;
            avg_z /= size;

            return new Vec3(avg_x, avg_y, avg_z);
        }

        @Override
        public void stop() {
            this.dog.getNavigation().stop();
            this.dog.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
            this.owner = null;
            this.enemies = List.of();
            this.moveAwayPos = null;
        }
    }

}
