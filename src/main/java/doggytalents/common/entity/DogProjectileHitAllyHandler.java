package doggytalents.common.entity;

import doggytalents.DoggyEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;

public class DogProjectileHitAllyHandler {
    
    //False mean pass, True means the canHitEntity check should return false
    public static boolean onCheckIfCanHitTarget(Projectile proj, Entity target) {
        var proj_owner = proj.getOwner();
        if (proj_owner == null || target == null)
            return false;

        boolean entity_type_condition =
            proj_owner.getType() == DoggyEntityTypes.DOG.get()
            || target.getType() == DoggyEntityTypes.DOG.get(); 
        if (!entity_type_condition)
            return false;

        if (shouldProjectileStillHitAlly(proj, proj_owner, target)) 
            return false;
        
        Entity maybe_dog = null;
        Entity other = null;
        if (proj_owner.getType() == DoggyEntityTypes.DOG.get()) {
            maybe_dog = proj_owner;
            other = target;
        } else {
            maybe_dog = target;
            other = proj_owner;
        }
            
        if (!(maybe_dog instanceof Dog dog))
            return false;
        
        boolean result = DogAllyCheck.isAlliedToDog(dog, other);
        if (!result)
            return false;
        
        return true;
    }

    public static boolean shouldProjectileStillHitAlly(Projectile proj, Entity proj_owner, Entity target) {
        return 
            proj.getType() == EntityType.SNOWBALL
            && proj_owner.getType() == EntityType.PLAYER;
    }

}
