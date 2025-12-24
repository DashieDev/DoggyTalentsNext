package doggytalents.common.backward_imitate;

import doggytalents.common.entity.Dog;
import net.minecraft.world.attribute.EnvironmentAttributes;

public class BedSleepUtil_1_21_11 {
    
    public static boolean bedWorks(Dog dog) {
        var bed_rule = dog.level().environmentAttributes()
            .getValue(EnvironmentAttributes.BED_RULE, dog.blockPosition());
        return !bed_rule.explodes()
            && bed_rule.canSleep(dog.level());
    }
}
