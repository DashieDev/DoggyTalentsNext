package doggytalents.common.fabric_helper.util;

import java.util.List;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FabricUtil {

    public static boolean makesPiglinsNeutral(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof ArmorItem armor))
            return false;
        var material = armor.getMaterial();
        return material == ArmorMaterials.GOLD;
    }

    public static BlockPathTypes getDanger(BlockPathTypes type) {
        return (type == BlockPathTypes.DAMAGE_FIRE   || type == BlockPathTypes.DANGER_FIRE  ) ? BlockPathTypes.DANGER_FIRE   :
            (type == BlockPathTypes.DAMAGE_OTHER  || type == BlockPathTypes.DANGER_OTHER ) ? BlockPathTypes.DANGER_OTHER  :
            (type == BlockPathTypes.LAVA) ? BlockPathTypes.DAMAGE_FIRE :
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

}