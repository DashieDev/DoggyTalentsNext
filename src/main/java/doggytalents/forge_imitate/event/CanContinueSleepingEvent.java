package doggytalents.forge_imitate.event;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player.BedSleepingProblem;

public class CanContinueSleepingEvent extends Event {
    private final Optional<BedSleepingProblem> problem;
    private final LivingEntity entity;
    private boolean canContinueToSleep = false;

    public CanContinueSleepingEvent(LivingEntity entity, @Nullable BedSleepingProblem problem) {
        this.problem = Optional.ofNullable(problem);
        this.entity = entity;
    }

    public void setContinueSleeping(boolean val) {
        this.canContinueToSleep = val;
    }

    public boolean canContinueSleeping() {
        return this.canContinueToSleep;
    }

    public BedSleepingProblem getProblem() {
        return this.problem.orElse(null);
    }

    public LivingEntity getEntity() {
        return this.entity;
    }
}
