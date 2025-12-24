package doggytalents.client.backward_imitate;

import net.minecraft.commands.Commands;
import net.minecraft.server.permissions.PermissionProviderCheck;
import net.minecraft.server.permissions.PermissionSetSupplier;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.player.Player;

public class PlayerUtil_1_21_11 {
    
    public static boolean isOperator(Player player) {
        var permissions = player.permissions();
        return permissions.hasPermission(Permissions.COMMANDS_OWNER);
    }

    public static <T extends PermissionSetSupplier> PermissionProviderCheck<T> operatorCheck() {
        return Commands.hasPermission(Commands.LEVEL_GAMEMASTERS);
    }

}
