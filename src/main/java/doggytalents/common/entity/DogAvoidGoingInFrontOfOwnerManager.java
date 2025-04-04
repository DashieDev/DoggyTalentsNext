package doggytalents.common.entity;

import doggytalents.api.backward_imitate.ItemUtil_1_21_5;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ProjectileWeaponItem;

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
        return ownerMayBeMining(owner)
            || ownerIsShooting(owner);
    }

    private boolean ownerIsShooting(LivingEntity owner) {
        return
            owner.isUsingItem() 
            && owner.getMainHandItem().getItem() instanceof ProjectileWeaponItem;      
    }

    private boolean ownerMayBeMining(LivingEntity owner) {
        return
            owner.swinging 
            && ItemUtil_1_21_5.isDiggerItem(owner.getMainHandItem());      
    }

}
