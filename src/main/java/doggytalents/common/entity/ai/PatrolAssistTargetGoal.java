package doggytalents.common.entity.ai;

import org.apache.commons.lang3.ObjectUtils;

import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class PatrolAssistTargetGoal extends NearestAttackableTargetGoal<Mob> {
    
    private final Dog dog;

    public PatrolAssistTargetGoal(Dog dog) {
        super(dog, Mob.class, false , (e) -> {
            if (!(e instanceof Mob mob))
                return false;
            var target = mob.getTarget();
            if (!(target instanceof Dog targetDog))
                return false;
            var targetOwnerUUID = targetDog.getOwnerUUID();
            if (targetOwnerUUID == null)
                return false;
            if (ObjectUtils.notEqual(dog.getOwnerUUID(), targetOwnerUUID))
                return false;
            if (targetDog.getMode() != EnumMode.PATROL)
                return false;
            if (targetDog.patrolTargetLock())
                return false;
            return dog.isWithinRestriction(targetDog.blockPosition());
        });
        this.dog = dog;
    }

    @Override
    public boolean canUse() {
        if (this.dog.getMode() != EnumMode.PATROL)
            return false;
        if (!this.dog.patrolTargetLock())
            return false;
        return true;
    }

}
