package doggytalents.common.talent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import doggytalents.api.feature.DataKey;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.network.packet.data.RescueDogRenderData;
import doggytalents.common.util.DogUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;

public class RescueDogTalent extends TalentInstance {
    private boolean renderBox = true;
    
    private final int SEARCH_RADIUS = 12; //const ?? maybe can improve thru talents
    private int tickTillSearch = 0;

    //private static DataKey<MoveToHealTargetGoal> RESCUE_DOG_AI = DataKey.make();

    private int healCooldown = 0;

    public RescueDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dog) {
        // if (!(dog instanceof Dog)) return;
        // if (!dog.hasData(RESCUE_DOG_AI)) {
        //     var rescueDogGoal = new MoveToHealTargetGoal((Dog)dog, this);
        //     dog.goalSelector.addGoal(((Dog) dog).TALENT_GOAL_PRIORITY, rescueDogGoal);
        //     dog.setData(RESCUE_DOG_AI, rescueDogGoal);
        // }
    }

    @Override
    public void livingTick(AbstractDog abstractDog) {
        if (abstractDog.level().isClientSide) {
            return;
        }

        if (!(abstractDog instanceof Dog)) return;

        var dog = (Dog) abstractDog;

        if (this.healCooldown > 0 ) {
            --this.healCooldown; return;
        }
        
        if (
            dog.readyForNonTrivialAction() 
            && !dog.getMode().shouldAttack() 
            && --this.tickTillSearch <= 0
        ) {
            this.tickTillSearch = 10;
            var target = findHealTarget(abstractDog);
            if (target != null && this.stillValidTarget(dog, target)) {
                this.triggerRescueAction(dog, target);
            }
        }

    }

    private void triggerRescueAction(Dog dog, @Nonnull LivingEntity target) {
        dog.triggerAction(new RescueAction(dog, this, target));
    }

    //TODO Decrease the healCost due to healing is more difficult now.
    public int healCost(AbstractDog dog, LivingEntity target) {
        int cost;
        if (this.level() >= 5) {
            cost = 10;
        } else {
            cost = 20;
        }

        return cost;
    }

    //TODO need tuned ? 
    public float healAmount(AbstractDog dog, LivingEntity target) {
        //Bonus level 3+
        float bonus = 0;

        float chance = dog.getRandom().nextFloat();

        /*
         * 20% : 3 Hearts
         * 25% : 2 Hearts
         * 30% : 1 Hearts
         * 25% : No
         */
        if ( this.level() >= 3 )
            if (chance > 0.8) {
                bonus = 3;
            } else if (chance > 0.55) {
                bonus = 2;
            } else if (chance > 0.25) {
                bonus = 1;
            }

        return Mth.floor(this.level() * 1.5D) + bonus*2;
    }

    @Override
    public boolean hasRenderer() {
        return true;
    }

    private void heal(AbstractDog dog, LivingEntity e) {
        if (this.healCooldown > 0) return;
        dog.setDogHunger(
            dog.getDogHunger() - this.healCost(dog, e)
        );
        if (this.level() >= 5) {
            forceHealTarget(dog, e, this.healAmount(dog, e));
        } else
            e.heal(
                this.healAmount(dog, e)
            );
        if (dog.level() instanceof ServerLevel) {
            ((ServerLevel) dog.level()).sendParticles(
                ParticleTypes.HEART, 
                e.getX(), e.getY(), e.getZ(), 
                this.level*8, 
                e.getBbWidth(), 0.8f, e.getBbWidth(), 
                0.1
            );
        }
        this.healCooldown = dog.getRandom().nextInt(3) * 20; // Between 2 seconds
    }

    private void forceHealTarget(AbstractDog dog, LivingEntity e, float add) {
        //🥴
        if (add <= 0)
            return;
        var add1 = net.minecraftforge.event.ForgeEventFactory.onLivingHeal(e, add);
        add = Math.max(add1, add);
        
        float h = e.getHealth();
        if (h > 0.0F) {
            e.setHealth(h + add);
        }
    }

    private boolean isTargetLowHealth(AbstractDog dog, LivingEntity e) {
        return e.getHealth() <= 8.0;
    }

    private boolean canAffordToHealTarget(AbstractDog dog, LivingEntity e) {
        return dog.getDogHunger() - 10 >= this.healCost(dog, e);
    }

    private boolean canHealTarget(AbstractDog dog, LivingEntity e) {
        return (
            dog.distanceToSqr(e) <= 5
            && dog.hasLineOfSight(e)
        );
    }

    private LivingEntity findHealTarget(AbstractDog dog) {
        var healTargets = new ArrayList<LivingEntity>();
        Predicate<LivingEntity> lowHealthAndInWitness =
            e -> this.isTargetLowHealth(dog, e) 
                && (dog.hasLineOfSight(e));
        
        //Get owner 
        var owner = dog.getOwner();
        if (owner == null) return null;

        //Check owner first
        if (lowHealthAndInWitness.test(owner)) {
            healTargets.add(owner);
        }
        
        //Get Dogs of the same owner
        var dogs = dog.level().getEntitiesOfClass(
            AbstractDog.class,    
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
            d -> (
                    d.getOwner() == owner
                    && lowHealthAndInWitness.test(d)
                )
            );
        if (!dogs.isEmpty()) {
            healTargets.addAll(dogs);
        }

        //Get Wolves of the same owner
        var wolves = dog.level().getEntitiesOfClass(
            Wolf.class,    
            dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
            w -> (
                    w.getOwner() == owner
                    && lowHealthAndInWitness.test(w)
                )
            );
        if (!wolves.isEmpty()) {
            healTargets.addAll(wolves);
        }

        if (dog instanceof Dog ddog && ddog.regardTeamPlayers()) {
            var teamPlayers = dog.level().getEntitiesOfClass(
                Player.class,    
                dog.getBoundingBox().inflate(SEARCH_RADIUS, 4, SEARCH_RADIUS),
                p -> (
                        p.isAlliedTo(owner)
                        && lowHealthAndInWitness.test(p)
                    )
                );
            if (!teamPlayers.isEmpty()) {
                healTargets.addAll(teamPlayers);
            }
        }
        return selectHealTarget(dog, healTargets);
    }

    private LivingEntity selectHealTarget(AbstractDog dog, ArrayList<LivingEntity> healTargets) {
        if (healTargets.isEmpty()) return null;

        var target = healTargets.get(0); 
        double mindistanceSqr = target.distanceToSqr(dog);

        var owner = dog.getOwner();
        
        for (var i : healTargets) {
            if (owner == i) return i;
            else {
                var d = i.distanceToSqr(dog);
                if (d < mindistanceSqr) {
                    target = i;
                    mindistanceSqr = d;
                }
            }
        }

        //ChopinLogger.l("target : " +target);

        return target;

    }

    private boolean stillValidTarget(Dog dog, LivingEntity target) {
        if (!target.isAlive()) return false;
        if (!this.isTargetLowHealth(dog, target)) return false;
        if (!this.canAffordToHealTarget(dog, target)) return false;
        if (dog.distanceToSqr(target) > 400) return false;         
        if (target instanceof Dog d && d.isDefeated()) return false;
        
        return true;
    }

    
    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);
        this.renderBox = compound.getBoolean("renderBox");
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);
        compound.putBoolean("renderBox", this.renderBox);
    }

    @Override
    public void writeToBuf(FriendlyByteBuf buf) {
        super.writeToBuf(buf);
        buf.writeBoolean(renderBox);
    }

    @Override
    public void readFromBuf(FriendlyByteBuf buf) {
        super.readFromBuf(buf);
        renderBox = buf.readBoolean();
    }

    @Override
    public void updateOptionsFromServer(TalentInstance fromServer) {
        if (!(fromServer instanceof RescueDogTalent rescue))
            return;
        this.renderBox = rescue.renderBox;
    }

    public void updateFromPacket(RescueDogRenderData data) {
        renderBox = data.val;
    }

    @Override
    public TalentInstance copy() {
        var ret = super.copy();
        if (!(ret instanceof RescueDogTalent rescue))
            return ret;
        rescue.setRenderBox(this.renderBox);
        return rescue;
    }

    public boolean renderBox() { return this.renderBox; }
    public void setRenderBox(boolean render) { this.renderBox = render; }

    public static class RescueAction extends TriggerableAction {

        private RescueDogTalent talentInst;
        private @Nonnull LivingEntity target;

        private int ticksUntilPathRecalc = 0;

        private final int stopDist = 2;    

        public RescueAction(Dog dog, RescueDogTalent talentInst, @Nonnull LivingEntity target) {
            super(dog, false, true);
            this.talentInst = talentInst;
            this.target = target;
        }

        @Override
        public void onStart() {
            //this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
            ticksUntilPathRecalc = 0;
        }

        @Override
        public void tick() {
            if (this.dog.getMode().shouldAttack()) {
                this.setState(ActionState.FINISHED); return;
            }

            if (!this.talentInst.stillValidTarget(dog, target)) {
                this.setState(ActionState.FINISHED); return;
            }

            if (this.dog.distanceToSqr(this.target) > stopDist*stopDist) {

                this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
                if (--this.ticksUntilPathRecalc <= 0) {
                    this.ticksUntilPathRecalc = 10;
                    if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                        //A Valid target is not that far away and is checked above.
                        //if (dog.distanceToSqr(target) > 400) return;
                        this.dog.getNavigation().moveTo(this.target, dog.getUrgentSpeedModifier());
                    }
                }
            } else {
                //TODO maintain some space ??
                //this.dog.getNavigation().stop();
                if (this.talentInst.canHealTarget(dog, target))
                    this.talentInst.heal(dog, target);
            }
        }

        @Override
        public void onStop() {
        }

        @Override
        public boolean canOverrideSit() {
            return true;
        }
        
    }

    // /**
    //  * @author DashieDev
    //  */
    // public static class MoveToHealTargetGoal extends Goal {

    //     private Dog dog;
    //     private RescueDogTalent talentInst;
    //     private LivingEntity target;

    //     private int ticksUntilPathRecalc = 0;

    //     private final int stopDist = 2;    

    //     public MoveToHealTargetGoal(Dog dog, RescueDogTalent talentInst) {
    //         this.dog = dog;
    //         this.talentInst = talentInst;
    //         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    //     }

    //     @Override
    //     public boolean canUse() {
    //         if (this.dog.isInSittingPose()) return false;
    //         if (!this.dog.isMode(EnumMode.DOCILE, EnumMode.GUARD_MINOR)) return false;
    //         this.target = this.talentInst.selectHealTarget(this.dog);
    //         if (target == null) return false;
    //         if (!this.stillValidTarget()) return false;

    //         return true;
    //     }

    //     @Override
    //     public boolean canContinueToUse() {
    //         if (this.dog.isInSittingPose()) return false;
    //         if (!this.dog.isMode(EnumMode.DOCILE, EnumMode.GUARD_MINOR)) return false;
    //         if (target == null) return false;

    //         return true;
    //     }

    //     @Override
    //     public void start() {
    //         this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
    //     }
        

    //     @Override
    //     public void tick() {

    //         if (target == null) return;

    //         if (!this.stillValidTarget()) {
    //             target = null;
    //             return;
    //         }
            
    //         if (this.dog.distanceToSqr(this.target) > stopDist*stopDist) {

    //             this.dog.getLookControl().setLookAt(target, 10.0F, this.dog.getMaxHeadXRot());
    //             if (--this.ticksUntilPathRecalc <= 0) {
    //                 this.ticksUntilPathRecalc = 10;
    //                 if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
    //                     //TODO ?
    //                     // if (this.dog.distanceToSqr(this.target) >= 144.0D) {
    //                     //     DogUtil.guessAndTryToTeleportToOwner(dog, 4);
    //                     // } else {
    //                         if (dog.distanceToSqr(target) > 400) return;
    //                         this.dog.getNavigation().moveTo(this.target, dog.getUrgentSpeedModifier());
    //                     //}
    //                 }
    //             }
    //         } else {
    //             //TODO maintain some space ??
    //             //this.dog.getNavigation().stop();
    //             if (this.talentInst.canHealTarget(dog, target))
    //                 this.talentInst.heal(dog, target);
    //         }
    //     }

    //     private boolean stillValidTarget() {
    //         if (!this.target.isAlive()) return false;
    //         if (!this.talentInst.isTargetLowHealth(dog, target)) return false;
    //         if (!this.talentInst.canAffordToHealTarget(dog, target)) return false;
    //         if (dog.distanceToSqr(target) > 400) return false;         
    //         if (target instanceof Dog d && d.isDefeated()) return false;
            
    //         return true;
    //     }


    // }

}
