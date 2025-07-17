package doggytalents.common.entity;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class DogGameEventListener implements GameEventListener {

    private final Dog dog;
    private final PositionSource positionSource;

    public DogGameEventListener(Dog dog) {
        this.dog = dog;
        this.positionSource = new EntityPositionSource(dog, dog.getEyeHeight());
    }

    @Override
    public PositionSource getListenerSource() {
        return positionSource;
    }

    @Override
    public int getListenerRadius() {
        return 12;
    }

    @Override
    public boolean handleGameEvent(ServerLevel level, Holder<GameEvent> event, 
        GameEvent.Context ctx, Vec3 pos) {
        
        if (dog.dogFear.handleGameEvent(level, event, ctx, pos))
            return true;

        return false;
    }

} 