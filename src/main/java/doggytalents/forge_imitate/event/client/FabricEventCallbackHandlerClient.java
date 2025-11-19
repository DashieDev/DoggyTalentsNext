package doggytalents.forge_imitate.event.client;

import doggytalents.forge_imitate.event.util.EventBus;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class FabricEventCallbackHandlerClient {
    
    public static void init() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(render_ctx -> {
            var stack = render_ctx.matrixStack();
            var pTicks = render_ctx.tickCounter();
            var camera = render_ctx.camera();
            EventBus.COMMON_BUS.post(new RenderLevelStageEvent(stack, pTicks, camera));
        });
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            EventBus.COMMON_BUS.post(new ClientTickEvent());
        });
        ScreenEvents.AFTER_INIT.register((mc, screen, w, h) -> {
            ScreenEvents.afterRender(screen).register((scr, graphics, mouseX, nouseY, pTicks) -> {
                EventBus.COMMON_BUS.post(new ScreenEvent.Render.Post(scr, graphics, mouseX, nouseY, pTicks));
            });
            EventBus.COMMON_BUS.post(new ScreenEvent.Init.Post(screen));
        });
    }

}
