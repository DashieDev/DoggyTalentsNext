package doggytalents.common.entity.ai.triggerable;

import javax.annotation.Nonnull;

import doggytalents.api.anim.DogAnimation;
import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class DogWantAttentionAction extends TriggerableAction {
    
    private boolean prevIsOrderedToSit;
    private static enum Phase { GO_TO_OWNER, BEG_FOR_ATTENTION }
    private Phase phase = Phase.GO_TO_OWNER;
    private final @Nonnull LivingEntity owner;

    private int goToOwnerTimeout;
    private int tickTillPathRecalc;
    private boolean whinedToAttention;

    private int tickAnim;
    private int stopTick;

    public DogWantAttentionAction(Dog dog, @Nonnull LivingEntity owner, boolean prevIsOrderedToSit) {
        super(dog, true, false);
        this.owner = owner;
        this.prevIsOrderedToSit = prevIsOrderedToSit;
    }

    @Override
    public void onStart() {
        this.tickTillPathRecalc = 5;
        this.phase = Phase.GO_TO_OWNER;
        this.goToOwnerTimeout = 200;
        this.whinedToAttention = false;
        tickAnim = 0;
    }

    @Override
    public void tick() {
        if (this.phase == Phase.GO_TO_OWNER) {
            if (this.goToOwnerTimeout <= 0) {
                stopAndMaySitDown();
                return;
            }
        } else {
            if (this.dog.tickCount >= this.stopTick) {
                stopAndMaySitDown();
                return;
            }
            if (this.dog.getAnim() != DogAnimation.PLAY_WITH_MEH) {
                stopAndMaySitDown();
                return;
            }
            if (this.dog.distanceToSqr(owner) > 16) {
                stopAndMaySitDown();
                return;
            }
        }

        if (this.phase == Phase.GO_TO_OWNER) {
            tickGoToOwner();
        } else {
            tickBegForAttention();
        }
    }

    private void stopAndMaySitDown() {
        this.setState(ActionState.FINISHED);
        if (this.prevIsOrderedToSit)
            this.dog.setOrderedToSit(true);
    }

    private void tickGoToOwner() {
        this.dog.getLookControl().setLookAt(owner);
        if (--this.goToOwnerTimeout <= 0) {
            this.goToOwnerTimeout = 0;
            return;
        }

        var d0 = dog.distanceToSqr(owner);
        boolean closeEnough = d0 <= 1.5 * 1.5;
            
        if (--this.tickTillPathRecalc <= 0) {
            if (!closeEnough)
                dog.getNavigation().moveTo(owner, 1);
            this.tickTillPathRecalc = 20;
        }
        
        if (closeEnough) {
            if (!dog.getNavigation().isDone()) {
                dog.getNavigation().stop();
            }
            if (!whinedToAttention) {
                whinedToAttention = true;
                this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
                int r = this.dog.getRandom().nextInt(3);
                owner.sendMessage(ComponentUtil.translatable(
                    "dog.msg.want_attention." + r, dog.getName().getString()), net.minecraft.Util.NIL_UUID);
            }
            checkAndSwitchToAttention();
        }
    }

    private void checkAndSwitchToAttention() {
        if (!DogUtil.checkIfOwnerIsLooking(dog, owner))
            return;
        
        this.phase = Phase.BEG_FOR_ATTENTION;
        this.tickAnim = 0;
        this.stopTick = dog.tickCount + DogAnimation.PLAY_WITH_MEH.getLengthTicks();
        this.dog.setAnim(DogAnimation.PLAY_WITH_MEH);
    }

    private void tickBegForAttention() {
        this.dog.getLookControl().setLookAt(owner);
        ++tickAnim;
        if (this.tickAnim == 54) {
            this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
        }
    }

    @Override
    public void onStop() {
        if (this.dog.getAnim() == DogAnimation.PLAY_WITH_MEH) {
            this.dog.setAnim(DogAnimation.NONE);
        }
    }

    @Override
    public boolean canOverrideSit() {
        return true;
    }

}
