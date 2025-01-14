package doggytalents.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;

import doggytalents.DoggyTalents;
import doggytalents.client.DTNClientDogSleepOnManager;
import doggytalents.common.talent.BedDogTalent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Player.BedSleepingProblem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.CanContinueSleepingEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;

public class DogSleepOnManager {
    
    private static final DogSleepOnManager SERVER_INSTANCE = new DogSleepOnManager();
    
    private DogSleepOnManager() {}
    
    public static DogSleepOnManager getServer(Level level) {
        if (level.isClientSide)
            throw new IllegalStateException("Only access this class's instance from the Logical Server.");
        return SERVER_INSTANCE;
    }
    
    public static DogSleepOnManager getServer(MinecraftServer server) {
        if (server == null)
            throw new IllegalStateException("Only access this class's instance from the Logical Server.");
        return SERVER_INSTANCE;
    }



    public void setOrRequestSleepOn(Dog dog, Player player) {
        if (!this.isSleepCondition(dog))
            return;
        if (dog.sleepOnManager.isSleepOnReady()) {
            setPlayerSleepOn(dog, player);
        } else {
            dog.sleepOnManager.setRequestedSleepOn(true);
        }
    }
    
    public boolean setPlayerSleepOn(Dog dog, Player player) {
        if (!canPlayerStartSleepOnDog(dog, player))
            return false;
        var sleep_pair_optional = findSleepRot(dog, player);
        if (!sleep_pair_optional.isPresent())
            return false;
        var sleep_pair = sleep_pair_optional.get();
        final float sleep_yrot = sleep_pair.getLeft();

        player.startSleeping(dog.blockPosition());
        player.moveTo(sleep_pair.getRight());
        rotateDogPerpenToSleepYRot(dog, sleep_yrot);
        rotatePlayerYRotToDog(dog, player, sleep_yrot);
        
        dog.setSleepOnState(new DogSleepOnState(player.getUUID(), true, sleep_yrot));
        addDogSleepOnPair(player, dog);

        ((ServerLevel) player.level()).updateSleepingPlayerList();
        return true;
    }

    public boolean canPlayerStartSleepOnDog(Dog dog, Player player) {
        if (!isSleepCondition(dog))
            return false;
        if (!dog.sleepOnManager.sleepOnReady)
            return false;
        return true;
    }
    
    public boolean isSleepCondition(Dog dog) {
        var level = (ServerLevel) dog.level();
        if (level.isDay())
            return false;
        if (!level.canSleepThroughNights())
            return false;
        var inst = dog.getTalent(DoggyTalents.BED_DOG.get(), BedDogTalent.class);
        if (!inst.isPresent())
            return false;
        if (!BedDogTalent.isSleepCondition(dog, inst.get()))
            return false;
        return true;
    }

    private Optional<Pair<Float, Vec3>> findSleepRot(Dog dog, Player player) {
        //First candidate
        float check_yrot1 = player.getYRot() + 180;
        var check_pos1 = getPlayerSleepPos(dog, check_yrot1);
        if (checkIfSleepPosIsEligible(dog, check_pos1))
            return Optional.of(Pair.of(check_yrot1, check_pos1));

        final float dog_yrot = dog.getYRot();
        for (int i = 0; i < 8; ++i) {
            float check_yrot = dog_yrot + i * 45f;
            var check_pos = getPlayerSleepPos(dog, check_yrot);
            if (!checkIfSleepPosIsEligible(dog, check_pos))
                continue;
            return Optional.of(Pair.of(check_yrot, check_pos));
        }
        return Optional.empty();
    }

    private boolean checkIfSleepPosIsEligible(Dog dog, Vec3 check_pos) {
        var air_iterater = 
            BlockPos.betweenClosed(
                BlockPos.containing(check_pos.add(-1, 0, -1)),
                BlockPos.containing(check_pos.add(1, 0, 1)));
        for (var pos : air_iterater) {
            var state = dog.level().getBlockState(pos);
            if (!state.isAir()) {
                return false;
            }
        }
        var solid_iterater = 
            BlockPos.betweenClosed(
                BlockPos.containing(check_pos.add(-1, -1, -1)),
                BlockPos.containing(check_pos.add(1, -1, 1)));
        for (var pos : solid_iterater) {
            var state = dog.level().getBlockState(pos);
            if (!state.isCollisionShapeFullBlock(dog.level(), pos)) {
                return false;
            }
        }
        return true;
    }

    private Vec3 getPlayerSleepPos(Dog dog, float dog_sleep_rot) {
        var sleep_on_pos = getSleepOnHeadPos(dog, dog_sleep_rot);
        var dog_view_vec = dog.calculateViewVector(0, dog_sleep_rot);
        final double distance_to_dog = 0.2;
        return
            new Vec3(dog_view_vec.x, 0, dog_view_vec.z).normalize()
                .scale(distance_to_dog)
                .add(sleep_on_pos);
    }

    private Vec3 getSleepOnHeadPos(Dog dog, float dog_sleep_rot) {
        final float side_translate = -0.3f;
        float translate_rot = dog_sleep_rot + 90;
        var translate_vec = dog.calculateViewVector(0, translate_rot)
            .scale(side_translate);
        return dog.position().add(translate_vec);
    }

    private void rotateDogPerpenToSleepYRot(Dog dog, float dog_sleep_yrot) {
        var rotate_yrot = Mth.wrapDegrees(dog_sleep_yrot + 90);
        dog.setYRot(rotate_yrot);
        dog.yBodyRot = dog.getYRot();
        dog.yHeadRot = dog.yBodyRot;
    }

    public Optional<Dog> getSleepingOnDog(LivingEntity entity) {
        if (sleepingOnPairs.isEmpty())
            return Optional.empty();
        if (!(entity instanceof Player player))
            return Optional.empty();
        return Optional.ofNullable(sleepingOnPairs.get(player.getUUID()).dog());
    }

    public void stopPlayerSleepOn(Dog dog) {
        dog.setSleepOnState(DogSleepOnState.NULL);
        clearPlayerSleepOnFor(dog);
    }

    

    private final Map<UUID, SleepOnPair> sleepingOnPairs = Maps.newHashMap();
    private final List<SleepOnPair> toRemove = new ArrayList<>();

    public static void tickServer(MinecraftServer server) {
        getServer(server).invalidateSleepers();
    }

    public static void onServerStop(MinecraftServer server) {
        getServer(server).sleepingOnPairs.clear();
    }

    private void invalidateSleepers() {
        if (this.sleepingOnPairs.isEmpty())
            return;
        for (var entry : sleepingOnPairs.entrySet()) {
            var pair = entry.getValue();
            if (stillValidSleepingPair(pair.dog(), pair.player()))
                continue;    
            toRemove.add(pair);
        }

        for (var x : toRemove) {
            stopPlayerSleepOn(x.dog());
        }
        toRemove.clear();
    }

    private boolean stillValidSleepingPair(Dog dog, Player player) {
        if (!player.isAlive() || !dog.isAlive())
            return false;
        if (!player.isSleeping())
            return false;
        var dog_sleeping_state = dog.getSleepOnState();
        if (!dog_sleeping_state.is_sleeping())
            return false;

        var sleep_pos = getPlayerSleepPos(dog, dog_sleeping_state.sleep_yrot());
        if (player.distanceToSqr(sleep_pos) > 0.1 * 0.1)
            return false;
        
        return true;
    }

    private void addDogSleepOnPair(Player player, Dog dog) {
        var uuid = player.getUUID();
        if (uuid == null)
            return; 
        sleepingOnPairs.put(uuid, new SleepOnPair(dog, player));
    }

    private void removeSleepingOnDogToMap(UUID sleeper_id) {
        sleepingOnPairs.remove(sleeper_id);
    }

    private void clearPlayerSleepOnFor(Dog dog) {
        if (sleepingOnPairs.isEmpty())
            return;
        var toRemove = new ArrayList<UUID>();
        for (var entry : sleepingOnPairs.entrySet()) {
            if (entry.getValue().dog() == dog) {
                toRemove.add(entry.getKey());
            }
        }
        for (var key : toRemove) {
            removeSleepingOnDogToMap(key);
        }
    }

    private void checkAndClearWhenPlayerWakeUp(Player player) {
        if (this.sleepingOnPairs.isEmpty())
            return;
        var pair = this.sleepingOnPairs.get(player.getUUID());
        if (pair == null)
            return;
        this.stopPlayerSleepOn(pair.dog());
    }

    private void notifySleepSuccesAllDogAndStopSleeping(ServerLevel level) {
        invalidateSleepers();
        for (var x : sleepingOnPairs.entrySet()) {
            var pair = x.getValue();
            var dog = pair.dog();
            if (!stillValidSleepingPair(dog, pair.player()))
                continue;
            if (dog.level() != level)
                continue;
            notifySleepSuccessDog(dog);
        }
    }

    private void notifySleepSuccessDog(Dog dog) {
        var inst = dog.getTalent(DoggyTalents.BED_DOG.get(), BedDogTalent.class);
        if (!inst.isPresent())
            return;
        inst.get().onSuccessfulSleep(dog);
    }



    public static void canPlayerContinueSleeping(CanContinueSleepingEvent event) {
        if (event.getProblem() != BedSleepingProblem.NOT_POSSIBLE_HERE)
            return;
        var player = event.getEntity();
        var dog_optional = DogSleepOnManager.getServer(player.getServer()).getSleepingOnDog(player);
        if (!dog_optional.isPresent())
            return;
        event.setContinueSleeping(true);
    }

    public static void beforeSleepFinishedForAllPlayer(SleepFinishedTimeEvent event) {
        var level = (ServerLevel) event.getLevel();
        DogSleepOnManager.getServer(level).notifySleepSuccesAllDogAndStopSleeping(level);
    }

    public static void onPlayerWakeUp(Player player) {
        var level = (ServerLevel) player.level();
        DogSleepOnManager.getServer(level).checkAndClearWhenPlayerWakeUp(player);
    }

    public static void onDogSleepOnDataUpdated(Dog dog, DogSleepOnState state) {
        if (dog.level().isClientSide)
            DTNClientDogSleepOnManager.get().onDogSleepOnDataUpdated(dog, state);
    }

    public static void onSleepGoalStop(Dog dog) {
        dog.sleepOnManager.onSleepOnGoalStop();
        getServer(dog.level()).stopPlayerSleepOn(dog);
    }

    public static boolean shouldBlockPush(Dog dog) {
        return dog.getSleepOnState().is_sleeping();
    }



    public static void rotatePlayerYRotToDog(Dog dog, Player player, float dog_sleep_yrot) {
        var rotate_yrot = Mth.wrapDegrees(dog_sleep_yrot - 180);
        player.setYRot(rotate_yrot);
        player.yBodyRot = player.getYRot();
        player.yHeadRot = player.yBodyRot;
    }

    public static Optional<Player> getSleeperFromDog(Dog dog) {
        var state = dog.getSleepOnState();
        return getSleeperFromDog(dog, state);
    }

    public static Optional<Player> getSleeperFromDog(Dog dog, DogSleepOnState state) {
        var sleeper = dog.level().getPlayerByUUID(state.sleeper());
        return Optional.ofNullable(sleeper);
    }



    private static record SleepOnPair(Dog dog, Player player) {} 

    public static record DogSleepOnState(UUID sleeper, boolean is_sleeping, float sleep_yrot) {
        public static DogSleepOnState NULL = new DogSleepOnState(net.minecraft.Util.NIL_UUID, false, 0);
    }

    public static class PerDog {
        private final Dog dog;
        private boolean sleepOnRequested = false;
        private boolean sleepOnReady = false;
        private int requestTimeout = 0;

        public PerDog(Dog dog) {
            this.dog = dog;
        }

        public void tick() {
            if (!this.dog.level().isClientSide)
                invalidateRequest();
        }

        public void setSleepOnReady(boolean val) {
            this.sleepOnReady = val;
        }

        public boolean isSleepOnReady() {
            return this.sleepOnReady;
        }

        private void invalidateRequest() {
            if (!this.sleepOnRequested)
                return;
            if (this.requestTimeout > 0) {
                --this.requestTimeout;
            }
            if (this.requestTimeout <= 0)
                this.sleepOnRequested = false;
        }

        public void setRequestedSleepOn(boolean val) {
            this.sleepOnRequested = val;
            this.requestTimeout = 20;
        }

        public boolean isSleepOnRequested() {
            return this.sleepOnRequested;
        }
        
        public void onSleepOnGoalStop() {
            this.sleepOnRequested = false;
            this.sleepOnReady = false;
        }
    }
}
