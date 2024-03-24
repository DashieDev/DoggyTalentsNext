package doggytalents.common.talent;

import doggytalents.api.anim.DogAnimation;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.FireBlock;

public class FireDrillTalent extends TalentInstance {

    private int rollCooldown = 0;
    private boolean fireExtinguished = false;
    private boolean isInFire = false;
    private boolean isRolling = false;
    
    public FireDrillTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void livingTick(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        if (!(dogIn instanceof Dog dog))
            return;
        
        if (this.rollCooldown > 0)
            --this.rollCooldown;

        if (dog.getRemainingFireTicks() >= 20 || isRolling) {
            updateInFire(dog);
            if (canRoll(dog)) {
                dog.triggerAction(new DogStopDropRollAction(dog, this));
                this.rollCooldown = 40;
            } 
        }
    }

    private int tickTillUpdateInFire = 10;
    private void updateInFire(Dog dog) {
        if (--tickTillUpdateInFire > 0)
            return;
        tickTillUpdateInFire = 10;
        isInFire = true;
        var bb = dog.getBoundingBox();
        var startPos = Util.containing(bb.minX, bb.minY, bb.minZ);
        var endPos = Util.containing(bb.maxX, bb.maxY, bb.maxZ);
        for (var pos : BlockPos.betweenClosed(startPos, endPos)) {
            var state = dog.level().getBlockState(pos);
            if (state.getBlock() instanceof FireBlock) {
                return;
            }
        }
        isInFire = false;
    }

    private boolean canRoll(Dog dog) {
        if (this.rollCooldown > 0)
            return false;
        if (isInFire)
            return false;
        if (!dog.onGround()) 
            return false;
        if (dog.isInSittingPose())
            return false;
        if (!dog.canDoIdileAnim())
            return false;
        if (!dog.readyForNonTrivialAction())
            return false;
        if (dog.getTarget() != null) {
            return false;
        }
        return true;
    }

    private boolean canContinueToRoll(Dog dog) {
        if (isInFire)
            return false;
        if (!dog.onGround()) 
            return false;
        if (dog.isInSittingPose())
            return false;
        if (!dog.canContinueDoIdileAnim())
            return false;
        if (fireExtinguished && dog.isOnFire())
            return false;
        return true;
    }

    @Override
    public InteractionResult stillIdleOrSitWhenHurt(AbstractDog dog, DamageSource source, float amount) {
        if (isRolling && source.isFire())
            return InteractionResult.SUCCESS;
        return InteractionResult.PASS;    
    }

    public static class DogStopDropRollAction extends TriggerableAction {

        private int stopTick;
        private int tickTillExtinguish = 60;        
        private FireDrillTalent talentInst;

        public DogStopDropRollAction(Dog dog, FireDrillTalent talentInst) {
            super(dog, false, false);
            this.talentInst = talentInst;
        }

        @Override
        public void onStart() {
            this.stopTick = dog.tickCount + DogAnimation.STOP_DROP_ROLL.getLengthTicks();
            this.dog.setAnimForIdle(DogAnimation.STOP_DROP_ROLL);
            talentInst.fireExtinguished = false;
            talentInst.isRolling = true;
        }

        @Override
        public void tick() {
            if (dog.getAnim() != DogAnimation.STOP_DROP_ROLL) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (dog.tickCount >= stopTick) {
                this.setState(ActionState.FINISHED);
                return;
            }
            if (!talentInst.canContinueToRoll(dog)) {
                this.setState(ActionState.FINISHED);
                return;
            }
            --tickTillExtinguish;
            if (tickTillExtinguish == 0 && dog.isOnFire()) {
                dog.clearFire();
                dog.playSound( 
                    SoundEvents.FIRE_EXTINGUISH,  
                    0.5F, 2.6F + dog.getRandom().nextFloat() - dog.getRandom().nextFloat() * 0.8F);
                talentInst.fireExtinguished = true;
            }
        }

        @Override
        public void onStop() {
            if (dog.getAnim() == DogAnimation.STOP_DROP_ROLL) {
                dog.setAnim(DogAnimation.NONE);
            }
            talentInst.rollCooldown = 40;
            talentInst.isRolling = false;
        }

        @Override
        public boolean canOverrideSit() {
            return true;
        }

        @Override
        public boolean canPreventSit() {
            return true;
        }

    }

    

}
