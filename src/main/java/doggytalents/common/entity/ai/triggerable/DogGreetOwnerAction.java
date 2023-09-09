package doggytalents.common.entity.ai.triggerable;

import javax.annotation.Nonnull;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.DogOwnerDistanceManager;
import doggytalents.common.util.DogUtil;
import doggytalents.common.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class DogGreetOwnerAction extends TriggerableAction {

    private LivingEntity owner;

    private static final int GREET_TICK_PER_LEFT_DAY = 20;
    private static final int MAX_GREET_TIME = 400;
    private static final int MAY_CANCEL_GREET_TIME = 100; 

    private static final int START_GREET_DISTANCE_SQR = 4;
    private static final int GREET_RADIUS = 1;
    private static final int JUMP_BASE_INTERVAL = 7;
    private static final int WHINE_BASE_INTERVAL = 50;
    private static final int HEART_CHANCE_WINDOW = 8;
    private static final int MSG_CHANCE_WINDOW = 10;

    int greetTime;

    int tickTillPathRecalc;

    BlockPos rbAroundOwner;
    int tickTillWhine;
    int tickTillJump;

    boolean tellOwner;

    final int greetStopTime;
    //24000

    public DogGreetOwnerAction(Dog dog, @Nonnull LivingEntity owner, long ownerLeftInterval) {
        super(dog, true, true);
        this.owner = owner;
        this.greetStopTime = this.calculateGreetTime(ownerLeftInterval);
    }

    private int calculateGreetTime(long ownerLeftInterval) {
        int a = Mth.floor((ownerLeftInterval/24000)*GREET_TICK_PER_LEFT_DAY);
        return Math.min(a, MAX_GREET_TIME);
    }

    @Override
    public void onStart() {
        this.tickTillPathRecalc = 0;
        this.greetTime = 0;
        this.tickTillWhine = this.dog.getRandom().nextInt(5)*10;
        this.tellOwner = true;
    }

    @Override
    public void tick() {
        if (this.greetTime >= this.greetStopTime) {
            this.setState(ActionState.FINISHED);
            return;
        }
        if (!this.owner.isAlive() || this.owner.isSpectator()) {
            this.setState(ActionState.FINISHED);
            return;
        }

        var d0 = this.dog.distanceToSqr(this.owner);
        this.dog.getLookControl().setLookAt(this.owner, 10.0F, this.dog.getMaxHeadXRot());
        if (d0 > START_GREET_DISTANCE_SQR) {
            
            if (--this.tickTillPathRecalc <= 0) {
                this.tickTillPathRecalc = 20;
                DogUtil.moveToOwnerOrTeleportIfFarAway(
                    dog, owner, this.dog.getUrgentSpeedModifier(),
                    400, 
                    false, false, 
                    400,
                    dog.getMaxFallDistance());
            }
            
        } else {
            if (this.tellOwner) {
                this.tellOwner = false;
                int r = this.dog.getRandom().nextInt(MSG_CHANCE_WINDOW);
                if (r < 5) {
                    this.owner.sendMessage(ComponentUtil.translatable("dog.msg.greet_owner." + r, this.dog.getName().getString()), net.minecraft.Util.NIL_UUID);
                }
            }
            this.doGreet();
        }
    }

    private void doGreet() {
        ++greetTime;
        var n = this.dog.getNavigation();
        if (n.isDone() && dog.tickCount % 2 != 0) {
            this.rbAroundOwner = this.getRandomPosAroundOwner(owner);
            n.moveTo(
                this.rbAroundOwner.getX(),
                this.rbAroundOwner.getY(),
                this.rbAroundOwner.getZ(), 1
            );
        }
        if (--tickTillJump <= 0) {
            this.tickTillJump = JUMP_BASE_INTERVAL;
            this.dog.getJumpControl().jump();
            //ChopinLogger.l("jump");
        }
        if (--tickTillWhine <= 0) {
            tickTillWhine = WHINE_BASE_INTERVAL
                + dog.getRandom().nextInt(3) *20;
            this.dog.playSound(SoundEvents.WOLF_WHINE, this.dog.getSoundVolume(), this.dog.getVoicePitch());
        }
        if (dog.getRandom().nextInt(HEART_CHANCE_WINDOW) == 0) {
            if (dog.level instanceof ServerLevel sLevel) {
                sLevel.sendParticles(
                    ParticleTypes.HEART, 
                    dog.getX(), dog.getY(), dog.getZ(), 
                    1, 
                    dog.getBbWidth(), 0.8f, dog.getBbWidth(), 
                    0.1
                );
            }
        } 
    }

    @Override
    public void onStop() {
        int greetOwnerLimit = 
            ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GREET_OWNER_LIMIT);
        if (greetOwnerLimit < 0) return;
        DogOwnerDistanceManager.decGreetCountForOwner(owner);
    }

    @Override
    public boolean canOverrideSit() {
        return true;
    }
    
    @Override
    public boolean canPreventSit() {
        return this.greetTime < MAY_CANCEL_GREET_TIME;
    }
    
    private BlockPos getRandomPosAroundOwner(LivingEntity owner) {
        var owner_b0 = owner.blockPosition();
        int rX = EntityUtil.getRandomNumber(owner, -GREET_RADIUS, GREET_RADIUS);
        int rY = EntityUtil.getRandomNumber(owner, -1, 1);
        int rZ = EntityUtil.getRandomNumber(owner, -GREET_RADIUS, GREET_RADIUS);
        return owner_b0.offset(rX, rY, rZ);
    }
    
}
