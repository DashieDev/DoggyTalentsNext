package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.List;

import doggytalents.DoggyTalents;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.phys.AABB;

public class GuardModeGoal extends NearestAttackableTargetGoal<Monster> {

    private final Dog dog;
    private LivingEntity owner;

    public GuardModeGoal(Dog dogIn) {
        super(dogIn, Monster.class, 3, false, false, (e) -> {
            if (dogIn.isMode(EnumMode.GUARD_FLAT)) {
                if (e instanceof AbstractPiglin) return false;
                if (e instanceof ZombifiedPiglin) return false;
                if (e instanceof EnderMan) return false;
            }
            return true;
        });
        this.dog = dogIn;
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

    public static class Minor extends Goal {

        private final Dog dog;
        private LivingEntity owner;
        private Monster nearestDanger;
        private int tickUntilSearch = 0;
        private int tickUntilGrowl = 0;
        private int tickUntilPathRecalc = 0;

        private final int SEARCH_RADIUS = 4;

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
            if (dog.distanceToSqr(this.dog.getOwner()) > 1.5 && --this.tickUntilPathRecalc <= 0) {
                //The dog always stays close to the owner, and tp when a little bit further
                //So the path is not that long, so interval = 3 is ok
                this.tickUntilPathRecalc = 3;
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                    if (this.dog.distanceToSqr(this.owner) >= 25) {
                        DogUtil.guessAndTryToTeleportToOwner(dog, 4);
                    } else {
                        this.dog.getNavigation().moveTo(this.owner, 1.5);
                    }
                }
            }

            if (--this.tickUntilSearch <= 0) {
                this.tickUntilSearch = 10;
                this.findDanger();
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
                
            } 
        }

        protected void findDanger() {
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
    

            if (this.dog.getTarget() != null) return false;

            return true;
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void tick() { 
            //this.dog.getLookControl().setLookAt(this.dog.getOwner(), 10.0F, this.dog.getMaxHeadXRot());
            if (dog.distanceToSqr(this.dog.getOwner()) > 1.5 && --this.tickUntilPathRecalc <= 0) {
                //The dog always stays close to the owner, and tp when a little bit further
                //So the path is not that long, so interval = 3 is ok
                this.tickUntilPathRecalc = 3;
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                    if (this.dog.distanceToSqr(this.owner) >= 25) {
                        DogUtil.guessAndTryToTeleportToOwner(dog, 4);
                    } else {
                        this.dog.getNavigation().moveTo(this.owner, 1.5);
                    }
                }
            }
        }

    }
}
