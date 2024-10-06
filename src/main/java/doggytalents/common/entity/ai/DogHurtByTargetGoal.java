package doggytalents.common.entity.ai;

import org.apache.commons.lang3.ObjectUtils;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;

public class DogHurtByTargetGoal extends HurtByTargetGoal {
    
    private final Dog dog;

    public DogHurtByTargetGoal(Dog dog) {
        super(dog);
        this.dog = dog;
        this.setAlertOthers();
    }

    @Override
    public boolean canUse() {
        if (!dog.getMode().shouldAttack())
            return false;
        return super.canUse();
    }

    @Override
    protected void alertOthers() {
        var target = dog.getLastHurtByMob();
        if (target == null) 
            return;
        double alert_radius = this.getFollowDistance();
        var alert_bb = AABB.unitCubeFromLowerCorner(dog.position())
            .inflate(alert_radius, 10, alert_radius);
        var alert_dogs = dog.level().getEntitiesOfClass(Dog.class, alert_bb,
            filter_dog -> isDogAlertTarget(filter_dog, target));
        if (alert_dogs.isEmpty())
            return;
        for (var alert_dog : alert_dogs) {
            this.alertOther(alert_dog, target);
        }
    }

    private boolean isDogAlertTarget(Dog other_dog, LivingEntity target) {
        if (other_dog == dog)
            return false;
        var other_owner_id = other_dog.getOwnerUUID();
        if (other_owner_id == null)
            return false;
        if (ObjectUtils.notEqual(dog.getOwnerUUID(), other_owner_id))
            return false;
        if (!other_dog.getMode().shouldAttack())
            return false;
        if (other_dog.getTarget() != null)
            return false;
        if (other_dog.isAlliedTo(target))
            return false;

        return true;
    }
}
