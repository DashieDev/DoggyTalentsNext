package doggytalents.forge_imitate.client;

import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;

import doggytalents.forge_imitate.event.Event;
import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

//TODO Unfinished.
public class ForgeGuiOverlayManager {

    private static ArrayList<IGuiOverlay> OVERLAYS = new ArrayList<>();
    private static ForgeGui gui;

    public static void init() {
        //TODO Figure out how to track the left/right Y offset from hotbar that
        //already occupied by other bars.  
        //HudRenderCallback.EVENT.register(ForgeGuiOverlayManager::onRenderCallback);
        gui = new ForgeGui(Minecraft.getInstance());
        EventCallbacksRegistry.postEvent(new RegisterGuiOverlaysEvent());
    }

    public static void onRenderCallback(GuiGraphics graphics, float pTicks) {
        var window = gui.mc.getWindow();
        int w = window.getGuiScaledWidth();
        int h = window.getGuiScaledHeight();

        for (var entry : OVERLAYS) {
            entry.render(gui, graphics, pTicks, w, h);
        }
    }
    
    public static class RegisterGuiOverlaysEvent extends Event {
        
        private RegisterGuiOverlaysEvent() {}

        public void registerAboveAll(String str, IGuiOverlay overlay) {
            OVERLAYS.add(0, overlay);
        }

    }

    public static interface IGuiOverlay {
        
        public void render(ForgeGui gui, GuiGraphics graphics, float pTicks, int width, int height);
        
    }

    public static class ForgeGui {

        private Minecraft mc;
        public int rightHeight = 39;
        public int leftHeight = 39;

        private ForgeGui(Minecraft mc) {
            this.mc = mc;
        }
        
        public boolean shouldDrawSurvivalElements() {
            var gameMode = this.mc.gameMode;
            if (gameMode == null)
                return false;
            return gameMode.canHurtPlayer() && mc.getCameraEntity() instanceof Player;
        }

        public void setupOverlayRenderState(boolean blend, boolean depth) {
            if (blend) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
            }
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
        }
    }

}
