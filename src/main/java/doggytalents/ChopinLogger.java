package doggytalents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.common.entity.Dog;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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

    public static void sendToOwner(Dog d, String s) {
        if (d.getOwner() == null) return;
        d.getOwner().sendSystemMessage(Component.literal("<" + d.getName().getString() + "> : " + s));
    }

    //For debugging purpose only
    private static boolean IS_DEBUG_ALLOW_DEATH = false;
    @SubscribeEvent
    public void onWolfOrDogDeath(LivingDeathEvent ev) {
        if (IS_DEBUG_ALLOW_DEATH) return;
        var e = ev.getEntity();
        if (
            e instanceof Dog
            || e instanceof Wolf
        ) {
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
