package doggytalents.forge_imitate.event;

import doggytalents.DoggyBlocks;
import doggytalents.DoggyEntityTypes;
import doggytalents.DoggyItems;
import doggytalents.DoggyTalentsNextEntry;
import doggytalents.common.event.EventHandler;
import doggytalents.common.item.ChopinRecordItem;
import doggytalents.forge_imitate.event.util.EventBus;

public class EventHandlerRegisterer {
    
    public static void init() {
        EventBus.COMMON_BUS.register(new EventHandler());
        DoggyTalentsNextEntry.MOD_BUS.addListener(EntityAttributeCreationEvent.class, 
            DoggyEntityTypes::addEntityAttributes);
        EventBus.COMMON_BUS.addListener(PlayerInteractEvent.RightClickBlock.class, 
            ChopinRecordItem::onRightClickBlock);
    }

}
