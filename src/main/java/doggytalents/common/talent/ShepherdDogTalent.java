package doggytalents.common.talent;

import doggytalents.DoggyItems;
import doggytalents.DoggyTalents;
import doggytalents.api.feature.DataKey;
import doggytalents.api.feature.EnumMode;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class ShepherdDogTalent extends TalentInstance {

    private List<Animal> targets = List.of(); 

    private static final int SEARCH_RANGE = 12;
    private static final int SEARCH_RANGE_EXT = 16;
    private static final int VALID_FOLLOWING_DISTANCE = 400;

    //private static DataKey<EntityAIShepherdDog> SHEPHERD_AI = DataKey.make();
    
    private int tickTillSearch;

    public ShepherdDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        // if (!dogIn.hasData(SHEPHERD_AI)) {
        //     EntityAIShepherdDog shepherdAI = new EntityAIShepherdDog(dogIn, 1.0D, 8F, entity -> !(entity instanceof TamableAnimal));
        //     if (dogIn instanceof Dog) {
        //         dogIn.goalSelector.addGoal(((Dog)dogIn).TALENT_GOAL_PRIORITY , shepherdAI);
        //     } else {
        //         dogIn.goalSelector.addGoal(7 , shepherdAI);
        //     }
            
        //     dogIn.setData(SHEPHERD_AI, shepherdAI);
        // }
    }

    @Override
    public void livingTick(AbstractDog abstractDog) {
        if (abstractDog.level.isClientSide) {
            return;
        }

        if (!(abstractDog instanceof Dog)) return;

        var dog = (Dog) abstractDog;

        var owner = dog.getOwner();
        if (owner == null) return;

        if (
            dog.readyForNonTrivialAction() 
            && (dog.getMode() == EnumMode.DOCILE) 
            && --this.tickTillSearch <= 0
        ) {
            this.tickTillSearch = 10;
            this.refreshShepherdTargets(dog, SEARCH_RANGE);
            if (!this.targets.isEmpty() 
                && EntityUtil.isHolding(owner, DoggyItems.WHISTLE.get(), 
                    (nbt) -> nbt.contains("mode") 
                            && nbt.getInt("mode") == 4)
            ) {
                this.triggerShepherdAction(dog, owner);
            }
        }
    }

    private void triggerShepherdAction(Dog dog, LivingEntity owner) {
        if (owner instanceof ServerPlayer sP)
        dog.triggerAction(new ShepherdAction(dog, sP, this));
    }

    public static int getMaxFollowers(int level) {
        switch(level) {
        case 1:
            return 1;
        case 2:
            return 2;
        case 3:
            return 4;
        case 4:
            return 8;
        case 5:
            return 16;
        default:
            return 0;
        }        
    }

    public boolean isValidAnimal(Dog dog, Animal animal) {
        if (!animal.isAlive()) return false;
        if (animal.isInvisible()) return false;
        if (animal instanceof TamableAnimal) return false; 
        if (animal.distanceToSqr(dog) > VALID_FOLLOWING_DISTANCE) return false;
        return animal.hasLineOfSight(dog);
    }

    public void refreshShepherdTargets(Dog dog, int searchRange) {
        this.targets = dog.level.getEntitiesOfClass(
            Animal.class, 
            dog.getBoundingBox().inflate(searchRange, 4, searchRange),
            e -> this.isValidAnimal(dog, e) 
        );
        if (this.targets.isEmpty()) return;
        Collections.sort(this.targets, new EntityUtil.Sorter(dog));
        int followCap = getMaxFollowers(this.level());
        if (followCap < this.targets.size()) {
            this.targets = this.targets.subList(0, Math.min(followCap, this.targets.size()));
        }
    }
    
    // public void revalidateShepherdTargets(Dog dog) {
    //     var toRemove = new ArrayList<Animal>();
    //     for (var target : this.targets) {
    //         if (!isValidAnimal(dog, target))
    //             toRemove.add(target);
    //     }
    //     this.targets.removeAll(toRemove);
    // }

    private static class ShepherdAction extends TriggerableAction {

        private static final int LOOK_OWNER_INTERVAL = 20;

        private final ShepherdDogTalent talentInst;
        private @Nonnull ServerPlayer owner;
        private int tickTillPathRecalc;
        private int tickTillLook;

        public ShepherdAction(Dog dog, @Nonnull ServerPlayer owner, ShepherdDogTalent talentInst) {
            super(dog, false, true);
            this.talentInst = talentInst;
            this.owner = owner; 
        }

        @Override
        public void onStart() {
            this.tickTillPathRecalc = 0;
            this.tickTillLook = 0;
        }

        @Override
        public void tick() {
            if (dog.getMode() != EnumMode.DOCILE) {
                this.setState(ActionState.FINISHED);
                return;
            }

            if (
                !EntityUtil.isHolding(owner, 
                DoggyItems.WHISTLE.get(), 
                (nbt) -> nbt.contains("mode") && nbt.getInt("mode") == 4)
            ) {
                this.setState(ActionState.FINISHED);
                return;
            }

            boolean update = --this.tickTillPathRecalc <= 0;
            if (update) this.tickTillPathRecalc = 20;

            if (update) {
                talentInst.refreshShepherdTargets(dog, SEARCH_RANGE_EXT);
            }

            if (talentInst.targets.isEmpty()) {
                this.setState(ActionState.FINISHED);
                return;
            }

            if (--this.tickTillLook <= 0) {
                this.tickTillLook = LOOK_OWNER_INTERVAL;
                this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
            }
            
            if (update) herdTargets();
        }

        private void herdTargets() {
            boolean teleport = this.owner.distanceToSqr(talentInst.targets.get(0)) > 256;

            for (Animal target : talentInst.targets) {
                double distanceAway = target.distanceToSqr(this.owner);
                target.getLookControl().setLookAt(this.owner, 10.0F, target.getMaxHeadXRot());
                if (teleport) {
                    if (!target.isLeashed() && !target.isPassenger()) {
                        EntityUtil.tryToTeleportNearEntity(target, target.getNavigation(), this.owner, 4);
                    }
                }
                else if (distanceAway >= 25) {
                    if (!target.getNavigation().moveTo(this.owner, 1.2D)) {
                        if (!target.isLeashed() && !target.isPassenger() && distanceAway >= 400) {
                            EntityUtil.tryToTeleportNearEntity(target, target.getNavigation(), this.owner, 4);
                        }
                    }
                }
                else {
                    target.getNavigation().stop();
                }
            }

            

            this.moveInTheMiddleOfHerdingGroup(teleport);

            if (this.dog.distanceToSqr(this.owner) > 1600) {
                DogUtil.guessAndTryToTeleportToOwner(dog, owner, 2);
            }

            if (this.dog.getRandom().nextFloat() < 0.15F) {
                this.dog.playSound(SoundEvents.WOLF_AMBIENT, this.dog.getSoundVolume() + 1.0F, (this.dog.getRandom().nextFloat() - this.dog.getRandom().nextFloat()) * 0.1F + 0.9F);
            }
        }

        private void moveInTheMiddleOfHerdingGroup(boolean teleport) {
            Vec3 avgPosVec = Vec3.ZERO;

            for (Animal target : talentInst.targets) {
                avgPosVec = avgPosVec.add(target.position());
            }

            avgPosVec = avgPosVec.scale(1D / talentInst.targets.size());

            double d_avgPosX_ownerX = avgPosVec.x - this.owner.getX();
            double d_avgPosZ_ownerZ = avgPosVec.z - this.owner.getZ();
            double size = Math.sqrt(d_avgPosX_ownerX * d_avgPosX_ownerX + d_avgPosZ_ownerZ * d_avgPosZ_ownerZ);
            double tpPosX = avgPosVec.x + d_avgPosX_ownerX / size * (2 + talentInst.targets.size() / 16);
            double tpPosZ = avgPosVec.z + d_avgPosZ_ownerZ / size * (2 + talentInst.targets.size() / 16);

            if (teleport) {
                EntityUtil.tryToTeleportNearEntity(this.dog, this.dog.getNavigation(), new BlockPos(tpPosX, this.dog.getY(), tpPosZ), 1);
            }

            this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
            if (!this.dog.getNavigation().moveTo(tpPosX, this.owner.getBoundingBox().minY, tpPosZ, 1)) {
                if (this.dog.distanceToSqr(tpPosX, this.owner.getBoundingBox().minY, tpPosZ) > 144D) {
                    if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                        EntityUtil.tryToTeleportNearEntity(this.dog, this.dog.getNavigation(), new BlockPos(tpPosX, this.dog.getY(), tpPosZ), 4);
                    }
                }
            }
        }

        @Override
        public void onStop() {
            
        }

        @Override
        public boolean canOverrideSit() {
            return false;
        }
        
    }

    // public static class EntityAIShepherdDog extends Goal {

    //     protected final AbstractDog dog;
    //     private final Level world;
    //     private final double followSpeed;
    //     private final float maxDist;
    //     private final PathNavigation dogPathfinder;
    //     private final Predicate<ItemStack> holdingPred;
    //     private final Predicate<Animal> predicate;
    //     private final Comparator<Entity> sorter;


    //     private int timeToRecalcPath;
    //     private LivingEntity owner;
    //     protected List<Animal> targets;
    //     private float oldWaterCost;

    //     private int MAX_FOLLOW = 5;

    //     public EntityAIShepherdDog(AbstractDog dogIn, double speedIn, float range, @Nullable Predicate<Animal> targetSelector) {
    //         this.dog = dogIn;
    //         this.world = dogIn.level;
    //         this.dogPathfinder = dogIn.getNavigation();
    //         this.followSpeed = speedIn;
    //         this.maxDist = range;
    //         this.predicate = (entity) -> {
    //             double d0 = EntityUtil.getFollowRange(this.dog);
    //             if (entity.isInvisible()) {
    //                 return false;
    //             }
    //             else if (targetSelector != null && !targetSelector.test(entity)) {
    //                 return false;
    //             } else {
    //                 return entity.distanceTo(this.dog) > d0 ? false : entity.hasLineOfSight(this.dog);
    //             }
    //         };
    //         this.holdingPred = (stack) -> {
    //             return stack.getItem() == DoggyItems.WHISTLE.get(); // TODO
    //         };

    //         this.sorter = new EntityUtil.Sorter(dogIn);
    //         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    //     }

    //     @Override
    //     public boolean canUse() {
    //         if (this.dog.getMode() != EnumMode.DOCILE) {
    //             return false;
    //         } else if (this.dog.getDogLevel(DoggyTalents.SHEPHERD_DOG) <= 0) {
    //             return false;
    //         } else {
    //             LivingEntity owner = this.dog.getOwner();
    //             if (owner == null) {
    //                return false;
    //             } else if (owner instanceof Player && ((Player) owner).isSpectator()) {
    //                 return false;
    //             } else if (!EntityUtil.isHolding(owner, DoggyItems.WHISTLE.get(), (nbt) -> nbt.contains("mode") && nbt.getInt("mode") == 4)) {
    //                 return false;
    //             } else {
    //                 List<Animal> list = this.world.getEntitiesOfClass(Animal.class, this.dog.getBoundingBox().inflate(12D, 4.0D, 12D), this.predicate);
    //                 Collections.sort(list, this.sorter);
    //                 if (list.isEmpty()) {
    //                     return false;
    //                 }
    //                 else {



    //                     this.MAX_FOLLOW = ShepherdDogTalent.getMaxFollowers(this.dog.getDogLevel(DoggyTalents.SHEPHERD_DOG));
    //                     this.targets = list.subList(0, Math.min(MAX_FOLLOW, list.size()));
    //                     this.owner = owner;
    //                     return true;
    //                 }
    //             }
    //         }
    //     }

    //     @Override
    //     public boolean canContinueToUse() {
    //         if (this.dog.getMode() != EnumMode.DOCILE) {
    //             return false;
    //         } else if (this.dog.getDogLevel(DoggyTalents.SHEPHERD_DOG) <= 0) {
    //             return false;
    //         } else if (!EntityUtil.isHolding(owner, DoggyItems.WHISTLE.get(), (nbt) -> nbt.contains("mode") && nbt.getInt("mode") == 4)) {
    //             return false;
    //         } else if (this.targets.isEmpty()) {
    //             return false;
    //         } else {
    //             return true;
    //         }
    //     }

    //     @Override
    //     public void start() {
    //         this.timeToRecalcPath = 0;
    //         this.oldWaterCost = this.dog.getPathfindingMalus(BlockPathTypes.WATER);
    //         this.dog.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    //     }

    //     @Override
    //     public void tick() {
    //         if (!this.dog.isInSittingPose()) {

    //             if (--this.timeToRecalcPath <= 0) {
    //                 this.timeToRecalcPath = 10;

    //                 // Pick up more animals
    //                 if (this.targets.size() < MAX_FOLLOW) {
    //                     List<Animal> list = this.world.getEntitiesOfClass(Animal.class,
    //                             this.dog.getBoundingBox().inflate(16, 4.0D, 16), this.predicate);
    //                     list.removeAll(this.targets);
    //                     Collections.sort(list, this.sorter);

    //                     this.targets.addAll(list.subList(0, Math.min(MAX_FOLLOW - this.targets.size(), list.size())));
    //                 }

    //                 Collections.sort(this.targets, this.sorter);
    //                 boolean teleport = this.owner.distanceTo(this.targets.get(0)) > 16;

    //                 for (Animal target : this.targets) {
    //                     //target.goalSelector.addGoal(0, new );

    //                     double distanceAway = target.distanceTo(this.owner);
    //                     target.getLookControl().setLookAt(this.owner, 10.0F, target.getMaxHeadXRot());
    //                     if (teleport) {
    //                         if (!target.isLeashed() && !target.isPassenger()) {
    //                             EntityUtil.tryToTeleportNearEntity(target, target.getNavigation(), this.owner, 4);
    //                         }
    //                     }
    //                     else if (distanceAway >= 5) {
    //                         if (!target.getNavigation().moveTo(this.owner, 1.2D)) {
    //                             if (!target.isLeashed() && !target.isPassenger() && distanceAway >= 20) {
    //                                 EntityUtil.tryToTeleportNearEntity(target, target.getNavigation(), this.owner, 4);
    //                             }
    //                         }
    //                     }
    //                     else {
    //                         target.getNavigation().stop();
    //                     }
    //                 }

    //                 Vec3 vec = Vec3.ZERO;

    //                 // Calculate average pos of targets
    //                 for (Animal target : this.targets) {
    //                     vec = vec.add(target.position());
    //                 }

    //                 vec = vec.scale(1D / this.targets.size());

    //                 double dPosX = vec.x - this.owner.getX();
    //                 double dPosZ = vec.z - this.owner.getZ();
    //                 double size = Math.sqrt(dPosX * dPosX + dPosZ * dPosZ);
    //                 double j3 = vec.x + dPosX / size * (2 + this.targets.size() / 16);
    //                 double k3 = vec.z + dPosZ / size * (2 + this.targets.size() / 16);

    //                 if (teleport) {
    //                     EntityUtil.tryToTeleportNearEntity(this.dog, this.dogPathfinder, new BlockPos(j3, this.dog.getY(), k3), 1);
    //                 }

    //                 this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
    //                 if (!this.dogPathfinder.moveTo(j3, this.owner.getBoundingBox().minY, k3, this.followSpeed)) {
    //                     if (this.dog.distanceToSqr(j3, this.owner.getBoundingBox().minY, k3) > 144D) {
    //                         if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
    //                             EntityUtil.tryToTeleportNearEntity(this.dog, this.dogPathfinder, new BlockPos(j3, this.dog.getY(), k3), 4);
    //                         }
    //                     }
    //                 }

    //                 if (this.dog.distanceTo(this.owner) > 40) {
    //                     EntityUtil.tryToTeleportNearEntity(this.dog, this.dogPathfinder, this.owner, 2);
    //                 }
    //                 // Play woof sound
    //                 if (this.dog.getRandom().nextFloat() < 0.15F) {
    //                     this.dog.playSound(SoundEvents.WOLF_AMBIENT, this.dog.getSoundVolume() + 1.0F, (this.dog.getRandom().nextFloat() - this.dog.getRandom().nextFloat()) * 0.1F + 0.9F);
    //                 }

    //                 // Remove dead or faraway entities
    //                 List<Animal> toRemove = new ArrayList<>();
    //                 for (Animal target : this.targets) {
    //                     if (!target.isAlive() || target.distanceTo(this.dog) > 25D)
    //                         toRemove.add(target);
    //                 }
    //                 this.targets.removeAll(toRemove);
    //             }
    //         }
    //     }

    //     @Override
    //     public void stop() {
    //         this.owner = null;
    //         for (Animal target : this.targets) {
    //             target.getNavigation().stop();
    //         }
    //         this.dogPathfinder.stop();
    //         this.dog.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
    //     }
    // }
}
