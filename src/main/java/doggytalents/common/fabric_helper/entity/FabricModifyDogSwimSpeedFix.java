package doggytalents.common.fabric_helper.entity;

import java.util.Optional;

import doggytalents.DoggyEntityTypes;
import doggytalents.forge_imitate.atrrib.ForgeMod;
import net.minecraft.world.entity.LivingEntity;

public class FabricModifyDogSwimSpeedFix {
    
    public static Optional<Float> onModifySwimSpeed(LivingEntity self, float currentSpeed) {
        if (self.getType() != DoggyEntityTypes.DOG.get())
            return Optional.empty();
        var dog = self;
        if (!dog.isInWater() || dog.isInLava())
            return Optional.empty();

        double ret = currentSpeed * dog.getAttributeValue(ForgeMod.SWIM_SPEED.holder());
        return Optional.of((float) ret);
    }

}
