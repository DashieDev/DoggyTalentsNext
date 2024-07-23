package doggytalents.common.fabric_helper.util;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.PathType;

public class FabricUtil {

    public static boolean makesPiglinsNeutral(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof ArmorItem armor))
            return false;
        var material = armor.getMaterial();
        return material == ArmorMaterials.GOLD;
    }

    public static PathType getDanger(PathType type) {
        return (type == PathType.DAMAGE_FIRE   || type == PathType.DANGER_FIRE  ) ? PathType.DANGER_FIRE   :
            (type == PathType.DAMAGE_OTHER  || type == PathType.DANGER_OTHER ) ? PathType.DANGER_OTHER  :
            (type == PathType.LAVA) ? PathType.DAMAGE_FIRE :
            null;
    }

    public static int getRepairRatio(ItemStack stack) {
        return 2;
    }

    public static int getWhistleKey(Player player, int keyCode) {
        if (!player.isShiftKeyDown())
            return -1;
        switch (keyCode) {
        case 49:
            return 0;
        case 50:
            return 1;
        case 51:
            return 2;
        case 52:
            return 3;
        default:
            return -1;
        }
    }

    public static float getPartialTick(Minecraft mc) {
        return mc.getTimer().getGameTimeDeltaPartialTick(true);
    }

}
