package doggytalents.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.Maps;

import doggytalents.ChopinLogger;
import doggytalents.client.DTNClientDogSleepOnManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

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
    
    public void setPlayerSleepOn(Dog dog, Player player) {
        if (!canPlayerSleepOn(player))
            return;
        var sleep_pos_optional = findSleepPos(dog);
        if (!sleep_pos_optional.isPresent())
            return;
        
        player.startSleeping(dog.blockPosition());
        player.moveTo(sleep_pos_optional.get());
        rotatePlayerYRotToDog(dog, player, sleep_pos_optional.get());
        rotateDogPerpenToOwner(dog, player);
        
        dog.setSleepOnState(new DogSleepOnState(player.getUUID(), true, sleep_pos_optional.get()));
        addDogSleepOnPair(player, dog);

        ((ServerLevel) player.level()).updateSleepingPlayerList();
    }

    private boolean canPlayerSleepOn(Player player) {
        var level = (ServerLevel) player.level();
        if (level.isDay())
            return false;
        if (!level.canSleepThroughNights())
            return false;
        return true;
    }

    private Optional<Vec3> findSleepPos(Dog dog) {
        var dog_pos = dog.position();
        var dog_view_vec = dog.getViewVector(1);
        final double distance_to_dog = 1.8;
        var check_vec = 
            new Vec3(dog_view_vec.x, 0, dog_view_vec.z).normalize()
                .scale(distance_to_dog);
        for (int i = 0; i < 8; ++i) {
            var check_pos = check_vec
                .yRot(i * 45f * Mth.DEG_TO_RAD)
                .add(dog_pos);
            var check_b0 = BlockPos.containing(check_pos);
            var type = WalkNodeEvaluator.getPathTypeStatic(dog, check_b0.mutable());
            if (type == PathType.WALKABLE)
                return Optional.of(check_pos);
        }
        return Optional.empty();
    }

    private void rotateDogPerpenToOwner(Dog dog, Player player) {
        double dx = player.getX() - dog.getX();
        double dz = player.getZ() - dog.getZ();
        var rotate_yrot = (float)( Mth.atan2(dz, dx) * Mth.RAD_TO_DEG );
        dog.setYRot(rotate_yrot);
        dog.yBodyRot = dog.getYRot();
        dog.yHeadRot = dog.getYRot();
    }

    public void stopPlayerSleepOn(Dog dog) {
        dog.setSleepOnState(DogSleepOnState.NULL);
        clearPlayerSleepOnFor(dog);
    }

    public static void rotatePlayerYRotToDog(Dog dog, Player player, Vec3 sleep_pos) {
        double dx = dog.getX() - sleep_pos.x();
        double dz = dog.getZ() - sleep_pos.z();
        var rotate_yrot = (float)( Mth.atan2(dz, dx) * Mth.RAD_TO_DEG - 90f );
        player.setYRot(rotate_yrot);
        player.yBodyRot = player.getYRot();
        player.yHeadRot = player.yBodyRot;
    }

    public Optional<Dog> getSleepingOnDog(LivingEntity entity) {
        if (sleepingOnPairs.isEmpty())
            return Optional.empty();
        if (!(entity instanceof Player player))
            return Optional.empty();
        return Optional.ofNullable(sleepingOnPairs.get(player.getUUID()).dog());
    }

    public void onSleepGoalStop(Dog dog) {
        dog.sleepOnManager.onSleepOnGoalStop();
        this.stopPlayerSleepOn(dog);
    }
    
    private final Map<UUID, SleepOnPair> sleepingOnPairs = Maps.newHashMap();
    private final List<SleepOnPair> toRemove = new ArrayList<>();

    public void tickServer() {
        invalidateSleepers();
    }

    public void onServerStop() {
        sleepingOnPairs.clear();
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

    public static Optional<Player> getSleeperFromDog(Dog dog) {
        var state = dog.getSleepOnState();
        return getSleeperFromDog(dog, state);
    }

    public static Optional<Player> getSleeperFromDog(Dog dog, DogSleepOnState state) {
        var sleeper = dog.level().getPlayerByUUID(state.sleeper());
        return Optional.ofNullable(sleeper);
    }

    public static void onDogSleepOnDataUpdated(Dog dog, DogSleepOnState state) {
        if (dog.level().isClientSide)
            DTNClientDogSleepOnManager.get().onDogSleepOnDataUpdated(dog, state);
    } 

    private static record SleepOnPair(Dog dog, Player player) {} 

    public static record DogSleepOnState(UUID sleeper, boolean is_sleeping, Vec3 sleep_pos) {
        public static DogSleepOnState NULL = new DogSleepOnState(net.minecraft.Util.NIL_UUID, false, Vec3.ZERO);
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
