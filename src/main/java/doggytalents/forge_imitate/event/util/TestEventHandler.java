package doggytalents.forge_imitate.event.util;

import doggytalents.DoggyTalentsNext;
import doggytalents.forge_imitate.event.LivingHurtEvent;

public class TestEventHandler {
    
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        
        DoggyTalentsNext.LOGGER.info("Ouch! "+ event.getEntity().getName().getString());
    }

    
    public static int inc = 0;

    @SubscribeEvent
    public void onTestDogEventBus(DogInteractTestEvent event) {
        incTest();
    }

    public void incTest() {
        ++inc;
    }

}
