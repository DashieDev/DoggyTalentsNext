package doggytalents.common.entity.ai;

import doggytalents.api.enu.forward_imitate.ComponentUtil;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.DogUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;

/**
 * @author DashieDev
 */
public class DogRandomStrollGoal extends WaterAvoidingRandomStrollGoal {

    private Dog dog;

    public DogRandomStrollGoal(Dog dog, double speedModifier) {
        super(dog, speedModifier);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (!dog.isDoingFine())
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
        
        if (this.dog.isMiningCautious()) {
            if (DogUtil.pathObstructOwnerMining(this.dog)) {
                this.stop();
            }
        }
    }    

    
}
