package doggytalents.client.entity.versionfix;

import java.util.Optional;

import doggytalents.common.entity.Dog;
import doggytalents.mixin.ClientLevelMixinAccessor;
import net.minecraft.DetectedVersion;
import net.minecraft.client.multiplayer.ClientLevel;

public class FixClientTeleportDesync_1_21 {
    
    private static boolean activated = false;

    public static void init() {
        activated = isTargetMcVersion();
    }

    private static boolean isTargetMcVersion() {
        return Optional.ofNullable(DetectedVersion.BUILT_IN)
            .map(x -> x.getName())
            .filter(x -> "1.21.1".equals(x))
            .isPresent();
    }

    public static boolean onDogLerpTo(Dog dog) {
        if (!activated)
            return false;
        if (!(dog.level() instanceof ClientLevel level_client))
            return false;
        var tickings = ((ClientLevelMixinAccessor) level_client).dtn__getTickingEntities();
        if (tickings == null)
            return false;
        if (tickings.contains(dog))
            return false;
        
        return true;
    }
}
