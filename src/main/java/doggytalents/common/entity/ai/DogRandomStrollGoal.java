package doggytalents.common.entity.ai;

import javax.annotation.Nullable;

import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

/**
 * @author DashieDev
 */
public class DogRandomStrollGoal extends RandomStrollGoal {

    private Dog dog;

    public DogRandomStrollGoal(Dog dog, double speedModifier) {
        super(dog, speedModifier);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (!dog.isDoingFine())
            return false;
        if (this.dog.randomStrollCooldown > 0)
            return false;
        if (this.dog.isOnFire() && this.dog.getRandom().nextFloat() < 0.1f) {
            this.forceTrigger = true;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (!dog.isDoingFine())
            return false;
        return super.canContinueToUse();
    }
    
    //  TODO : Make this more user friendly, maybe make this into a talent or mode
    //
    //  But i can see people is going to need this cause i once got my TorchDog into lava
    //Because this reason here.., and it is also kinda annoying to see dogs going in 
    //front of owner when mining.
    //
    //  If Owner Swing with a DiggerItem in hand, go into Mining Cautious Mode, 
    //retains the mode for 30 seconds when the owner stops swining. 
    //
    //  In the mode, upon moving towards a random block (even when out of reach)
    //the dog will actively checks the path if 
    @Override
    public void tick() {
        super.tick();
        
        if (this.dog.avoidGoInFrontOfOwnerManager.isActive()) {
            if (DogUtil.pathGoingInFrontOfOwner(this.dog)) {
                this.stop();
            }
        }
    }    

    @Nullable
    @Override
    protected Vec3 getPosition() {
        boolean underwater_random = this.dog.isInWaterOrBubble()
            && this.dog.canSwimUnderwater()
            && this.dog.isDogSwimming();
        if (underwater_random)  
            return BehaviorUtils.getRandomSwimmablePos(this.dog, 10, 7);
        
        return getDogWaterAvoidRandomPos();
    }

    private Vec3 getDogWaterAvoidRandomPos() {
        if (this.dog.isInWaterOrBubble()) {
            Vec3 vec3 = LandRandomPos.getPos(this.dog, 15, 7);
            return vec3 == null ? super.getPosition() : vec3;
        } else {
            return this.dog.getRandom().nextFloat() >= WaterAvoidingRandomStrollGoal.PROBABILITY ? LandRandomPos.getPos(this.dog, 10, 7) : super.getPosition();
        }
    }

}
