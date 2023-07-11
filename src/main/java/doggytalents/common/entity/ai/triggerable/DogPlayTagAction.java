package doggytalents.common.entity.ai.triggerable;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import doggytalents.common.forward_imitate.ComponentUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class DogPlayTagAction extends TriggerableAction {

    private boolean ownerBeenTagged = false;
    private static final int RUN_AWAY_RADIUS = 20;
    private static final int RUN_AWAY_RADIUS_MIN = 16;
    private static final int RUN_AWAY_PATH_INTERVAL = 10;
    private static final int RUN_AWAY_LOOKBACK_INTERVAL = 20;
    private static final int RUN_AWAY_LOOKBACK_TIME = 20;
    private static final int TIME_LIMIT_PER_TURN = 400;

    private int tickTillPathRecalc;
    private int cooldownChase;
    private int timeLeft;

    private int tickTillLook;

    private LivingEntity owner;

    public DogPlayTagAction(Dog dog, LivingEntity owner) {
        super(dog, true, true);
        this.owner = owner;
    }

    @Override
    public void onStart() {
        this.timeLeft = TIME_LIMIT_PER_TURN;
        this.cooldownChase = 10;
    }

    @Override
    public void tick() {
        --timeLeft;
        if (dog.distanceToSqr(owner) > RUN_AWAY_RADIUS*RUN_AWAY_RADIUS || timeLeft <= 0) {
            this.setState(ActionState.FINISHED);
            if (this.ownerBeenTagged) {
                owner.sendMessage(ComponentUtil.translatable("dog.msg.play_tag.dog_win", dog.getName().getString()), net.minecraft.Util.NIL_UUID);
                dog.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
            } else {
                owner.sendMessage(ComponentUtil.translatable("dog.msg.play_tag.you_win", dog.getName().getString()), net.minecraft.Util.NIL_UUID);
                owner.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
            }
            return;
        }
        if (!this.ownerBeenTagged ) {
            if (this.cooldownChase <= 0) {
                chaseOwner(dog, owner);
            } else {
                --this.cooldownChase;
            }
            
        } else {
            runAway(dog, owner);
        }
        
    }

    private void chaseOwner(Dog dog, LivingEntity owner) {
        var n = dog.getNavigation();
        var dog_b0 = dog.blockPosition();
        var owner_b0 = owner.blockPosition();
        var d0 = dog.distanceToSqr(owner);
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (--this.tickTillPathRecalc <= 0) {
            this.tickTillPathRecalc = RUN_AWAY_PATH_INTERVAL;
            if (d0 > 256) this.tickTillPathRecalc += 5;
            n.moveTo(owner, 1);
        }
        if (  
            n.isDone() 
            && dog_b0.distSqr(owner_b0) <= 4
            && !this.canReachTarget(owner, d0) 
        ) {
            dog.getMoveControl().setWantedPosition(owner.getX(), owner.getY(), owner.getZ(), 1);
        }
        if(n.isDone() && dog.tickCount % 2 != 0 && !this.canReachTarget(owner, d0)) {
            this.tickTillPathRecalc = 0;
        }
        if (checkAndTag(dog, owner)) {
            n.stop();
            owner.sendMessage(ComponentUtil.translatable("dog.msg.play_tag.gotcha", dog.getName().getString() ), net.minecraft.Util.NIL_UUID);
            this.dog.playSound(SoundEvents.WOLF_AMBIENT, 1, 1);
            this.ownerBeenTagged = true;
            this.cooldownChase = 30;
            this.timeLeft = TIME_LIMIT_PER_TURN;
            return;
        }
    }

    private boolean checkAndTag(Dog dog, LivingEntity owner) {
        if (this.canReachTarget(owner, dog.distanceToSqr(owner))) {
            
            //this.dog.swing(InteractionHand.MAIN_HAND);
            //owner.hurt(DamageSource.mobAttack(dog), 0.001f);
            dog.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
            return true;
        }
        return false;
    }

    private void runAway(Dog dog, LivingEntity owner) {
       var n = dog.getNavigation();
       var d = dog.distanceToSqr(owner);
       if (--this.tickTillLook <= 0) {
            this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
            if (this.tickTillLook <= -RUN_AWAY_LOOKBACK_TIME) {
                this.tickTillLook = RUN_AWAY_LOOKBACK_INTERVAL;
            }
       }
       if (n.isDone() && dog.tickCount % 2 != 0) {
            var b0 = getRandomPosAwayFromOwner(dog, owner);
            n.moveTo(b0.getX(), b0.getY(), b0.getZ(), 1);
       }
       //DISTANCE RECALC PATH
       if (d < 9 && dog.tickCount % 10 == 0) {
            n.stop();
       }
       if (this.cooldownChase > 0) {
            --this.cooldownChase;
       }
       if (canReachTarget(owner, d) && this.cooldownChase <= 0) {
            n.stop();
            dog.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
            this.ownerBeenTagged = false;
            this.cooldownChase = 30;
            this.timeLeft = TIME_LIMIT_PER_TURN;
       }

    }

    private BlockPos getRandomPosAwayFromOwner(Dog dog, LivingEntity owner) {
        var owner_b0 = owner.blockPosition();
        int off = RUN_AWAY_RADIUS - RUN_AWAY_RADIUS_MIN + 1;
        int dx = EntityUtil.getRandomNumber(dog,-off, off);
        dx += Mth.sign(dx)*RUN_AWAY_RADIUS_MIN;
        int dy = EntityUtil.getRandomNumber(dog,-2, 2);
        int dz = EntityUtil.getRandomNumber(dog,-off, off);
        dz += Mth.sign(dz)*RUN_AWAY_RADIUS_MIN;
        
        return owner_b0.offset(dx, dy, dz);
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        
    }

    protected boolean canReachTarget(LivingEntity target, double distanceToTargetSqr) {
        return this.getTagReachSqr(target) >= distanceToTargetSqr;
    }

    protected double getTagReachSqr(LivingEntity target) {
        return (double) (this.dog.getBbWidth() * 2.0F * this.dog.getBbWidth() * 2.0F + target.getBbWidth());
    }
    
}
