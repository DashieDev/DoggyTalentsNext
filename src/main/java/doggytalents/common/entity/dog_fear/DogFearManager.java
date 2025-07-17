package doggytalents.common.entity.dog_fear;

import java.util.Map;
import com.google.common.collect.Maps;

import doggytalents.common.entity.Dog;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class DogFearManager {

    private final Dog dog;
    private final Map<DogFear, Integer> fearMap = Maps.newHashMap();

    public DogFearManager(Dog dog) {
        this.dog = dog;
    }

    @SuppressWarnings("deprecation")
    public boolean handleGameEvent(ServerLevel level, Holder<GameEvent> event, 
        GameEvent.Context ctx, Vec3 pos) {
        
        if (event.is(GameEvent.LIGHTNING_STRIKE)) {
            triggerFear(DogFears.THUNDER, 10 * 20);
            return true;
        }

        if (event.is(GameEvent.EXPLODE)) {
            triggerFear(DogFears.EXPLOSION, 10 * 20);
            return true;
        }

        return false;
    }

    public void tickServer() {
        var iterator = fearMap.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            int end_time = entry.getValue();
            if (this.dog.tickCount >= end_time)
                iterator.remove();
        }
    }

    public boolean hasAnyFear() {
        return !this.fearMap.isEmpty();
    }

    public void triggerFear(DogFear fear, int duration) {
        int end_time = dog.tickCount + duration;
        this.fearMap.put(fear, end_time);
    }
    
}
