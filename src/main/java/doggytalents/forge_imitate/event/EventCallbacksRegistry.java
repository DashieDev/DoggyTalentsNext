package doggytalents.forge_imitate.event;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EventCallbacksRegistry {

    private static ArrayList<EventCallBack<?>> CALLBACKS = new ArrayList<>();

    public static synchronized void registerCallback(EventCallBack<?> callback) {
        CALLBACKS.add(callback);
    }

    public static <E extends Event> E postEvent(E event) {
        CALLBACKS.forEach(x -> x.mayInvoke(event));
        return event;
    }
    
    public static interface EventCallBack<E extends Event> {

        public void mayInvoke(Event event);

    }

    public static class SingleEventCallBack<E extends Event> implements EventCallBack<E> {

        private Class<E> eventType;
        private Consumer<E> callBack;

        public SingleEventCallBack(Class<E> eventType, Consumer<E> callBack) {
            this.eventType = eventType;
            this.callBack = callBack;
        }

        public Class<E> getEventType() {
            return this.eventType;
        }

        @Override
        public void mayInvoke(Event event) {
            if (event.getClass() != this.eventType)
                return;
            this.callBack.accept(this.eventType.cast(event));
        }

    }
    
    public static class InstanceEventCallBack<T, E extends Event> implements EventCallBack<E> {

        private T self;
        private Class<E> eventType;
        private BiConsumer<T, E> callBack;

        public InstanceEventCallBack(T self,  Class<E> eventType, BiConsumer<T, E> callBack) {
            this.self = self;
            this.eventType = eventType;
            this.callBack = callBack;
        }
        
        public Class<E> getEventType() {
            return this.eventType;
        }

        @Override
        public void mayInvoke(Event event) {
            if (event.getClass() != this.eventType)
                return;
            this.callBack.accept(self, this.eventType.cast(event));
        }

    }

}
