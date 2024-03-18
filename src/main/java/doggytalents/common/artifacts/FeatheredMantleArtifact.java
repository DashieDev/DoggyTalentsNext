package doggytalents.common.artifacts;

import java.util.UUID;

import doggytalents.DoggyItems;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import doggytalents.forge_imitate.atrrib.ForgeMod;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class FeatheredMantleArtifact extends DoggyArtifact {

    private static final UUID PILLOW_PAW_BOOST_ID = UUID.fromString("1f002df0-9d35-49c6-a863-b8945caa4af4");

    private boolean glide = false;

    public FeatheredMantleArtifact() {
        super(() -> DoggyItems.FEATHERED_MANTLE.get());
    }

    @Override
    public void init(AbstractDog dogIn) {
        if (dogIn.getDogLevel(DoggyTalents.PILLOW_PAW) < 5) 
            return;
        startGliding(dogIn);
        glide = true;
    }

    @Override
    public void remove(AbstractDog dogIn) {
        if (!glide) return;
        stopGliding(dogIn);
        glide = false;
    }

    @Override
    public void tick(AbstractDog dogIn) {
        if (dogIn.level().isClientSide) 
            return;
        if (dogIn.getDogLevel(DoggyTalents.PILLOW_PAW) < 5) 
            return;
        dogIn.fallDistance = 0;
    }

    private void startGliding(AbstractDog dog) {
        dog.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), PILLOW_PAW_BOOST_ID, this::createSpeedModifier);
    }

    private void stopGliding(AbstractDog dog) {
        dog.removeAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), PILLOW_PAW_BOOST_ID);
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, UUID uuidIn) {
        return new AttributeModifier(uuidIn, "Pillow Paw", -0.8, AttributeModifier.Operation.MULTIPLY_BASE);
    }
    
}
