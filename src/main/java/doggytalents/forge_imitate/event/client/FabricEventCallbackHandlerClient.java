package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class FabricEventCallbackHandlerClient {
    
    public static void init() {
        WorldRenderEvents.END_EXTRACTION.register(render_ctx -> {
            //var stack = render_ctx.matrixStack();
            var pTicks = render_ctx.tickCounter();
            var levelRenderState_1_21_10 = render_ctx.worldState();
            EventCallbacksRegistry.postEvent(new RenderLevelStageEvent(/*stack*/null, pTicks, levelRenderState_1_21_10));
        });
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            EventCallbacksRegistry.postEvent(new ClientTickEvent());
        });
        ScreenEvents.AFTER_INIT.register((mc, screen, w, h) -> {
            ScreenEvents.afterRender(screen).register((scr, graphics, mouseX, nouseY, pTicks) -> {
                EventCallbacksRegistry.postEvent(new ScreenEvent.Render.Post(scr, graphics, mouseX, nouseY, pTicks));
            });
            EventCallbacksRegistry.postEvent(new ScreenEvent.Init.Post(screen));
        });
    }

}
