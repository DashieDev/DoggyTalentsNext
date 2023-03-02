package doggytalents.common.entity;

import doggytalents.ChopinLogger;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;

public class DogMiningCautiousManager {

    private Dog dog;
    private int miningCautiousTime = 0;

    private static final int MINING_CAUTIOUS_DURATION = 600;
    
    public DogMiningCautiousManager(Dog dog) {
        this.dog = dog;
    }
    
    public void tick() {
        if (miningCautiousTime > 0) --miningCautiousTime;
        
        if ((dog.tickCount & 1) != 0) return;
        var owner = dog.getOwner();
        if (owner == null) return;
        if (ownerMayBeMining(owner)) {
            miningCautiousTime = MINING_CAUTIOUS_DURATION;
            ChopinLogger.lwn (this.dog, "mining Cautious : " + miningCautiousTime);
        }
    }

    public boolean isMiningCautious() {
        return this.miningCautiousTime > 0;
    }

    private boolean ownerMayBeMining(LivingEntity owner) {
        return
            owner.swinging 
            && owner.getMainHandItem().getItem() instanceof DiggerItem;      
    }

}
