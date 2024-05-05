package doggytalents.forge_imitate.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import doggytalents.common.fabric_helper.entity.network.container_sync.data.BlockPosData;
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
    
    public static <D> void openScreen(ServerPlayer player, MenuProvider provider, Class<D> dataClass, Supplier<D> dataSup) {
        var wrapper = new ExtendedScreenHandlerFactoryWrapper<D>(provider, dataSup);
        player.openMenu(wrapper);
    }

    public static void openScreen(ServerPlayer player, MenuProvider provider, BlockPos pos) {
        openScreen(player, provider, BlockPosData.class, () -> {
            return new BlockPosData(pos);
        });
    }

    private static class ExtendedScreenHandlerFactoryWrapper<D> implements ExtendedScreenHandlerFactory<D> {

        private MenuProvider provider;
        private Supplier<D> dataSup;

        public ExtendedScreenHandlerFactoryWrapper(MenuProvider provider, Supplier<D> dataSup) {
            this.provider = provider;
            this.dataSup = dataSup;
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
        public D getScreenOpeningData(ServerPlayer player) {
            return this.dataSup.get();
        }
    }

}
