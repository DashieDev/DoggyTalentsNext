package doggytalents.forge_imitate.event.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import doggytalents.forge_imitate.event.Event;

public interface EventListener {
    
    public void invoke(Event event);

    public static InstanceWrapped createInstanceWrapped(Object instance, Method method) {
        return new InstanceWrapped(instance, UnreflectUtil.getMethodHandleFromMethod(method));
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

}
