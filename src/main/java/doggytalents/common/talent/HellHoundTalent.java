package doggytalents.common.talent;

import java.util.UUID;

import doggytalents.api.impl.DogAlterationProps;
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

public class HellHoundTalent extends TalentInstance {

    private final int SEARCH_RANGE = 3;
    private int tickUntilSearch = 0;
    private int fireResistTickLeft = 60;
    private int fireResistHealCooldown = 0;

    public HellHoundTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
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
    public void props(AbstractDog dog, DogAlterationProps props) {
        if (this.level() >= 5) {
            props.setFireImmune();
        }
    }

    @Override
    public InteractionResult isInvulnerableTo(AbstractDog dogIn, DamageSource source) {
        if (source.isFire()) {
            return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void doInitialAttackEffects(AbstractDog dogIn, Entity entity) {
        if (this.level() >= 5) {
            entity.setSecondsOnFire(15);
        } else if (this.level() >= 4) {
            entity.setSecondsOnFire(4);
        }
    }

    @Override
    public InteractionResult canResistPushFromFluidType() {
        if (this.level() >= 5) 
            return InteractionResult.SUCCESS;

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Float> gettingAttackedFrom(AbstractDog dog, DamageSource source, float damage) {
        if (!source.isFire()) {
            return InteractionResultHolder.pass(damage);
        }
        var resistValue = getResistValue(source);
        this.fireResistHealCooldown = 80;
        this.fireResistTickLeft = Math.min(fireResistTickLeft, resistValue);
        if (this.fireResistTickLeft > 0) {
            return InteractionResultHolder.fail(0f);
        }
        this.fireResistTickLeft = resistValue;
        return InteractionResultHolder.pass(getFireDamageReduced(source, damage));
    }

    private float getFireDamageReduced(DamageSource source, float amount) {
        if (source == DamageSource.LAVA) {
            if (this.level() >= 4)
                return 1;
            if (this.level() >= 3)
                return 2;
            return Math.max(0, 1- (this.level()*0.25f)) * amount;
        }
        return 1f;
    }

    private int getMaxAccumulate() {
        if (this.level() <= 2)
            return this.level() + 1;
        if (this.level() <= 3)
            return this.level() + 3;
        return this.level() + 10;
    }

    private int getResistValue(DamageSource source) {
        if (this.level() >= 4) {
            return getMaxAccumulate() * 20;
        }
        if (source == DamageSource.LAVA) {
            return getMaxAccumulate()*10;
        }
        if (source == DamageSource.IN_FIRE) {
            return getMaxAccumulate() * 15;
        }
        return getMaxAccumulate() * 20;
    }

    public boolean canGenerateHeat() {
        return this.level() >= 4;
    }

    @Override
    public InteractionResult shouldNotAfraidOfFire(AbstractDog dog) {
        if (this.level() < 3)
            return InteractionResult.PASS;
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult stillIdleOrSitWhenHurt(AbstractDog dog, DamageSource source, float amount) {
        if (this.level() >= 4 && source.isFire()) {
            return InteractionResult.SUCCESS;
        }
        if (source == DamageSource.ON_FIRE)
            return InteractionResult.SUCCESS;
        return InteractionResult.PASS;    
    }

    @Override
    public void tick(AbstractDog d) {
        if (d.level().isClientSide)
            return;
        if (this.level() < 5) {
            updateResistValues();
            return;
        };
        if (!(d instanceof Dog dog))
            return;
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

    private void updateResistValues() {
        if (this.fireResistHealCooldown > 0)
            --this.fireResistHealCooldown;
        if (this.fireResistHealCooldown <= 0) {
            this.fireResistTickLeft = 60;
            return;
        }
        if (this.fireResistTickLeft > 0)
            --this.fireResistTickLeft;
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
}
