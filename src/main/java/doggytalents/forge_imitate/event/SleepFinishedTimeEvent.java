package doggytalents.forge_imitate.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class SleepFinishedTimeEvent extends Event {
    private ServerLevel level;
    public SleepFinishedTimeEvent(ServerLevel level) {
        this.level = level;
    }

    public Level getLevel() {
        return this.level;
    }
}
