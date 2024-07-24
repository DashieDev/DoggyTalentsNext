package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class DoggyDashTalent extends TalentInstance {

    private static final ResourceLocation DASH_BOOST_ID = Util.getResource("doggy_dash_boost");

    public DoggyDashTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dogIn) {
        dogIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, DASH_BOOST_ID, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractDog dogIn, int level) {
        dogIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, DASH_BOOST_ID, this::createSpeedModifier);
    }

    @Override
    public void remove(AbstractDog dogIn) {
        dogIn.removeAttributeModifier(Attributes.MOVEMENT_SPEED, DASH_BOOST_ID);
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, ResourceLocation uuidIn) {
        if (this.level() > 0) {
            double speed = 0.03D * this.level();

            if (this.level() >= 5) {
                speed += 0.04D;
            }

            return new AttributeModifier(uuidIn, speed, AttributeModifier.Operation.ADD_VALUE);
        }

        return null;
    }
}
