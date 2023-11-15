package doggytalents.common.talent;

import java.util.UUID;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

public class HellHoundTalent extends TalentInstance {

    private static final UUID FIRE_SWIM_BOOST_ID = UUID.fromString("69192651-74c8-4366-8375-a488628f4556");

    private float oldLavaCost;
    private float oldFireCost;
    private float oldDangerFireCost;

    private final int SEARCH_RANGE = 3;
    private int tickUntilSearch = 0;
    private int fireDamageAccumulate;
    private int lavaDamageAccumulate;

    private DogLavaNavigation navigation;
    private boolean swimming;

    public HellHoundTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dog) {
        if (this.level() < 5) return; //HOTFIX!🔥🔥🔥
        if (dog.level().isClientSide)
            return;
        this.oldLavaCost = dog.getPathfindingMalus(BlockPathTypes.LAVA);
        this.oldFireCost = dog.getPathfindingMalus(BlockPathTypes.DAMAGE_FIRE);
        this.oldDangerFireCost = dog.getPathfindingMalus(BlockPathTypes.DANGER_FIRE);

        dog.setPathfindingMalus(BlockPathTypes.LAVA, 8.0f);
        dog.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
        dog.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
        if (dog instanceof Dog) {
            this.navigation = new DogLavaNavigation((Dog)dog, dog.level());
            swimming = false;
        }
    }

    @Override
    public void set(AbstractDog dog, int levelBefore) {
        if (dog.level().isClientSide)
            return;
        if (levelBefore >= 5 && this.level() < 5) {
            dog.setPathfindingMalus(BlockPathTypes.LAVA, this.oldLavaCost);
            dog.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, this.oldFireCost);
            dog.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, this.oldDangerFireCost);
        }
    }

    @Override
    public void remove(AbstractDog dog) {
        if (this.level() < 5) return;
        if (dog.level().isClientSide)
            return;
        dog.setPathfindingMalus(BlockPathTypes.LAVA, this.oldLavaCost);
        dog.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, this.oldFireCost);
        dog.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, this.oldDangerFireCost);
        this.stopSwimming(dog);
    }

    @Override
    public InteractionResultHolder<Float> calculateFallDistance(AbstractDog dogIn, float distance) {
        if (this.level() >= 5 && dogIn.isInLava()) {
            return InteractionResultHolder.success(0f);
        }
        return super.calculateFallDistance(dogIn, distance);
    }

    @Override
    public InteractionResultHolder<Integer> setFire(AbstractDog dogIn, int second) {
        return InteractionResultHolder.success(Mth.floor(second * this.getFireDecreasePercentage()));
    }

    private float getFireDecreasePercentage() {
        if (this.level() <= 0 || this.level() >= 5)
            return 1f;
        switch (this.level()) {
        default:
            return 1f;
        case 1:
            return 0.9f;
        case 2:
            return 0.8f;
        case 3:
            return 0.5f;
        case 4:
            return 0.25f;
        }
    }

    @Override
    public InteractionResult isImmuneToFire(AbstractDog dogIn) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult isInvulnerableTo(AbstractDog dogIn, DamageSource source) {
        if (source.isFire()) {
            return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void doAdditionalAttackEffects(AbstractDog dogIn, Entity entity) {
        if (this.level() > 0) {
            entity.setSecondsOnFire(this.level());
        }
    }

    @Override
    public InteractionResult canResistPushFromFluidType(FluidType type) {
        if (type != ForgeMod.LAVA_TYPE.get())   
            return InteractionResult.PASS;

        if (this.level() >= 5) 
            return InteractionResult.SUCCESS;

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Float> gettingAttackedFrom(AbstractDog dog, DamageSource source, float damage) {
        if (!source.isFire()) {
            return InteractionResultHolder.pass(damage);
        }
        if (source != DamageSource.LAVA) {
            ++this.fireDamageAccumulate;
            if (this.fireDamageAccumulate >= this.level() + 1) {
                this.fireDamageAccumulate = 0;
                return InteractionResultHolder.pass(1f);
            }
        } else {
            ++this.lavaDamageAccumulate;
            if (this.lavaDamageAccumulate >= 10 * this.level()) {
                this.lavaDamageAccumulate = 0;
                return InteractionResultHolder.pass(Math.max(0, 1- (this.level()*0.25f)) * damage);
            }
        }
        return InteractionResultHolder.fail(0f);
    }

    @Override
    public InteractionResult stillIdleOrSitWhenHurt(AbstractDog dog, DamageSource source, float amount) {
        if (source.isFire())
            return InteractionResult.SUCCESS;
        return InteractionResult.PASS;    
    }

    @Override
    public void tick(AbstractDog d) {
        if (d.level().isClientSide)
            return;
        if (this.level() < 5) return;
        if (!(d instanceof Dog dog))
            return;
        if (dog.isInLava() && !this.swimming) {
            this.startSwimming(dog);  
        } 
        if (!dog.isInLava() && this.swimming) {
            this.stopSwimming(dog);
        }
        floatHellhound(dog);
        if (dog != null) {
            if (dog.isShakingLava()) {
                if (dog.getTimeDogIsShaking() > 0.8) {
                    if (--this.tickUntilSearch <= 0) {
                        this.tickUntilSearch = 10;
                        this.fireSpreadToEnermies(dog);
                    }
                }
            }
        }
    }

    private void floatHellhound(AbstractDog dog) {
        if (!dog.isInLava()) return;
        var collisioncontext = CollisionContext.of(dog);
        if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, 
            dog.blockPosition(), true) 
            && !dog.level().getFluidState(dog.blockPosition().above()).is(FluidTags.LAVA)) {
            dog.setOnGround(true);
        } else {
            dog.setDeltaMovement(dog.getDeltaMovement().add(0.0D, 0.085D, 0.0D));
        }
    }

    @Override
    public InteractionResult canStandOnFluid(AbstractDog dogIn, FluidState state) {
        if (this.level() >= 5 && state.is(FluidTags.LAVA)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void fireSpreadToEnermies(AbstractDog dog) {
        var targets = 
            dog.level().getEntitiesOfClass(
                LivingEntity.class, 
                dog.getBoundingBox().inflate(SEARCH_RANGE, 2, SEARCH_RANGE)
            );
        for (var x : targets) {
            if (x instanceof Enemy) {
                x.setSecondsOnFire(5);
            }
        }
    }

    private void applySwimAttributes(AbstractDog dog){
        dog.setAttributeModifier(ForgeMod.SWIM_SPEED.get(), FIRE_SWIM_BOOST_ID, (dd, u) -> 
            new AttributeModifier(u, "Lava Swim Boost", 4, Operation.ADDITION)
        );
    }

    private void removeSwimAttributes(AbstractDog dog) {
        dog.removeAttributeModifier(ForgeMod.SWIM_SPEED.get(), FIRE_SWIM_BOOST_ID);
    }

    @Override
    public InteractionResultHolder<BlockPathTypes> inferType(AbstractDog dog, BlockPathTypes type) {
        if (level < 5) return super.inferType(dog, type);
        // CAUTION : MAGMA_BLOCK also returns DAMAGE_FIRE instead of BLOCKED
        // so the dog may suffocate.
        // if (type == BlockPathTypes.DAMAGE_FIRE) {
        //     return InteractionResult.SUCCESS;
        // }
        //Won't push owner due to A.I check
        if (type == BlockPathTypes.DANGER_FIRE) {
            return InteractionResultHolder.success(BlockPathTypes.WALKABLE);
        }
        if (type == BlockPathTypes.LAVA) {
            return InteractionResultHolder.success(BlockPathTypes.BLOCKED);
        }
        return super.inferType(dog, type);
    }

    public void startSwimming(AbstractDog dog) {
        dog.resetMoveControl();
        dog.setNavigation(navigation);
        dog.setDogSwimming(true);
        swimming = true;
    }

    public void stopSwimming(AbstractDog dog) {
        dog.resetMoveControl();
        dog.resetNavigation();
        dog.setDogSwimming(false);
        swimming = false;
    }

    public static class DogLavaNavigation extends DogPathNavigation {

        private Dog dog;

        public DogLavaNavigation(Dog dog, Level level) {
            super(dog, level);
            this.dog = dog;
        }

        @Override
        protected boolean hasValidPathType(BlockPathTypes type) {
            if (type == BlockPathTypes.LAVA)
                return true;
            if (type == BlockPathTypes.DAMAGE_FIRE)
                return true;
            if (type == BlockPathTypes.DANGER_FIRE)
                return true;
            return super.hasValidPathType(type);
         }
   
        @Override
        public boolean isStableDestination(BlockPos pos) {
            if (this.level.getBlockState(pos).is(Blocks.LAVA))
                return true;
            return super.isStableDestination(pos);
        }

        protected PathFinder createPathFinder(int p_26453_) {
            this.nodeEvaluator = new WalkNodeEvaluator() {
                @Override
                protected double getFloorLevel(BlockPos pos) {
                    if (this.level.getFluidState(pos).is(FluidTags.LAVA)) {
                        return pos.getY();
                    }
                    return super.getFloorLevel(pos);
                }
            };
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, p_26453_);
        }

        @Override
        protected boolean canUpdatePath() {
            return super.canUpdatePath() && !dog.isOnSwitchNavCooldown();
        }
        
    }
}
