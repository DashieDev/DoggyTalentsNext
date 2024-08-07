package doggytalents.common.talent;
import java.util.EnumSet;
import java.util.UUID;

import doggytalents.DoggyTalents;
import doggytalents.api.feature.DataKey;
import doggytalents.api.impl.DogAlterationProps;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.inferface.InferTypeContext;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogSwimMoveControl;
import doggytalents.common.entity.ai.nav.DogWaterBoundNavigation;
import doggytalents.common.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;

public class SwimmerDogTalent extends TalentInstance {
                     
    public SwimmerDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
    }
    
    @Override
    public void livingTick(AbstractDog abstractDog) {
        if (abstractDog.level().isClientSide) {
            return;
        }

        if (this.level() >= 5 && abstractDog.isVehicle()) {
            // canBeSteered checks entity is LivingEntity
            var control = abstractDog.getControllingPassenger();
            if (control != null && control.isInWater()) {
                control.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 80, 1, true, false));
            }
        }

        
        
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
        if (this.level() > 1) {
            props.addBonusSwimSpeed(Mth.clamp(this.level(), 2, 5) * 2);
        }
        if (this.level() >= 2)
            props.setResistWaterPush();
    }

    @Override
    public InteractionResultHolder<PathType> inferType(AbstractDog dog, PathType type, InferTypeContext context) {
        //This allows the owner to help the dog to reach the surface.
        if (type == PathType.WATER) {
            return InteractionResultHolder.success(PathType.WALKABLE);
        }
        return super.inferType(dog, type, context);
    }
}
