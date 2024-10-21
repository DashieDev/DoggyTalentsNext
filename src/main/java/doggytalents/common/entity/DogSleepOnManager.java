package doggytalents.common.entity;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.Maps;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class DogSleepOnManager {
    
    private final Dog dog;
    private boolean sleepOnRequested = false;
    private boolean sleepOnReady = false;
    private int requestTimeout = 0;

    public DogSleepOnManager(Dog dog) {
        this.dog = dog;
    }

    public void tick() {
        if (!this.dog.level().isClientSide)
            invalidateRequest();
        
        var sleep_on_state = dog.getSleepOnState();
        if (!sleep_on_state.is_sleeping())
            return;

        if (!this.dog.level().isClientSide)
            invalidateSleepingPlayer(sleep_on_state);

        
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

    private void invalidateSleepingPlayer(DogSleepOnState sleep_on_state) {
        var id = sleep_on_state.sleeper();
        var sleeper = ((ServerLevel) dog.level()).getEntity(id);
        boolean stil_valid_sleeper =
            sleeper instanceof LivingEntity living
            && living.isSleeping();
        if (!stil_valid_sleeper)
            dog.setSleepOnState(DogSleepOnState.NULL);
    }

    public void setRequestedSleepOn(boolean val) {
        this.sleepOnRequested = val;
        this.requestTimeout = 20;
    }

    public boolean isSleepOnRequested() {
        return this.sleepOnRequested;
    }

    public void setPlayerSleepOn(Player player) {
        var sleep_pos_optional = findSleepPos();
        if (!sleep_pos_optional.isPresent())
            return;
        player.startSleeping(dog.blockPosition());
        player.moveTo(sleep_pos_optional.get());
        rotatePlayerYRotToDog(player);
        this.dog.setSleepOnState(new DogSleepOnState(player.getUUID(), true));
    }
    
    private Optional<Vec3> findSleepPos() {
        var dog_pos = this.dog.position();
        var dog_view_vec = this.dog.getViewVector(1);
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

    private void rotatePlayerYRotToDog(Player player) {
        double dx = dog.getX() - player.getX();
        double dz = dog.getZ() - player.getZ();
        var rotate_yrot = (float)( Mth.atan2(dz, dx) * Mth.RAD_TO_DEG - 90f );
        player.setYRot(rotate_yrot);
        player.yBodyRot = player.getYRot();
        player.yHeadRot = player.yBodyRot;
    }

    public void stopPlayerSleepOn() {
        this.dog.setSleepOnState(DogSleepOnState.NULL);
    }

    public void onSleepOnGoalStop() {
        this.sleepOnRequested = false;
        this.stopPlayerSleepOn();
        this.sleepOnReady = false;
    }

    public static Player getSleeperFromDog(Dog dog) {
        var pet_uuid = dog.getSleepOnState();
        var petter = dog.level().getPlayerByUUID(pet_uuid.sleeper());
        return petter;
    }

    public static record DogSleepOnState(UUID sleeper, boolean is_sleeping) {
        public static DogSleepOnState NULL = new DogSleepOnState(net.minecraft.Util.NIL_UUID, false);
    }

    private static final Map<UUID, Dog> sleepingOnDogs = Maps.newHashMap(); 

    public static void onServerStop() {
        sleepingOnDogs.clear();
    }

    public static void addSleepingOnDogToMap(UUID id, Dog dog) {
        sleepingOnDogs.put(id, dog);
    }

    public static void removeSleepingOnDogToMap(UUID id) {
        sleepingOnDogs.remove(id);
    }

    public static Optional<Dog> getSleepingOnDog(LivingEntity entity) {
        if (sleepingOnDogs.isEmpty())
            return Optional.empty();
        if (!(entity instanceof Player player))
            return Optional.empty();
        return Optional.ofNullable(sleepingOnDogs.get(player.getUUID()));
    }

    public static void clearPlayerSleepOnFor(Dog dog) {
        var toRemove = new ArrayList<UUID>();
        for (var entry : sleepingOnDogs.entrySet()) {
            if (entry.getValue() == dog) {
                toRemove.add(entry.getKey());
            }
        }
        for (var key : toRemove) {
            removeSleepingOnDogToMap(key);
        }
    }

    public static void onDogSleepOnDataUpdated(Dog dog, DogSleepOnState state) {
        if (!state.is_sleeping()) {
            clearPlayerSleepOnFor(dog);
            return;
        }
        var sleeper_id = state.sleeper();
        addSleepingOnDogToMap(sleeper_id, dog);
        var sleeper = getSleeperFromDog(dog);
        if (sleeper == null)
            return;
        dog.sleepOnManager.rotatePlayerYRotToDog(sleeper);
        
    }
}
