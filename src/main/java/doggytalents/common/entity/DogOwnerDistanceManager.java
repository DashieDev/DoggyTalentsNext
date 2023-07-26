package doggytalents.common.entity;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.ai.triggerable.DogGreetOwnerAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;

public class DogOwnerDistanceManager {
    
    private static final int OWNER_FAR_AWAY_DISTANCE_SQR = 400;
    private static final int OWNER_START_GREET_DISTANCE_SQR = 144;
    private static final long START_MISSING_OWNER_TIME = 24000*4; // 4 days.
    private static final int UPDATE_INTERVAL = 5;

    private static Map<UUID, Integer> GREETING_DOG_LIMIT_MAP = Maps.newConcurrentMap();

    private final Dog dog;

    private long lastWithOwnerTime;
    private boolean willGreet;

    private int tickTillUpdate;

    private long lastOwnerLeftInteval;

    public DogOwnerDistanceManager(Dog dog) {
        this.dog = dog;
        this.lastWithOwnerTime = dog.level.getDayTime();
    }

    public void tick() {
        if (--this.tickTillUpdate <= 0) {
            this.tickTillUpdate = UPDATE_INTERVAL;
            var owner = this.dog.getOwner();
            if (owner != null) updateGreetingCondition(dog, owner);
        }
    }

    private void updateGreetingCondition(Dog dog, @Nonnull LivingEntity owner) {
        if (this.isOwnerReturned(dog, owner)) {
            long gtime = dog.level.getDayTime();
            long dtime = gtime - lastWithOwnerTime;
            if (dtime >= START_MISSING_OWNER_TIME) {
                this.willGreet = true;
                this.lastOwnerLeftInteval = dtime;
            }
            this.lastWithOwnerTime = gtime;
        }
        if (this.canStartGreeting(dog, owner)) {
            this.triggerGreetingAction(dog, owner, this.lastOwnerLeftInteval);
            this.lastOwnerLeftInteval = 0;
        }
    }

    private boolean isOwnerReturned(Dog dog, LivingEntity owner) {
        return dog.distanceToSqr(owner) < OWNER_FAR_AWAY_DISTANCE_SQR;
    }

    private boolean canStartGreeting(Dog dog, LivingEntity owner) {
        return 
            this.willGreet
            && dog.distanceToSqr(owner) <= OWNER_START_GREET_DISTANCE_SQR
            && !owner.isSpectator()
            && !dog.isBusy();
    }

    public void save(CompoundTag tag) {
        var tg0 = new CompoundTag();
        tg0.putLong("lastWithOwnerTime", this.lastWithOwnerTime);
        tg0.putBoolean("willGreet", this.willGreet);
        tag.put("ownerDistanceManager", tg0);
    }

    public void load(CompoundTag tag) {
        if (tag.contains("ownerDistanceManager", Tag.TAG_COMPOUND)) {
            var tg0 = tag.getCompound("ownerDistanceManager");
            this.lastWithOwnerTime = tg0.getLong("lastWithOwnerTime");
            this.willGreet = tg0.getBoolean("willGreet");
        } else {
            this.lastWithOwnerTime = this.dog.level.getDayTime();
            this.willGreet = false;
        }
    }

    public void triggerGreetingAction(Dog dog, @Nonnull LivingEntity owner, long ownerLeftInterval) {
        this.willGreet = false;
        boolean greetOwnerEnabled = 
            ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GREET_OWNER);
        int greetOwnerLimit = 
            ConfigHandler.ServerConfig.getConfig(ConfigHandler.SERVER.DOG_GREET_OWNER_LIMIT);
        if (!greetOwnerEnabled) return;
        if (greetOwnerLimit > 0) {
            if (getGreetCountForOwner(owner) >= greetOwnerLimit) return;
        }
        dog.triggerAction(new DogGreetOwnerAction(dog, owner, ownerLeftInterval));
        incGreetCountForOwner(owner);
    }

    public static int getGreetCountForOwner(LivingEntity owner) {
        return GREETING_DOG_LIMIT_MAP.getOrDefault(owner.getUUID(), 0);
    }

    public static void incGreetCountForOwner(LivingEntity owner) {
        GREETING_DOG_LIMIT_MAP.compute(owner.getUUID(), (uuid, old_val)  -> {
            if (old_val == null) {
                return 1;
            }
            return old_val + 1;
        });
    }

    public static void decGreetCountForOwner(LivingEntity owner) {
        GREETING_DOG_LIMIT_MAP.computeIfPresent(owner.getUUID(), (uuid, old_val)  -> {
            if (old_val == null) {
                return null;
            }
            int new_val = old_val - 1;
            if (new_val <= 0) return null;
            return new_val;
        });
    }

}
