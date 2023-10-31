package doggytalents;

import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.common.entity.Dog;
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
            (e instanceof Dog && !IS_DEBUG_ALLOW_DEATH)
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
    public void onDogRightClick(final PlayerInteractEvent.EntityInteract event) {
        var level = event.getLevel();
        var stack = event.getItemStack();
        var target = event.getTarget();
        var owner = event.getEntity();

        if (level.isClientSide)
            return;
        if (stack.getItem() != Items.STONE_PICKAXE) 
            return;
        if (!(target instanceof Dog dog)) 
            return;
        event.setCanceled(true);
        
        stack.setTag(new CompoundTag());
        var savedTag = new CompoundTag();
        dog.saveWithoutId(savedTag);
        stack.getTag().put("savedDog", savedTag);
        dog.discard();

        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    @SubscribeEvent
    public void onItemUseEvent(final PlayerInteractEvent.RightClickItem event) {
        var level = event.getLevel();
        var stack = event.getItemStack();
        var pos = event.getPos();
        if (level.isClientSide)
            return;
        if (stack.getItem() != Items.STONE_PICKAXE) 
            return;

        var tag = stack.getTag();
        if (tag == null || !tag.contains("savedDog", Tag.TAG_COMPOUND))
            return;
        
        event.setCanceled(true);

        Dog dog = DoggyEntityTypes.DOG.get().create((ServerLevel)   level, null, null, pos, MobSpawnType.TRIGGERED, true, false);

        dog.load(tag.getCompound("savedDog"));
        ((ServerLevel)level).addFreshEntityWithPassengers(dog);
        

        event.setCancellationResult(InteractionResult.SUCCESS);
    }
}
