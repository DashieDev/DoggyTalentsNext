package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.List;

import doggytalents.ChopinLogger;
import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;

public class GuardModeGoal extends NearestAttackableTargetGoal<Monster> {

    private final Dog dog;
    private LivingEntity owner;

    public GuardModeGoal(Dog dogIn, boolean checkSight) {
        super(dogIn, Monster.class, 0, checkSight, false, null);
        this.dog = dogIn;
    }

    @Override
    public boolean canUse() {
        this.owner = this.dog.getOwner();
        
        if (this.owner == null) return false;

        if (!this.dog.isMode(EnumMode.GUARD))
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
        private Monster nearest_danger;
        private int tickUntilRescanDanger = 0;

        private final int SEARCH_RADIUS = 4;

        public Minor(Dog dog) {
            this.dog = dog;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {

            this.owner = this.dog.getOwner();
            
            if (this.owner == null) return false;

            //if (!this.dog.isMode(EnumMode.GUARD_MINOR))
                //return false;

            return true;
        }

        @Override
        public void tick() { 
            if (this.tickUntilRescanDanger <= 0) {
                this.findDanger();
                
                if (this.nearest_danger != null) {
                    dog.playSound(
                        SoundEvents.WOLF_GROWL, 
                        dog.getSoundVolume(), 
                        (dog.getRandom().nextFloat() - dog.getRandom().nextFloat()) * 0.2F + 1.0F

                    );
                    ChopinLogger.lwn(this.dog, "Hey! danger!");
                    this.tickUntilRescanDanger = 60 + this.dog.getRandom().nextInt(40);
                    ChopinLogger.l(""+this.tickUntilRescanDanger);
                } else {
                    this.tickUntilRescanDanger = 10;
                }
                
            } else {
                --this.tickUntilRescanDanger;
            }
        }

        protected void findDanger() {
            this.nearest_danger = this.dog.level
                .getNearestEntity(
                    Monster.class,
                    TargetingConditions.forCombat()
                        .selector(e -> {
                            return this.dog.hasLineOfSight(e);
                        }),
                    this.dog,
                    this.owner.getX(),
                    this.owner.getEyeY(),
                    this.owner.getZ(),
                    new AABB(this.owner.blockPosition()).inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS)
                );
        }

    }
}
