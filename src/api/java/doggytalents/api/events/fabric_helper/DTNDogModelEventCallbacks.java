package doggytalents.api.events.fabric_helper;

import doggytalents.api.events.RegisterCustomDogModelsEvent;
import doggytalents.api.events.RegisterDogSkinJsonPathEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class DTNDogModelEventCallbacks {
    
    public static final Event<RegisterCustomDogModelsCallback> REGISTER_CUSTOM_DOG_MODEL = EventFactory.createArrayBacked(RegisterCustomDogModelsCallback.class, callbacks -> event -> {
        for (var x : callbacks) {
            x.onRegister(event);
        }
    });

    public static final Event<RegisterDogSkinJsonPathCallBack> REGISTER_DOG_SKIN_JSON = EventFactory.createArrayBacked(RegisterDogSkinJsonPathCallBack.class, callbacks -> event -> {
        for (var x : callbacks) {
            x.onRegister(event);
        }
    });

    public static interface RegisterCustomDogModelsCallback {
        
        public void onRegister(RegisterCustomDogModelsEvent event);

    }

    public static interface RegisterDogSkinJsonPathCallBack {

        public void onRegister(RegisterDogSkinJsonPathEvent event);

    }

}
