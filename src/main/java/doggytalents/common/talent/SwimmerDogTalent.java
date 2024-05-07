package doggytalents.common.talent;
import java.util.EnumSet;
import java.util.UUID;

import doggytalents.DoggyAttributes;
import doggytalents.DoggyTalents;
import doggytalents.api.feature.DataKey;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;

import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogSwimMoveControl;
import doggytalents.common.entity.ai.nav.DogWaterBoundNavigation;
import doggytalents.forge_imitate.atrrib.ForgeMod;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class SwimmerDogTalent extends TalentInstance {
    
    //private SwimmerDogGoal goal;

    private DogSwimMoveControl moveControl;
    private DogWaterBoundNavigation navigator;

    private static final UUID SWIM_BOOST_ID = UUID.fromString("50671e42-1ded-4f97-9e2b-78bbeb1e8772");

    //private static DataKey<SwimmerDogGoal> SWIM_AI = DataKey.make();

    private boolean swimming;
    private float oldWaterCost;
    private float oldWaterBorderCost;
                     
    public SwimmerDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        if (!(dogIn instanceof Dog dog)) return;
        this.moveControl = 
            new DogSwimMoveControl(dog);
        this.navigator = 
            new DogWaterBoundNavigation(dog, dog.level());
        swimming = false;
        // if (!(dogIn instanceof Dog)) return;
        // if (!dogIn.hasData(SWIM_AI)) {
        //     SwimmerDogGoal swimmerDogGoal = new SwimmerDogGoal((Dog)dogIn);
        //     dogIn.goalSelector.addGoal(7, swimmerDogGoal);
        //     dogIn.setData(SWIM_AI, swimmerDogGoal);
        // } else {
        //     dogIn.getData(SWIM_AI).applySwimAttributes();
        // }
    }
    
    @Override
    public void livingTick(AbstractDog abstractDog) {
        if (abstractDog.level().isClientSide) {
            return;
        }

        if (!(abstractDog instanceof Dog)) return;

        var dog = (Dog) abstractDog;

        if (this.level() >= 5 && dog.isVehicle()) {
            // canBeSteered checks entity is LivingEntity
            var control = dog.getControllingPassenger();
            if (control != null && control.isInWater()) {
                control.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 80, 1, true, false));
            }
        }

        if (this.swimming) updateSwimming(dog);
        else updateNotSwimming(dog);
        
    }

    @Override
    public void remove(AbstractDog abstractDog) {
        if (this.swimming && abstractDog instanceof Dog dog) {
            this.swimming = false;
            stopSwimming(dog);
        }
    }

    private void updateSwimming(Dog dog) {
        if (
            (!dog.isInWater() && dog.onGround())
            || dog.isLowAirSupply()
        ) {
            this.swimming = false;
            stopSwimming(dog);
        }
    }

    private void updateNotSwimming(Dog dog) {
        if (
            dog.isInWater()
            && readyToBeginSwimming(dog)
            && !dog.isDogSwimming()
        ) {
            this.swimming = true;
            this.startSwimming(dog);
        }
    }

    private boolean readyToBeginSwimming(Dog dog) {
        return dog.getAirSupply() == dog.getMaxAirSupply();
    }

    private void applySwimAttributes(Dog dog){
        dog.setAttributeModifier(ForgeMod.SWIM_SPEED.holder(), SWIM_BOOST_ID, (dd, u) -> 
            new AttributeModifier(u, "Swim Boost", 2*dog.getDogLevel(DoggyTalents.SWIMMER_DOG), Operation.ADD_VALUE)
        );
    }

    private void removeSwimAttributes(Dog dog) {
        dog.removeAttributeModifier(ForgeMod.SWIM_SPEED.holder(), SWIM_BOOST_ID);
    }
    
    private void startSwimming(Dog dog) {
        dog.setJumping(false);
        dog.setNavigation(this.navigator);
        dog.setMoveControl(this.moveControl);
        if (dog.isInSittingPose()) { 
            dog.setInSittingPose(false);
        }
        applySwimAttributes(dog);
        dog.setDogSwimming(true);
    }

    private void stopSwimming(Dog dog) {
        dog.resetMoveControl();
        dog.resetNavigation();
        removeSwimAttributes(dog);
        dog.setDogSwimming(false);
    }

    @Override
    public InteractionResult canBeRiddenInWater(AbstractDog dogIn) {
        return this.level() >= 2 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Integer> decreaseAirSupply(AbstractDog dogIn, int air) {
        if (this.level() > 0 && dogIn.getRandom().nextInt(this.level() + 1) > 0) {
            return InteractionResultHolder.success(air);
        }

        return InteractionResultHolder.pass(air);
    }

    @Override
    public InteractionResultHolder<Integer> determineNextAir(AbstractDog dogIn, int currentAir) {
        if (this.level() > 0) {
            return InteractionResultHolder.pass(currentAir + this.level());
        }

        return InteractionResultHolder.pass(currentAir);
    }

    @Override
    public void props(AbstractDog dog, DogAlterationProps props) {
        props.setCanSwimUnderwater();
        if (this.level() >= 5)
            props.setCanBreatheUnderwater();
    }

    @Override
    public InteractionResult canResistPushFromFluidType() {
        // if (type != ForgeMod.WATER_TYPE.get())
        //     return InteractionResult.PASS;

        if (this.level() >= 2) 
            return InteractionResult.SUCCESS;

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<PathType> inferType(AbstractDog dog, PathType type) {
        //This allows the owner to help the dog to reach the surface.
        if (type == PathType.WATER) {
            return InteractionResultHolder.success(PathType.WALKABLE);
        }
        return super.inferType(dog, type);
    }

    //TODO Maybe incoproate this into tick()
    // public static class SwimmerDogGoal extends Goal {

    //     //TODO Something's not right, need to re look at the dog goal enable pattern while riding
    //     private Dog dog;
    //     //private boolean isGettingAir = false;
    //     private SmoothSwimmingMoveControl moveControl;
    //     private WaterBoundPathNavigation navigator;
    //     private float oldWaterCost;
    //     private float oldWaterBorderCost;

    //     public SwimmerDogGoal(Dog d) {
    //         this.dog = d;
    //         this.moveControl = new SmoothSwimmingMoveControl(d, d.getMaxHeadXRot(), d.getMaxHeadYRot(), 1, 1, false);
    //         this.navigator = new DogWaterBoundNavigation(d, d.level);
    //     }                 
        
    //     @Override
    //     public boolean canUse() {
    //         if (this.dog.getDogLevel(DoggyTalents.SWIMMER_DOG) <= 0) return false;
    //         return this.dog.isInWater() && this.dog.canSwimUnderwater() && dog.getAirSupply() == dog.getMaxAirSupply() && !this.checkSurroundingForLand(this.dog, this.dog.blockPosition()) ;
    //     }

    //     @Override
    //     public boolean canContinueToUse() {
    //         if (!this.dog.isInWater()) return false;
    //         if (!this.dog.canSwimUnderwater()) return false;
    //         if (this.checkSurroundingForLand(this.dog, this.dog.blockPosition())) return false;
    //         return !this.dog.isLowAirSupply();
    //     }

    //     @Override
    //     public void start() {
    //         this.startSwimming();
    //     }

    //     @Override
    //     public void stop() {
    //         this.stopSwimming();
    //     }

    //     private boolean checkSurroundingForLand(AbstractDog dogIn, BlockPos p) {
    //         for (BlockPos dp : BlockPos.betweenClosed(p.offset(-1, -1, -1), p.offset(1, 1, 1))) {
    //             PathType pn = WalkNodeEvaluator.getBlockPathTypeStatic(dogIn.level(), dp.mutable());
    //             if (pn == PathType.WALKABLE || pn == PathType.WATER_BORDER) return true;
    //         }
    //         return false;
    //     }

    //     private void applySwimAttributes(){
    //         this.dog.setAttributeModifier(ForgeMod.SWIM_SPEED.get(), SWIM_BOOST_ID, (dd, u) -> 
    //             new AttributeModifier(u, "Swim Boost", 2*this.dog.getDogLevel(DoggyTalents.SWIMMER_DOG), Operation.ADDITION)
    //         );
    //     }

    //     private void removeSwimAttributes() {
    //         this.dog.removeAttributeModifier(ForgeMod.SWIM_SPEED.get(), SWIM_BOOST_ID);
    //     }
        
    //     private void startSwimming() {
    //         this.dog.setJumping(false);
    //         this.dog.setNavigation(this.navigator);
    //         this.dog.setMoveControl(this.moveControl);
    //         if (this.dog.isInSittingPose()) { 
    //             this.dog.setInSittingPose(false);
    //         }
    //         this.applySwimAttributes();
    //         this.oldWaterCost = this.dog.getPathfindingMalus(PathType.WATER);
    //         this.oldWaterBorderCost = this.dog.getPathfindingMalus(PathType.WATER_BORDER);
    //         this.dog.setPathfindingMalus(PathType.WATER_BORDER, 0);
    //         this.dog.setPathfindingMalus(PathType.WATER, 0);
    //         this.dog.setDogSwimming(true);
    //     }

    //     private void stopSwimming() {
    //         this.dog.resetMoveControl();
    //         this.dog.resetNavigation();
    //         this.removeSwimAttributes();
    //         this.dog.setPathfindingMalus(PathType.WATER_BORDER, this.oldWaterBorderCost);
    //         this.dog.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
    //         this.dog.setDogSwimming(false);
    //     }
    // }

}
