package doggytalents;

import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.common.entity.Dog;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChopinLogger {
    private static final Logger LOGGER = LogManager.getLogger("chopin");
    public static void l(String s) {
        ChopinLogger.LOGGER.info(s); 
    }
    public static void lwn(Dog d, String s) {
        ChopinLogger.LOGGER.info("<dog : " + d.getName().getString() + "> " + s);
    }
    public static void lwn(Entity e, String s) {
        if (! (e instanceof Dog) ) {
            ChopinLogger.LOGGER.info("< that's not a dog >" + s);
            return;
        }
        Dog d = (Dog) e;
        ChopinLogger.LOGGER.info("<dog : " + d.getName().getString() + "> " + s);
    }
    public static void p(String s) {
        System.out.println("[chopin]  " + s);
    }

    public static void sendToOwner(Dog d, String s) {
        if (d.getOwner() == null) return;
        d.getOwner().sendSystemMessage(Component.literal("<" + d.getName().getString() + "> : " + s));
    }

    public static void outputToFile(String s) {
        try {
            var file = new File("chopin.txt");
            file.createNewFile();
            var fileWriter = new FileWriter(file);
            fileWriter.append(s);
            fileWriter.close();
        } catch (Exception e) {
            
        }
        
        
    }


    //For debugging purpose only, should be final to be editable
    public static final boolean IS_DEBUG_ALLOW_DEATH = true;
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onWolfOrDogDeath(LivingDeathEvent ev) {
        var e = ev.getEntity();
        if (
            (e instanceof Dog && false)
            || e instanceof Wolf
        ) {
            if (ev.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
            ev.setCanceled(true);
            e.setHealth(e.getMaxHealth());
            if (e instanceof Dog) {
                ChopinLogger.lwn(e, "💩");
            } else {
                ChopinLogger.l("💩");
            }
        }
    }

    @SubscribeEvent
    public void onItemUseEvent(final PlayerInteractEvent.RightClickItem event) {
        var level = event.getLevel();
        var stack = event.getItemStack();
        var pos = event.getEntity().blockPosition();
        if (level.isClientSide)
            return;
        if (stack.getItem() != DoggyItems.RICE_WHEAT.get()) 
            return;
        if (level.getBlockState(pos.below()).isAir())
            return;
        level.setBlockAndUpdate(pos.below(2), Blocks.WATER.defaultBlockState());
        var area = BlockPos.betweenClosed(pos.offset(-5, -3, -5), pos.offset(5, 3, 5));
        for (var area_pos : area) {
            var state = level.getBlockState(area_pos);
            var state_above = level.getBlockState(area_pos.above());
            if (!state.is(Blocks.GRASS_BLOCK) && !state.is(Blocks.FARMLAND))
                continue;  
            if (!state_above.isPathfindable(level, area_pos, PathComputationType.LAND))
                continue;
            level.setBlockAndUpdate(area_pos, Blocks.FARMLAND.defaultBlockState());
            var crop = DoggyBlocks.RICE_CROP.get();
            var crop_state = crop.defaultBlockState();
            crop_state = crop_state.setValue(crop.getAgeProperty(), crop.getMaxAge());
            level.setBlockAndUpdate(area_pos.above(), crop_state);
        }
    }
}
