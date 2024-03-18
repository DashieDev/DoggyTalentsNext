package doggytalents.forge_imitate.network;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class NetworkHooks {
    
    public static void openScreen(ServerPlayer player, MenuProvider provider, Consumer<FriendlyByteBuf> bufWriter) {
        var wrapper = new ExtendedScreenHandlerFactoryWrapper(provider, bufWriter);
        player.openMenu(wrapper);
    }

    public static void openScreen(ServerPlayer player, MenuProvider provider, BlockPos pos) {
        openScreen(player, provider, buf -> {
            buf.writeBlockPos(pos);
        });
    }

    private static class ExtendedScreenHandlerFactoryWrapper implements ExtendedScreenHandlerFactory {

        private MenuProvider provider;
        private Consumer<FriendlyByteBuf> bufWriter;

        public ExtendedScreenHandlerFactoryWrapper(MenuProvider provider, Consumer<FriendlyByteBuf> bufWriter) {
            this.provider = provider;
            this.bufWriter = bufWriter;
        }

        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
            return this.provider.createMenu(id, inv, player);
        }

        @Override
        public Component getDisplayName() {
            return this.provider.getDisplayName();
        }

        @Override
        public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
            bufWriter.accept(buf);
        }

    }

}
