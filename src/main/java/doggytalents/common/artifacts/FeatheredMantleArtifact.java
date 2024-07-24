package doggytalents.common.artifacts;

import java.util.UUID;

import doggytalents.DoggyItems;
import doggytalents.DoggyTalents;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FeatheredMantleArtifact extends DoggyArtifact {

    private static final ResourceLocation PILLOW_PAW_BOOST_ID = Util.getResource("pillow_paw_boost");

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
        dog.setAttributeModifier(Attributes.GRAVITY, PILLOW_PAW_BOOST_ID, this::createSpeedModifier);
    }

    private void stopGliding(AbstractDog dog) {
        dog.removeAttributeModifier(Attributes.GRAVITY, PILLOW_PAW_BOOST_ID);
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, ResourceLocation uuidIn) {
        return new AttributeModifier(uuidIn, -0.8, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }
    
}
