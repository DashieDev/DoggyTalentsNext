package doggytalents.common.fabric_helper.entity;

import doggytalents.forge_imitate.event.EventCallbacksRegistry;
import doggytalents.forge_imitate.event.LivingDropsEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class FabricMobKillDropCapture {
    
    //We only need to care if the entity has dropped some loot or not, not the contents it dropped.
    private static ThreadLocal<Boolean> droppedSinceLastCapture = ThreadLocal.withInitial(() -> false);

    private static void beginCapture() {
        droppedSinceLastCapture.set(false);
    }

    public static void onServerMobSpawnItemDrop(LivingEntity entity) {
        droppedSinceLastCapture.set(true);
    }

    public static void onServerMobLootStart(LivingEntity entity) {
        beginCapture();
    }

    //If this get called, we can be sure that onServerMobLootStart was called before
    //during the same call to the mixin target method. 
    public static void onServerMobLootEnd(LivingEntity entity, DamageSource source) {
        if (!droppedSinceLastCapture.get())
            return;
        var event = new LivingDropsEvent(entity, source);
        EventCallbacksRegistry.postEvent(event);
    }
    
}
