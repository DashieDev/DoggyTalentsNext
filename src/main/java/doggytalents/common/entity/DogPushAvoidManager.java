package doggytalents.common.entity;

import org.apache.commons.lang3.ObjectUtils;

import doggytalents.common.config.ConfigHandler;
import net.minecraft.world.entity.Entity;

public class DogPushAvoidManager {
 
    public final Dog dog;
    private short avoidPushTick = 0;
    private short avoidPushNotOnGroundTick = 0;

    public DogPushAvoidManager(Dog dog) {
        this.dog = dog;
    }

    public void tickServer() {
        updatePushAvoidAndNotOnGroundTicks();
    }

    private void updatePushAvoidAndNotOnGroundTicks() {
        if (this.dog.getNavigation().isDone()) {
            if (this.avoidPushTick > 0)
                --this.avoidPushTick;
        } else {
            this.avoidPushTick = 5;
        }
        
        if (this.avoidPushTick <= 0) {
            this.avoidPushNotOnGroundTick = 0;
            return;
        }
            
        if (this.dog.onGround()) {
            if (this.avoidPushNotOnGroundTick > 0)
                --this.avoidPushNotOnGroundTick;
        } else {
            this.avoidPushNotOnGroundTick = 5;
        }
    }

    public boolean shouldBlockPush(Entity target) {
        if (this.dog.level().isClientSide)
            return false;

        if (checkPushAvoidOwner(target))
            return true;

        if (checkPushAvoidDog(target))
            return true;
        
        return false;
    }

    public boolean checkPushAvoidOwner(Entity target) {
        if (!ConfigHandler.SERVER.DOG_DONT_PUSH_OWNER.get())
            return false;
        if (ObjectUtils.notEqual(target.getUUID(), this.dog.getOwnerUUID()))
            return false;
        
        return true;
    }

    public boolean checkPushAvoidDog(Entity target) {
        if (this.dog.isDefeated())
            return false;
        if (isDogInFluid(dog))
            return false;
        if (!(target instanceof Dog otherDog))
            return false;
        if (!shouldDogsNotPushEachOther(this.dog, otherDog))
            return false;
        
        return true;
    }

    public static boolean shouldDogsNotPushEachOther(Dog dog1, Dog dog2) {
        if (!atLeastOnePushAvoiding(dog1, dog2))
            return false;
        if (!isTeammateDogs(dog1, dog2))
            return false;
        boolean atleast_one_not_flying =
            !dog1.isDogFlying() || !dog2.isDogFlying();
        if (!atleast_one_not_flying)
            return false;

        if (atLeastOnePushAvoidingNotOnGround(dog1, dog2))
            return true;
        if (checkBlockPushResistingDogWhileNav(dog1, dog2))
            return true;
        
        return false;
    }
    
    private static boolean atLeastOnePushAvoiding(Dog dog1, Dog dog2) {
        return dog1.dogPushAvoidManager.avoidPushTick > 0
            || dog2.dogPushAvoidManager.avoidPushTick > 0;
    }

    private static boolean atLeastOnePushAvoidingNotOnGround(Dog dog1, Dog dog2) {
        if (!dog1.onGround() || !dog2.onGround())
            return true;
        return dog1.dogPushAvoidManager.avoidPushNotOnGroundTick > 0
            || dog2.dogPushAvoidManager.avoidPushNotOnGroundTick > 0;
    }

    private static boolean checkBlockPushResistingDogWhileNav(Dog dog1, Dog dog2) {
        boolean dog1_nav = !dog1.getNavigation().isDone();
        boolean dog2_nav = !dog2.getNavigation().isDone();
        if (dog1_nav == dog2_nav)
            return false;
        Dog not_nav_dog = !dog1_nav ? dog1 : dog2;
        return not_nav_dog.isDogResistingPush();
    }

    private static boolean isTeammateDogs(Dog dog1, Dog dog2) {
        var owner1 = dog1.getOwner();
        var owner2 = dog2.getOwner();
        if (owner1 == null || owner2 == null)
            return false;
        if (owner1 == owner2)
            return true;
        
        return owner1.isAlliedTo(owner2);
    }

    private boolean isDogInFluid(Dog dog) {
        return !dog.getMaxHeightFluidType().isAir();
    }

}
