package doggytalents.common.storage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.Maps;

import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Constants;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OnlineDogLocationManager {

    private final Map<UUID, Dog> onlineDogs = Maps.newHashMap();
    private final ArrayList<UUID> toRemove = new ArrayList<>();

    private final DogLocationStorage storage;

    public OnlineDogLocationManager(DogLocationStorage storage) {
        this.storage = storage;
    }

    public void tick() {
        if (onlineDogs.isEmpty())
            return;
        invalidateOnlineDogs();
        updateAllOnlineDogs();
    }

    private void invalidateOnlineDogs() {
        for (var entry : onlineDogs.entrySet()) {
            var dog = entry.getValue();
            if (!dog.isRemoved())
                continue;
            toRemove.add(entry.getKey());
            if (ConfigHandler.SERVER.LOG_WHEN_DOG_GO_OFFLINE.get())
                logOfflineDog(entry.getValue());
            dog.dogAi.handleOfflineDog();
        }
        if (toRemove.isEmpty())
            return;
        for (var x : toRemove) {
            onlineDogs.remove(x);
        }
        toRemove.clear();
    }

    public void onDogGoOnline(Dog dog) {
        this.onlineDogs.put(dog.getUUID(), dog);
    }

    public Optional<Dog> getOnlineDog(UUID dogId) {
        var dog = this.onlineDogs.get(dogId);
        if (dog != null && dog.isRemoved()) {
            return Optional.empty();
        }
        return Optional.ofNullable(dog);
    }

    private int tickTillUpdateAllOnlineDog = 40;
    private void updateAllOnlineDogs() {
        if (--tickTillUpdateAllOnlineDog > 0)
            return;
        tickTillUpdateAllOnlineDog = 40;
        for (var entry : onlineDogs.entrySet()) {
            var dog = entry.getValue();
            if (dog.isRemoved())
                continue;
            syncDataToStorage(storage, dog);
        }
    }

    private void syncDataToStorage(DogLocationStorage storage, Dog dog) {
        var data = storage.getOrCreateData(dog);
        if (data == null)
            return;
        data.update(dog);
    }

    public void onServerStop() {
        unrideAllDogOnPlayer();
        
        //Preserve to log offline info
        if (!ConfigHandler.SERVER.LOG_WHEN_DOG_GO_OFFLINE.get())
            this.onlineDogs.clear();
        this.toRemove.clear();
    }
    
    public void onServerStopped() {
        if (!ConfigHandler.SERVER.LOG_WHEN_DOG_GO_OFFLINE.get())
            return;
        
        for (var entry : this.onlineDogs.entrySet()) {
            logOfflineDog(entry.getValue());
        }

        this.onlineDogs.clear();
    }

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/dogOnlineTracker");
    public static void logOfflineDog(Dog dog) {
        if (dog.getOwnerUUID() == null)
            return;
        if (dog.locationUpdatedUponRemove == null)
            return;
        var remove_reason = dog.getRemovalReason();
        var pos = dog.blockPosition();
        var name_str = dog.getName().getString();
        var pos_str = pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
        var type_str = remove_reason == null ? "WHAT?"
            : remove_reason.toString();
        
        var log_msg = dog.locationUpdatedUponRemove
            .getFormattedLog(name_str, pos_str, type_str);
        LOGGER.debug(log_msg);
    }

    private void unrideAllDogOnPlayer() {
        for (var entry : this.onlineDogs.entrySet()) {
            var dog = entry.getValue();
            if (dog.isRemoved())
                continue;
            if (!dog.isPassenger())
                continue;
            var vehicle = dog.getVehicle();
            if (vehicle instanceof Player) {
                dog.unRide();
            }
        }
    }

    public static enum RemoveState {
        NONE("Dog [ %s ] failed to update location upon going offline at [ %s ] with type [ %s ]"), 
        UPDATED("Dog [ %s ] has gone Offline at [ %s ] with type [ %s ]"), 
        UNLOADED_TO_RESPAWN("Dog [ %s ] has been unloaded to Respawn Storage"), 
        REMOVED("Untamed Dog [ %s ] has been removed."),
        REMOVE_TRUSTED("Dog [ %s ] has been trustfully removed at [ %s ] with type [ %s ]"); 
        
        private final String unformattedLog;
        private RemoveState(String unformattedLog) {
            this.unformattedLog = unformattedLog;
        }

        public String getFormattedLog(String name, String pos, String type) {
            if (name == null) name = "??";
            if (pos == null) pos = "??";
            if (type == null) type = "??";
            return String.format(this.unformattedLog, name, pos, type);
        }
    }
    
}
