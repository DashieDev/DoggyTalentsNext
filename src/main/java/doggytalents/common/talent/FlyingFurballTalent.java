package doggytalents.common.talent;

import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.entity.ai.nav.DogFlyingMoveControl;
import doggytalents.common.entity.ai.nav.DogFlyingNavigation;
import doggytalents.common.entity.anim.DogAnimation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class FlyingFurballTalent extends TalentInstance {

    private static UUID FLYING_FURBALL_BOOST_UUID = UUID.fromString("2a802049-97d0-4de5-934e-e24ed1d2ab9f");
    private static UUID FLYING_FURBALL_GRAVITY_UUID = UUID.fromString("76390e7e-e38d-4de5-8fce-83af09b03a3e");

    private DogFlyingMoveControl moveControl;
    private DogFlyingNavigation navigation;
    
    private boolean isFlying = false;
    
    public FlyingFurballTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractDog dog) {
        if (dog.level().isClientSide)
            return;
        if (!(dog instanceof Dog d))
            return;
        d.setAttributeModifier(Attributes.FLYING_SPEED, FLYING_FURBALL_BOOST_UUID, this::createSpeedModifier);
        this.moveControl = 
            new DogFlyingMoveControl(d, this);
        this.navigation = 
            new DogFlyingNavigation(d, dog.level());
        startGliding(dog);
    }

    @Override
    public void set(AbstractDog dogIn, int level) {
        dogIn.setAttributeModifier(Attributes.FLYING_SPEED, FLYING_FURBALL_BOOST_UUID, this::createSpeedModifier);
    }

    @Override
    public void remove(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        dogIn.removeAttributeModifier(Attributes.FLYING_SPEED, FLYING_FURBALL_BOOST_UUID);
        dogIn.resetMoveControl();
        dogIn.resetNavigation();
        dogIn.setDogFlying(false);
        stopGliding(dogIn);
        dogIn.setNoGravity(false);
        if (dogIn instanceof Dog d && d.getAnim() == DogAnimation.FLY_AIR_BOURNE)
            d.setAnim(DogAnimation.NONE);
    }

    @Override
    public void tick(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        if (!(dogIn instanceof Dog dog))
            return;

        if (dog.getNavigation() != navigation && willFly(dog)) {
            dog.setMoveControl(moveControl);
            dog.setNavigation(navigation);
        }

        if (dog.isDogFlying() && dog.getNavigation() != this.navigation) {
            dog.setDogFlying(false);
            dog.setNoGravity(false);
        }

        if (dog.isDogFlying() && !willFly(dog))  {
            dog.setDogFlying(false);
            dog.setNoGravity(false);
            if (dog.getAnim() == DogAnimation.FLY_AIR_BOURNE) {
                dog.setAnim(DogAnimation.FLY_LANDING);
            }
        }

        if (dog.isDogFlying() && dog.getAnim() == DogAnimation.NONE) {
            dog.setAnim(DogAnimation.FLY_AIR_BOURNE);
        }

        if (!dog.isDogFlying() && willFly(dog)) {
            dog.setDogFlying(true);
            if (dog.getAnim() != DogAnimation.FLY_AIR_BOURNE)
                dog.setAnim(DogAnimation.FLY_JUMP_START);
        }

        if (!dog.isDogFlying() && dog.getAnim() == DogAnimation.FLY_AIR_BOURNE) {
            dog.setAnim(DogAnimation.NONE);
        }

        dogIn.fallDistance = 0;
    }

    private boolean willFly(AbstractDog dog) {
        return !dog.onGround() && !dog.isInSittingPose() && !dog.isPassenger()
            && !dog.isInWater();
    }

    @Override
    public InteractionResult canFly(AbstractDog dog) {
        return InteractionResult.SUCCESS;
    }

    public AttributeModifier createSpeedModifier(AbstractDog dogIn, UUID uuidIn) {
        if (this.level() > 0) {
            double speed = (this.level() - 1)* 0.1;

            return new AttributeModifier(uuidIn, "Flying Furball", speed, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    public void startGliding(AbstractDog dog) {
        dog.setAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), FLYING_FURBALL_GRAVITY_UUID, this::createGravityModifier);
    }

    public void stopGliding(AbstractDog dog) {
        dog.removeAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), FLYING_FURBALL_GRAVITY_UUID);
    }

    public AttributeModifier createGravityModifier(AbstractDog dogIn, UUID uuidIn) {
        return new AttributeModifier(uuidIn, "Flying Furball Gravity", -0.8, AttributeModifier.Operation.MULTIPLY_BASE);
    }

}
