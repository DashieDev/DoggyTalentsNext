package doggytalents.common.forward_imitate;

import doggytalents.common.entity.Dog;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class EntityUtil_1_20_under {
    
    public static boolean canBeLeashed(Mob target, Dog checker) {
        var owner = checker.getOwner();
        if (!(owner instanceof Player player))
            return false;

        return target.canBeLeashed(player);
    }

}
