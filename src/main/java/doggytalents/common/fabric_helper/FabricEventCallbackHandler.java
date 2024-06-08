package doggytalents.common.fabric_helper;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.LivingHurtEvent;
import doggytalents.forge_imitate.event.PlayerInteractEvent;
import doggytalents.forge_imitate.event.ServerStoppingEvent;
import doggytalents.forge_imitate.event.ServerTickEvent;
import doggytalents.forge_imitate.event.TagsUpdatedEvent;
import doggytalents.forge_imitate.event.TagsUpdatedEvent.UpdateCause;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;

public class FabricEventCallbackHandler {
    
    public static void init() {
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            EventCallbacksRegistry.postEvent(new ServerStoppingEvent(server));         
        });
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            EventCallbacksRegistry.postEvent(new ServerTickEvent(server));      
        });
        CommonLifecycleEvents.TAGS_LOADED.register((registry, isClient) -> {
            EventCallbacksRegistry.postEvent(new TagsUpdatedEvent(
                isClient ? UpdateCause.CLIENT_PACKET_RECEIVED
                : UpdateCause.SERVER));
        });
        UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            var stack = player.getItemInHand(hand);
            var ret = EventCallbacksRegistry.postEvent(new PlayerInteractEvent.EntityInteract(player, entity, stack));
            if (!ret.isCancelled())
                return InteractionResult.PASS;
            var res = ret.getCancelInteractionResult();
            if (res != null && res != InteractionResult.PASS)
                return res;
            return InteractionResult.FAIL;
        });
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            var ret = EventCallbacksRegistry.postEvent(new LivingHurtEvent(entity, source, amount));
            if (ret.isCancelled())
                return false;
            return true;
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            var stack = player.getItemInHand(hand);
            var ret = EventCallbacksRegistry.postEvent(new PlayerInteractEvent
                .RightClickBlock(player, hitResult.getBlockPos(), hitResult.getDirection(), stack));
            if (!ret.isCancelled())
                return InteractionResult.PASS;
            var res = ret.getCancelInteractionResult();
            if (res != null && res != InteractionResult.PASS)
                return res;
            return InteractionResult.FAIL;
        });
    }

}
