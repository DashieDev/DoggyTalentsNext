package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.List;

import doggytalents.ChopinLogger;
import doggytalents.DoggyTalents;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.phys.AABB;

public class GuardModeGoal extends NearestAttackableTargetGoal<Mob> {

    private final Dog dog;
    private LivingEntity owner;

    //Guard Mode Job is to make sure everything in this radius around the owner is safe
    //If there is an enermy in this radius, the dog will attack the enermy until it either
    //leaves the radius or dies.
    private static final int GUARD_DISTANCE_SQR = 25;
    private static final int GUARD_DISTANCE = 5;

    public GuardModeGoal(Dog dog) {
        super(dog, Mob.class, 3, false, false, (e) -> {
            if (!(e instanceof Enemy)) return false;
            if (dog.isMode(EnumMode.GUARD_FLAT)) {
                if (e instanceof AbstractPiglin) {
                    var owner = dog.getOwner();
                    if (owner != null) {
                        for (var stack : owner.getArmorSlots()) {
                            if (stack.makesPiglinsNeutral(owner)) {
                                return false;
                            }
                        }
                    }
                }
                if (e instanceof ZombifiedPiglin) return false;
                if (e instanceof EnderMan) return false;
            }
            return true;
        });
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        this.owner = this.dog.getOwner();
        
        if (this.owner == null) return false;

        if (!this.dog.isMode(EnumMode.GUARD, EnumMode.GUARD_FLAT))
            return false;

        if (!super.canUse()) return false;

        return true;
    }

    @Override
    protected double getFollowDistance() {
        return 6D;
    }

    @Override
    protected void findTarget() {
       this.target = this.dog.level.getNearestEntity(this.targetType, this.targetConditions, this.dog, this.owner.getX(), this.owner.getEyeY(), this.owner.getZ(), this.getTargetSearchArea(this.getFollowDistance()));
    }

    /**
     * @author DashieDev
     */
    public static class Minor extends Goal {

        private final Dog dog;
        private LivingEntity owner;
        private Monster nearestDanger;
        private int tickUntilSearch = 0;
        private int tickUntilGrowl = 0;
        private int tickUntilPathRecalc = 0;

        private final int SEARCH_RADIUS = GUARD_DISTANCE;

        public Minor(Dog dog) {
            this.dog = dog;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {

            if (!this.dog.isMode(EnumMode.GUARD_MINOR))
                return false;

            this.owner = this.dog.getOwner();
        
            if (this.owner == null) return false;

            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void start() {
            this.tickUntilGrowl = 0;
            this.tickUntilSearch = 0;
        }

        @Override
        public void tick() { 
            if (this.nearestDanger != null)
            this.dog.getLookControl().setLookAt(this.nearestDanger, 10.0F, this.dog.getMaxHeadXRot());
            if (dog.distanceToSqr(owner) > 2 && --this.tickUntilPathRecalc <= 0) {
                this.tickUntilPathRecalc = 5;
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                    //Outside guard radius -> tp 
                    //TODO Swimmer dog won't be able to tp when guard underwater.
                    if (this.dog.distanceToSqr(this.owner) > GUARD_DISTANCE_SQR) {
                        DogUtil.guessAndTryToTeleportToOwner(dog, owner, 4);
                    } else {
                        this.dog.getNavigation().moveTo(this.owner, 1.5);
                    }
                }
            }

            if (--this.tickUntilSearch <= 0) {
                this.tickUntilSearch = 10;
                boolean wasSafe = this.nearestDanger == null;
                this.findDanger();
                if (this.nearestDanger != null && wasSafe ) {
                    this.tickUntilGrowl = 0;
                }
            }

            if (this.nearestDanger != null) {
                if (--this.tickUntilGrowl <= 0) {
                    this.tickUntilGrowl = 25;
                    dog.playSound(
                        SoundEvents.WOLF_GROWL, 
                        // Scream for owner in case he can't hear him because 
                        // he is listening to some intense RACHMANINOV
                        1, 
                        
                        (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F
                    );
                }
                
                ChopinLogger.lwn(this.dog, "Hey! danger!");
                ChopinLogger.l(""+this.tickUntilSearch);
            } 
        }

        protected void findDanger() {
            if (dog.getDogLevel(DoggyTalents.RESCUE_DOG) > 0) {
                //Rescue Dog don't find alert owner.
                //This allows her to guard the owner's or other
                //guard dogs' HEALTH instead
                return;
            }
            this.nearestDanger = this.dog.level
                .getNearestEntity(
                    Monster.class,
                    TargetingConditions.forCombat().selector( target -> {
                        if (dog.getDogLevel(DoggyTalents.CREEPER_SWEEPER) > 0) {
                            //Creeper Sweeper dog only detect creeper in this mode
                            return (target instanceof Creeper);
                        } else {
                            return true;
                        }
                    }
                    ), 
                    this.dog,
                    this.owner.getX(),
                    this.owner.getEyeY(),
                    this.owner.getZ(),
                    new AABB(this.owner.blockPosition()).inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS)
                );
        }

    }

    /**
     * @author DashieDev
     */
    public static class Major extends Goal {

        private final Dog dog;
        private LivingEntity owner;
        private int tickUntilPathRecalc = 0;


        public Major(Dog dog) {
            this.dog = dog;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {

            
            if (!this.dog.isMode(EnumMode.GUARD, EnumMode.GUARD_FLAT))
                return false;

            this.owner = this.dog.getOwner();
        
            if (this.owner == null) return false;
            
            var target = this.dog.getTarget();
            if (target != null) {
                // If target is in guard distance, then let the dog attack until target is no longer in.
                if (target.distanceToSqr(this.owner) <= GUARD_DISTANCE_SQR) {
                    return false;
                }
            }

            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void tick() { 
            
            if (dog.distanceToSqr(owner) > 2 && --this.tickUntilPathRecalc <= 0) {
                this.tickUntilPathRecalc = 5;
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                    //Outside guard radius -> tp 
                    if (this.dog.distanceToSqr(this.owner) > GUARD_DISTANCE_SQR) {
                        DogUtil.guessAndTryToTeleportToOwner(dog, owner, 4);
                    } else {
                        this.dog.getNavigation().moveTo(this.owner, 1.5);
                    }
                }
            }
        }

    }
}
