package doggytalents;

import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.common.entity.Dog;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
    public static final boolean IS_DEBUG_ALLOW_DEATH = false;
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onWolfOrDogDeath(LivingDeathEvent ev) {
        var e = ev.getEntity();
        if (
            (e instanceof Dog && !IS_DEBUG_ALLOW_DEATH)
            || e instanceof Wolf
        ) {
            if (ev.getSource().isBypassInvul()) return;
            ev.setCanceled(true);
            e.setHealth(e.getMaxHealth());
            if (e instanceof Dog) {
                ChopinLogger.lwn(e, "💩");
            } else {
                ChopinLogger.l("💩");
            }
        }
    }
}
