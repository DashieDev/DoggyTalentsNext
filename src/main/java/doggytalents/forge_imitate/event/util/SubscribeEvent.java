package doggytalents.forge_imitate.event.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Map;

@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface SubscribeEvent {

}
