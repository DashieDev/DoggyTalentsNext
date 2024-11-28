package doggytalents.common.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;

public class DogAvoidGoingInFrontOfOwnerManager {

    private Dog dog;
    private int activeTime = 0;

    private static final int ACTIVE_DURATION = 600;
    
    public DogAvoidGoingInFrontOfOwnerManager(Dog dog) {
        this.dog = dog;
    }
    
    public void tick() {
        if (activeTime > 0) --activeTime;
        
        if ((dog.tickCount & 1) != 0) return;
        var owner = dog.getOwner();
        if (owner == null) return;
        if (shouldAvoidGoingInfrontOfOwner(owner)) {
            activeTime = ACTIVE_DURATION;
        }
    }

    public boolean isActive() {
        return this.activeTime > 0;
    }

    private boolean shouldAvoidGoingInfrontOfOwner(LivingEntity owner) {
        return ownerMayBeMining(owner);
    }

    private boolean ownerMayBeMining(LivingEntity owner) {
        return
            owner.swinging 
            && owner.getMainHandItem().getItem() instanceof DiggerItem;      
    }

}
