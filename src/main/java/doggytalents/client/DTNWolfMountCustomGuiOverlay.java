package doggytalents.client;

import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;

import doggytalents.DoggyEntityTypes;
import doggytalents.client.entity.render.DogScreenOverlays;
import doggytalents.common.config.ConfigHandler;
import doggytalents.common.entity.Dog;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;

public class DTNWolfMountCustomGuiOverlay {
    
    public static boolean onRenderVehicleHealth(GuiGraphics graphics, Gui gui) {
        if (!isApplicable())
            return false;

        var vehicle_optional = getPlayerVehicle();
        if (!vehicle_optional.isPresent())
            return false;
        var vehicle = vehicle_optional.get();

        if (!isTargetVehicle(vehicle))
            return false;

        if (!(vehicle instanceof Dog dog))
            return false;
        
        int draw_x = graphics.guiWidth() / 2 + 91 - 80;
        int draw_y = graphics.guiHeight() - gui.rightHeight;
        RenderSystem.enableBlend();

        var mc = Minecraft.getInstance();
        var font = mc.font;

        float dog_health = dog.getHealth();
        int dog_health_percent = Mth.ceil((dog_health / dog.getMaxHealth()) * 100f);
        dog_health_percent = Mth.clamp(dog_health_percent, 0, 100);

        float dog_hunger = dog.getDogHunger();
        int dog_hunger_percent = Mth.ceil((dog_hunger / dog.getMaxHunger()) * 100f);
        dog_hunger_percent = Mth.clamp(dog_hunger_percent, 0, 100);

        var health_level_str = Component.literal(Integer.toString(dog_health_percent) + "%"); 
        var food_level_str = Component.literal(Integer.toString(dog_hunger_percent) + "%"); 
        
        Optional<MutableComponent> air_level_str = Optional.empty();
        boolean render_air = dog.canDrownInFluidType(NeoForgeMod.WATER_TYPE.value()) 
            && (
                dog.isEyeInFluid(FluidTags.WATER)
                || dog.getAirSupply() < dog.getMaxAirSupply()
            );
        boolean is_low_air = false;
        if (render_air) {
            float dog_air = dog.getAirSupply();
            int dog_air_percent = Mth.ceil((dog_air / dog.getMaxAirSupply()) * 100f);
            dog_air_percent = Mth.clamp(dog_air_percent, 0, 100);
            air_level_str = Optional.of(Component.literal(Integer.toString(dog_air_percent) + "%"));
            is_low_air = dog_air_percent <= 30;
        }

        if (dog.getHealth() <= 10)
            health_level_str = health_level_str.withStyle(ChatFormatting.RED);
        if (dog.getDogHunger() <= 20)
            food_level_str = food_level_str.withStyle(ChatFormatting.RED);
        if (render_air && is_low_air)
            air_level_str.map(x -> x.withStyle(ChatFormatting.RED));

        int unit_str_offset = 24;
        int unit_str_yoffset = 1;

        int pX = draw_x;
        int pY = draw_y;
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16, 0 ,9, 9);
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16 + 36, 0, 9, 9);
        pX += 11;
        graphics.drawString(font, health_level_str, 
            pX + unit_str_offset - font.width(health_level_str), pY + unit_str_yoffset, 0xffffffff);
        pX += 32;
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16, 27, 9, 9);
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16 + 36, 27, 9, 9);
        pX += 11;
        graphics.drawString(font, food_level_str, 
            pX + unit_str_offset - font.width(food_level_str), pY + unit_str_yoffset, 0xffffffff);

        var dog_name_str_0 = dog.getName().getString();
        var dog_name_str = font.width(dog_name_str_0) > (render_air ? 40 : 67) ? 
            font.plainSubstrByWidth(dog_name_str_0, render_air ? 36 : 63) + ".."
            : dog_name_str_0 + ":";

        pY -= 10;
        pX = draw_x;
        graphics.drawString(font, dog_name_str, pX, pY + unit_str_yoffset, 0xffffffff);
        
        if (air_level_str.isPresent()) {
            pX = draw_x + 43;
            graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16, 18, 9, 9);
            pX += 11;
            graphics.drawString(font, air_level_str.get(), 
                pX + unit_str_offset - font.width(air_level_str.get()), 
                pY + unit_str_yoffset, 0xffffffff);
        }
        

        gui.rightHeight += 20;

        RenderSystem.disableBlend();
        
        return true;
    }

    public static Optional<Integer> onGetVehicleMaxHearts(LivingEntity vehicle) {
        if (!isApplicable())
            return Optional.empty();

        if (!isTargetVehicle(vehicle))
            return Optional.empty();
        
        //We can take two lines since a standard Dog would require 2 or more lines to render its
        //health anyways.
        return Optional.of(40);
    }

    private static Optional<Entity> getPlayerVehicle() {
        var mc = Minecraft.getInstance();
        var camera_entity = mc.getCameraEntity();
        if (camera_entity == null)
            return Optional.empty();
        if (camera_entity.getType() != EntityType.PLAYER)
            return Optional.empty();
        
        var vehicle = camera_entity.getVehicle();
        if (vehicle == null)
            return Optional.empty();
        
        return Optional.of(vehicle);
    }

    private static boolean isTargetVehicle(Entity vehicle) {
        if (vehicle == null)
            return false;
        if (vehicle.getType() != DoggyEntityTypes.DOG.get()) 
            return false;
        if (!vehicle.showVehicleHealth())
            return false;

        return true;
    }

    public static boolean isApplicable() {
        return ConfigHandler.CLIENT.DTN_WOLF_MOUNT_OVERLAY.get();
    }

}
