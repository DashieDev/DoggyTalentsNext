package doggytalents.common.entity.ai;

import java.util.EnumSet;
import java.util.Optional;

import doggytalents.api.feature.EnumMode;
import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class PatrolModeGoal extends Goal {
    
    private final Dog dog;
    private LivingEntity owner;
    private int tickUntilPathRecalc = 0;

    private int patrolDistance;


    public PatrolModeGoal(Dog dog) {
        this.dog = dog;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        //Reset BowlPos, force the dog to find new bowl
        this.dog.setBowlPos(this.dog.level.dimension(), Optional.empty());
    }

    @Override
    public void tick() {
        
    }

}
