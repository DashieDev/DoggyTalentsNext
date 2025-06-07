package doggytalents.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class ReflectionUtil {

    public static Object invokeMethod(Method method, Object instance, Object... params) {
        try {
            return method.invoke(instance, params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeStaticMethod(Method method, Object... params) {
        return invokeMethod(method, null, params);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz == null) {
                throw new RuntimeException("Null class " + className);
            }

            return clazz;
        } catch (ClassNotFoundException e) {
            // Class not present
            throw new RuntimeException(e);
        }
    }

    public static <T> Optional<T> getPrivateField(Object inst, String name, Class<T> type) {
        return getPrivateField(inst.getClass(), inst, name, type);
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static <T> Optional<T> getPrivateField(Class<?> instClass, Object inst, String name, Class<T> type) {
        var ret = Optional.<T>empty();
        try {
            if (!instClass.isAssignableFrom(inst.getClass()))
                return ret;
            var field = instClass.getDeclaredField(name);
            boolean accessible_0 = field.isAccessible();
            field.setAccessible(true);
            try {
                var val = (T) field.get(inst);
                ret = Optional.ofNullable(val);
            } catch (Exception e) {

            }
            field.setAccessible(accessible_0);
        } catch (SecurityException | NoSuchFieldException e) {
            
        }
        
        return ret;
    }
}
