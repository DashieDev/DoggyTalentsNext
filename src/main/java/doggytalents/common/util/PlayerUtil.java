package doggytalents.common.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlayerUtil {
    
    public static void sendSystemMessage(LivingEntity entity, Component msg) {
        if (!(entity instanceof ServerPlayer player))
            return;
        player.sendSystemMessage(msg);
    }

    public static void addCooldown(Player player, Item item, int cooldown_ticks) {
        player.getCooldowns().addCooldown(new ItemStack(item), cooldown_ticks);
    }

    public static boolean isOnCooldown(Player player, Item item) {
        return player.getCooldowns().isOnCooldown(new ItemStack(item));
    }

}
