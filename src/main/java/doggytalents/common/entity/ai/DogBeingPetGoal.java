package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.List;

import doggytalents.api.anim.DogAnimation;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogPettingManager.DogPettingType;
import doggytalents.common.entity.ai.triggerable.DogWantAttentionAction;
import doggytalents.common.entity.anim.DogPose;
import doggytalents.common.util.EntityUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;

public class DogBeingPetGoal extends Goal {

    private Dog dog;
    private boolean petEnd = false;
    private int petEndTickLeft = 0;
    private DogAnimation currentLoopAnim = DogAnimation.FACERUB_PP;
    private static final List<DogAnimation> facerubloopAnims_p_pp = List.of(
        DogAnimation.FACERUB_PP,
        DogAnimation.FACERUB_PP2,
        DogAnimation.FACERUB_P,
        DogAnimation.FACERUB_P2
    );
    private static final List<DogAnimation> facerubloopAnims_f_ff = List.of(
        DogAnimation.FACERUB_F,
        DogAnimation.FACERUB_F2,
        DogAnimation.FACERUB_F,
        DogAnimation.FACERUB_FF2
    );
    private static final List<DogAnimation> hugloopAnims_p_pp = List.of(
        DogAnimation.HUG_PP,
        DogAnimation.HUG_PP2,
        DogAnimation.HUG_P,
        DogAnimation.HUG_P2
    );
    private static final List<DogAnimation> hugloopAnims_f_ff = List.of(
        DogAnimation.HUG_F,
        DogAnimation.HUG_F2,
        DogAnimation.HUG_F,
        DogAnimation.HUG_FF2
    );
    private int tickTillChangeLoop = 0;
    private int petTick_ff_threshold = 0;
    private int petTick = 0;
    private int sound_cooldown;
    private int triggerCooldown;

    public DogBeingPetGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.dog.isDefeated())
            return false;
        if (!this.dog.isOrderedToSit())
            return false;
        if (!this.dog.pettingManager.isPetting())
            return false;
        if (!this.dog.canDoIdileAnim())
            return false;
        if (dog.isOnFire())
            return false;
        if (this.dog.getDogPose() != DogPose.SIT)
            return false;
        if (!this.dog.onGround())
            return false;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.dog.isDefeated())
            return false;
        if (!this.dog.isOrderedToSit())
            return false;
        if (!this.dog.canContinueDoIdileAnim())
            return false;
        if (!this.dog.isInSittingPose())
            return false;
        if (!this.dog.onGround())
            return false;
        return this.petEndTickLeft > 0;
    }

    @Override
    public void start() {
        var start_anim = getStartAnim();
        var first_anim = getFirstAnim();
        var end_anim = getEndAnim();
        this.petEndTickLeft = end_anim.getLengthTicks();
        this.dog.setAnimForIdle(start_anim);
        this.currentLoopAnim = first_anim;
        this.tickTillChangeLoop = first_anim.getLengthTicks() * 3;
        this.petEnd = false;
        this.petTick = 0;
        int r = dog.getRandom().nextIntBetweenInclusive(0, 25);
        this.petTick_ff_threshold = (5 + r) * 20;
        this.sound_cooldown = 0;
        this.triggerCooldown = startTriggerCooldown(dog);
    }

    private void resetLoopAnim() {
        var petloopAnims_target = getLoopAnims_pp_p();
        if (this.petTick >= this.petTick_ff_threshold)
            petloopAnims_target = getLoopAnims_ff_f();
        int r_anim = this.dog.getRandom().nextInt(petloopAnims_target.size());
        this.currentLoopAnim = petloopAnims_target.get(r_anim);
        int r_loop_count = 1;
        this.tickTillChangeLoop = r_loop_count * this.currentLoopAnim.getLengthTicks();
    }

    @Override
    public void tick() {
        var end_anim = getEndAnim();
        var start_anim = getStartAnim();
        if (!this.dog.pettingManager.isPetting()) {
            this.petEnd = true;
        }
        if (this.petEnd) {
            --this.petEndTickLeft;
            if (this.dog.getAnim() != end_anim)
                this.dog.setAnim(end_anim);
            this.dog.pettingManager.setLocked(true);
            return;
        }
        this.petEndTickLeft = end_anim.getLengthTicks();
        if (this.dog.getAnim() == DogAnimation.NONE 
        || (this.dog.getAnim() == start_anim && this.dog.animationManager.isHolding())) {
            this.dog.setAnim(this.currentLoopAnim);
        }
        if (this.dog.getAnim() == this.currentLoopAnim) {
            --this.tickTillChangeLoop;
        }
        if (this.tickTillChangeLoop <= 0) {
            this.resetLoopAnim();
            this.dog.setAnim(this.currentLoopAnim);
        }
        var owner = this.dog.getOwner();
        if (owner != null)
            this.dog.getLookControl().setLookAt(owner, 10.0F, this.dog.getMaxHeadXRot());
        ++petTick;
        updateAmbientSound();
        updateTriggerNearbyDogsJealous();
        rejuvinateDog();
    }

    private void updateAmbientSound() {
        if (this.sound_cooldown > 0) {
            --this.sound_cooldown;
            return;
        }
        
        SoundEvent selectedEvent = null;
        float r = dog.getRandom().nextFloat();
        if (r >= 0.1)
            return;
        float r2 = dog.getRandom().nextFloat();
        float sound = dog.getSoundVolume();
        if (r2 <= 0.2) {
            selectedEvent = SoundEvents.WOLF_WHINE;
            this.sound_cooldown = 20;   
        } else {
            selectedEvent = SoundEvents.WOLF_PANT;
            this.sound_cooldown = 10;
            sound = 1.5f;
        }

        if (this.petTick < this.petTick_ff_threshold) {
            this.sound_cooldown = 50;
        }

        if (selectedEvent != null) {
            dog.playSound(selectedEvent, sound, dog.getVoicePitch());
        }
    }

    private void updateTriggerNearbyDogsJealous() {
        if (--this.triggerCooldown > 0)
            return;
        this.triggerCooldown = shortTriggerCooldown(dog);
        //--triggerAttemptLeft;
        var target_optional = EntityUtil.getRandomEntityAround(
            dog, Dog.class, 7, 1, filter_dog -> isTargetJealousDog(filter_dog));
        if (!target_optional.isPresent())
            return;
        
        var target = target_optional.get();
        float r = this.dog.getRandom().nextFloat();
        if (r > target.pettingManager.getJealousChance())
            return;
        triggerCooldown = longTriggerCooldown(dog);
        var owner = target.getOwner();
        if (owner == null)
            return;
        target.triggerAction(new DogWantAttentionAction(target, 
            owner, target.isOrderedToSit()));
    }

    private void rejuvinateDog() {
        if (dog.getRandom().nextInt(400) != 0)
            return;
        boolean add_hunger = dog.getRandom().nextBoolean();
        if (add_hunger) {
            if (dog.getDogHunger() < 20)
                dog.addHunger(1);
        } else {
            if (dog.getHealth() / dog.getMaxHealth() < 0.25f)
                dog.heal(1);
        }
    }

    private int startTriggerCooldown(Dog dog) {
        return 5 * 20;
    }

    private int longTriggerCooldown(Dog dog) {
        return 10 * 20 + dog.getRandom().nextInt(6) * 20;
    }

    private int shortTriggerCooldown(Dog dog) {
        return 2 * 20 + dog.getRandom().nextInt(6) * 20;
    }

    private boolean isTargetJealousDog(Dog target) {
        if (!target.isDoingFine())
            return false;
        if (target.isBusy())
            return false;
        var owner = target.getOwner();
        if (owner == null || owner != dog.getOwner())
            return false;
        var pet_manager = target.pettingManager;
        if (pet_manager.getJealousChance() <= 0)
            return false;
        return true;
    }

    @Override
    public void stop() {
        var anim = dog.getAnim();
        if (!anim.interupting()) {
            this.dog.setAnim(DogAnimation.NONE);
        }
        //On interupt
        dog.pettingManager.stopPetting();
        this.dog.pettingManager.setLocked(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private DogAnimation getStartAnim() {
        var type = dog.getPettingState().type();
        if (type == DogPettingType.HUG) {
            return DogAnimation.HUG_START;
        } else {
            return DogAnimation.FACERUB_START;
        }
    }

    private DogAnimation getFirstAnim() {
        var type = dog.getPettingState().type();
        if (type == DogPettingType.HUG) {
            return DogAnimation.HUG_PP;
        } else {
            return DogAnimation.FACERUB_PP;
        }
    }

    private DogAnimation getEndAnim() {
        var type = dog.getPettingState().type();
        if (type == DogPettingType.HUG) {
            return DogAnimation.HUG_END;
        } else {
            return DogAnimation.FACERUB_END;
        }
    }

    private List<DogAnimation> getLoopAnims_pp_p() {
        var type = dog.getPettingState().type();
        if (type == DogPettingType.HUG) {
            return hugloopAnims_p_pp;
        } else {
            return facerubloopAnims_p_pp;
        }
    }

    private List<DogAnimation> getLoopAnims_ff_f() {
        var type = dog.getPettingState().type();
        if (type == DogPettingType.HUG) {
            return hugloopAnims_f_ff;
        } else {
            return facerubloopAnims_f_ff;
        }
    }
    
}