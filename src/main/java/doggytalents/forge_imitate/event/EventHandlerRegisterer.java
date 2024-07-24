package doggytalents.forge_imitate.event;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.common.event.EventHandler;
import doggytalents.common.variants.DTNWolfVariantsSpawnOverride;
import doggytalents.common.variants.DTNWolfVariantsSpawnPlacements;
import doggytalents.common.variants.VSCodeWolfSpawnHandler;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.InstanceEventCallBack;
import doggytalents.forge_imitate.event.EventCallbacksRegistry.SingleEventCallBack;

public class EventHandlerRegisterer {
    
    private static EventHandler handlerIst = new EventHandler();

    public static void init() {
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, ServerTickEvent>
                (handlerIst, ServerTickEvent.class,
                    (x, y) -> x.onServerTickEnd(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, ServerStoppingEvent>
                (handlerIst, ServerStoppingEvent.class,
                    (x, y) -> x.onServerStop(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, PlayerInteractEvent.EntityInteract>
                (handlerIst, PlayerInteractEvent.EntityInteract.class,
                    (x, y) -> x.onWolfRightClickWithTreat(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, EntityJoinLevelEvent>
                (handlerIst, EntityJoinLevelEvent.class,
                    (x, y) -> x.onEntitySpawn(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, PlayerLoggedInEvent>
                (handlerIst, PlayerLoggedInEvent.class,
                    (x, y) -> x.playerLoggedIn(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, TagsUpdatedEvent>
                (handlerIst, TagsUpdatedEvent.class,
                    (x, y) -> x.onTagsUpdated(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, ProjectileImpactEvent>
                (handlerIst, ProjectileImpactEvent.class,
                    (x, y) -> x.onProjectileHit(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, EntityTravelToDimensionEvent>
                (handlerIst, EntityTravelToDimensionEvent.class,
                    (x, y) -> x.onEntityChangeDimension(y)
                )
        );
        EventCallbacksRegistry.registerCallback(
            new InstanceEventCallBack<EventHandler, LivingHurtEvent>
                (handlerIst, LivingHurtEvent.class,
                    (x, y) -> x.onDogPassenegerHurtInWall(y)
                )
        );
        // EventCallbacksRegistry.registerCallback(
        //     new InstanceEventCallBack<EventHandler, LootingLevelEvent>
        //         (handlerIst, LootingLevelEvent.class,
        //             (x, y) -> x.onLootDrop(y)
        //         )
        // );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<RegisterColorHandlersEvent.Block>(
                RegisterColorHandlersEvent.Block.class,
                DoggyBlocks::registerBlockColours
            )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<RegisterColorHandlersEvent.Item>(
                RegisterColorHandlersEvent.Item.class,
                DoggyItems::registerItemColours
            )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<EntityAttributeCreationEvent>(
                EntityAttributeCreationEvent.class,
                DoggyEntityTypes::addEntityAttributes
            )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<MobSpawnEvent.FinalizeSpawn>(
                MobSpawnEvent.FinalizeSpawn.class,
                DTNWolfVariantsSpawnOverride::onWolfSpawn
            )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<MobSpawnEvent.PositionCheck>(
                MobSpawnEvent.PositionCheck.class,
                DTNWolfVariantsSpawnPlacements::onPositionCheck
            )
        );
        EventCallbacksRegistry.registerCallback(
            new SingleEventCallBack<PlayerInteractEvent.RightClickBlock>(
                PlayerInteractEvent.RightClickBlock.class,
                VSCodeWolfSpawnHandler::onRightClickBlock
            )
        );
    }

}
