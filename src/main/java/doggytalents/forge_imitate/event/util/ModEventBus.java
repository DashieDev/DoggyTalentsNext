package doggytalents.forge_imitate.event.util;

import java.util.function.Consumer;

import doggytalents.forge_imitate.event.Event;

public class ModEventBus {
    
    private final EventBus eventBus = new EventBus(); 

    private ModEventBus() {

    }

    public static ModEventBus create() {
        return new ModEventBus();
    }

    public <T extends Event> void addListener(Class<T> eventType, Consumer<T> listener) {
        this.eventBus.addListener(eventType, listener);
    }

    public void finishRegister() {
        this.eventBus.finishRegister();
    }

    public ModEventBus post(Event e) {
        this.eventBus.post(e);
        return this;
    }
}
