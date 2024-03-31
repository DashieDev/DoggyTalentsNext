package doggytalents.common.entity.ai;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Supplier;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.DogWrappedGoal.HasTickNonRunningPrev;
import doggytalents.common.entity.ai.triggerable.DogTriggerableGoal;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class DogAiManager {
    
    private final Dog dog;
    private final Supplier<ProfilerFiller> profilerSup;
    private final ArrayList<WrappedGoal> goals = new ArrayList<>();
    private final ArrayList<WrappedGoal> targetGoals = new ArrayList<>();
    private final ArrayList<WrappedGoal> tickNonRunningGoals = new ArrayList<>();
    private final Map<Goal.Flag, WrappedGoal> runningGoalsWithFlag = new EnumMap<>(Goal.Flag.class);
    private final EnumSet<Goal.Flag> lockedFlags = EnumSet.noneOf(Goal.Flag.class);

    private DogSitWhenOrderedGoal sitGoal;
    private WrappedGoal nonTrivialActionGoal;
    private WrappedGoal trivialActionGoal;

    public DogAiManager(Dog dog, Supplier<ProfilerFiller> profileSup) {
        this.dog = dog;
        this.profilerSup = profileSup;
    }

    public void init() {

        int p = 1;
        registerDogGoal(p, new DogFloatGoal(this.dog));
        registerDogGoal(p, new DogDrunkGoal(this.dog));
        registerDogGoal(p, new DogAvoidPushWhenIdleGoal(this.dog));
        //registerDogGoal(1, new PatrolAreaGoal(this.dog));
        ++p;
        registerDogGoal(p, new DogGoAwayFromFireGoal(this.dog));
        ++p;
        initSitGoal(p);
        registerDogGoal(p, new DogProtestSitOrderGoal(this.dog));
        ++p;
        registerDogGoal(p, new DogLowHealthGoal.StickToOwner(this.dog));
        registerDogGoal(p, new DogLowHealthGoal.RunAway(this.dog));
        //registerDogGoal(4, new DogLeapAtTargetGoal(this.dog, 0.4F));
        ++p;
        initTriggerableActionGoals(false, p);
        ++p; //Prioritize Talent Action
        registerDogGoal(p, new DogHungryGoal(this.dog, 1.0f, 2.0f));
        ++p;
        //All mutex by nature
        registerDogGoal(p, new GuardModeGoal.Minor(this.dog));
        registerDogGoal(p, new GuardModeGoal.Major(this.dog));
        ++p;
        registerDogGoal(p, new DogMeleeAttackGoal(this.dog, 1.0D, true, 20, 40));
        registerDogGoal(p, new DogGoRestOnBedGoalDefeated(this.dog));
        ++p;
        registerDogGoal(p, new DogFindWaterGoal(this.dog));
        ++p;
        initTriggerableActionGoals(true, p);
        //registerDogGoal(p, new FetchGoal(this.dog, 1.0D, 32.0F));
        registerDogGoal(p, new DogFollowOwnerGoalDefeated(this.dog));
        registerDogGoal(p, new DogFollowOwnerGoal(this.dog, 1.0D, 10.0F, 2.0F));
        ++p;
        registerDogGoal(p, new DogMoveBackToRestrictGoal(this.dog));
        registerDogGoal(p, new DogBreedGoal(this.dog, 1.0D));
        ++p;
        registerDogGoal(p, new DogRandomStrollGoal(this.dog, 1.0D));
        registerDogGoal(p, new DogRandomStandIdleGoal(this.dog));
        registerDogGoal(p, new DogRandomSniffGoal(this.dog));
        registerDogGoal(p, new DogCommonStandIdleGoal(this.dog));
        ++p;
        registerDogGoal(p, new DogBegGoal(this.dog, 8.0F));
        ++p;
        registerDogGoal(p, new DogFeelingNakeyGoal(this.dog));
        registerDogGoal(p, new DogLookAtPlayerGoal(this.dog));
        registerDogGoal(p, new RandomLookAroundGoal(this.dog));
        registerDogGoal(p, new DogRandomSitIdleGoal(this.dog));
        registerDogGoal(p, new DogCommonSitIdleGoal(this.dog));
        registerDogGoal(p, new DogRestWhenSitGoal(this.dog));

        registerTargetGoal(1, new DogOwnerHurtByTargetGoal(this.dog));
        registerTargetGoal(2, new DogOwnerHurtTargetGoal(this.dog));
        registerTargetGoal(3, (new HurtByTargetGoal(this.dog)).setAlertOthers());
        registerTargetGoal(5, new DogNearestToOwnerAttackableTargetGoal<>(this.dog, AbstractSkeleton.class, false));
        registerTargetGoal(6, new BerserkerModeGoal(this.dog));
        registerTargetGoal(6, new GuardModeGoal(this.dog));
        registerTargetGoal(6, new PatrolAssistTargetGoal(this.dog));
        
    }

    private void initTriggerableActionGoals(boolean trivial, int priority) {
        if (!trivial) {
            this.nonTrivialActionGoal = 
                registerDogGoal(priority, new DogTriggerableGoal(dog, false));
        } else {
            this.trivialActionGoal =
                registerDogGoal(priority, new DogTriggerableGoal(dog, true));
        }
    }

    private void initSitGoal(int priority) {
        this.sitGoal = new DogSitWhenOrderedGoal(dog);
        registerDogGoal(priority, sitGoal);
    }

    private WrappedGoal registerDogGoal(int priority, Goal goal) {
        var ret = new WrappedGoal(priority, new DogWrappedGoal(goal));
        if (goal instanceof HasTickNonRunningPrev) {
            this.tickNonRunningGoals.add(ret);
        }
        this.goals.add(ret);
        return ret;
    }

    private void registerTargetGoal(int priority, TargetGoal goal) {
        this.targetGoals.add(new WrappedGoal(priority, goal));
    }

    public void tickServer() {

        if (!dog.canUpdateDogAi())
            return;

        var profiler = this.profilerSup.get();
        profiler.push("dogAi");

        tickNonRunningGoalWithPrev();        
        
        boolean updateTime = isTimeToUpdateNonEveryTick(dog);

        if (updateTime) {
            updateRunningGoalWithFlag();
            stopRunningGoalIfShouldBeStopped(this.goals);
            stopRunningGoalIfShouldBeStopped(this.targetGoals);

            findNewGoalToStart(this.goals);
            findNewGoalToStart(this.targetGoals);
        }

        boolean only_tick_always_update = !updateTime;
        tickRunningGoals(this.goals, only_tick_always_update);
        tickRunningGoals(this.targetGoals, only_tick_always_update);

        profiler.pop();
    }

    private void tickNonRunningGoalWithPrev() {
        for (var goal : tickNonRunningGoals) {
            if (goal.getGoal() instanceof DogWrappedGoal dogGoal) {
                if (goal.isRunning())
                    continue;    
                dogGoal.tickDogWhenGoalNotRunning();
            }
        }
    }

    private boolean isTimeToUpdateNonEveryTick(Dog dog) {
        if (dog.tickCount <= 1)
            return false;
        int timeLine = dog.level().getServer().getTickCount() + dog.getId();
        return timeLine % 2 == 0;
    }

    private void updateRunningGoalWithFlag() {
        var itr = runningGoalsWithFlag.entrySet().iterator();
        while (itr.hasNext()) {
            var entry = itr.next();
            if (!entry.getValue().isRunning())
                itr.remove();
        }
    }

    private void stopRunningGoalIfShouldBeStopped(ArrayList<WrappedGoal> goalList) {
        for (var goal : goalList) {
            if (!goal.isRunning())
                continue;
            if (goalMustStop(goal))
                goal.stop();
        }
    }

    private boolean goalMustStop(WrappedGoal goal) {
        if (goalHasLockedFlag(goal)) 
            return true;
        if (!goal.canContinueToUse())
            return true;
        return false;
    }

    private void findNewGoalToStart(ArrayList<WrappedGoal> goalList) {
        for (var goal : goalList) {
            if (goal.isRunning())
                continue;
            if (goalCanStart(goal)) {
                startGoal(goal);
            }
        }
    }

    private void startGoal(WrappedGoal goal) {
        for (var flag : goal.getFlags()) {
            var runningGoal = this.runningGoalsWithFlag.get(flag);
            if (runningGoal != null) {
                runningGoal.stop();
            }
            this.runningGoalsWithFlag.put(flag, goal);
        }
        
        goal.start();
    }

    private boolean goalCanStart(WrappedGoal goal) {
        if (goalHasLockedFlag(goal))
            return false;
        if (isBlockedByAnotherGoalSharingFlag(goal))
            return false;
        return goal.canUse();
    }

    private boolean isBlockedByAnotherGoalSharingFlag(WrappedGoal goal) {
        var flags = goal.getFlags();
        if (flags.isEmpty())
            return false;
        for (var flag : flags) {
            var runningGoal = this.runningGoalsWithFlag.get(flag);
            if (runningGoal == null)
                continue;
            if (runningGoal.canBeReplacedBy(goal))
                continue;
            return true;
        } 
        return false;
    }

    private void tickRunningGoals(ArrayList<WrappedGoal> goalList, boolean tick_only_always_update) {
        for (var goal : goalList) {
            if (!goal.isRunning())
                continue;
            if (tick_only_always_update && !goal.requiresUpdateEveryTick())
                continue;
            goal.tick();
        }
    }

    private boolean goalHasLockedFlag(WrappedGoal goal) {
        for (var flag : goal.getFlags()) {
            if (this.lockedFlags.contains(flag)) {
                return true;
            }
        }
        return false;
    }

    public void setLockedFlag(Goal.Flag flag, boolean free) {
        if (!free)
            this.lockedFlags.add(flag);
        else
            this.lockedFlags.remove(flag);
    }

    public void forceStopAllGoal() {
        for (var goal : this.goals) {
            if (goal.isRunning())
                goal.stop();
        }
    }

    public void forceStopAllGoalWithFlag(Goal.Flag flag) {
        for (var goal : this.goals) {
            if (goal.isRunning() && goal.getFlags().contains(flag))
                goal.stop();
        }
    }

    public boolean isBusy() {
        int trivial_p = this.trivialActionGoal.getPriority();
        for (var flag : this.trivialActionGoal.getFlags()) {
            var runningGoal = this.runningGoalsWithFlag.get(flag);
            if (runningGoal == null)
                continue;
            if (!runningGoal.isRunning())
                continue;
            if (runningGoal.getPriority() <= trivial_p)
                return true;
        }
        return false;
    }

    public boolean readyForNonTrivivalAction() {
        int non_trivial_p = this.nonTrivialActionGoal.getPriority();
        for (var flag : this.nonTrivialActionGoal.getFlags()) {
            var runningGoal = this.runningGoalsWithFlag.get(flag);
            if (runningGoal == null)
                continue;
            if (!runningGoal.isRunning())
                continue;
            if (runningGoal.getPriority() <= non_trivial_p)
                return false;
        }
        return true;
    }

}
