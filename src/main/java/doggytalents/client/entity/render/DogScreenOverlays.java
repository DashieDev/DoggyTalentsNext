package doggytalents.client.entity.render;

import com.mojang.blaze3d.systems.RenderSystem;
import doggytalents.common.entity.Dog;
import doggytalents.forge_imitate.client.ForgeGuiOverlayManager.IGuiOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;

public class DogScreenOverlays {

    public static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png");

    public static final IGuiOverlay FOOD_LEVEL_ELEMENT = (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        boolean isMounted = mc.player.getVehicle() instanceof Dog;
        if (isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
            Dog dog = (Dog) mc.player.getVehicle();
            gui.setupOverlayRenderState(true, false);

            mc.getProfiler().push("food_dog");

            RenderSystem.enableBlend();
            int left = screenWidth / 2 + 91;
            int top = screenHeight - gui.rightHeight;
            gui.rightHeight += 10;

            int level = Mth.ceil((dog.getDogHunger() / dog.getMaxHunger()) * 20.0D);

            for (int i = 0; i < 10; ++i) {
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                int y = top;
                int icon = 16;
                byte background = 0;

                if (dog.hasEffect(MobEffects.HUNGER)) {
                    icon += 36;
                    background = 13;
                }

                mStack.blit(GUI_ICONS_LOCATION, x, y, 16 + background * 9, 27, 9, 9);

                if (idx < level)
                    mStack.blit(GUI_ICONS_LOCATION, x, y, icon + 36, 27, 9, 9);
                else if (idx == level)
                    mStack.blit(GUI_ICONS_LOCATION, x, y, icon + 45, 27, 9, 9);
            }
            RenderSystem.disableBlend();
            mc.getProfiler().pop();
        }
    };

    public static final IGuiOverlay AIR_LEVEL_ELEMENT = (gui, mStack, partialTicks, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        boolean isMounted = mc.player.getVehicle() instanceof Dog;
        if (isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
            Dog dog = (Dog) mc.player.getVehicle();

            gui.setupOverlayRenderState(true, false);
            mc.getProfiler().push("air_dog");
            RenderSystem.enableBlend();
            int left = screenWidth / 2 + 91;
            int top = screenHeight - gui.rightHeight;

            int air = dog.getAirSupply();
            int maxAir = dog.getMaxAirSupply();
            if (dog.isEyeInFluid(FluidTags.WATER) || air < maxAir) {
                int full = Mth.ceil((double)(air - 2) * 10.0D / maxAir);
                int partial = Mth.ceil((double)air * 10.0D / maxAir) - full;

                for (int i = 0; i < full + partial; ++i) {
                    int x = left - i * 8 - 9;
                    int y = top;

                    mStack.blit(GUI_ICONS_LOCATION, x, y, (i < full ? 16 : 25), 18, 9, 9);
                }
                gui.rightHeight += 10;
            }

            RenderSystem.disableBlend();
            mc.getProfiler().pop();
        }
    };
}
