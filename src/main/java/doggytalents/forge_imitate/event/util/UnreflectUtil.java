package doggytalents.forge_imitate.event.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class UnreflectUtil {
    
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    public static MethodHandle getMethodHandleFromMethod(Method method) {
        MethodHandle ret;
        try {
            ret = LOOKUP.unreflect(method);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot unreflect", e);
        }
        return ret;
    }
    

}
