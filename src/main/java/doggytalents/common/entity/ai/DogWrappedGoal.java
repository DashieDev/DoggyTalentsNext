package doggytalents.common.entity.ai;

import java.util.EnumSet;

import javax.annotation.Nullable;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

public class DogWrappedGoal extends Goal {

    private final Goal goal;

    public DogWrappedGoal(Goal goal) {
        this.goal = goal;
    }

    public boolean canUse() {
        return this.goal.canUse();
    }

    public boolean canContinueToUse() {
        return this.goal.canContinueToUse();
    }

    public boolean isInterruptable() {
        return this.goal.isInterruptable();
    }

    public void start() {
        this.goal.start();
    }

    public void stop() {
        this.goal.stop();
    }

    public boolean requiresUpdateEveryTick() {
        return this.goal.requiresUpdateEveryTick();
    }

    protected int adjustedTickDelay(int tick) {
        return this.requiresUpdateEveryTick() ? tick : tick/2;
    }

    public void tick() {
        this.goal.tick();
    }

    public void setFlags(EnumSet<Goal.Flag> p_26005_) {
        this.goal.setFlags(p_26005_);
    }

    public EnumSet<Goal.Flag> getFlags() {
        return this.goal.getFlags();
    }

    public Goal getGoal() {
        return this.goal;
    }
    
}
