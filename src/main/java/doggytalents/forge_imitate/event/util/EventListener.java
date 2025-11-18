package doggytalents.forge_imitate.event.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import doggytalents.forge_imitate.event.Event;

public /*sealed*/ interface EventListener {
    
    public void invoke(Event event);

    public static InstanceWrapped createInstanceWrapped(Object instance, Method method) {
        return new InstanceWrapped(instance, UnreflectUtil.getMethodHandleFromMethod(method));
    }

    @SuppressWarnings("unchecked")
    public static SimpleWrapped createSimpleUnsafe(Consumer<? extends Event> listener) {
        return new SimpleWrapped((Consumer<Event>) listener);
    }

    public static class InstanceWrapped implements EventListener {

        private static final MethodType ADAPTER_SIGNATURE = 
            MethodType.methodType(void.class, Object.class, Event.class);

        private final MethodHandle adaptedHandler;
        private final Object instance;

        private InstanceWrapped(Object instance, MethodHandle handler) {
            this.instance = instance;
            this.adaptedHandler = handler.asType(ADAPTER_SIGNATURE);
        }

        @Override
        public void invoke(Event event) {
            try {
                adaptedHandler.invokeExact(this.instance, event);
            } catch (Error | RuntimeException e) {
                throw e;   
            } catch (Throwable t) {
                throw new RuntimeException("Unhandled checked exception thrown", t);
            }
        }
    }

    public static class SimpleWrapped implements EventListener {

        private final Consumer<Event> wrappedListener;

        private SimpleWrapped(Consumer<Event> wrappedListener) {
            this.wrappedListener = wrappedListener;
        }

        @Override
        public void invoke(Event event) {
            this.wrappedListener.accept(event);
        }
    }

}
