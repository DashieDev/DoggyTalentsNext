package doggytalents.forge_imitate.event.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import doggytalents.common.lib.Constants;
import doggytalents.forge_imitate.event.Event;

public class EventBus {

    public static final Logger LOGGER = LogManager.getLogger(Constants.MOD_ID + "/eventBus");

    public static final EventBus COMMON_BUS = new EventBus();
    
    private final Map<Class<?>, EventListener[]> listenersByType = new IdentityHashMap<>();
    private final List<Pair<Class<?>, EventListener>> registeredListeners = new ArrayList<>();
    private boolean locked = false;

    public void register(Object handlerObj) {
        var handler_class = handlerObj.getClass();
        var handler_methods = new ArrayList<Method>();
        for (var method : handler_class.getMethods()) {
            if (method.getAnnotation(SubscribeEvent.class) != null)
                handler_methods.add(method);
        }
        handler_methods.forEach(x -> register(x, handlerObj));
    }

    private void register(Method handler_method, Object handler_obj) {
        if (this.locked)
            throw new IllegalStateException(
                "Event bus is locked. Cannot register additional listeners.");
        
        var event_type = validateHandlerMethodAndGetEventType(handler_method,
            () -> "Invalid handler method: [ %s ] from [ %s ] "
                .formatted(handler_method, handler_obj.getClass()));
        
        var listener = EventListener.createInstanceWrapped(handler_obj, handler_method);
        this.registeredListeners.add(Pair.of(event_type, listener));

        LOGGER.info("registered method [ %s ] from [ %s ]"
            .formatted(handler_method, handler_obj.getClass()));
    }

    private Class<?> validateHandlerMethodAndGetEventType(Method handler_method, Supplier<String> exception_msg) {
        var param_types = handler_method.getParameterTypes();
        if (param_types.length != 1)
            throw new IllegalArgumentException(exception_msg.get());
        
        if (Modifier.isStatic(handler_method.getModifiers()))
            throw new IllegalArgumentException(exception_msg.get());
        
        var event_type = param_types[0];
        if (!Event.class.isAssignableFrom(event_type))
            throw new IllegalArgumentException(exception_msg.get());

        return event_type;
    }

    public void finishRegister() {
        this.locked = true;
        var listener_list_map = new HashMap<Class<?>, List<EventListener>>();
        for (var listener : this.registeredListeners) {
            listener_list_map.computeIfAbsent(listener.getLeft(), 
                k -> new ArrayList<>()).add(listener.getRight());
        }
        listener_list_map.entrySet().stream()
            .forEach(e -> {
                this.listenersByType.put(e.getKey(), e.getValue()
                    .toArray(new EventListener[0]));   
            });
    }

    public void post(Event event) {
        if (!this.locked)
            throw new IllegalStateException("Event bus must be locked before use");
        var listeners = this.listenersByType.get(event.getClass());
        for (var listener : listeners) {
            listener.invoke(event);
        }
    }
}
