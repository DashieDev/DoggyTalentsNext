package doggytalents.common.block.tileentity;

import doggytalents.DoggyTileEntityTypes;
import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DogBathBlockEntity extends BlockEntity {

    public DogBathBlockEntity(BlockPos pos, BlockState blockState) {
        super(DoggyTileEntityTypes.DOG_BATH.get(), pos, blockState);
    }

    public int tickTillProvoke = 10;

    public static void tick(Level level, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if (level == null)
            return;
        if (level.isClientSide)
            return; 
        if (!(blockEntity instanceof DogBathBlockEntity bath)) {
            return;
        }

        if (--bath.tickTillProvoke > 0)
            return;
        
        bath.tickTillProvoke = 10;

        var dogList = level.getEntitiesOfClass(Dog.class, new AABB(pos).inflate(5, 3, 5));
        if (dogList.isEmpty())
            return;
        
        for (var dog : dogList) {
            if (dog.isBusy())
                continue;
            if (!dog.isDoingFine())
                continue;
            if (dog.isOrderedToSit())
                continue;
            if (dog.isInSittingPose())
                continue;
            if (dog.getAnim() != DogAnimation.NONE)
                continue;
            if (dog.getRandom().nextFloat() >= 0.02)
                continue;
            dog.triggerAction(new DogDrinkFromFoodBowl(dog, bath));
            bath.tickTillProvoke += 40 + dog.getRandom().nextInt(61);
            break;
        }
    }

    public static class DogDrinkFromFoodBowl extends TriggerableAction {

        private final DogBathBlockEntity bath;
        private int tickTillPathRecalc;
        private int goToBowlTimeout = 0;
        private boolean isDoingAnim;
        private int stopTick;
        private int animTick;

        public DogDrinkFromFoodBowl(Dog dog, DogBathBlockEntity bath) {
            super(dog, false, false);
            this.bath = bath;
        }

        @Override
        public void onStart() {
            this.goToBowlTimeout = 10 * 20;
            this.isDoingAnim = false;
            this.animTick = 0;
        }

        @Override
        public void tick() {
            if (!this.stillValidTarget()) {
                setState(ActionState.FINISHED);
                return;
            }

            if (this.isDoingAnim) {
                if (!stillInDrinkDistance()) {
                    setState(ActionState.FINISHED);
                    return;
                }
                if (dog.getAnim() != DogAnimation.DRINK_WATER) {
                    setState(ActionState.FINISHED);
                    return;
                }
                if (dog.tickCount >= stopTick) {
                    this.setState(ActionState.FINISHED);
                    return;
                }
            } else {
                boolean dist_can_start_drinking = distCanStartDrinking();
                if (!dist_can_start_drinking) {
                    --this.goToBowlTimeout;
                }
                if (this.goToBowlTimeout <= 0 && !dist_can_start_drinking) {
                    setState(ActionState.FINISHED);
                    return;
                }
            }

            var bowlPos = getBowPos();
            this.dog.getLookControl().setLookAt(bowlPos.x, bowlPos.y, bowlPos.z, 
                10.0F, this.dog.getMaxHeadXRot());
            
            if (this.isDoingAnim) {
                doAnim();
            } else {
                moveToBowl(distCanStartDrinking());
            }

        }

        private void moveToBowl(boolean is_close_to_bowl) {
            if (is_close_to_bowl) {
                this.isDoingAnim = true;
                this.dog.setAnim(DogAnimation.DRINK_WATER);
                this.stopTick = dog.tickCount + DogAnimation.DRINK_WATER.getLengthTicks();
                this.dog.getNavigation().stop();
                return;
            }
            var bowlPos = getBowPos();
            
            if (--this.tickTillPathRecalc <= 0) {
                this.tickTillPathRecalc = 10;
                if (!this.dog.isLeashed() && !this.dog.isPassenger()) {
                    //A Valid target is not that far away and is checked above.
                    //if (dog.distanceToSqr(target) > 400) return;
                    this.dog.getNavigation().moveTo(bowlPos.x, bowlPos.y, bowlPos.z, 
                        this.dog.getUrgentSpeedModifier());
                }
            }
        }

        private void doAnim() {
            ++animTick;
            if (5 <= animTick && animTick <= 35) {
                if (animTick % 4 == 0)
                    this.dog.playSound(SoundEvents.GENERIC_DRINK, 
                        0.2F, dog.level().random.nextFloat() * 0.1F + 0.9F); 
            }
            if (animTick == 50) {
                if (dog.level() instanceof ServerLevel sLevel) {
                    sLevel.sendParticles(
                        ParticleTypes.SPLASH, 
                        dog.getX(), dog.getY(), dog.getZ(), 
                        10, 
                        dog.getBbWidth(), 0.8f, dog.getBbWidth(), 
                        0.1
                    );
                }
            }
            float backward_strafe_dist = 0.6f;
            if (this.getBowlDistanceSqr() < backward_strafe_dist * backward_strafe_dist) {
                this.dog.getMoveControl().strafe(-0.5f, 0);
            }
        }

        @Override
        public void onStop() {
            if (this.dog.getAnim() == DogAnimation.DRINK_WATER) {
                this.dog.setAnim(DogAnimation.NONE);
            }   
        }

        private boolean stillValidTarget() {  
            if (this.bath.isRemoved())
                return false;
            if (getBowlDistanceSqr() > 16*16) 
                return false;
            return true;
        }

        private boolean distCanStartDrinking() {
            double max_dist = dog.getBbWidth()/2 + 0.5;
            return getBowlDistanceSqr() <= max_dist * max_dist;
        }

        private boolean stillInDrinkDistance() {
            double max_dist = dog.getBbWidth()/2 + 0.5 + 0.3;
            return getBowlDistanceSqr() <= max_dist * max_dist;
        }

        private double getBowlDistanceSqr() {
            var bowlPos = getBowPos();
            return dog.distanceToSqr(bowlPos);
        }

        private Vec3 getBowPos() {
            return Vec3.atBottomCenterOf(this.bath.getBlockPos());
        }

    }

}
