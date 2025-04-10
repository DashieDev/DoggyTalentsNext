package doggytalents.common.forward_imitate;

import doggytalents.common.entity.DogSleepOnManager;
import doggytalents.common.storage.DogLocationStorage;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;

public class ServerTickEventWorkaround_1_18_2 {
    
    public static void onLevelTick1_18_2(TickEvent.WorldTickEvent event) {
        var level = event.world;
        if (level.isClientSide)
            return;
        if (event.phase != Phase.END)
            return;
        if (!level.dimension().equals(Level.OVERWORLD))
            return;
        DogLocationStorage.get(level).getOnlineDogsManager().tick();
        DogSleepOnManager.tickServer(level.getServer());
    }

}
