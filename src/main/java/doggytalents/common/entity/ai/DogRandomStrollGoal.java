package doggytalents.common.entity.ai;

import doggytalents.ChopinLogger;
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
    private int tickCountStopMiningCautious;

    public DogRandomStrollGoal(Dog dog, double speedModifier) {
        super(dog, speedModifier);
        this.dog = dog;
    }

    @Override
    public boolean canUse() {

        if (this.ownerMayBeMining()) {
            this.tickCountStopMiningCautious = this.dog.tickCount + 600; // keep checking for 30 seconds
        }


        return super.canUse();
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
        
        if (this.ownerMayBeMining()) {
            this.tickCountStopMiningCautious = this.dog.tickCount + 600; // keep checking for 30 seconds
        }
        
        if (this.dog.tickCount < this.tickCountStopMiningCautious) {
            if (this.pathObstructOwnerMining()) {
                this.stop();
            }
        }
    }

    //TODO Mining genius : the dog will follow owner while putting torch down 
    //Closely and also run with him
    //And lead any other dogs which is close too .... 
    //TODO CHANGE : make the logic make more sense and efficent
    //Check if owner is swinging with a digger item in hand.
    private boolean ownerMayBeMining() {
        var owner = this.dog.getOwner();
        if (owner == null) return false;
        return
            owner.swinging 
            && owner.getMainHandItem().getItem() instanceof DiggerItem;
            
    }

    /**
     * Check if the forward nodes in dog's path is obstructing owner mining,
     * an obstructing path is defined in {@link DogUtil#posWillCollideWithOwnerMovingForward}
     */
    private boolean pathObstructOwnerMining() {

        var n = this.dog.getNavigation(); 
        var p = n.getPath();
        if (p == null || p.getNodeCount() <= 0) return false;

        var owner = dog.getOwner();
        if (owner == null) return false;
        
        //Iterate through the next 5 blocks of the path and check if obstruct owner.
        int i0 = p.getNextNodeIndex();
        int i_end = Mth.clamp(i0+5, i0, p.getNodeCount()-1);
        for (int i = i0; i < i_end; ++i) {

            boolean flag = 
                DogUtil.posWillCollideWithOwnerMovingForward(dog, owner, p.getNodePos(i));

            if (flag) {
                // ChopinLogger.sendToOwner(this.dog, 
                //     this.dog.getName().getString()
                //      + " : i was going to go to this pos,"
                //      + p.getNodePos(i)
                //      + " but it is not good!"); //debug chopin
                return true;
            }

        }

        return false;
    }

    
}
