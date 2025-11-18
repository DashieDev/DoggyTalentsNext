package doggytalents.forge_imitate.event.util;

import java.util.function.Consumer;

import doggytalents.forge_imitate.event.Event;

public class ModEventBus {
    
    private final EventBus eventBus = EventBus.createWithEventTypeChecker(
        event_type -> ModBusEvent.class.isAssignableFrom(event_type));

    private ModEventBus() {

    }

    public static ModEventBus create() {
        return new ModEventBus();
    }

    public <T extends Event> void addListener(Class<T> eventType, Consumer<T> listener) {
        this.eventBus.addListener(eventType, listener);
    }

    public ModEventBus finishRegister() {
        this.eventBus.finishRegister();
        return this;
    }

    public ModEventBus post(Event e) {
        this.eventBus.post(e);
        return this;
    }
}
