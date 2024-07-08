package doggytalents.common.storage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.common.collect.Maps;

import doggytalents.ChopinLogger;
import doggytalents.common.entity.Dog;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.server.ServerStoppingEvent;

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
        }
        if (toRemove.isEmpty())
            return;
        for (var log : toRemove) {
            var dog_log = onlineDogs.get(log);
            var reason = dog_log.getRemovalReason();
            ChopinLogger.l("Dog " + "[ "
                + dog_log.getName().getString() 
                + " ] went offline. Type [ "
                + (reason == null ? "null" : reason.toString()) + " ]");
        }
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
        
        this.onlineDogs.clear();
        this.toRemove.clear();
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
    
}
