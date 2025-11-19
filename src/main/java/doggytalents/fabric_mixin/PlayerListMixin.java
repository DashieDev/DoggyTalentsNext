package doggytalents.fabric_mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import doggytalents.forge_imitate.event.util.EventBus;
import doggytalents.forge_imitate.event.PlayerLoggedInEvent;
import doggytalents.forge_imitate.event.PlayerLoggedOutEvent;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    
    @Inject(at = @At("TAIL"),  method = "placeNewPlayer(Lnet/minecraft/network/Connection;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/network/CommonListenerCookie;)V")
    public void dtn__placeNewPlayer(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo info) {
        EventBus.COMMON_BUS.post(new PlayerLoggedInEvent(player));
    }

    @Inject(at = @At("HEAD"),  method = "remove(Lnet/minecraft/server/level/ServerPlayer;)V")
    public void dtn__remove(ServerPlayer player, CallbackInfo info) {
        EventBus.COMMON_BUS.post(new PlayerLoggedOutEvent(player));
    }

}
