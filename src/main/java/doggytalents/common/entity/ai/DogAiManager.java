package doggytalents.common.entity.ai;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.triggerable.TriggerableAction;
import doggytalents.common.entity.ai.triggerable.TriggerableAction.ActionState;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class DogAiManager {

    private static final String PROFILER_STR = "dogAi";
    
    private final Dog dog;
    private final Supplier<ProfilerFiller> profilerSup;
    private final ArrayList<WrappedGoal> goals = new ArrayList<>();
    private final ArrayList<WrappedGoal> targetGoals = new ArrayList<>();
    private final ArrayList<IHasTickNonRunning> tickNonRunningGoals = new ArrayList<>();
    private final Map<Goal.Flag, WrappedGoal> runningGoalsWithFlag = new EnumMap<>(Goal.Flag.class);
    private final EnumSet<Goal.Flag> lockedFlags = EnumSet.noneOf(Goal.Flag.class);

    private DogSitWhenOrderedGoal sitGoal;
    private DogActionExecutorGoal nonTrivialActionGoal;
    private DogActionExecutorGoal trivialActionGoal;
    private int delayedActionStart = 0;
    private int timeoutPending = 0;

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
        registerDogGoal(p, new DogRangedAttackGoal(this.dog));
        registerDogGoal(p, new DogMeleeAttackGoal(this.dog));
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
        registerDogGoal(p, new DogBeingPetGoal(this.dog));
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
        registerTargetGoal(3, new DogHurtByTargetGoal(dog));
        registerTargetGoal(6, new BerserkerModeGoal(this.dog));
        registerTargetGoal(6, new GuardModeGoal(this.dog));
        registerTargetGoal(6, new PatrolAssistTargetGoal(this.dog));
        registerTargetGoal(7, new DogNearestToOwnerAttackableTargetGoal<>(this.dog, AbstractSkeleton.class, false));
    }

    private void initTriggerableActionGoals(boolean trivial, int priority) {
        if (!trivial) {
            this.nonTrivialActionGoal = new DogActionExecutorGoal(this, trivial, priority);
            registerDogGoal(priority, this.nonTrivialActionGoal);
        } else {
            this.trivialActionGoal = new DogActionExecutorGoal(this, trivial, priority);
            registerDogGoal(priority, this.trivialActionGoal);
        }
    }

    private void initSitGoal(int priority) {
        this.sitGoal = new DogSitWhenOrderedGoal(dog); 
        registerDogGoal(priority, this.sitGoal);
    }

    private WrappedGoal registerDogGoal(int priority, Goal goal) {
        var ret = new WrappedGoal(priority, goal);
        if (goal instanceof IHasTickNonRunning ticker) {
            this.tickNonRunningGoals.add(ticker);
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
        profiler.push(PROFILER_STR);

        if (this.delayedActionStart > 0)
            --delayedActionStart;
        else
            timeoutPending();

        tickNonRunningGoalWithPrev();
        
        boolean updateTime = isTimeToUpdateNonEveryTick(dog);

        invalidateRunning(updateTime);
        invalidateFlags(updateTime);
        startNewGoalOrAction(updateTime);
        tickRunning(updateTime);
        
        profiler.pop();
    }

    private void timeoutPending() {
        var activeActionOptional = this.getActiveAction();
        if (!activeActionOptional.isPresent())
            return;
        if (activeActionOptional.get().getState() != ActionState.PENDING)
            return;
        ++timeoutPending;
        if (timeoutPending >= 20)
            this.clearTriggerableAction();
    }

    private void invalidateRunning(boolean goalUpdateTime) {
        if (goalUpdateTime) {
            stopRunningGoalIfShouldBeStopped(this.goals);
            stopRunningGoalIfShouldBeStopped(this.targetGoals);
        }
    }

    private void invalidateFlags(boolean goalUpdateTime) {
        if (goalUpdateTime) {
            invalidateNotRunningFlags();
        }
    }

    private void startNewGoalOrAction(boolean goalUpdateTime) {
        if (goalUpdateTime) {
            findNewGoalToStart(this.goals);
            findNewGoalToStart(this.targetGoals);
        }
    }

    private void tickRunning(boolean goalUpdateTime) {
        boolean only_tick_always_update = !goalUpdateTime;
        tickRunningGoals(this.goals, only_tick_always_update);
        tickRunningGoals(this.targetGoals, only_tick_always_update);
    }

    private void tickNonRunningGoalWithPrev() {
        for (var goal : tickNonRunningGoals) {
            goal.tickDogWhenNotRunning();
        }
    }

    private boolean isTimeToUpdateNonEveryTick(Dog dog) {
        if (dog.tickCount <= 1)
            return false;
        int timeLine = dog.level().getServer().getTickCount() + dog.getId();
        return timeLine % 2 == 0;
    }

    private void invalidateNotRunningFlags() {
        var itr = runningGoalsWithFlag.entrySet().iterator();
        while (itr.hasNext()) {
            var entry = itr.next();
            var value = entry.getValue();
            if (value == null || !value.isRunning())
                itr.remove();
        }
    }

    private void stopRunningGoalIfShouldBeStopped(ArrayList<WrappedGoal> goalList) {
        for (var goal : goalList) {
            if (!goal.isRunning())
                continue;
            if (goalMustStop(goal))
                stopGoal(goal);
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
                stopGoal(runningGoal);
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

    private void stopGoal(WrappedGoal goal) {
        goal.stop();
        if (goal.getFlags().contains(Goal.Flag.MOVE))
            dog.getNavigation().stop();
    }

    public void setLockedFlag(Goal.Flag flag, boolean free) {
        if (!free)
            this.lockedFlags.add(flag);
        else
            this.lockedFlags.remove(flag);
    }

    public void forceStopAllGoal() {
        this.clearTriggerableAction();
        for (var goal : this.goals) {
            if (goal.isRunning())
                stopGoal(goal);
        }
    }

    public void forceStopAllGoalWithFlag(Goal.Flag flag) {
        if (this.nonTrivialActionGoal.getFlags().contains(flag)) {
            this.clearTriggerableAction();
        }
        for (var goal : this.goals) {
            if (goal.isRunning() && goal.getFlags().contains(flag))
                stopGoal(goal);
        }
    }

    private void onNonTrivialActionInterupted() {
        this.trivialActionGoal.clearAction();
    }

    public boolean isBusy() {
        if (this.getActiveAction().isPresent())
            return true;
        int trivial_p = this.trivialActionGoal.getPriority();
        for (var flag : this.trivialActionGoal.getFlags()) {
            if (this.lockedFlags.contains(flag))
                return true;
            var runningGoal = this.runningGoalsWithFlag.get(flag);
            if (runningGoal == null)
                continue;
            if (runningGoal.getGoal() == sitGoal)
                continue;
            if (!runningGoal.isRunning())
                continue;
            if (runningGoal.getPriority() <= trivial_p)
                return true;
        }
        return false;
    }

    public boolean readyForNonTrivivalAction() {
        boolean hasNonTrivialAction = this.getActiveAction()
            .map(x -> !x.isTrivial())
            .orElse(false);
        if (hasNonTrivialAction)
            return false;
        int non_trivial_p = this.nonTrivialActionGoal.getPriority();
        for (var flag : this.nonTrivialActionGoal.getFlags()) {
            if (this.lockedFlags.contains(flag))
                return false;
            var runningGoal = this.runningGoalsWithFlag.get(flag);
            if (runningGoal == null)
                continue;
            if (runningGoal.getGoal() == sitGoal)
                continue;
            if (!runningGoal.isRunning())
                continue;
            if (runningGoal.getPriority() <= non_trivial_p)
                return false;
        }
        return true;
    }

    public boolean triggerAction(TriggerableAction action) {
        if (action == null)
            return false;
        if (isOtherActionOccupied(action))
            return false;
        boolean sitBlock =
            this.dog.isOrderedToSit() 
            && (this.dog.forceSit() || !action.canOverrideSit());
        if (sitBlock)
            return false;
        this.dog.setOrderedToSit(false);
        putActionInExecutor(action);
        this.delayedActionStart = 0;
        this.timeoutPending = 0;
        return true;
    }
    
    private boolean isOtherActionOccupied(TriggerableAction action) {
        var activeActionOptional = this.getActiveAction();
        if (!activeActionOptional.isPresent())
            return false;
        var activeAction = activeActionOptional.get();
        boolean override_condition =
            activeAction.isTrivial() && !action.isTrivial();
        return !override_condition;
    }

    private void putActionInExecutor(TriggerableAction action) {
        if (action.isTrivial()) {
            this.trivialActionGoal.setAction(action);
            return;
        }
        var trivialAction = this.trivialActionGoal.getAction();
        if (trivialAction != null) {
            if (trivialAction.canPause()) {
                trivialAction.setState(ActionState.PAUSED);
            } else {
                this.trivialActionGoal.clearAction();
            }
        }
        this.nonTrivialActionGoal.setAction(action);
    }

    public boolean triggerActionDelayed(TriggerableAction action, int delayed) {
        var ret = triggerAction(action);
        if (ret)
            this.delayedActionStart = delayed;
        return ret;
    }

    public void clearTriggerableAction() {
        this.nonTrivialActionGoal.clearAction();
        this.trivialActionGoal.clearAction();
    }

    public boolean isActionBlockingSit() {
        var actionOptional = getActiveAction();

        if (!actionOptional.isPresent())
            return false;
        
        var action = actionOptional.get();

        return action.canPreventSit() && action.getState() == ActionState.RUNNING;
    }

    public Optional<TriggerableAction> getActiveAction() {
        TriggerableAction action = null;
        if (this.nonTrivialActionGoal.getAction() != null) {
            action = this.nonTrivialActionGoal.getAction();
        } else {
            action = this.trivialActionGoal.getAction();
        }
        return Optional.ofNullable(action);
    }

    public static interface IHasTickNonRunning {
    
        public void tickDogWhenNotRunning();
        
    }

    public static class DogActionExecutorGoal extends Goal {

        private @Nullable TriggerableAction action = null;
        private final DogAiManager dogAi;
        private final int priority;
        private final boolean isTrivial;

        public DogActionExecutorGoal(DogAiManager dogAi, boolean trivial, int p) {
            this.dogAi = dogAi;
            this.isTrivial = trivial;
            this.priority = p;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.action != null;
        }

        @Override
        public boolean canContinueToUse() {
            return this.action != null;
        }

        public void setAction(TriggerableAction action) {
            if (this.action != null)
                this.clearAction();
            this.action = action;
        }
        
        public void clearAction() {
            if (this.action == null)
                return;
            this.action.stop();
            this.action = null;
        }

        public boolean hasAction() {
            return this.action != null;
        }

        public TriggerableAction getAction() {
            return this.action;
        }

        @Override
        public void stop() {
            var action = this.action;
            if (action == null)
                return;
            if (action.getState() == ActionState.FINISHED) {
                this.clearAction();
                return;
            }
            if (action.isStarted() && action.canPause()) {
                action.setState(ActionState.PAUSED);
                return;
            }
            this.clearAction();
            if (!this.isTrivial) {
                this.dogAi.onNonTrivialActionInterupted();
            }
        }

        @Override
        public void tick() {
            var action = this.action;
            if (action == null) return;
            if (dogAi.delayedActionStart > 0)
                return;
            var state = action.getState();
            boolean shouldTick = false;
            if (state == ActionState.PENDING) {
                action.setState(ActionState.RUNNING);
                action.start();
            } else if (state == ActionState.PAUSED) {
                action.setState(ActionState.RUNNING);
                //action.onResume()
                if (action.isStarted()) 
                    shouldTick = true;
                else
                    action.start();
            } else if (state == ActionState.RUNNING) {
                shouldTick = true;
            }
            if (shouldTick)
                action.tick();
            
            state = action.getState();
            if (state == ActionState.FINISHED)
                clearAction();
        }
        
        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public int getPriority() {
            return priority;
        }

    }

}
