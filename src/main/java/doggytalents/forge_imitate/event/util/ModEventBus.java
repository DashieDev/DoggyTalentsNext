package doggytalents.forge_imitate.event.util;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

import doggytalents.forge_imitate.event.Event;

public class ModEventBus {
    
    private final EventBus eventBus = new EventBus();
    private final Map<Class<? extends Event>, Supplier<? extends Event>> eventCreators =
        Maps.newHashMap();

    private ModEventBus() {

    }

    public static ModEventBus create() {
        return new ModEventBus();
    }

    public <T extends Event> void addListener( 
        Consumer<T> listener, ModBusEventInfo<T> eventInfo) {
        this.eventBus.addListener(eventInfo.eventType(), listener);
        eventCreators.putIfAbsent(eventInfo.eventType(), eventInfo.eventCreator());
    }

    public void finishRegisterAndPostAll() {
        this.eventBus.finishRegister();

        for (var entry : this.eventCreators.entrySet()) {
            var e = entry.getValue().get();
            this.eventBus.post(e);
        }
    }

    public static record ModBusEventInfo<T extends Event>(
        Class<T> eventType, Supplier<T> eventCreator) {}
}
